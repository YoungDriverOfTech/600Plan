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



### 三数之和

[15. 三数之和](https://leetcode.cn/problems/3sum/)

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (sum == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[start]);
                    list.add(nums[end]);
                    result.add(list);

                    // 去重
                    while (start < end && nums[start] == nums[start + 1]) {
                        start++;
                    }
                    start++;

                    while (start < end && nums[end] == nums[end - 1]) {
                        end--;
                    }
                    end--;
                } else if (sum > 0) {
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

