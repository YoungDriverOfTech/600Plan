
# 1 数组问题
## 1.1 拿到数组题目应确认的点
1. 什么类型数组 int char？ 正整数，整数？
2. 有无重复元素
3. 是否有序
4. 要求的输出是什么？ 全部结果/任意结果
5. 函数定义
    - 输入参数（几个，什么类型）
    - 输出/返回值
    - 名字（长没事，要具体）
6. 检查input（null）


## 1.2 两数之和
[1. 两数之和](https://leetcode.cn/problems/two-sum/)
### brute force  

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
### 排序+双指针  

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



## 1.3 三数之和

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

### 排序+遍历+双指针

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


## 1.4 四数之和  

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

## 1.5 K-sum解法总结  
- 排序
- 尝试遍历第一个数，将问题转化为k-1Sum
- 时间复杂度  
> 2-Sum: O(nlogn) + O(n) = O(nlogn)  
> 3-Sum: O(nlogn) + O(n^2) = O(n^2)  
> 4-Sum: O(nlogn) + O(n^3) = O(n^3)  
> k-Sum: O(nlogn) + O(n^(k-1)) = O(n^(k-1))  

# 2 实战题目  

## 2.1 反转字符数组
[344. 反转字符串](https://leetcode.cn/problems/reverse-string/)

### 双指针
Time: O(n)
```java
class Solution {
    public void reverseString(char[] s) {
        if (s == null || s.length == 1) {
            return;
        }

        int left = 0;
        int right = s.length - 1;

        while (left < right) {
            char c = s[left];
            s[left] = s[right];
            s[right] = c;
            left++;
            right--;
        }
    }
}
```


## 2.2 奇偶排序

[905. 按奇偶排序数组](https://leetcode.cn/problems/sort-array-by-parity/)
### 双指针
Time: O(n)

```java
class Solution {
    public int[] sortArrayByParity(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            while (left < right && nums[left] % 2 == 0) {
                left++;
            }

            while (left < right && nums[right] % 2 != 0) {
                right--;
            }

            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
        return nums;
    }
}
```

## 2.3 合并两个有序数组
[88. 合并两个有序数组](https://leetcode.cn/problems/merge-sorted-array/)

### 双指针
Time: O(n)

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] result = new int[m + n];

        int index = 0;
        int index1 = 0;
        int index2 = 0;

        // 把两个数组中的数据先保存到一个临时数组
        while (index1 < m && index2 < n) {
            if (nums1[index1] <= nums2[index2]) {
                result[index++] = nums1[index1++];
            } else {
                result[index++] = nums2[index2++];
            }
        }
        
        // 没有被遍历的元素也放到临时数组里面
        while (index1 < m) {
            result[index++] = nums1[index1++];
        }
        while (index2 < n) {
            result[index++] = nums2[index2++];
        }

        // 用临时数组填充nums1
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = result[i];
        }
    }
}
```

## 2.4 前缀和  

### 途中的i，j指的都是下标
![prefix](./images/prefixSum.png)

### 前缀和模版代码  
```java
// 1. 前缀和数组的长度为原来数组的长度+1
// 2. 注意构造时index的取值范围
int[] prefixSum = new int[num.length + 1];
for (int i = 0; i < num.length; i++) {
    prefixSum[i + 1] = prefixSum[i] + num[i];
}

/*
nums:   1  2  3  4  
prefix: 0  1  3  6  10

nums[2] = prefix[3] - prefix[2] = 3
interval[2, 3] = prefix[4] - prefix[2] = 7

interval[2, 3] 指的是原数组的索引
*/
```

### 前缀和练习  
[303. 区域和检索 - 数组不可变](https://leetcode.cn/problems/range-sum-query-immutable/)

```java
 class NumArray {

    private int[] prefixSum;

    public NumArray(int[] nums) {
        prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
    }
    
    // 这里返回的时候是O(1)复杂度
    public int sumRange(int left, int right) {
        return prefixSum[right + 1] - prefixSum[left];
    }
}
```

### 数组动态和
[1480. 一维数组的动态和](https://leetcode.cn/problems/running-sum-of-1d-array/)

Time/Space: O(n)
```java
class Solution {
    public int[] runningSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int[] prefixSum = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                prefixSum[i] = nums[i];
            } else {
                prefixSum[i] = prefixSum[i - 1] + nums[i];
            }
        }

        return prefixSum;
    }
}
```

### 奇数长度子数组之和
[1588. 所有奇数长度子数组的和](https://leetcode.cn/problems/sum-of-all-odd-length-subarrays/)

Brute force  
Time: O(n^3)
```java
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        if (arr == null || arr.length == 0) {
            return - 0;
        }

        int result = 0;

        // 遍历下标
        for (int i = 0; i < arr.length; i++) {
            // 遍历长度1，3，5，7，9. 但是最终长度不能超过参数数组的长度，即最大是等于
            for (int len = 1; i + len <= arr.length; len = len + 2) {
                // 现在就可以求奇数连续数组的和了，开始位置是i，结束位置是i+len-1
                for (int j = i; j < i + len; j++) {
                    result = result + arr[j];
                }
            }
        }
        return result;
    }
}
```

前缀和  
Time: O(n^2)
```java
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        if (arr == null || arr.length == 0) {
            return - 0;
        }

        int[] prefixSum = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }
        int result = 0;

        // 遍历下标
        for (int i = 0; i < arr.length; i++) {
            // 遍历长度1，3，5，7，9. 但是最终长度不能超过参数数组的长度，即最大是等于
            for (int len = 1; i + len <= arr.length; len = len + 2) {
                // 这块可以使用前缀和了，开始的位置是i没问题。结束的位置是i+len-1，但是因为前缀和公式prefixSum[j + 1], 所以最后的1会被消除掉
                result = result + (prefixSum[i + len] - prefixSum[i]);
            }
        }
        return result;
    }
}
```

## 2.5 和为k的自数组

[560. 和为 K 的子数组](https://leetcode.cn/problems/subarray-sum-equals-k/)  

### brute force  

Time: O(n^2)

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = 0;
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    result += 1;
                }
            }
            sum = 0;
        }
        return result;
    }
}
```

