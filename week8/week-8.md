# 1. 排序算法

## 1.1 插入排序

### 核心

把拿到的数字，和每一个数字进行比较，找到正确的位置即可

时间复杂度： Best: O(n)    Average: O(n^2)    Worst: O(n^2)



### 模板代码

```java
public class Main {
    public void sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        for (int j = 1; j < nums.length; j++) {
            int i = j - 1; // 最右边的元素
            int key = nums[j];

            // 从最右边开始，往左找第一个比该元素小的位置，把元素放到这个位置的后面
            while (i >= 0 && nums[i] > key) {
                nums[i + 1] = nums[i];
                i--;
            }
            nums[i + 1] = key;
        }
    }
}

```



## 1.2 冒泡排序

### 核心

反复交换相邻未按次序排列的元素

时间复杂度： O(n^2)

### 模板代码

```java
public class Main {
    public void sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int length = nums.length;

        for (int i = 0; i < length; i++) {
            for (int j = 1; j < length - i; j++) {
                if (nums[j] < nums[j - 1]) {
                    int temp = nums[j - 1];
                    nums[j - 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }
}

```



## 1.3 归并排序

Time: O(nlogn)

Space: O(n)

### 写法1

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



### 写法2

```java
public class Main {
    public void sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int[] temp = new int[nums.length];

        mergeSort(nums, temp, 0, nums.length - 1);
    }

    public void mergeSort(int[] nums, int[] temp, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = start + (end - start) / 2;
        mergeSort(nums, temp, start, mid);
        mergeSort(nums, temp, mid + 1, end);
        merge(nums, temp, start, mid, end);
    }

    private void merge(int[] nums, int[] temp, int start, int mid, int end) {
        int left = start;
        int right = mid + 1;
        int index = start;

        // merge two sorted array
        while (left <= mid && right <= end) {
            if (nums[left] <= nums[right]) {
                temp[index++] = nums[left++];
            } else {
                temp[index++] = nums[right++];
            }
        }

        while (left <= mid) {
            temp[index++] = nums[left++];
        }
        while (right <= end) {
            temp[index++] = nums[right++];
        }

        for (int i = start; i <= end; i++) {
            nums[i] = temp[i];
        }
    }
}
```



## 1.4 快速排序

### 核心想法

核心想法：递归，分治法（divide and conquer）

Time: O(nlogn)

Space: O(n)

- 最坏的情况：划分的两个数组分别有n-1个和0个元素 -> O(n^2)
- 最好的情况：O(nlogn)
- 平均：O(nlogn)



### 快拍的分治

- 分解：将数组划分为两个（可能为空）子数组，使得前一个子数组中的每个元素都小于或等于nums[pivot]，后一个都大于nums[pivot]
- 解决：递归地对两个子数组分别排序
- 合并：由于子数组都是原地排序不需要合并



### 写法1

```java
public class Main {
    public void sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int[] temp = new int[nums.length];

        quickSort(nums, 0, nums.length - 1);
    }

    private void quickSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = partition(nums, start, end);
        quickSort(nums, start, pivot - 1);
        quickSort(nums, pivot + 1, end);
    }

    private int partition(int[] nums, int start, int end) {
        int pivotValue = nums[end];

        int j = start - 1;

        for (int i = start; i < end; i++) {
            if (nums[i] <= pivotValue) {
                j++;

                // 说明 i之前一定有元素大于pivotValue，需要交换
                if (i != j) {
                    swap(nums, i, j);
                }
            }
        }
        
        // start ... j : x < value   j + 1: value  j + 2 ... end: x > value
        swap(nums, j + 1, end);
        return j + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

### 写法2
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

    public static void main(String[] args) throws Exception {
        QuickSort quickSort = new QuickSort();
        int[] param = new int[]{3,2,1,5,6,4};
        System.out.println(Arrays.toString(param));
        quickSort.sort(param);
        System.out.println(Arrays.toString(param));
    }
}
```

### 写法3 

```java
public class Main {
    public void sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int[] temp = new int[nums.length];

        quickSort(nums, 0, nums.length - 1);
    }

    private void quickSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = partition(nums, start, end);
        quickSort(nums, start, pivot - 1);
        quickSort(nums, pivot + 1, end);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private int partition(int[] nums, int start, int end) {
        int pivot = start;
        int value = nums[pivot];

        while (start < end) {
            while (start < end && nums[end] >= value) {
                end--;
            }

            // find the element that smaller than value
            swap(nums, start, end);
            
            while (start < end && nums[start] <= value) {
                start++;
            }
            
            // find the element that larger than value
            swap(nums, start, end);
        }
        return start;
    }
}
```

