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



### 最接近的三数之和

[16. 最接近的三数之和](https://leetcode.cn/problems/3sum-closest/)

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int start = i + 1;
            int end = nums.length  -1;
            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (sum == target) {
                    return sum;
                }

                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }

                if (sum > target) {
                    // 去重
                    while (start < end && nums[end] == nums[end - 1]) {
                        end--;
                    }
                    end--;
                } else {
                    while (start < end && nums[start] == nums[start + 1]) {
                        start++;
                    }
                    start++;
                }
            }
        }
        return result;
    }
}
```



### 有效三角形的个数

[611. 有效三角形的个数](https://leetcode.cn/problems/valid-triangle-number/)

```java
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);

        int result = 0;
        for (int i = nums.length - 1; i >= 2; i--) {
            int start = 0;
            int end = i - 1;

            while (start < end) {
                int sum = nums[start] + nums[end];
                if (sum > nums[i]) {
                    result += end - start;// 因为已经排序过了，所以start后面的一定都满足条件
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



### 盛最多水的容器

[11. 盛最多水的容器](https://leetcode.cn/problems/container-with-most-water/)

```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int result = 0;

        while (left < right) {
            int square = Math.min(height[left], height[right]) * (right - left);
            result = Math.max(square, result);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return result;
    }
}
```