### 前缀和  

Time: O(n^2)  
Space: O(n)

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        int result = 0;
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (prefixSum[j + 1] - prefixSum[i] == k) {
                    result += 1;
                }
            }
        }
        return result;
    }
}
```

## 2.6 二维区域和检索  

[304. 二维区域和检索 - 矩阵不可变](https://leetcode.cn/problems/range-sum-query-2d-immutable/)  

### brute force  

Time: O(n^2)

```java
class NumMatrix {

    private int[][] matrix;

    public NumMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (row1 > row2 || col1 > col2) {
            return -1;
        }

        int sum = 0;
        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}
```

### 一维前缀和  

对数组的每一行都取前缀和，然后for循环遍历每行，相加就OK

Time: O(m)  m是行数

```java
class NumMatrix {

    private int[][] prefixSum;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n= matrix[0].length;

        this.prefixSum = new int[m][n + 1];

        // 构造每行的前缀和，因为开辟了prefixSum数组，space: O(m * n)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i][j + 1] = prefixSum[i][j] + matrix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int result = 0;

        // Time: O(m)
        for (int i = row1; i <= row2; i++) {
            result += prefixSum[i][col2 + 1] - prefixSum[i][col1];
        }
        return result;
    }
}
```

### 二维数组前缀和(背下来)

prefixSum[i][j] = (0, 0), (i, 0), (o, j), (i, j) 所构成的矩形的元素之和。 那么求某一部分的话

假如要求【5，6】的元素和。那么先求出【6】- 【3】- 【4】+【1】。因为3 和 4 被减去的时候，【1】被多减去了一次，所以最后要加上【1】。

| 1    | 2    | 3    |
| ---- | ---- | ---- |
| 4    | 5    | 6    |
| 7    | 8    | 9    |



那么怎么初始化二维前缀和数组呢？看下面的表格

prefixSum(i+1, j+1) = prefixSum(i, j+1)  + prefixSum(i + 1, j)  - prefixSum(i, j)  + matrix(i+1, j+1)

前两行和 + 前两列和 - (i, j)和 + (i + 1, j + 1)的值

|      |        |          |
| ---- | ------ | -------- |
|      | i, j   | i, j+1   |
|      | i+1, j | i+1, j+1 |

```java
class NumMatrix {
    private int[][] prefixSum;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n= matrix[0].length;

        this.prefixSum = new int[m + 1][n + 1];

        // 构造二维数组前缀和
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i + 1][j + 1] = prefixSum[i][j + 1] + prefixSum[i + 1][j] - prefixSum[i][j] + matrix[i][j]; // 因为matrix的矩阵比prefixSum的要小一个，所以最后就不用加1了
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // Time: O(1)
        // 因为prefixSum的矩阵会从（1，1）开始，所以求坐标的时候应该都要+1
        return prefixSum[row2 + 1][col2 + 1] - prefixSum[row1][col2 + 1] - prefixSum[row2 + 1][col1] + prefixSum[row1][col1];
    }
}
```

```java
class NumMatrix {
    // 二维前缀和
    private int[][] prefixSum;

