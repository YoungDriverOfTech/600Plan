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



### 子集2

[90. 子集 II](https://leetcode.cn/problems/subsets-ii/)

```java
class Solution {

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 解集
        List<List<Integer>> result = new ArrayList<>();

        // 校验input
        if (nums == null || nums.length == 0) {
            return result;
        }

        // 单一解
        List<Integer> list = new ArrayList<>();

        // 排序
        Arrays.sort(nums);

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
            // 剪枝
            if (i != pos && nums[i] == nums[i - 1]) {
                continue;
            }

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



### 全排列

nums数组长度为n，全排列的个数为n!

[46. 全排列](https://leetcode.cn/problems/permutations/)

```java
class Solution {
  
  	// Time: O(n!)
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        List<Integer> list = new ArrayList<>();
        helper(result, list, nums);

        return result;
    }

    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums) {
        if (list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (list.contains(nums[i])) {
                continue;
            }
            list.add(nums[i]);
            helper(result, list, nums);
            list.remove(list.size() - 1);
        }
    }
}
```



## 全排列2

[47. 全排列 II](https://leetcode.cn/problems/permutations-ii/)

如果有重复元素，选择靠前的，后面的全部都是重复

```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[nums.length]; // 访问过就是true
        Arrays.sort(nums);
        
        helper(result, list, nums, visited);

        return result;
    }

    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, boolean[] visited) {
        if (list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // 【1，1，1】： 到底是已经访问过了
            // 【1(1), 1(2), 2】【1(2), 1(1), 2】重复元素. 第一轮的时候会把第一个list装进result，然后第一个1(1)在被
            // 重制为false。 第二轮的时候，发现如果元素相等，并且前一个没被访问过，说明前一轮被重置成false，那么应该跳过当前元素
            if (visited[i] || (i != 0 && nums[i] == nums[i - 1] && !visited[i - 1])) {
                // 如果有重复元素，选择靠前的，后面的全部都是重复.!nums[i - 1]条件保证了选择靠前的
                // 如果回溯到了靠后的相同元素，会执行continue来跳过。
                continue;
            }
            
            list.add(nums[i]);
            visited[i] = true;
            helper(result, list, nums, visited);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }
}
```



### 组合总和

[39. 组合总和](https://leetcode.cn/problems/combination-sum/)

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        if (candidates == null || candidates.length == 0) {
            return result;
        }

        List<Integer> list = new ArrayList<>();
        helper(result, list, candidates, target, 0);
        return result;
    }

    private void helper(List<List<Integer>> result, List<Integer> list, int[] candidates, int target, int pos) {
        // 递归什么时候退出？
        // 单一解什么时候加入result
        if (target == 0) {
            result.add(new ArrayList<Integer>(list));
        }

        // 如果target<0 ,应该直接退出. 剪枝
        if (target < 0) {
            return;
        }

        // 递归，到下一层
        for (int i = pos; i < candidates.length; i++) {
            list.add(candidates[i]);

            helper(result, list, candidates, target - candidates[i], i); // pos取i，因为一个数字可以被多次取

            list.remove(list.size() - 1);
        }
    }
}
```



### 组合总和2

[40. 组合总和 II](https://leetcode.cn/problems/combination-sum-ii/)

```java
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        if (candidates == null || candidates.length == 0) {
            return result;
        }

        Arrays.sort(candidates);
        List<Integer> list = new ArrayList<>();
        helper(result, list, candidates, target, 0);
        return result;
    }


    private void helper(List<List<Integer>> result, List<Integer> list, int[] candidates, int target, int pos) {
        // 递归什么时候退出？
        // 单一解什么时候加入result
        if (target == 0) {
            result.add(new ArrayList<Integer>(list));
        }

        // 如果target<0 ,应该直接退出. 剪枝
        if (target < 0) {
            return;
        }

        // 递归，到下一层
        for (int i = pos; i < candidates.length; i++) {
            // 去重, i > pos 不是0
            if (i > pos && candidates[i] == candidates[i - 1]) {
                continue;
            }

            list.add(candidates[i]);

            helper(result, list, candidates, target - candidates[i], i + 1);

            list.remove(list.size() - 1);
        }
    }
}
```





### 复原ip地址

93

```java


```

