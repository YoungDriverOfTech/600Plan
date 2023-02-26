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



#### 搜索实战

[700. 二叉搜索树中的搜索](https://leetcode.cn/problems/search-in-a-binary-search-tree/)

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
    public TreeNode searchBST(TreeNode root, int val) {
        while (root != null) {
            if (root.val == val) {
                return root;
            } else if (root.val < val) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return null;
    }
}
```



#### 添加实战

[701. 二叉搜索树中的插入操作](https://leetcode.cn/problems/insert-into-a-binary-search-tree/)

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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
            return root;
        }

        TreeNode node = root;
        while (node != null) {
            if (node.val == val) {
                return root;
            } else if (node.val > val) {
                if (node.left != null) {
                    node = node.left;
                } else {
                    node.left = new TreeNode(val);
                    return root;
                }
            } else {
                if (node.right != null) {
                    node = node.right;
                } else {
                    node.right = new TreeNode(val);
                    return root;
                }
            }
        }
        return root;
    }
}
```



#### 删除实战

主要步骤：

1. 查找要删除节点
2. 若找到删除的节点n，删除它
3. 调整树结构，使删除节点n后的树仍为二叉搜索树



从BST删除一个节点分为三种情况

1. 节点n没有任何子树  ->  直接删掉这个节点
2. 节点n只有一个子树  ->  删掉n节点，把子节点提升到原来n的位置
3. 节点n有两个子树  -> 用n的前继/后继节点代替n，然后删掉原来前继/后继节点的位置（前继/后继节点是中序遍历中首个小于/大于n的节点）

[450. 删除二叉搜索树中的节点](https://leetcode.cn/problems/delete-node-in-a-bst/)

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
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (root.val < key) {
            // 比根节点大，那么就可能在树的右边
            root.right = deleteNode(root.right, key);
        } else if (root.val > key) {
            // 比根节点小，那么就可能在树的左边
            root.left = deleteNode(root.left, key);
        } else {
            // 等于根节点，分三种情况
            // 1 目标节点没有子树，那么直接删除目标节点即可
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.left != null && root.right == null) {    // 为了防止不必要麻烦，把怕断条件写全
                // 2 目标节点有单个子树，那么把子节点的位置挪到目标节点上面
                // 2.1 只有左子树，没有右子树
                root = root.left;
            } else if (root.left == null && root.right != null) {
                // 2.2 只有右子树，没有左子树
                root = root.right;
            } else {
                // 3 目标节点同时有左右子树，那么就找后继节点（右子树中序遍历的第一个节点）
                // 把root的值替换成后继节点的值
                TreeNode afterNode = findAfterNode(root.right);
                root.val = afterNode.val;

                // 再把后继节点给删掉，把删掉后的右子树在接到root的右指针上面
                root.right = deleteNode(root.right, afterNode.val);
            }
        }

        return root;
    }

    private TreeNode findAfterNode(TreeNode root) {
        if (root.left == null) {
            return root;    // 找后继节点，不能返回null
        }
        return findAfterNode(root.left);
    }
}
```





# 2. 深度优先DFS/广度优先DFS

## 2.1 概念

### 树的DFS

不撞南墙不回头，类似于前序遍历

### 树的BFS

以根节点为圆心画圆，类似于层序遍历

### 图的DFS

从某一点出发，一只走到底，然后往回走，知道遍历整个图

### 图的BFS

从一个根开始，以这个根为圆心，找邻居节点，完成以后，以邻居节点为圆心继续画圆，知道找完所有的节点



## 2.2 模板

### 一般图DFS模板

```java
public class DFS {
  class Point {
    int num;
    int value;
  }
  
  private boolean[] mared;
  private int count;
  public DFS(Graph G, Point start) {
    marked = new boolean[G.v()];
    dfs(G, start);
  }
  
  public void dfs(Graph G, Point v) {
    marked[v.num] = true;
    count++;
    for (Point w : G.adj(v)) {
      if (!marked[w.num]) {
        dfs(G, w);
      }
    }
  }
  
  public int count() {
    return count;
  }
}
```



### 二叉树dfs模板

```java
public void dfs(TreeNode node) {
  doSomething(node);
  dfs(node.left);
  dfs(node.right);
}
```



## 2.3 分治法（divide and conquer）

### 概念

- 分解（Divide）：将问题划分为一些子问题，子问题的形式与原问题一样，只是规模更小
- 解决（Conquer）：递归的求解出子问题。如果子问题规模足够小，则停止递归，直接求解。
- 合并（Combine）：将子问题的解组成成原问题的解

### 二叉树分治法模板

```java
public Result divideAndConquer(TreeNode root) {
  if (root == null) {
    // do
  }
  
  Result leftResult = divideAndConquer(root.left);
  Result rightResult = divideAndConquer(root.right);
  Result finalResult = combine(leftResult, rightResult);
  return finalResult;
}
```



## 2.4 实战

### 二叉树最大深度

#### 分治法

[104. 二叉树的最大深度](https://leetcode.cn/problems/maximum-depth-of-binary-tree/)

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
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftResult = maxDepth(root.left);
        int rightResult = maxDepth(root.right);

        int finalResult = Math.max(leftResult, rightResult) + 1;
        return finalResult;
    }
}
```

