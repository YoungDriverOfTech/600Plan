# 1 回溯法

## 1.1 题目特点

- 大多数为dfs问题中的一维问题，少数为二叉树或者矩阵
- 【重要】实现：DFS+剪枝
- 【重要】找出所有方案（大多数）/找出最优方案（极少）：要想到回溯法
- 【重要】大多数题目可分为组合问题或者排列问题



## 1.2 什么是回溯法

### 从递归问题说起看回溯

![recursion](./images/recursion-backtrack.png)



## 1.3 回溯模板心法

- 组合：求子集问题
- 排列：求排列问题



### 组合问题

- 求所有满足条件的组合，本质就是求子集
- 解空间树结构为子集树
- 时间复杂度：O(2^n)
- 组合/子集问题的解中元素常与顺序无关
- 求解会挑选原数据集的部分元素而不是全部



### 排列

- 我爱罗/我罗爱/爱我罗/爱罗我/罗我爱/罗爱我
- 解空间树结构为排列树
- 时间复杂度O(n!)
- 排列问题的解中选素常与顺序相关
- 求解会挑选原数据集的全部元素



### 模板

```java
public List<List<Integer>> solveProblem(int[] nums) {
  // result list
  List<List<Integer>> result = new ArrayList<>();
  
  // check empty
  if (nums == null || nums.length == 0) {
    return result;
  }
  
  // single answer
  List<Integer> list = new ArrayList<>();
  
  // sort nums if necessary
  // Arrays.sort(nums);
  
  helper(result, list, nums, ?);
  
  return result
}

private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int pos, ?){
  // end recursion condition
  if (condition xx) {
    return;
  }
  
  // add single answer into result
  if (condition xx) {
    result.add(new ArrayList(list));
  }
  
  // 剪枝
  if (condition xx) {
    // doSomething
    return;
  }
  
  // 递归拆解子问题到下一层
  for (sub : total-subs) {
    if (condition xx) {
      // 剪枝
      break; // or continue;
    }
    list.add(element);
    helper(result, list, nums, ?);
    // 回溯
    list.remove(element);
  }
}
```



## 1.4 实战

### 子集

[78. 子集](https://leetcode.cn/problems/subsets/)

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        // 解集
        List<List<Integer>> result = new ArrayList<>();

        // 校验input
        if (nums == null || nums.length == 0) {
            return result;
        }

        // 单一解
        List<Integer> list = new ArrayList<>();

        // 排序（本题不需要）
        // Arrays.sort(nums);

        // 计算解集：把单一list放入result中
        helper(result, list, nums, 0);

        return result;
    }

    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int pos) {
        // 1 什么时候退出递归

        // 2 什么时候可以把单一解加(Deep copy)入到result中 -> 重点
        result.add(new ArrayList<Integer>(list));

        // 3 递归分解成子问题，到下一层（可能需要剪枝）
        for (int i = pos; i < nums.length; i++) {
            // (1) 把nums[i]加到单一解list中
            list.add(nums[i]);
            // (2) 递归计算以nums[i]结尾的子序列的单一解
            helper(result, list, nums, i + 1);
            // (3) 回溯，去掉最后一个元素
            list.remove(list.size() - 1);
        }
    }
}
```
