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



# Week 5

[Week-5](./week5/week-5.md) 



# Week 6

[Week-6](./week6/week-6.md) 



# Week 8

[Week-8](./week8/week-8.md) 



# Week 9

[Week-9](./week9/week-9.md) 



# Week 10

[Week-10](./week10/week-10.md) 