### 扩展: 期望为线性时间的快速选择算法（quick select）
求数组nums中第k小的元素

***quick select运行过程***
- 检查递归基本情况
- 将数组划分为两个（可能为空）子鼠族，是的前一个子数组中的每个元素都小于或者等于nums[index],后一个都大于nums[index]
- 检查nums[index]是否为第k小的元素
    1. 如果是则返回nums[index]
    2. 否则需要确定第k小的元素落在哪一个子数组
        - 如果num > k, 则落入划分低区
        - 繁殖num < k, 则落入划分高区，而且我们已经知道有num个元素比nums[index]小

```java
public class QuickSelect {

    // 求数组中第k小的元素
    public int getKthSmallerElement(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            throw new RuntimeException();
        }

        return quickSelect(0, nums.length - 1, k, nums);
    }

    private int quickSelect(int start, int end, int k, int[] nums) {
        // 递归最基本条件
        if (start == end) {
            return nums[start];
        }

        int index = partition(nums, start, end);
        int num = index - start + 1; // 包括nums[index]
        if (k == num) {
            return nums[index];
        } else if (k < num) {
            return quickSelect(start, index - 1, k, nums); // 为什么index - 1， 因为int num = index - start + 1; // 包括nums[index]
        } else {
            // 为什么要 k - num， 因为index（包括index）前面的数子都小于nums[index]. 那么要找第k小的，只能在高区找
            // 这时候得需要减去比nums[index]小的num个元素，在高区找剩下的第 k - num 小的元素
            return quickSelect(index + 1, end, k - num, nums);
        }
    }
    
    // 这个partition可以被替换成写法2的partition
    private int partition(int[] nums, int start, int end) {
        int pivotValue = nums[end];

        int j = start - 1;

        for (int i = start; i < end; i++) {
            if (nums[i] <= pivotValue) {
                j++;

                // 说明 i之前一定有元素大于pivotValue，需要交换
                if (i != j) {
                    swap(nums, i, j);
                }
            }
        }

        // start ... j : x < value   j + 1: value  j + 2 ... end: x > value
        swap(nums, j + 1, end);
        return j + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
```

## 1.5 实战

### 对链表进行插入排序

[147. 对链表进行插入排序](https://leetcode.cn/problems/insertion-sort-list/)

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

            // 找到node.next.val > head.val 那么就把head塞到node的后面
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



### 排序链表

[148. 排序链表](https://leetcode.cn/problems/sort-list/)

归并排序解法
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
    // 归并排序解法，但是不满足follow up
    public ListNode sortList(ListNode head) {
        // 如果只有一个或者null节点，直接返回即可
        if (head == null || head.next == null) {
            return head;
        }

        // 找到中间节点
        // 1 2 3 4 5 -> 3
        // 1 2 3 4  -> 2 
        ListNode mid = findMid(head);

        ListNode right = sortList(mid.next);
        mid.next = null;
        ListNode left = sortList(head);

        return merge(left, right);
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

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode node = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                node.next = l1;
                l1 = l1.next;
            } else {
                node.next = l2;
                l2 = l2.next;
            }
            node = node.next;
        }

        if (l1 == null) {	// if 不要写成while
            node.next = l2;
        }
        if (l2 == null) {
            node.next = l1;
        }

        return dummy.next;
    }
}
```

快速排序解法
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
    // 利用快速排序
    // 把链表划分成高区和低区还有pivot，低 -> pivot -> 高 即可完成排序
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode lowNodeDummy = new ListNode(-1);
        ListNode lowNode = lowNodeDummy;
        ListNode highNodeDummy = new ListNode(-1);
        ListNode highNode = highNodeDummy;

        // 遍历这个节点，把head当作pivot的节点，比pivot小的进低区，大的或者等于的进高区
        int pivotValue = head.val;
        ListNode node = head.next;

        while (node != null) {
            // 不能把和pivot值一样的放入低区，无限循环，比如【1, 1】
            if (node.val < pivotValue) {
                lowNode.next = node;
                lowNode = lowNode.next;
            } else {
                highNode.next = node;
                highNode = highNode.next;
            }

            // 遍历节点也需要往后走
            node = node.next;
        }

        // 现在lowNode/highNode都指向了各区的最后一个节点。现在需要做的是，如果各区最后一个节点后还连着别的节点
        // sortedLowHead -> pivot
        lowNode.next = head;
        highNode.next = null;

        // 需要断开，因为pivot节点要加到低区区排序
        head.next = null;

        // 然后递归的调用，调用方法会返回排好序的低区和高区的头节点
        ListNode sortedLowHead = sortList(lowNodeDummy.next);
        ListNode sortedHighHead = sortList(highNodeDummy.next);

        // 不能等到这里才接上head，因为有可能递归之前，所有节点都落在高区，那么sortedHighHead就是一个null节点
        // 最后return sortedLowHead 的话，就是null了
        // lowNode.next = head; 

        // sortedLowHead -> pivot -> sortedHighHead
        head.next = sortedHighHead;
        return sortedLowHead;
    }
}
```



