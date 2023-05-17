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

    // 快慢指针，快指针每次走两步，慢指针每次走一步
    // 分析下来，节点个数是奇数，那么慢指针是最后想要的答案。节点个数是偶数，那么慢指针还需要再走一步才能走到中间位置
    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 节点个数是偶数个的时候
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
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