    // 首先构造二维前缀数组
    public NumMatrix(int[][] matrix) {
        // 先求出第一行，第一列的前缀和
        int rows = matrix.length;
        int cols = matrix[0].length;
        prefixSum = new int[rows][cols];
        prefixSum[0][0] = matrix[0][0];

        for (int i = 1; i < rows; i++) {
            prefixSum[i][0] = prefixSum[i - 1][0] + matrix[i][0];
        }
        for (int i = 1; i < cols; i++) {
            prefixSum[0][i] = prefixSum[0][i - 1] + matrix[0][i];
        }

        // 构建剩余格子的前缀和
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                prefixSum[i][j] = prefixSum[i - 1][j] + prefixSum[i][j - 1] - prefixSum[i - 1][j - 1] + matrix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (row1 == 0 && col1 == 0) {
            return prefixSum[row2][col2];
        }

        if (row1 == 0) {
            return prefixSum[row2][col2] - prefixSum[row2][col1 - 1];
        }

        if (col1 == 0) {
            return prefixSum[row2][col2] - prefixSum[row1 - 1][col2];
        }

        return prefixSum[row2][col2] - prefixSum[row2][col1 - 1] - prefixSum[row1 - 1][col2] + prefixSum[row1 - 1][col1 - 1];
    }
}
```



## 2.7 除自身以外数组的乘积

[238. 除自身以外数组的乘积](https://leetcode.cn/problems/product-of-array-except-self/)

### 前缀/后缀积

前缀积: left[i] = left[i - 1] * nums[i - 1]  

后缀积: right[i] = right[i + 1] * nums[i + 1]

Time: O(n)

Space: O(n) 输出数组不过为空间复杂度计算部分

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] result = new int[nums.length];
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];

        // 前缀积: left[i] = left[i - 1] * nums[i - 1]  
        left[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        // 后缀积: right[i] = right[i + 1] * nums[i + 1]
        right[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        // 最终答案
        for (int i = 0; i < nums.length; i++) {
            result[i] = left[i] * right[i];
        }

        return result;
    }
}
```

### 前缀/后缀积 优化

Time: O(n)

Space: O(n) 输出数组不过为空间复杂度计算部分

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] result = new int[nums.length];

        // 前缀积: left[i] = left[i - 1] * nums[i - 1]  
        result[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // 用factor来几录后缀积
        int factor = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] = result[i] * factor;
            factor = factor * nums[i];
        }

        return result;
    }
}
```



### 头尾遍历

Time: O(n)

Space: O(1) 输出数组不过为空间复杂度计算部分

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
				// 不初始化为1的话，会出现0的乘积
        int[] result = new int[nums.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = 1;
        }

        int temp1 = 1;
        for (int i = 1; i < nums.length; i++) {
            temp1 = temp1 * nums[i - 1];
            result[i] = temp1;
        }

        int temp2 = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            temp2 = temp2 * nums[i + 1];
            result[i] = result[i] * temp2;
        }
        return result;
    }
}
```



## 2.8 数组列表（ArrayList）

- 基于数组实现的容量大小可动态变化的数据结构
- 可以将很多数组操作的细节封装起来

### 实现一个ArrayList

1. 定义属性字段
   - 在数组的基础上实现，存储数据信息：int data[]
   - 属性：data, size, capacity
2. 定义构造器
3. 定义方法
   - add, get, set, remove, size

```java
package MyDataStructure;

public class MyArrayList {

    private final static int DEFAULT_CAPACITY = 10;

    // properties
    private int[] elements;

    private int capacity;

    private int size = 0;

    // constructors
    public MyArrayList() {
        this.elements = new int[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new RuntimeException("Capacity value must be positive.");
        }else if (capacity < DEFAULT_CAPACITY) {
            this.elements = new int[DEFAULT_CAPACITY];
            this.capacity = DEFAULT_CAPACITY;
        } else {
            this.elements = new int[capacity];
            this.capacity = capacity;
        }
    }

    // methods

    /**
     * Add element into array
     * @param index element index
     * @param value element value
     */
    public void add(int index, int value) {
        rangeCheckForAdd(index);

        // resize
        if (size == capacity) {
            resize(2 * size);
        }

        for (int i = index; i < size; i++) {
            // todo fix out of index
            elements[i + 1] = elements[i];
        }
        elements[index] = value;
        size++;
    }

    /**
     * Dynamic grow list length
     */
    private void resize(int capacity) {
        // Create a new array
        int[] newElements = new int[capacity];

        // Copy
        for (int i = 0; i < size; i++) {
            int value = elements[i];
            newElements[i] = value;
        }

        elements = newElements;
        this.capacity = capacity;
    }

    /**
     * Add value into last position of array list
     * @param value element value
     */
    public void add(int value) {
        add(size, value);
    }

    /**
     * Remove the element by index
     * @param index element index
     * @return removed element
     */
    public int remove(int index) {
        rangeCheck(index);

        int toRemoved = elements[index];

        // Remove elements
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;

        return toRemoved;
    }

    /**
     * Replace element value by index
     * @param index element index
     * @param value element value
     */
    public void set(int index, int value) {
        rangeCheck(index);
        elements[index] = value;
    }



    /**
     * Get element by index
     * @param index element index
     * @return element value
     */
    public int get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * Get first element value equals with parameter value
     * @param value element value
     * @return element index
     */
    public int getFirstElementByValue(int value) {
        return 0;
    }

    /**
     * Get the length of array list
     * @return the length of array
     */
    public int length() {
        return size;
    }

    /**
     * Whether array list is empty
     * @return true/false
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Check index validity
     * @param index element index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Invalid index: " + index);
        }
    }

    /**
     * Check index validity for add
     * @param index element index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Invalid index: " + index);
        }
    }

    public static void main(String[] args) {

    }
}

```

