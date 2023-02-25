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



### 中序遍历

- 用栈模拟遍历
- 先要对左子树进行迭代
- 当前节点为空时进行出栈操作，将当前节点用右子节点替代

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
        if (root == null) {
            return result;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;

        while(!stack.isEmpty() || node != null) {
            
            // 先压入左边那一排
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            // 现在弹出的是最左边的叶子节点，找这个节点有没有右节点
            node = stack.pop();

            // 收集跟节点进入result，因为是左根右，最左边的叶子节点要最先进入结果集
            result.add(node.val);

            node = node.right;
        }

        return result;
    }
}
```

### 后序遍历

- 三种遍历中最难

- 双栈实现

- 代码需要背诵

  1. 创建两个栈s1，s2，将root压入s1中

  2. 当s1不为空，执行以下操作

     a. 弹出s1中的元素，并将该节点压入s2中

     b. 如果该节点的左子节点不为空，将左子节点压入s1中

     c. 如果该节点的右子节点不为空，将右子节点压入s1中

  3. 循环迭代s2，弹出s2的元素加入到结果集

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
    // 双栈。 s2栈存储真正的数据：这个在站内顺序是根右左，那么弹出来的时候就是左右根了
    // s1栈存储的时候 左 右 -》到s2（s2里面这时候已经有了根）里面就是右左
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 1. 创建两个栈s1，s2，将root压入s1中
        Deque<TreeNode> s1 = new ArrayDeque<>();
        Deque<TreeNode> s2 = new ArrayDeque<>();
        s1.push(root);

        // 2. 当s1不为空，执行以下操作
        while (!s1.isEmpty()) {
            // a. 弹出s1中的元素，并将该节点压入s2中
            TreeNode node = s1.pop();
            s2.push(node);

            // b. 如果该节点的左子节点不为空，将左子节点压入s1中
            if (node.left != null) {
                s1.push(node.left);
            }

            // c. 如果该节点的右子节点不为空，将右子节点压入s1中
            if (node.right != null) {
                s1.push(node.right);
            }
        }

        while (!s2.isEmpty()) {
            result.add(s2.pop().val);
        }
        return result;
    }
}
```

单栈

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
    class NodeWithFlag {
        public TreeNode node;
        public boolean isVisited;

        public NodeWithFlag(TreeNode node, boolean isVisited) {
            this.node = node;
            this.isVisited = isVisited;
        }
    }

    // 记住代码
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        Deque<NodeWithFlag> stack = new ArrayDeque<>();
        NodeWithFlag nodeWithFlag;
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            
            while (node != null) {
                stack.push(new NodeWithFlag(node, false));
                node = node.left;
            }

            nodeWithFlag = stack.pop();
            node = nodeWithFlag.node;
            if (nodeWithFlag.isVisited) {
                result.add(node.val);
                node = null;
            } else {
                nodeWithFlag.isVisited = true;
                stack.push(nodeWithFlag);
                node = node.right;
            }
        }

        return result;
    }
}
```

## 1.4 构造二叉树

结论： 需要两种不同的遍历结果来构造唯一确定的二叉树，并且其中一个必须是中序遍历。

口诀：

​	后序最末根节点

​	前序最前跟节点

​	中序划定左与右

### 前序/中序构造树

[105. 从前序与中序遍历序列构造二叉树](https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

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

    private int[] preorder;
    private int[] inorder;
    private Map<Integer, Integer> map;

    // preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) {
            return null;
        }
        
        this.preorder = preorder;
        this.inorder = inorder;

        // 中序遍历数组存入map，key：数组值，value：数组索引
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return helper(0, preorder.length - 1, 0, inorder.length - 1);
    }

    // 思路就是递归的构造根结点，把左子树和分开构造
    private TreeNode helper(int pStart, int pEnd, int iStart, int iEnd) {
        if (pStart > pEnd || iStart > iEnd) {
            return null;
        }

        // 1. 在前序数组里面寻找root节点
        TreeNode root = new TreeNode(preorder[pStart]);

        // 2. 利用中序数组划分左右子树
        // a. 找到中序数组中跟节点的索引
        int rootPos = map.get(root.val);

        // b.中序左子树：【iStart, rootPos - 1】 中序右子树： 【rootPos + 1, iEnd】
        // c.前序左子树： 【pStart + 1, pStart + 中序从开头到root的节点数量】 前序右子树： 【pStart + leftNodeCount + 1, pEnd】
        int leftNodeCount = rootPos - iStart;
        root.left = helper(pStart + 1, pStart + leftNodeCount, iStart, rootPos - 1);
        
        root.right = helper(pStart + leftNodeCount + 1, pEnd, rootPos + 1, iEnd);
        return root;
    }

}
```



### 后序/中序构造树

[106. 从中序与后序遍历序列构造二叉树](https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)

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
    private int[] inorder;
    private int[] postorder;
    private Map<Integer, Integer> map;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || inorder.length == 0 || postorder == null || postorder.length == 0) {
            return null;
        }

        this.inorder = inorder;
        this.postorder = postorder;

        // 中序遍历数组放入map，key：数组值 value：数组索引
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return helper(0, inorder.length - 1, 0, postorder.length - 1);
    }

    private TreeNode helper(int iStart, int iEnd, int pStart, int pEnd) {
        if (iStart > iEnd || pStart > pEnd) {
            return null;
        }

        // 从后序遍历中获得跟节点
        TreeNode root = new TreeNode(postorder[pEnd]);
        int rootPos = map.get(root.val);

        // 求左子树节点的个数
        int leftNodeCount = rootPos - iStart;

        // 中序左子树：【iStart, rootPos - 1】 中序右子树： 【rootPos + 1, iEnd】
        // 后序左子树：【pStart, pStart + leftNodeCount - 1】 后序右子树： 【pStart + leftNodeCount, pEnd】
        root.left = helper(iStart, rootPos - 1, pStart, pStart + leftNodeCount - 1);
        root.right = helper(rootPos + 1, iEnd, pStart + leftNodeCount, pEnd - 1); // 注意 pEnd已经作为跟节点了，所以构造右子树的时候应该把pEnd - 1
        return root;
    }
}
```



## 1.5 二叉搜索树（Binary Search Tree BST）

### 特性

1. 所有子树均为二叉搜索树
2. 任一左子树的全部节点的值均小于其根节点的值
3. 任一右子树的全部节点的值均大于其根节点的值
4. 中序遍历是递增数列



### 操作

二叉搜索树可以高效进行如下操作

1. 查找：find
2. 添加：add
3. 删除：delete

不论哪一种操作，时间复杂度均和树的高度有关。如果共有n个元素，平均操作需要O(logn)的时间