# Week 1
[Basic Math Knowledge](./week1/basicMath.md)   
[Time Complexity](./week1/timeComplexity.md)   
[Week-1](./week1/week-1.md) 

## 前缀和公式
```java
// 构造前缀和
int[] prefixSum = new int[num.length + 1];
for (int i = 0; i < num.length; i++) {
    prefixSum[i + 1] = prefixSum[i] + num[i];
}

// 获取某一个元素
nums[i] = prefixSum[i + 1] - prefixSum[i]

// 获取某一段的和
interval[i, j] = prefixSum[j + 1] - prefixSum[i]
```


# Week 2
[Week-2](./week2/week-2.md) 

## 单调栈
> 大单减小单增，遍历方向题目中 (大单减[小于<]小单增[大于>]，指的是找下一个更大/小的元素)  
> 大减小小增大
### 递减栈
```java
// 单调递减栈 bottom -> top
Deque<Integer> stack = new ArrayDeque<>();
for (int i = 0; i < nums.length; i++) {
    while (!stack.isEmpty() && stack.peek() < nums[i]) {
        stack.pop();
    }
    stack.push(nums[i]);
}
```

### 递增栈
```java
// 单调递增栈 bottom -> top
Deque<Integer> stack = new ArrayDeque<>();
for (int i = 0; i < nums.length; i++) {
    while (!stack.isEmpty() && stack.peek() > nums[i]) {
        stack.pop();
    }
    stack.push(nums[i]);
}
```

### 二分查找模板

```java
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        // 不加等于号是为了让循环退出时候left和right重合
        while (left < right) {
            int mid = (left + right) / 2;

            if (nums[mid] < target) {
                // 如果nums[mid] < target, 说明mid之前的所有元素都比target小
                // 那么下次搜索的范围就是【mid + 1, right】
                left = mid + 1;
            } else {
                // 如果nums[mid] >= target, 说明mid之后的所有元素都比target大
                // 那么下次搜索的范围就是【left, mid】
                right = mid;
            }
        }

        // 如果target不在【left,right】中，需要在做一次判断
        return left;
    }
```

```java
    public int binarySearch1(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        // 不加等于号是为了让循环退出时候left和right重合
        while (left < right) {
            int mid = (left + right + 1) / 2;

            if (nums[mid] > target) {
                // 如果nums[mid] > target，说明mid以后的元素都大于target
                // 那么下轮搜索范围就是【left, mid - 1】
                right = mid - 1;
            } else {
                // 如果nums[mid] <= target，说明mid以前的元素都小于target
                // 那么下轮搜索范围就是【mid, right】
                left = mid;   // 因为这块可能出现死循环，所以算mid的时候就要使用+1的办法
            }
        }

        // 如果target不在【left,right】中，需要在做一次判断
        return left;
    }
```

### 一/二维坐标转换
```java
矩阵中行列计算，坐标转换计算技巧：

行数：m

列数：n

二维坐标转换到一维：row * n + col (row, col是这个元素的第row行，第col列)

一维坐标转换到二维：x = index / n, y = index % n
```


# Week 3

[Week-3](./week3/week-3.md) 

### 快慢指针求中点模板
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    private ListNode findMid(ListNode head) {
        // 1 1 1 1 1
        //     ↑
        // 1 1 1 1 1 1
        //     ↑
        // 奇数/偶数的时候，中点的位置。找中点的时候先让fast走一步，这样就能达成上面的效果
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
        
        // 如果是偶数个节点，那么中间节点会是这个前面的那个，如果想返回后面的那个中间节点，参照这个
        // fast == null : 奇数个节点 直接返回slow
        // fast != null : 偶数个节点 需要返回slow.next
        // 1 1 1 1 1 1
        //       ↑
        // return fast == null ? slow : slow.next;
    }
}
```

### 快慢指针求链表是否有环模板
```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    // 快慢指针，快的会追上慢的
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            // 判断放在移动指针后面，因为首次进入while的时候，快慢都指向了head，会直接判断成功
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
```

### 链表删除重复节点模板
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // 因为头节点也可能重复，那么只保留下头节点就可以了。现在明白的头节点不发生变化，那么不用dummyNode就行了
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 去除重复元素，即让node.next = node.next.next;
        ListNode node = head;
        while (node != null && node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
    }
}
```

### 反转链表模板
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            // 把cur的下一个节点保存起来，当作temp。 然后修改cur的下一个节点的指针到pre
            // 把pre挪到cur的位置，cur挪到temp的位置
            ListNode temp = cur.next;
            cur.next = pre;

            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
