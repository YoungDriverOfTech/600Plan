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
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummyNode = new ListNode(-1, head);
        ListNode node = dummyNode;

        // 这块其实就是保证了最少得要有2个节点
        while (node.next != null && node.next.next != null) {

            // 精髓就是判断是否相等的时候，和node没关系，是判断node后面两个节点是否相等
            if (node.next.val == node.next.next.val) {
                int val = node.next.val;
                while (node.next != null && node.next.val == val) {
                    node.next = node.next.next;
                }
            } else {
                node = node.next;
            }
        }

        return dummyNode.next;
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
				
      	// 1. 先拿到当前节点的next个节点，记做temp
      	// 2. 修改当前节点的next指针到pre节点
      	// 3. pre指针移动到cur的位置，cur指针移动到temp的位置（即原来cur的next指针的位置）
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



## 4.6 反转链表2

[92. 反转链表 II](https://leetcode.cn/problems/reverse-linked-list-ii/)

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



## 4.7 k个节点一组反转

[25. K 个一组翻转链表](https://leetcode.cn/problems/reverse-nodes-in-k-group/)

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
    public ListNode reverseKGroup(ListNode head, int k) {
        // 思路： 1 遍历链表  2 判断够不够k个  3 反转k个。不够的话直接返回

        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode node = dummyNode;

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



## 4.8 两两交换链表中的节点

[24. 两两交换链表中的节点](https://leetcode.cn/problems/swap-nodes-in-pairs/)

就是上面的解法，k=2而已

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
        // 1 遍历链表
        while (node != null && node.next != null && node.next.next != null) {   // 这个条件保证pre后面一定有两个节点可以用来反转
            ListNode pre = node;

            // 2个反转1次
            ListNode cur = pre.next;
            ListNode temp = cur.next;

            cur.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;

            // 反转完成后，cur节点会移动到反转后的最后一个位置，比如1 2 3 反转饭后会变成 3 2 1. cur的指针一直是在1上
            // 那么为了下一次反转，应该吧node（pre）指针给知道现在的cur上面
            node = cur;
        }
        return dummyNode.next;
    }
}
```



## 4.9 两数相加问题2

[445. 两数相加 II](https://leetcode.cn/problems/add-two-numbers-ii/)

可以使用反转链表。先反转后相加，再反转回来（不写了）

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
        if (l1 == null && l2 == null) {
            return null;
        }

        Deque<Integer> stack1 = new ArrayDeque<>();
        Deque<Integer> stack2 = new ArrayDeque<>();

        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }

        ListNode pre = new ListNode(-1);
        int carry = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int num1 = stack1.isEmpty() ? 0 : stack1.pop();
            int num2 = stack2.isEmpty() ? 0 : stack2.pop();
            int sum = num1 + num2 + carry;

            int value = sum % 10;
            carry = sum / 10;

            // 每次计算出来的值，是用头插法插入pre节点的后面，比如pre -> 1 -> 2  现在来了3，pre -> 3 -> 1 -> 2
            // 1. 每次记录下pre.next的位置,记为temp，然后断开和pre的链接
            // 2. 新节点来了以后，让pre.next 指向新节点
            // 3. 把新节点的next指向tamp
            ListNode temp = pre.next;
            ListNode newNode = new ListNode(value);

            pre.next = newNode;
            newNode.next = temp;
        }
        return pre.next;
    }
}
```



## 5.0 重排链表

[143. 重排链表](https://leetcode.cn/problems/reorder-list/)

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
    // 1 找到链表中点，分为两段
    // 2 把后面那段反转一下
    // 3 然后把两段链表在连起来
    public void reorderList(ListNode head) {

        // 1 找到链表中点，分为两段
        ListNode mid = findMid(head);
        
        // 2 把后面那段反转一下
        ListNode tail = reverseList(mid.next);
        mid.next = null; // 记得把mid以后的断开，因为是两段不同的链表了

        // 3 然后把两段链表在连起来
        mergeList(head, tail);
    }

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
    }

    private ListNode reverseList(ListNode head) {
        ListNode pre = null;
        while (head != null) {
            ListNode temp = head.next;

            head.next = pre;
            pre = head;
            head = temp;
        }
        return pre;
    }

    private void mergeList(ListNode head, ListNode tail) {
        // 利用一个index来进行重排。
        int index = 0;
        ListNode dummyNode = new ListNode(-1);

        while (head != null && tail != null) {
            if (index % 2 == 0) {
                dummyNode.next = head;
                head = head.next;
            } else {
                dummyNode.next = tail;
                tail = tail.next;
            }
            dummyNode = dummyNode.next;
            index++;
        }

        while (head != null) {
            dummyNode.next = head;
            head = head.next;
        }
        while (tail != null) {
            dummyNode.next = tail;
            tail = tail.next;
        }
    }
}
```



## 5.1 回文链表

[234. 回文链表](https://leetcode.cn/problems/palindrome-linked-list/)

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
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // find mid node
        ListNode mid = findMid(head);
        ListNode second = mid.next;
        mid.next = null;

        // reverse second part link lis
        ListNode tail = reverseLinkedList(second);

        while (head != null && tail != null) {
            if (head.val != tail.val) {
                return false;
            }
            head = head.next;
            tail = tail.next;
        }

        return (head == null && tail == null) || (head != null && tail == null); 
    }

    private ListNode findMid(ListNode head) {
        ListNode fast = head.next;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    private ListNode reverseLinkedList(ListNode cur) {
        ListNode pre = null;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next= pre;

            pre = cur;
            cur = temp;
        }

        return pre;
    }
}
```



## 5.2 旋转链表

[61. 旋转链表](https://leetcode.cn/problems/rotate-list/)

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
    // 链表结构发生改变->dummy
    // 1 2 3 4 5
    //     ↑ 最终要的效果就是取到3，然后前面的1 2 接到5的后面
    // 1. 快指针找到5，慢指针找到2（2.next能找到3），然后断开2和3的链接
    // 2. 5.next接到head
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        // 因为k可能很大，所以先获得长度，取余数，算出实际的旋转次数
        int length = getLen(head);
        k = k % length;

        // 使用快慢指针找到3和5的位置
        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 5和1连起来，然后3和4断开
        fast.next = head;   // 这一行必须在下面那行前面，因为slow/fast都可能都在最后一个，直接用slow.next取结果，会取到空
        ListNode result = slow.next;
        slow.next = null;
        return result;
    }

    private int getLen(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
}
```



# 6. 递归

## 6.1 斐波那契数列

[剑指 Offer 10- I. 斐波那契数列](https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/)

```java
class Solution {
    public int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        int result1 = 0;
        int result2 = 1;
        int answer = 0;
        for (int i = 2; i <= n; i++) {
            answer = (result1 + result2) % 1000000007;
            result1 = result2;
            result2 = answer;
        }
        return answer;
    }
}
```



## 6.2 汉诺塔问题

[面试题 08.06. 汉诺塔问题](https://leetcode.cn/problems/hanota-lcci/)

```java
class Solution {
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        if (A == null || A.size() == 0) {
            return;
        }

        int n = A.size();
        moveHanota(n, A, C, B);
    }

    private void moveHanota(int n, List<Integer> origin, List<Integer> destination, List<Integer> buffer) {
        if (n == 1) {
            int element = origin.remove(origin.size() - 1);
            destination.add(element);
            return;
        }

        moveHanota(n - 1, origin, buffer, destination);
        int element = origin.remove(origin.size() - 1);
        destination.add(element);
        moveHanota(n - 1, buffer, destination, origin);
    } 
}
```

