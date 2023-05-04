# 1. 王者难度算法-动态规划

## 1.1 什么是动态规划

就是for/递归 + 记忆化搜索



## 1.2 四个步骤设计动态规划

- 定义状态（State）：刻画一个最优解的结构特征
- 定义状态转移方程（Function）：状态之间的联系与状态转移【核心难点】
- 初始条件与边界条件（Condition）：基本条件是什么，最小状态是什么
- 最优解求解（Solution）



## 1.3 什么时候可以使用动态规划

- 最优子结构性质：一个问题的最优解包含其子问题的最优解
- 子问题的重叠性：问题的递归算法会反复求解相同的子问题，而不是一只生成新的子问题
- 无后效性：对一个确定的状态，不必关心这个状态是怎么出现的，也不必考虑这个状态的前一个状态是什么



### 通常用于最优化问题

- 求方案总数
- 方案可行性
- 求最优化解
- DP不适合输出所有可行方案的题目



# 2. 实战

## 2.1 单序列动态规划：斐波那契系列

### 爬楼梯

[70. 爬楼梯](https://leetcode.cn/problems/climbing-stairs/)

用dp数组

```java
class Solution {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }

        // dp[n]: 有多少中不同的方法爬到第n阶
        int[] dp = new int[n + 1];  // 为了不用倒腾索引的关系，最后直接返回dp[n]
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            // 状态转移方程：dp[n] = 最后一步爬1个台阶的方案总数（dp[n - 1]） + 最后一步爬2个台阶的方案总数（dp[n - 2]）
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
```

滚动数组优化

```java
class Solution {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }

        // dp[n]: 有多少中不同的方法爬到第n阶
        int[] dp = new int[2];  // 为了不用倒腾索引的关系，最后直接返回dp[n]
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            // 状态转移方程：dp[n] = 最后一步爬1个台阶的方案总数（dp[n - 1]） + 最后一步爬2个台阶的方案总数（dp[n - 2]）
            dp[i % 2] = dp[(i - 1) % 2] + dp[(i - 2) % 2];
        }

        return dp[n % 2];
    }
}
```

用两个数字

```java
class Solution {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }

        // dp[n]: 有多少中不同的方法爬到第n阶
        int pre = 1, cur = 1, sum = 0;

        for (int i = 2; i <= n; i++) {
            // 状态转移方程：dp[n] = 最后一步爬1个台阶的方案总数（dp[n - 1]） + 最后一步爬2个台阶的方案总数（dp[n - 2]）
            sum = pre + cur;
            pre = cur;
            cur = sum;
        }

        return cur;
    }
}
```



### 第 N 个泰波那契数

[1137. 第 N 个泰波那契数](https://leetcode.cn/problems/n-th-tribonacci-number/)

```java
class Solution {
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int[] dp = new int[3];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i % 3] = dp[(i - 3) % 3] + dp[(i - 2) % 3] + dp[(i - 1) % 3];
        }

        return dp[n % 3];
    }
}
```



### 使用最小花费爬楼梯

[746. 使用最小花费爬楼梯](https://leetcode.cn/problems/min-cost-climbing-stairs/)

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // 题意解释：到达楼梯顶部是要走完整个cost，要超出cost数组最后一个元素才算走完
        if (cost == null || cost.length == 0) {
            return 0;
        }

        // State: dp[n]:到达第n级阶梯所需要的最小花费
        int[] dp = new int[2];
        
        // 因为 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。 -》 说明0和1级阶梯的话是不用花费的，初始为0即可
        dp[0] = 0;
        dp[1] = 0;

        int n = cost.length;
        for (int i = 2; i <= n; i++) {
            // Function: dp[i] = min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2])
            dp[i % 2] = Math.min(dp[(i - 1) % 2] + cost[i - 1], dp[(i - 2) % 2] + cost[i - 2]);
        }

        // Solution: dp[n % 2].  因为n是超过了cost的索引，是最终的结果
        return dp[n % 2];
    }
}
```



## 2.2 单序列动态规划：打家劫舍系列

### 打家劫舍

[198. 打家劫舍](https://leetcode.cn/problems/house-robber/)

普通dp

```java
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp多一个单位来计算，为了防止空指针之类的问题
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i - 1], dp[i - 1]);
        }
        return dp[nums.length];
    }
}
```

滚动数组

```java
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp多一个单位来计算，为了防止空指针之类的问题
        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = nums[0];
        int n = nums.length;

        for (int i = 2; i <= n; i++) {
            dp[i % 2] = Math.max(dp[(i - 2) % 2] + nums[i - 1], dp[(i - 1) % 2]);
        }
        return dp[n % 2];
    }
}
```



### 打家劫舍 II

[213. 打家劫舍 II](https://leetcode.cn/problems/house-robber-ii/)

普通dp

```java
class Solution {
    // 有题意：第一个和最后一个房子连起来，说明如果打劫了第一个则不能打劫最后一个
    // 如果从第二个开始打劫，那么可以打劫最后一个。 做两次dp。
    // 第一个dp是从第一个房子开始：
    // dp[0] = 0
    // dp[1] = nums[0]
    // dp[i] = max(dp[i - 2] + nums[i - 1], dp[i - 1])