```

### 反转链表--头插法
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // 因为反转链表，所以链表结构改变，头节点有可能被反转->dummyNode
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode(-1, head);

        // 先找到pre节点的位置，因为left从1开始，但是索引是从0开始，所以遍历的时候需要left-1
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        ListNode curNode = pre.next;

        // 使用头插法 1 2 3 4 -> 1 3 2 4 -> 1 4 3 2, cur节点是2，每次都把cur后面的元素翻到pre前
        
        for (int i = left; i < right; i++) {
            ListNode temp = curNode.next; // temp将来是要放到pre的后面的。

            curNode.next = temp.next;   // 当前节点应该链接temp后面的那个
            temp.next = pre.next;   // 因为temp将来是反转后排头儿的，那temp.next = cur(pre.next)
            pre.next = temp;    // tempNode就是要被放到pre.next，
        }
        return dummyNode.next;
    }
}
```

### k个一组 旋转链表
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode node = dummyNode;
        int k = 2;
        // 1 遍历链表
        while (node.next != null) {
            ListNode pre = node;
            ListNode check = node;

            // 2 检查够不够k个元素，不够的话直接返回
            for (int i = 0; i < k; i++) {
                if (check.next == null) {
                    return dummyNode.next;
                }
                check = check.next;
            }

            // 3 链表节点够k个的话，开始反转. 3个节点反转2次，2个反转一次，所以反转次数是k-1
            ListNode cur = pre.next;
            for (int i = 0; i < k - 1; i++) {
                ListNode temp = cur.next;

                cur.next = temp.next;
                temp.next = pre.next;
                pre.next = temp;
            }

            // 反转完成后，cur节点会移动到反转后的最后一个位置，比如1 2 3 反转饭后会变成 3 2 1. cur的指针一直是在1上
            // 那么为了下一次反转，应该吧node（pre）指针给知道现在的cur上面
            node = cur;
        }
        return dummyNode.next;
    }
}
```

# Week 4

[Week-4](./week4/week-4.md) 

### 非递归二叉树前序遍历
- 在访问完跟节点以后我们需要先访问左节点然后是右节点
- 用栈模拟递归
- 访问当前节点，右节点先入栈，然后左节点入栈
- Time:O(n) n为节点个数 Space: O(n)


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

### 二叉树非递归中序遍历  

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


### 二叉树非递归后序遍历  
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

### 删除二叉树节点
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

### 路径总和
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

# Week 5

[Week-5](./week5/week-5.md) 

### 回溯模板
```java
public List<List<Integer>> solveProblem(int[] nums) {
    // result list
    List<List<Integer>> result = new ArrayList<>();

    // check empty
    if (nums == null || nums.length == 0) {
        return result;
    }

    // single answer
    List<Integer> list = new ArrayList<>();

    // sort nums if necessary
    // Arrays.sort(nums);

    helper(result, list, nums, ?);

    return result
}

private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int pos, ?){
    // end recursion condition
    if (condition xx) {
        return;
    }

    // add single answer into result
    if (condition xx) {
        result.add(new ArrayList(list));
    }

    // 剪枝
    if (condition xx) {
        // doSomething
        return;
    }

    // 递归拆解子问题到下一层
    for (sub : total-subs) {
        if (condition xx) {
            // 剪枝
            break; // or continue;
        }
        list.add(element);
        helper(result, list, nums, ?);
        // 回溯
        list.remove(element);
    }
}
```

### 全排列
```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[nums.length]; // 访问过就是true
        Arrays.sort(nums);
        
        helper(result, list, nums, visited);

        return result;
    }

    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, boolean[] visited) {
        if (list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // 【1，1，1】： 到底是已经访问过了
            // 【1(1), 1(2), 2】【1(2), 1(1), 2】重复元素. 第一轮的时候会把第一个list装进result，然后第一个1(1)在被
            // 重制为false。 第二轮的时候，发现如果元素相等，并且前一个没被访问过，说明前一轮被重置成false，那么应该跳过当前元素
            if (visited[i] || (i != 0 && nums[i] == nums[i - 1] && !visited[i - 1])) {
                // 如果有重复元素，选择靠前的，后面的全部都是重复.!nums[i - 1]条件保证了选择靠前的
                // 如果回溯到了靠后的相同元素，会执行continue来跳过。
                continue;
            }
            
            list.add(nums[i]);
            visited[i] = true;
            helper(result, list, nums, visited);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }
}
```
### 复原IP地址
```java
class Solution {
    // 1. 0 《= 行《= 255
    // 2. 放0的时候，只能放单个0，不能0后面再跟着别的数字
    // 3. x只能是整数
    // 4. 只能有4段
    // 5. s的长度是[4, 12]
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        // 单一解
        List<String> list = new ArrayList<>();

