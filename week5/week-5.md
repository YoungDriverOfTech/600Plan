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

