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

