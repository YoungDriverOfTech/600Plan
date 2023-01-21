
# 拿到数组题目时候应该问的问题
1. 什么类型数组 int char？ 正整数，整数？
2. 有无重复元素
3. 是否有序
4. 要求的输出是什么？ 全部结果/任意结果
5. 函数定义
    - 输入参数（几个，什么类型）
    - 输出/返回值
    - 名字（长没事，要具体）
6. 检查input（null）


# 两数之和
[1. 两数之和](https://leetcode.cn/problems/two-sum/)
## brute force  

因为使用了双重循环，时间复杂度是O(n^2)
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[2];
        }
        int[] result = new int[2];
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }
}
```
## 排序+双指针  

类库的排序算法复杂度是O(nlogn),双指针是O(n)。所以总体复杂度是O(nlogn)
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[2];
        }
        int[] result = new int[2];
        Arrays.sort(nums);

        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                left++;
            } else if (nums[left] + nums[right] > target) {
                right--;
            } else {
                result[0] = left;
                result[1] = right;
                return result;
            }
        }
        return result;
    }
}
```



# 三数之和

[15. 三数之和](https://leetcode.cn/problems/3sum/)
## brute force  

三重循环，复杂度是O(n^3)
```java
public class Solution {
    
    public int[] threeSum(int[] nums, int target) {
        int[] result = new int[3];
        if (nums.length < 3) {
            return result;
        }

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length -1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        result[0] = nums[i];
                        result[1] = nums[j];
                        result[2] = nums[k];
                        return result;
                    }
                }
            }
        }
        return result;
    }

}
```

## 排序+遍历+双指针

排序算法复杂度是O(nlogn), 因为进行了两次循环，所以复杂度是O(n^2),最后的复杂度就是O(n^2)
```java
public class Solution {
    
    public int[] threeSum(int[] nums, int target) {
        int[] result = new int[3];
        if (nums.length < 3) {
            return result;
        }

        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int first = i + 1;
            int second = nums.length - 1;
            int newTarget = target - nums[i];

            while (first < second) {
                if (nums[first] + nums[second] == newTarget) {
                    result[0] = nums[i];
                    result[1] = nums[first];
                    result[2] = nums[second];
                    return result;
                }

                if (nums[first] + nums[second] > newTarget) {
                    seond--;
                }
                if (nums[first] + nums[second] < newTarget) {
                    first++;
                }
            }
        }
        return result;
    }

}
```


# 四数之和  

[18. 四数之和](https://leetcode.cn/problems/4sum/)
## 排序+双指针
排序算法复杂度是O(nlogn), 因为进行了3次循环，所以复杂度是O(n^3),最后的复杂度就是O(n^3)

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int start = j + 1;
                int end = nums.length - 1;

                while (start < end) {
                    long sum = (long)nums[i] + (long)nums[j] + (long)nums[start] + (long)nums[end];

                    if (sum == target) {
                        List<Integer> fourElementList = new ArrayList<>();
                        fourElementList.add(nums[i]);
                        fourElementList.add(nums[j]);
                        fourElementList.add(nums[start]);
                        fourElementList.add(nums[end]);
                        result.add(fourElementList);

                        // 去重 left -> right
                        while (start < end && nums[start] == nums[start + 1]) {
                            start++;
                        }
                        start++;

                        // 去重 right <- left
                        while (start < end && nums[end] == nums[end - 1]) {
                            end--;
                        }
                        end--;
                    } else if (sum < target) {
                        start++;
                    } else {
                        end--;
                    }
                }
            }
        }
        return result;
    }
}
```

# K-sum解法总结  
- 排序
- 尝试遍历第一个数，将问题转化为k-1Sum
- 时间复杂度  
> 2-Sum: O(nlogn) + O(n) = O(nlogn)
> 3-Sum: O(nlogn) + O(n^2) = O(n^2)
> 4-Sum: O(nlogn) + O(n^3) = O(n^3)
> k-Sum: O(nlogn) + O(n^(k-1)) = O(n^(k-1))