### 数组中的第K个最大元素

[215. 数组中的第K个最大元素](https://leetcode.cn/problems/kth-largest-element-in-an-array/)

```java
class Solution {
    // 取到第k大的元素 -> 取第 length - k + 1小的元素
    // 取第2大的元素 在[3,2,1,5,6,4] -> 5
    // 6 - 2 + 1= 5 第5小的元素
    public int findKthLargest(int[] nums, int k) {
        k = nums.length - k + 1;
        return quickSort(nums, 0, nums.length - 1, k);
    }

    private int quickSort(int[] nums, int start, int end, int k) {
        if (start == end) {
            return nums[start];
        }

        int pivotIndex = pritition(nums, start, end);
        int num = pivotIndex - start + 1;
        if (num == k) {
            return nums[pivotIndex];
        } else if (num < k) {
            return quickSort(nums, pivotIndex + 1, end, k - num);
        } else {
            return quickSort(nums, start, pivotIndex - 1, k);
        }
    }

    private int pritition(int[] nums, int start, int end) {
        int pivotIndex = end;
        int traversalIndex= start;

        for (int i = start; i < end; i++) {
            if (nums[i] <= nums[pivotIndex]) {
                swap(nums, i, traversalIndex);
                traversalIndex++;
            }
        }
        swap(nums, traversalIndex, end);
        return traversalIndex;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```



### 颜色分类

[75. 颜色分类](https://leetcode.cn/problems/sort-colors/)

```java
class Solution {
    public void sortColors(int[] nums) {
        int first = 0;
        int second = nums.length - 1;
        int i = 0;

        while (i <= second) {
            if (nums[i] == 0) {
                swap(nums, i, first);
                first++;
                i++;
            } else if (nums[i] == 2) {
                swap(nums, i, second);
                second--; // 这里不需要i++，因为不确定从second换过去的值，所以还需要while循环里面判断一下
            } else {
                i++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```



### 剑指 Offer 51. 数组中的逆序对

[剑指 Offer 51. 数组中的逆序对](https://leetcode.cn/problems/shu-zu-zhong-de-ni-xu-dui-lcof/)

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

[493. 翻转对](https://leetcode.cn/problems/reverse-pairs/)

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



# 2. 查询时间为O(1)的数据结构-哈希表

## 2.1 哈希表基本原理

- 数组通过下标访问数据的一种拓展
- 核心：利用哈希函数，将键值映射到数组上（bucket）



## 2.2 Hash Function哈希函数

- 哈希函数是用来将一个字符串或者任何其他类型转换为小于hash表大小且大于等于0的整数
- 一个好的哈希函数
  - 尽可能少的产生冲突
  - 算得快



 ### 哈希函数案例

一种广泛使用的哈希函数算法是使用数值33（Times 33算法）

hashcode("abcd") = ascii(a) * 33^3 + ascii(b) * 33^2 + ascii(c) * 33^1 + ascii(d)

​								=(97 * * 33^3 + 98  * 33^2 + 99 * 33^1 + 100) % HASH_SIZE

​								= 3595978 % HASH_SIZE

其中HASH_SIZE表示哈希表的大小

给出一个字符串作为key和一个哈希表的大小，返回着个字符串的哈希值

```java
public int hashCode(char[] key, int hashSize) {
  long result = 0;
  for (int i = 0; i < key.length; i++) {
    result = (result * 33 + (int)(key[i])) % hashSize;
  }
  return (int) result;
}
```



## 2.3 hash冲突

- 表面原因：会有一些key会map到相同的index上
- 本质：无限空间往有限空间映射



### 闭散列：开放定址法

- 线性探测

> 有冲突的话，去找下一个内存位置，如果还被占用，那就一直往后找（如果找遍了hash表都满了，那就存不了了）
>
> 查找的时候同理，如果查找的时候有hash冲突，往后面的地址寻找的时候，发现了空地址，说明值不在hash表中

- 二次探测

> 有冲突的话，去找下一个内存位置，但是下一个位置是按照平方值来寻找的

- 双重散列（hash）

> 有冲突的话，用另一个hash函数重新计算位置