    // 第二个dp是从第二个房子开始：
    // dp[0] = 0
    // dp[1] = 0
    // dp[i] = max(dp[i - 2] + nums[i - 1], dp[i - 1])
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 从第一个房子开始打劫
        int n = nums.length;
        int[] dp1 = new int[n + 1];
        dp1[0] = 0;
        dp1[1] = nums[0];
        for (int i = 2; i <= n; i++) {
            // 如果到了最后一个房子，则跳过
            if (i == n) {
                dp1[i] = dp1[i - 1];
                continue;
            }

            dp1[i] = Math.max(dp1[i - 2] + nums[i - 1], dp1[i - 1]);
        }

        // 从第二个房子开始打劫
        int[] dp2 = new int[n + 1];
        dp2[0] = 0;
        dp2[1] = 0;
        for (int i = 2; i <= n; i++) {
            dp2[i] = Math.max(dp2[i - 2] + nums[i - 1], dp2[i - 1]);
        }

        return Math.max(dp1[n], dp2[n]);
    }
}
```

滚动数组

```java
class Solution {
    // 有题意：第一个和最后一个房子连起来，说明如果打劫了第一个则不能打劫最后一个
    // 如果从第二个开始打劫，那么可以打劫最后一个。 做两次dp。
    // 第一个dp是从第一个房子开始：
    // dp[0] = 0
    // dp[1] = nums[0]
    // dp[i] = max(dp[i - 2] + nums[i - 1], dp[i - 1])

    // 第二个dp是从第二个房子开始：
    // dp[0] = 0
    // dp[1] = 0
    // dp[i] = max(dp[i - 2] + nums[i - 1], dp[i - 1])
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 从第一个房子开始打劫
        int n = nums.length;
        int[] dp1 = new int[2];
        dp1[0] = 0;
        dp1[1] = nums[0];
        for (int i = 2; i <= n; i++) {
            // 如果到了最后一个房子，则跳过
            if (i == n) {
                dp1[i % 2] = dp1[(i - 1) % 2];
                continue;
            }

            dp1[i % 2] = Math.max(dp1[(i - 2) % 2] + nums[i - 1], dp1[(i - 1) % 2]);
        }

        // 从第二个房子开始打劫
        int[] dp2 = new int[n + 1];
        dp2[0] = 0;
        dp2[1] = 0;
        for (int i = 2; i <= n; i++) {
            dp2[i % 2] = Math.max(dp2[(i - 2) % 2] + nums[i - 1], dp2[(i - 1) % 2]);
        }

        return Math.max(dp1[n % 2], dp2[n % 2]);
    }
}
```



### 删除并获得点数

[740. 删除并获得点数](https://leetcode.cn/problems/delete-and-earn/)

```java
class Solution {
    // 思路：选了一个数字以后，数组里面的这个数组会被全部选择。这个数字（具体值，不是索引）的左边/右边则不能选择
    // -> 打家劫舍问题，Math.max(dp[i - 1], dp[i - 2] + nums[i])
    // 可以先把nums转换成一个价值数组，这个数组索引是原来nums里面的值，即nums[i], 而索引对应的值则是nums[i] * count ，即总价值
    public int deleteAndEarn(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 求出nums最大值，确定values数组的长度
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
        }

        // new出价值数组，因为数组索引从0开始，所以总长度上需要加1
        int[] values = new int[max + 1];
        for (int num : nums) {
            values[num] += num; // 以nums = [2,2,3,3,3,4]为例 -- values = [0, 0, 4, 9, 4]
        }

        int[] dp = new int[2];
        dp[0] = 0; // dp[0] 肯定等于0. 因为在计算values数组是，nums[i] == 0 的值不管加几次，最终结果都是0
        dp[1] = values[1]; // 原本应该是 Math.max(values[0], values[1]). 因为values[0]始终等于0，所以就简化了

