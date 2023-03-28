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
        for (int j = 0; j < leftLength; j++) {
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