        if (s.length() < 4 || s.length() > 12) {
            return result;
        }

        helper(result, list, s, 0);
        return result;
    }

    private void helper(List<String> result, List<String> list, String s, int pos) {
        // 递归什么时候退出？

        // 单一解何时加入result & 剪枝
        if (list.size() == 4) {
            // s字符串有甚于的字符不行，剪枝
            if (pos != s.length()) {
                return;
            }

            StringBuilder sb = new StringBuilder();
            String ip = sb.append(list.get(0)).append(".")
                          .append(list.get(1)).append(".")
                          .append(list.get(2)).append(".")
                          .append(list.get(3)).toString();
            result.add(ip);
        }

        // 递归到下一层
        for (int i = pos; i < s.length() && i < pos + 3; i++) { // i < pos + 3 因为截取字符串的时候到不了最后
            String ipPartNum = s.substring(pos, i + 1); // 0-255, len最多是3
            if (isValid(ipPartNum)) {
                list.add(ipPartNum);
                helper(result, list, s, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    private boolean isValid(String ipPartNum) {
        // 开头是0的，只有一种可能：0
        if (ipPartNum.charAt(0) == '0') {
            return ipPartNum.equals("0");
        }
        
        int num = Integer.valueOf(ipPartNum);
        return num >= 0 && num <= 255;
    }
}
```

### 生成括号
```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        // 单一解
        char[] ch = new char[n * 2];
        helper(result, ch, n, n, 0);
        return result;
    }

    private void helper(List<String> result, char[] ch, int leftRemain, int rightRemain, int index) {
        // 什么时候退出递归
        if (ch.length == index) {
            result.add(new String(ch));
            return;
        }

        // 什么时候填入左括号。只要左括号有剩余，就能填进去
        if (leftRemain > 0) {
            ch[index] = '(';
            helper(result, ch, leftRemain - 1, rightRemain, index + 1);
            // 这里面不能把ch[index] 重制回去，因为下面右边括号的逻辑需要左边括号是已经被填充完的
            // 不然会出现这样的情况 null null null ) ) )
        }

        // 什么时候填入右括号。只有右括号剩余数量>左括号剩余数量才行。（等于也不行，等于的话优先填入左括号）
        if (rightRemain > leftRemain) {
            ch[index] = ')';
            helper(result, ch, leftRemain, rightRemain - 1, index + 1);
        }
    }
}
```

### 二叉树的所有路径
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
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result =new ArrayList<>();
        if (root == null) {
            return result;
        }

        // single answer
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(root.val));
        helper(result, list, root);

        return result;
    }

    private void helper(List<String> result, List<String> list, TreeNode root) {
        // 什么以后退出递归
        if (root == null) {
            return;
        }

        // 什么时候吧单一解加入到result
        if (root.left == null && root.right == null) {
            result.add(String.join("->", list));
            return;
        }

        // 回溯
        if (root.left != null) {
            list.add(String.valueOf(root.left.val));
            helper(result, list, root.left);
            list.remove(list.size() - 1);
        }

        if (root.right != null) {
            list.add(String.valueOf(root.right.val));
            helper(result, list, root.right);
            list.remove(list.size() - 1);
        }
    }
}
```

# Week 6

[Week-6](./week6/week-6.md) 

### 邻接表数量
```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        // count：做了几次DFS
        int count = 0;

        // step 0: 构建邻接表
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // 因为要构造成无向图，所以节点都需要双连
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // step 1: 套模板
        boolean[] marked = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 只有没有被标记过，才能进行dfs
            if (!marked[i]) {
                dfs(adj, marked, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(Map<Integer, List<Integer>> adj, boolean[] marked, int nodeNum) {
        // 首先标记节点
        marked[nodeNum] = true;

        // dfs
        for (int node : adj.get(nodeNum)) {
            if (!marked[node]) {
                dfs(adj, marked, node);
            }
        }
    }
}
```

### 图克隆
```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {

    // 用Map<Old, New>来做映射，然后神拷贝每个节点，最后返回即可
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        return dfs(map, node);
    }

    private Node dfs(Map<Node, Node> map, Node node) {
        if (map.containsKey(node)) {
            return map.get(node);
        }

        // map中不包含的话，就需要进行神拷贝了
        Node newNode = new Node(node.val); // 邻接的list回自动被创建出来
        map.put(node, newNode);

        for (Node neighbor : node.neighbors) {
            // 需要对每一个邻接的节点进行神拷贝，然后加到newNode的邻接list中
            Node newNeighbor = dfs(map, neighbor);
            newNode.neighbors.add(newNeighbor);
        }
        return newNode;
    }
}
```

### 二维数组dfs模板
```java
class Solution {
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        // 因为可以从任意位置开始，所以需要遍历。但是可以剪枝：遍历到的字符和word的首字符要相等才行
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0) && !visited[i][j]) {
                    boolean result = dfs(board, visited, i, j, word, 0);
                    // 如果已经存在一个答案了，直接返回
                    if (result) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, boolean[][] visited, int x, int y, String word, int index) {
        // 剪枝
        if (board[x][y] != word.charAt(index)) {
            return false;
        }

        // 标记
        visited[x][y] = true;

        // 已经找到全部的字符
        // 这里只需要到-1就行了，因为最上面的剪枝条件已经判断了最后一个字符的相等
        if (index == word.length() - 1) {
            return true;
        }

        // dfs
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (checkRange(board, newX, newY) && !visited[newX][newY]) {
                result = result || dfs(board, visited, newX, newY, word, index + 1);
            }
        }

        // 重置标记，因为回溯
        visited[x][y] = false;
        return result;
    }

    private boolean checkRange(char[][] board, int x, int y) {
        return x >= 0
            && x < board.length
            && y >= 0
            && y < board[0].length;
    }
}
```

### 被围绕区域
```java
class Solution {

    // 先求出边界的O的联通分量的节点，全部换成B
    // 然后遍历矩阵，O换成X，B换成O即可

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isBorder(board, i, j) && board[i][j] == 'O' && !visited[i][j]) {
                    dfs(board, visited, i, j);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'B') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, boolean[][] visited, int x, int y) {
        if (board[x][y] == 'X') {
            return;
        }

        visited[x][y] = true;
        board[x][y] = 'B';

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (checkRange(board, newX, newY) && !visited[newX][newY]) {
                dfs(board, visited, newX, newY);
            }
        }
    }

    private boolean isBorder(char[][] board, int x, int y) {
        return x == 0 || x == board.length - 1 || y == 0 || y == board[0].length - 1;
    }

    private boolean checkRange(char[][] board, int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }
}
```

### 连通分量
**计算连通分量的时候，可以使用DFS or BFS。简单来说DFS就是递归，而BFS就是使用队列**
#### DFS
```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        // count：做了几次DFS
        int count = 0;

        // step 0: 构建邻接表
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // 因为要构造成无向图，所以节点都需要双连
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // step 1: 套模板
        boolean[] marked = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 只有没有被标记过，才能进行dfs
            if (!marked[i]) {
                dfs(adj, marked, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(Map<Integer, List<Integer>> adj, boolean[] marked, int nodeNum) {
        // 首先标记节点
        marked[nodeNum] = true;

        // dfs
        for (int node : adj.get(nodeNum)) {
            if (!marked[node]) {
                dfs(adj, marked, node);
            }
        }
    }
}
```

#### BFS
```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        // count：做了几次DFS
        int count = 0;

        // step 0: 构建邻接表
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // 因为要构造成无向图，所以节点都需要双连
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // step 1: 套模板
        boolean[] marked = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 只有没有被标记过，才能进行dfs
            if (!marked[i]) {
                bfs(adj, marked, i);
                count++;
            }
        }
        return count;
    }

    private void bfs(Map<Integer, List<Integer>> adj, boolean[] marked, int nodeNum) {
      Queue<Integer> queue = new LinkedList<>();
      queue.offer(nodeNum);
      marked[nodeNum] = true;
      
      while (!queue.isEmpty()) {
        Integer curNode = queue.poll();
        
        for (Integer adjNode : adj.get(curNode)) {
          if (!marked[aadjNode]) {
            marked[adjNode] = true;
            queue.offer(adjNode);
          }
        }
      }
    }
}
```

### BFS遍历层数
```java
class Solution {

    // 利用广度优先遍历，去wordList找出startWord，然后变换每一个字符，找到了就加入队列，然后就是按着这个单词
    // 画圆，在找下一层的单词，直到找到endword。那么返回层数就可以了
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 构造字典
        Set<String> dict = new HashSet<>(wordList);
        if (beginWord.equals(endWord)) {
            return 0;
        }

        // 构造BFS的队列以及visited标记容器
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
      	visited.add(beginWord);
        int level = 1;  // 为什么要初始化为1，因为首个单词已经进入了队列

        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();    // 因为要求的是层数，所以需要用for循环来从queue中去单词

            for (int i = 0; i < size; i++) {
                // 如果是进入的第一个单词，则不用判断，因为上面最开始的部分已经进行判断了，只需要判断邻接节点
                // 是不是和end相等即可
                String curWord = queue.poll();

                // 取到当前单词的邻接单词list（就是以当前单词画圆）
                List<String> adjWords = getAdjWords(curWord, dict);
                for (String adjWord : adjWords) {
                    // 邻接节点只有没有被访问过，才能进行入队
                    if (!visited.contains(adjWord)) {
                        if (adjWord.equals(endWord)) {
                            return level;
                        }

                        queue.offer(adjWord);
                        visited.add(adjWord);
                    }
                }
            }
        }
        return 0;
    }

    private List<String> getAdjWords(String curWord, Set<String> dict) {
        List<String> result = new ArrayList<>();

        // 从a到z依次替换curWord的每个字符，去查看在字典中存在不存在，存在的话加入到结果集
        for (int i = 0; i < curWord.length(); i++) {
            int ch = curWord.charAt(i);

            // 如果遍历到的字符和ch相等的话就跳过
            for (char j = 'a'; j <= 'z'; j++) {
                if (j == ch) {
                    continue;
                }

                String replaceddWord = replacedWord(curWord, i, j);
                if (dict.contains(replaceddWord)) {
                    result.add(replaceddWord);
                }
            }
        }
        return result;
    }

    private String replacedWord(String curWord, int index, char j) {
        char[] curWordArr = curWord.toCharArray();
        curWordArr[index] = j;
        return new String(curWordArr);
    }
}
```

# Week 8

[Week-8](./week8/week-8.md) 

### 归并排序
```java
public class Main {
    public void sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        mergeSort(nums, 0, nums.length - 1);
    }

    public void mergeSort(int[] nums, int start, int end) {
        if (start < end) {
            int mid = start + (end - start) / 2;
            mergeSort(nums, start, mid);
            mergeSort(nums, mid + 1, end);
            merge(nums, start, mid, end);
        }
    }

    private void merge(int[] nums, int start, int mid, int end) {
        int leftLength = mid - start + 1;
        int rightLength = end - mid;

        int[] left = new int[leftLength]; // 存[start, ..., mid]
        int[] right = new int[rightLength]; // 存[mid + 1, ..., end]

        for (int i = 0; i < leftLength; i++) {
            left[i] = nums[start + i];
        }
        for (int j = 0; j < rightLength; j++) {
            right[j] = nums[mid + 1 + j];
        }

        int index = start;
        int i = 0;
        int j = 0;
        
        // merge two sorted array
        while (i < leftLength && j < rightLength) {
            if (left[i] < right[j]) {
                nums[index++] = left[i++];
            } else {
                nums[index++] = right[j++];
            }
        }
        
        while (i < leftLength) {
            nums[index++] = left[i++];
        }
        while (j <rightLength) {
            nums[index++] = right[j++];
        }
    }
}
```

### 快速排序
```java
package sortTemplate;

import java.util.Arrays;

public class QuickSort{

    public void sort(int[] sourceArray) throws Exception {
        quickSort(sourceArray, 0, sourceArray.length - 1);
    }

    private void quickSort(int[] arr, int left, int right) {
        // 如果只剩下一个元素，那么就不需要排序了
        if (left >= right) {
            return;
        }

        int pivotIndex = partition(arr, left, right);
        quickSort(arr, left, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        // 挑选最右边的元素作为基准值
        int pivot = arr[right];

        // 从左到右的遍历整个数组，日过比基准值小，那么就和storeIndex交换
        int storeIndex = left;
        for (int i = left; i < right; i++) {    // 这里i < right 不应该有等于号，因为最右边的元素被当成比较标准元素，最后会进行交换，所以不能写等号
            if (arr[i] <= pivot) {
                swap(arr, i, storeIndex);
                storeIndex += 1;    // 这个的左边，一直存放的小于pivot的值，所以一旦发生了交换，这个索引就需要往右移动
            }
        }

        // 最后storeIndex存放的是大于pivot的值，所以要和pivotIndex进行再一次交换
        // 因为一开始的时候，把pivot丢到了最右边，所以和最右边交换即可
        swap(arr, storeIndex, right);

        return storeIndex;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

### 链表插入排序
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // 思路：把dummy和head看成两个链表来处理，依次拿出head里面的每个元素和dummy链表的每一个元素从左到右左比较
    // 只要发现了第一个在dummy链表中的大于head的节点。那么就把head这个节点放到dummy链表中大节点（第一个大于head）的前面，然后连起来链表
    // 然后继续判断head链表的下一个节点
    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode dummyNode = new ListNode(-1);
        while (head != null) {
            ListNode node = dummyNode;

            // 从左往右找到node里面第一个大于head.val的节点，那么把head节点加到这个大节点的前面
            while (node.next != null && node.next.val < head.val) {
                node = node.next;
            }

            // 找到node.next.val > head.val 那么就把node塞到head的后面
            ListNode temp = head.next;
            head.next = node.next;
            node.next = head;

            // 现在已经把head接到node后面了，那么应该就绪便利head后面的节点
            head = temp;
        }

        return dummyNode.next;
    }
}
```

### 逆序对
```java
class Solution {
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int start, int end) {
        if (start < end) {
            int mid = start + (end - start) / 2;
            int leftCount = mergeSort(nums, start, mid);
            int rightCount = mergeSort(nums, mid + 1, end);

            // 逆序对的结果就是左边的逆序对+右边+左右merge时候产生的逆序对
            return leftCount + rightCount + merge(nums, start, mid, end);
        }
        
        // 如果排序到了最后一个数字，那么就不可能存在逆序对，直接返回0
        return 0;
    }

    private int merge(int[] nums, int start, int mid, int end) {
        // 我什么要加1，假如总长度5，那么mid - start = 2，end - mid = 2，还少一个，把多的这个放到左边
        int leftLength = mid - start + 1;
        int rightLength = end - mid;

        // new 出帮助的数组
        int[] left = new int[leftLength];
        int[] right = new int[rightLength];

        for (int i = 0; i < leftLength; i++) {
            left[i] = nums[start + i];
        }
        for (int j = 0; j < rightLength; j++) {
            right[j] = nums[mid + 1 + j]; // 因为mid给了左边了
        }

        int i = 0;
        int j = 0;
        int index = start;
        int pairs = 0;

        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                // 左边小于等于右边 不能存在逆序对
                nums[index++] = left[i++];
            } else {
                nums[index++] = right[j++];
                // 左边大于右边，那么左边当前元素后面的元素，都会大于right[j]，所以逆序对的个数是leftLength - i
                pairs += leftLength - i;
            }
        }

        while (i < leftLength) {
            nums[index++] = left[i++];
        }
        while (j < rightLength) {
            nums[index++] = right[j++];
        }
        return pairs;
    }
}

```

### 翻转对
```java
class Solution {
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int start, int end) {
        if (start < end) {
            int mid = start + (end - start) / 2;
            int leftCount = mergeSort(nums, start, mid);
            int rightCount = mergeSort(nums, mid + 1, end);

            // 逆序对的结果就是左边的逆序对+右边+左右merge时候产生的逆序对
            return leftCount + rightCount + merge(nums, start, mid, end);
        }
        
        // 如果排序到了最后一个数字，那么就不可能存在逆序对，直接返回0
        return 0;
    }

    private int merge(int[] nums, int start, int mid, int end) {
        // 我什么要加1，假如总长度5，那么mid - start = 2，end - mid = 2，还少一个，把多的这个放到左边
        int leftLength = mid - start + 1;
        int rightLength = end - mid;

        // new 出帮助的数组
        int[] left = new int[leftLength];
        int[] right = new int[rightLength];

        for (int i = 0; i < leftLength; i++) {
            left[i] = nums[start + i];
        }
        for (int j = 0; j < rightLength; j++) {
            right[j] = nums[mid + 1 + j]; // 因为mid给了左边了
        }

        // 统计【start，end】之间的反转对个数，注意：左右两个区间一定是有序的，因为已经递归的排序过了
        // 注意：计算翻转对和排序的逻辑需要分开算。因为if的条件是不一样的。
        // 翻转对：需要check的是nums[i] > 2 * nums[j] 
        // 排序：需要check nums[i] <= nums[j]
        // 如果在排序的时候进行翻转对的判断，因为条件不一样，所以结果一定会有些偏差
        int i = start;
        int j = mid + 1;
        int pairs = 0;
        while (i <= mid && j <= end) {
            if (nums[i] > (long) nums[j] * 2) {
                j++; // 这时不能i++，因为i也许比j+1以后两倍的值还要大
                pairs += mid - i + 1;
            } else {
                i++;
            }
        }

        i = 0;
        j = 0;
        int index = start;
        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                nums[index++] = left[i++];
            } else {
                nums[index++] = right[j++];
            }
        }

        while (i < leftLength) {
            nums[index++] = left[i++];
        }
        while (j < rightLength) {
            nums[index++] = right[j++];
        }
        return pairs;
    }
}
```

### 字母异位词
```java
class Solution {

    // 字符相减可以计算出来一个ascii码值，这个值可以当作索引
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != s.length()) {
            return false;
        }

        int[] indexArr = new int[26];
        // 统计s里面每个字符出现的次数
        for (int i = 0; i < s.length(); i++) {
            indexArr[s.charAt(i) - 'a'] += 1;
        }

        // 计算t里面出现的次数，如果出现负值，说明t里面有字符是多余s的，不满足条件
        for (int i = 0; i < t.length(); i++) {
            indexArr[t.charAt(i) - 'a'] -= 1;

            if (indexArr[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }

        // 如果s里面的字符有多余t的字符，也不满足
        for (int i = 0; i < indexArr.length; i++) {
            if (indexArr[i] != 0) {
                return false;
            }
        }

        return true;
    }
}
```

### LRU
```java
class LRUCache {
    class DoubleLinkedList {
        int key;
        int value;
        DoubleLinkedList prev;
        DoubleLinkedList next;

        public DoubleLinkedList(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, DoubleLinkedList> map;
    private DoubleLinkedList dummyHead;
    private DoubleLinkedList dummyTail;
    private int capacity;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;

        this.dummyHead = new DoubleLinkedList(-1, -1);
        this.dummyTail = new DoubleLinkedList(-1, -1);
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }
    
    // 最少被永用过的放在链表最尾部，最新被用过的放在最前部
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        // curNode 要从curNode的位置上拿出来
        DoubleLinkedList curNode = map.get(key);
        DoubleLinkedList nextNode = curNode.next;
        DoubleLinkedList prevNode = curNode.prev;
        System.out.println(key);
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        curNode.next = null;
        curNode.prev = null;

        // 放到链表的第一个位置上面
        DoubleLinkedList originHead = dummyHead.next;
        curNode.next = originHead;
        originHead.prev = curNode;

        // 链接dummyHead和current节点
        dummyHead.next = curNode;
        curNode.prev = dummyHead;

        return curNode.value;
    }
    
    public void put(int key, int value) {
        // 存在的话，不用考虑capacity，直接覆盖值就行
        if (get(key) != -1) {
            map.get(key).value = value;
            return;
        }

        // 如果不存在，需要判断是不是已经到达capcacity，来进行删除最老元素
        DoubleLinkedList newNode = new DoubleLinkedList(key, value);
        if (map.size() == capacity) {
            // 如果已经到了capacity，需要移除最后一个元素
            DoubleLinkedList originTail = dummyTail.prev;
            DoubleLinkedList prevTail = originTail.prev;

            prevTail.next = dummyTail;
            dummyTail.prev = prevTail;
            originTail.next = null;
            originTail.prev = null;

            // map里面也需要进行移除原来的最后一个元素
            map.remove(originTail.key);
        }

        // 剩下的不管有没有到capcacity，都需要把新节点放到head，直接放节点到map, 并且链接dummyHead节点
        map.put(key, newNode);

        // 放到链表的第一个位置上面
        DoubleLinkedList originHead = dummyHead.next;
        newNode.next = originHead;
        originHead.prev = newNode;

        // 链接dummyHead和current节点
        dummyHead.next = newNode;
        newNode.prev = dummyHead;
    }
}
```

# Week 9

[Week-9](./week9/week-9.md) 

### 最接近的3数之和
```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target) {
                    return sum;
                }

                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }

                if (sum > target) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return result;
    }
}
```

### 有效三角形个数
```java
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);

        int result = 0;
        for (int i = nums.length - 1; i >= 2; i--) {
            int start = 0;
            int end = i - 1;

            while (start < end) {
                int sum = nums[start] + nums[end];
                if (sum > nums[i]) {
                    // 因为已经排序过了，所以start后面的一定都满足条件. 这里为什么不end - start + 1 呢？
                    // nums = [2,2,3,4] 如果end = 4. start = 2(first). end = 3 -> 因为要选出的是组合数量。因为3和4已经是固定的了，那么就看满足条件的start有几个。现在只有2(first / second)满足。
                    // 如果+1的话，那把end也给算上了，不符合题意
                    result += end - start;
                    end--;
                } else {
                    start++;
                }
            }
        }
        return result;
    }
}
```

### 接雨水
**单调栈**
```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int midIndex = stack.pop();
                if (!stack.isEmpty()) {
                    int heightGap = Math.min(height[stack.peek()], height[i]) - height[midIndex];
                    int width = i - stack.peek() - 1;
                    result += heightGap * width;
                }
            }
            stack.push(i);
        }
        return result;
    }
}
```

**三循环**
```java
class Solution {
    public int trap(int[] height) {
        // 找到每个点左右两边最大的柱子。计算当前点可以储存多少水的时候，就可以用较小柱子的高度-当前柱子高度
        if (height == null || height.length <= 2) {
            return 0;
        }

        // 每个点左边的最大柱子高度
        int[] left = new int[height.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < height.length; i++) {
            max = Math.max(max, height[i]);
            left[i] = max; // 如果当前柱子是最高的，那么就把当前柱子的高度，赋值给left[i]. 那么在计算的时候，当前的点可以存储的水一定是0
        }

        int[] right = new int[height.length];
        max = Integer.MIN_VALUE;
        for (int i = height.length - 1; i >= 0; i--) {
            max = Math.max(max, height[i]);
            right[i] = max;
        }

        int result = 0;
        for (int i = 0; i < height.length; i++) {
            result += Math.min(left[i], right[i]) - height[i];
        }
        return result;
    }
}
```

**双指针**
```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }

        int result = 0;
        int left = 0;
        int right = height.length - 1;
        int leftMax = height[left];
        int rightMax = height[right];

        // 必须写上等于，因为左右指针相等的时候，相等的这个索引所在的位置，也可能存水
        while (left <= right) {
            // 根据短板的原理，如果leftMax < rightMax，说明左边的存水量是leftMax - height[i]
            if (leftMax < rightMax) {
                leftMax = Math.max(leftMax, height[left]);
                result += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                result += rightMax - height[right];
                right--;
            }
        }
        return result;
    }
}
```

### 滑动窗口模板
```java
public void slidingWindow(int[] nums) {
  // 可能需要map记录窗口内元素
  Map<Integer, Integer> map;
  
  // 同向双指针
  int i = 0;
  int j = 0;
  
  // 外层for循环，内层while循环作为主体
  for (int i = 0; i < nums.length; i++) {
    while (j < nums.length) {
      // 搞清楚窗口扩展条件
      if (condition 1) {
        j++;
        
        // 更新j状态，窗口内数据更新
      } else {
        break;
      }
    }
    
    // 窗口收缩条件
    // 更新i的状态，窗口内数据更新
  }
}
```

### 无重复字符串-滑动窗口模板解法
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Set<Character> set = new HashSet<>();
        int i = 0;
        int j = 0;
        int result = 0;

        for (; i < s.length(); i++) {
            while (j < s.length()) {
                char ch = s.charAt(j);
                if (!set.contains(ch)) {
                    set.add(ch);
                    result = Math.max(result, set.size());
                    j++;
                } else {
                    break;
                }
            }

            set.remove(s.charAt(i));
        }
        return result;
    }
}
```

### 至多包含两个不同字符的最长字串长度
```java
class Slution {
    public int lengthOfLongestSusstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int i = 0;
        int j = 0;
        int result = 0;

        // key: 字符  value：字符最后一次出现的位置
        Map<Character, Integer> map = new HashMap<>();

        for (i = 0; i < s.length(); i++) {
            while (j < s.length()) {

                char ch = s.charAt(j);
                if (map.size() <= 2) {
                    map.put(ch, j);
                    result = Math.max(result, j - i + 1);
                    j++;
                } else {
                    break;
                }
            }

            // 收缩左边界
            int minIndex = s.length() - 1;
            for (int index : map.values()) {
                minIndex = Math.min(minIndex, index);
            }
            char charToDelete = s.charAt(minIndex);
            map.remove(charToDelete);
            i = minIndex;
        }
    }
}
```

# Week 10

[Week-10](./week10/week-10.md) 