        for (int i = 2; i < values.length; i++) {
            dp[i % 2] = Math.max(dp[(i - 1) % 2], dp[(i - 2) % 2] + values[i]);
        }
        return dp[(values.length - 1) % 2];
    }
}
```



## 2.3 单序列动态规划：最长上升子序列LIS系列

- 题目特点：经典单序列dp问题，经典状态设计
- 解题要点：dp[i]表示以nums[i]结尾的最长递增子序列长度

### 最长递增子序列

[300. 最长递增子序列](https://leetcode.cn/problems/longest-increasing-subsequence/)

```java
class Solution {
    // 思路：dp[i]: 以nums[i]结尾的最长严格递增子序列的长度
    // 那么我们就便利nums，取出每一个数字来，和这个数字之前的每一个数nums[j]比，如果说大于之前的数nums[j]，那么i这个位置上
    // dp的值，就应该是dp[i] = Math.max(dp[0] + 1...dp[i - 1] + 1)
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 制表法
        // State: dp[i]: 以nums[i]结尾的最长严格递增子序列的长度
        // 以nums[i]结尾的最长严格递增子序列的长度至少为1，所以初始化为1
        // 因为最终要便利dp数组去的最长严格递增子序列的长度，所以无法压缩状态，不能用滚动数组
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            // Function: 通过制表法。
            // 遍历找之前结尾比nums【i】小的递增子序列，然后找到其中最长的
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // Solution: 计算结果
        int result = 0;
        for (int value : dp) {
            result = Math.max(result, value);
        }
        return result;
    }
}
```



### 最长递增子序列的个数

[673. 最长递增子序列的个数](https://leetcode.cn/problems/number-of-longest-increasing-subsequence/)

```java
class Solution {
    public int findNumberOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // State
        // dp[i]: 表示以i结尾的最长递增子序列的长度，初始化为1
        // count[i]: 表示以i结尾的最长递增子序列的的个数，初始化为1
        int[] dp = new int[nums.length];
        int[] count = new int[nums.length];
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);

        // Function
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 只有当nums[i] > nums[j]的时候，才可能出现更长的子序列
                if (nums[i] > nums[j]) {
                    // 出现更长的子序列以后需要做两件事
                    // 1. 更新dp[i]的长度，因为出现更长的子序列了
                    // 2. 更新count[i]的个数：如果dp[j] + 1 > dp[i] -> 说明新的最长子序列出现了，需要把dp[i]更新成dp[j] + 1, count[i] = count[j]
                    //                      如果dp[j] + 1 == dp[i] -> 说明等长的最长子序列出现了，需要把count[i] += 1
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        count[i] += count[j]; // PS: 这里不能+=1
                    } else {
                        // 没有这种情况，因为
                    }
                }
            }
        }

        // Solution:
        // 找到最长的最长子序列的长度
        int max = 0;
        for (int num : dp) {
            max = Math.max(num, max);
        }

        int result = 0;
        for (int i = 0; i < dp.length; i++) {
            if (max == dp[i]) {
                result += count[i];
            }
        }
        return result;
    }
}
```



### 俄罗斯套娃信封问题

[354. 俄罗斯套娃信封问题](https://leetcode.cn/problems/russian-doll-envelopes/)

```java
class Solution {
    // 思路： width生序排序，如果width一样的话，堆宽度逆序排序，这样的话在对宽度求最长子序列长度即可求出答案
    // 会超时一些case. 85 / 87 个通过测试用例
    /**
        例子： 这样的话就能求出 1，2，3
        [2,3].
        [5,4],
        [6,7],
        [6,4]

        更为复杂的例子：答案就是1，2，4，5.  对宽度求最长子序列的时候会自动把[6,7]过滤掉，去选择[6,5]
        [2,3].
        [5,4],
        [6,7],
        [6,5],
        [7,6]
     */
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0 || envelopes[0].length == 0) {
            return 0;
        }

        // Sort
        Arrays.sort(envelopes, (int[]a, int[]b) -> {
            return a[0] == b[0] ? b[1] - a[1] : a[0] - b[0];
        });

        // 把排好序的信封的高度做成一个数组，然后进行最长子序列取得
        int[] heights = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            heights[i] = envelopes[i][1];
        }

        return lengthOfLIS(heights);
    }

    // 思路：dp[i]: 以nums[i]结尾的最长严格递增子序列的长度
    // 那么我们就便利nums，取出每一个数字来，和这个数字之前的每一个数nums[j]比，如果说大于之前的数nums[j]，那么i这个位置上
    // dp的值，就应该是dp[i] = Math.max(dp[0] + 1...dp[i - 1] + 1)
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp[i]: 表示以i结尾的最长子序列的长度,初始化为1
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 当nums[i] > nums[j]的时候，才会产生更长的子序列
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int result = 0;
        for (int value : dp) {
            result = Math.max(result, value);
        }
        return result;
    }
}
```



## 2.4 单序列动态规划：最大子数组和系列

- 题目特点：求连续区间的最值问题，不能间断
- 解题要点：dp[i]围绕以nums[i]结尾定义



