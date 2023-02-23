# 1. 二叉树

## 1.1 理论知识

### 概念

- 高度：从下往上，从1开始
- 深度：从上往下，从1开始



## 一些重要属性

- 在第i层（i从0开始），最多有2^i 个节点
- 一个深度为k的树，最多有2^k - 1个节点
- 一个有n个节点，树的高度将会为log(n + 1)
- 如果从 根节点开始为节点编号，对于完全二叉树：
  - 对于节点编号为k的节点，其左孩子编号为2k+1，右孩子编号为2k+2



## 1.2 二叉树遍历

### 前序遍历

父 -> 左 -> 右

[144. 二叉树的前序遍历](https://leetcode.cn/problems/binary-tree-preorder-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }

        result.add(root.val);
        preorderTraversal(root.left, result);
        preorderTraversal(root.right, result);
    }
}
```

### 中序遍历

左 -> 父 -> 右

[94. 二叉树的中序遍历](https://leetcode.cn/problems/binary-tree-inorder-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        preorderTraversal(root.left, result);
        result.add(root.val);
        preorderTraversal(root.right, result);
    }
}
```

### 后序遍历

左 -> 右 -> 父

[145. 二叉树的后序遍历](https://leetcode.cn/problems/binary-tree-postorder-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        preorderTraversal(root.left, result);
        preorderTraversal(root.right, result);
        result.add(root.val);
    }
}
```



## 1.3 二叉树非递归遍历

### 前序遍历

- 在访问完跟节点以后我们需要先访问左节点然后是右节点
- 用栈模拟递归
- 访问当前节点，右节点先入栈，然后左节点入栈
- Time:O(n) n为节点个数   Space: O(n)

[144. 二叉树的前序遍历](https://leetcode.cn/problems/binary-tree-preorder-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val); // 根节点

            // 因为栈先进后出的特点，要满足前序遍历，根左右的顺序，那么入栈的时候，先压入右，再压左
            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return result;
    }
}
```

用栈来模拟递归

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    // 用栈模拟递归，因为顺序是根左右，每次压栈的时候先压最左边的一排，然后栈现在保存的是最左边的叶子节点
    // 这时候出栈，找这个节点有没有右子节点，没有的话，继续弹栈，继续找上面一个节点又没有右子节点
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;

        while(!stack.isEmpty() || node != null) {
            
            // 先压入左边那一排，顺便收集跟节点进入result
            while (node != null) {
                result.add(node.val);
                stack.push(node);
                node = node.left;
            }

            // 现在弹出的是最左边的叶子节点，找这个节点有没有右节点
            node = stack.pop();
            node = node.right;
        }

        return result;
    }
}
```





