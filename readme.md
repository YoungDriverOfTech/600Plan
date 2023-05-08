# Week 1
[Basic Math Knowledge](./week1/basicMath.md)   
[Time Complexity](./week1/timeComplexity.md)   
[Week-1](./week1/week-1.md) 

## 前缀和公式
```java
// 构造前缀和
int[] prefixSum = new int[num.length + 1];
for (int i = 0; i < num.length; i++) {
    prefixSum[i + 1] = prefixSum[i] + num[i];
}

// 获取某一个元素
nums[i] = prefixSum[i + 1] - prefixSum[i]

// 获取某一段的和
interval[i, j] = prefixSum[j + 1] - prefixSum[i]
```


# Week 2
[Week-2](./week2/week-2.md) 



# Week 3

[Week-3](./week3/week-3.md) 



# Week 4

[Week-4](./week4/week-4.md) 



# Week 5

[Week-5](./week5/week-5.md) 



# Week 6

[Week-6](./week6/week-6.md) 



# Week 8

[Week-8](./week8/week-8.md) 



# Week 9

[Week-9](./week9/week-9.md) 



# Week 10

[Week-10](./week10/week-10.md) 
