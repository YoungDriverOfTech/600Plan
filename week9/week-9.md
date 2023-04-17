# 1. 双指针

## 1.1 相遇型双指针

### Two Sum类

- 相等型：2 Sum/3 Sum/K Sum
- 不等型：Closet/Smaller/Greater



## 1.2 相遇类指针实战

### 两数之和 II - 输入有序数组

[167. 两数之和 II - 输入有序数组](https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/)

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length == 0) {
            return new int[] {-1, -1};
        }

        int start = 0;
        int end = numbers.length - 1;
        while (start < end) {
            int sum = numbers[start] + numbers[end];
            if (target == sum) {
                return new int[] {start + 1, end + 1};
            } else if (sum > target) {
                end--;
            } else {
                start++;
            }
        }
        return new int[] {-1, -1};
    }
}
```