#### 遍历法

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
    private int depth;
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        depth = 1;
        helper(root, 1);
        return depth;
    }

    private void helper(TreeNode root, int currentDepth) {
        if (root == null) {
            return;
        }

        if (currentDepth > depth) {
            depth = currentDepth;
        }

        helper(root.left, currentDepth + 1);
        helper(root.right, currentDepth + 1);
    }
}
```



### 二叉树最小深度

#### 分治法

[111. 二叉树的最小深度](https://leetcode.cn/problems/minimum-depth-of-binary-tree/)

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
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftResult = minDepth(root.left);
        int rightResult = minDepth(root.right);

        // 如果有特殊case，比如二叉树变成了一个链表，那么最短距离是链表的长度，而不能是0，所以要对这种case做特殊处理
        if (leftResult == 0) {
            return rightResult + 1;
        } else if (rightResult == 0) {
            return leftResult + 1;
        } else {
            return Math.min(leftResult, rightResult) + 1;
        }
    }
}
```

#### 遍历法

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
    private int minDepth = Integer.MAX_VALUE;
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        helper(root, 1);
        return minDepth;
    }

    private void helper(TreeNode root, int currentDepth) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null && currentDepth < minDepth) {
            minDepth = currentDepth;
        }

        helper(root.left, currentDepth + 1);
        helper(root.right, currentDepth + 1);
    }
}
```



### 反转二叉树

[226. 翻转二叉树](https://leetcode.cn/problems/invert-binary-tree/)

#### 分治法

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
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }

        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;

        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
```



#### 遍历法

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
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }

        helper(root);
        return root;
    }

    private void helper(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        helper(root.left);
        helper(root.right);
    }
}
```



### 对陈二叉树

[101. 对称二叉树](https://leetcode.cn/problems/symmetric-tree/)

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
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return helper(root.left, root.right);
    }

    private boolean helper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null || left.val != right.val) {
            return false;
        }

        return helper(left.left, right.right) && helper(left.right, right.left);
    }
}
```



### 平衡二叉树

[110. 平衡二叉树](https://leetcode.cn/problems/balanced-binary-tree/)

#### 基本解法

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
    // 题目要求：每个节点的左右子树的高度差绝对值不超过1 -> 每个左右子节点必须也是平衡的
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        int leftHeight = getLen(root.left);
        int rightHeight = getLen(root.right);

        // 判断对于根节点的子节点高度差是不是小于等于1
        boolean heightGapFlag = Math.abs(leftHeight - rightHeight) <= 1;

        // 递归的判断子节点是不是也是平衡的
        boolean isLeftBalanced = isBalanced(root.left);
        boolean isRightBalanced = isBalanced(root.right);

        return heightGapFlag && isLeftBalanced && isRightBalanced;
    }

    private int getLen(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getLen(node.left);
        int rightHeight = getLen(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```



#### 优化解法

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
    public boolean isBalanced(TreeNode root) {
        return getLen(root) != -1;
    }

    private int getLen(TreeNode node) {
        if (node == null) {
            return 0;
        }
        // 递归的计算高度，从叶子节点开始往根节点计算。(上一种方法计算高度的时候，有很多重复的计算，这次从底部开始算，省掉了重复计算)
        int leftHeight = getLen(node.left);
        int rightHeight = getLen(node.right);

        // 只要有某个节点的子树高度差大于1了，那么直接返回-1
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```



### 路径总合

[112. 路径总和](https://leetcode.cn/problems/path-sum/)

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
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        boolean  leftResult = hasPathSum(root.left, targetSum - root.val);
        boolean  rightResult = hasPathSum(root.right, targetSum - root.val);
        return leftResult || rightResult;
    }
}
```



### 二叉树的最近公共祖先

[236. 二叉树的最近公共祖先](https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/)

#### 分治法

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    // 有三种情况
    // 1 p q位于root左子树侧 -> 左子树
    // 2 p q位于root有子树侧 -> 右子树
    // 3 p q位于root不同子树侧 -> 根节点
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }

        TreeNode leftResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right, p, q);

        if (leftResult != null && rightResult != null) {
            return root; // 情况3
        } else if (leftResult == null || rightResult == null) {
            return leftResult == null ? rightResult : leftResult; // 情况1 2
        } else {
            return null;
        }
    }
}
```

