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



## 2.2 链表中倒数第k个节点

[剑指 Offer 22. 链表中倒数第k个节点](https://leetcode.cn/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/)

### brute force

遍历两次

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    // 假如长度是5，那倒数第2个就是整数第5-2=3个（这个3是索引）
    public ListNode getKthFromEnd(ListNode head, int k) {
        int index = getLength(head) - k;

        ListNode cur = head;
        while (index-- != 0) {
            cur = cur.next;
        }
        return cur;
    }

    private int getLength(ListNode head) {
        int length = 0;
        ListNode cur = head;

        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }
}
```



### 快慢指针

遍历一次

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    // 快指针先走k步以后，慢指针在跟上走
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fast = head;
        ListNode slow = head;

        while (k-- != 0) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

}
```



## 2.3 环形链表

[141. 环形链表](https://leetcode.cn/problems/linked-list-cycle/)

模板题，快慢指针

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



## 2.4 环形链表2

[142. 环形链表 II](https://leetcode.cn/problems/linked-list-cycle-ii/)

1. 判相遇
2. cur=slow相遇点就是如环点

![时间复杂度](./images/142.jpeg) 

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
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;    // 应该返回空，而不是head。如果head只有一个节点，应该返回null
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                ListNode cur = head;

                while (slow != cur) {
                    slow = slow.next;
                    cur = cur.next;
                }

                return cur;
            }
        }
        return null;
    }
}
```



# 3 链表结构变化相关问题

## 3.1 题目总览

- 合并链表
  - 合并两个有序链表
  - 两数相加
- 删除节点
  - 删除链表的倒数第n个节点
  - 删除链表中的重复元素
- 反转链表
  - 反转链表
  - k个一组反转链表
  - 交换相邻节点
- 综合类
  - 两数相加
  - 重排链表
  - 回文链表

## 3.2 务必记住的三句话

1. 链表结构会不会变化 => 头节点会不会变化 => dummy node
2. 翻转链表用头插法
3. 画图关注节点的链接与断开



# 4 实战继续  

## 4.1 合并有序链表

[21. 合并两个有序链表](https://leetcode.cn/problems/merge-two-sorted-lists/)

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

    // 链表结构会不会变化 => 头节点会不会变化 => dummy node
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }

        ListNode dummyNode = new ListNode(-1);
        ListNode head = dummyNode;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                head.next = list1;
                list1 = list1.next;
            } else {
                head.next = list2;
                list2 = list2.next;
            }
            head = head.next;
        }

        if (list1 == null) {
            head.next = list2;
        } else {
            head.next = list1;
        }
        return dummyNode.next;
    }
}
```



## 4.2 两数相加

[2. 两数相加](https://leetcode.cn/problems/add-two-numbers/)

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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(-1);
        ListNode head = dummyNode;

        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int num1 = l1 == null ? 0 : l1.val;
            int num2 = l2 == null ? 0 : l2.val;
            int sum = num1 + num2 + carry;

            head.next = new ListNode(sum % 10);
            head = head.next;

            carry = sum / 10;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return dummyNode.next;
    }
}
```



## 4.3 删除链表的倒数第N个节点

[19. 删除链表的倒数第 N 个结点](https://leetcode.cn/problems/remove-nth-node-from-end-of-list/)

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
    // 链表结构会不会变化 => 头节点会不会变化 => dummy node
    // 快指针指向head，慢指针指向dummyNode节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode fast = head;
        ListNode slow = dummyNode;

        // 快指针先走n步，然后慢指针再开始走
        while (n-- > 0) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        
        return dummyNode.next;
    }
}
```



## 4.4 删除链表重复元素

[83. 删除排序链表中的重复元素](https://leetcode.cn/problems/remove-duplicates-from-sorted-list/)

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



## 4.4 删除链表重复元素2

[82. 删除排序链表中的重复元素 II](https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/)

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
    // 链表结构会不会变化 => 头节点会不会变化 => dummy node
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 因为要去除所有的重复元素，比如 1 2 2 3 3 4 5 6. 那么2 2 3 3 会被去除。那么去除2 2 的时候，取到第一个2的
        // 节点是没有意义的。必须渠道前面一个2前面的一个节点才行。那么就是渠道前面的节点，然后判断node.next.val == node.next.next.val才行
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode node = dummyNode;

        while (node != null && node.next != null && node.next.next != null) {
            if (node.next.val == node.next.next.val) {
                // node后面两个节点相等的时候，记录node后第一个节点的值，然后后面如果出现和这个值相等的节点，就把node的next指针的节点指向在后面一个，一直到值不相同的时候
                int value = node.next.val; 

                // 继续往后面查看有没有相等元素
                while (node.next != null && node.next.val == value) {
                    node.next = node.next.next;
                }
            } else {
                // node 后面两个节点不相等的时候，把node往后面一栋
                node = node.next;
            }
        }

        return dummyNode.next;
    }
}
```



## 4.5 反转链表

[206. 反转链表](https://leetcode.cn/problems/reverse-linked-list/)

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

        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode temp = cur.next;

            cur.next = pre;

            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
```

