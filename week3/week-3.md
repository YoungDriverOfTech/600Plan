# 1 链表理论

## 1.1 什么是链表

链表是一种线性表，但是并不会按线性顺序存储数据，而是在每一个节点里存到下一个节点的指针(Pointer)



# 2 实战

## 2.1 得到链表中点元素

[876. 链表的中间结点](https://leetcode.cn/problems/middle-of-the-linked-list/)

### brute force

遍历两次

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
    public ListNode middleNode(ListNode head) {
        int length = getLength(head);
        int index = length / 2;

        ListNode cur = head;
        while (index-- != 0) {
            cur = cur.next;
        }

        return cur;
    }

    private int getLength(ListNode head) {
        ListNode cur = head;
        int length = 0;

        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }
}
```





### 双指针

遍历一次，快慢指针

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

