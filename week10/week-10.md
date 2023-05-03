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

