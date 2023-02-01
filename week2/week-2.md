# 1 栈的初始化和基本操作

## 1.1 初始化

```java
Deque<Integer> stack = new ArrayDeque<>();
Deque<Integer> stack = new LinkedList<>();
```

## 1.2 基本操作

```java
// 时间复杂度都是O(1)
stack.push(x);
stack.pop();
stack.peek();
stack.isEmpty();
```



# 2 队列的初始化和基本操作

## 2.1 初始化

```java
Queue<Integer> queue = new LinkedList<>();
```



## 2.2 基本操作

```java
// 时间复杂度都是O(1)
queue.offer();
queue.poll();
queue.peek();
queue.isEmpty()
```



# 3 实战  

## 3.1 双栈实现队列
LIFO + LIFO = FIFO  
[232. 用栈实现队列](https://leetcode.cn/problems/implement-queue-using-stacks/)

```java
class MyQueue {
    // Space: O(n)
    // Time: push, empty O(1)/ pop, peek 因为这个是针对一个元素来进行操作，一个元素最多可以进行的操作只有4个， stack1->in  stack1->out  stack2-> in  stack2->out
    // 所以时间复杂度，算下来是O(1)
    private Deque<Integer> stack1;
    private Deque<Integer> stack2;

    public MyQueue() {
        stack1 = new ArrayDeque<>();
        stack2 = new ArrayDeque<>();
    }
    
    public void push(int x) {
        stack1.push(x);
    }
    
    public int pop() {
        shiftStack();
        return stack2.pop();
    }
    
    public int peek() {
        shiftStack();
        return stack2.peek();
    }
    
    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    // 为什么要把stack1的数字倒腾进stack2，是因为LIFO + LIFO = FIFO
    // 加上if (stack2.isEmpty()) 是因为如果前一次把stack1的数据倒进了stack2中，然后又push了几个到stack1，没有if做判断的会，会把队列顶部的元素给替换掉
    private void shiftStack() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }
}
```

## 3.2 最小栈

[155. 最小栈](https://leetcode.cn/problems/min-stack/)

```java
class MinStack {

    // Time: O(1)  Space: O(n)
    // 双栈，最小栈随着每次push，都记录一个最小值。但是初始化的时候要记录一个最大的数防止空指针

    private Deque<Integer> stack;
    private Deque<Integer> minStack;

    public MinStack() {
        stack = new LinkedList<>();
        minStack = new LinkedList<>();
        minStack.push(Integer.MAX_VALUE);   // 加这个是为了防止push方法的空指针
    }
    
    public void push(int val) {
        stack.push(val);
        minStack.push(Math.min(val, minStack.peek()));
    }
    
    public void pop() {
        stack.pop();
        minStack.pop(); // minStack别忘记Pop
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}
```

## 3.3 有效括号

[20. 有效的括号](https://leetcode.cn/problems/valid-parentheses/)
```java
class Solution {

    // Time: O(n)  Space: O(n)
    public boolean isValid(String s) {
        // 如果为空或者奇数个，肯定不满足
        if (s == null || s.length() == 0 || s.length() % 2 != 0) {
            return false;
        }

        // 遇到左括号押栈，右括号弹栈并且比较
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (isLeftParenthesis(ch)) {
                stack.push(ch);
            } else {
                // 如果是右括号，先看看栈里面有没有元素，如果没有说明右括号多余，直接返回即可
                if (stack.isEmpty()) {
                    return false;
                }

                // 如果栈有元素，那么就进行有效性比较
                char left = stack.pop();
                if (!isValid(left, ch)) {
                    return false;
                }
            }
        }

        // 防止都是做括号，应该返回栈是否是空的信息
        return stack.isEmpty();
    }

    private boolean isLeftParenthesis(char ch) {
        return (ch == '(') || (ch == '{') || (ch == '[');
    }

    private boolean isValid (char left, char right) {
        if (right == ')') {
            return left == '(';
        } else if (right == '}') {
            return left == '{';
        } else if (right == ']') {
            return left == '[';
        } else {
            return false;
        }
    }
}
```



## 3.4 逆波兰表达式

[150. 逆波兰表达式求值](https://leetcode.cn/problems/evaluate-reverse-polish-notation/)

```java
class Solution {
    // 遇到数字压栈，遇到操作符弹出两个数字，计算后再压栈。
    // Time: O(n)  Space: O(n)
    public int evalRPN(String[] tokens) {

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (isOperator(token)) {
                int lastNum = deque.pop();
                int firstNum = deque.pop();

                int result = operate(firstNum, lastNum, token);
                deque.push(result);
            } else {
                deque.push(Integer.parseInt(token));
            }
        }
        return deque.pop();
    }

    private boolean isOperator(String token) {
        return ("+".equals(token)) || ("-".equals(token)) || ("*".equals(token)) || ("/".equals(token));
    }

    private int operate(int firstNum, int lastNum, String token) {
        if ("+".equals(token)) {
            return firstNum + lastNum;
        } else if ("-".equals(token)) {
            return firstNum - lastNum;
        } else if ("*".equals(token)) {
            return firstNum * lastNum;
        } else {
            return firstNum / lastNum;
        }
    }
}
```



## 3.5 单调栈

在栈的FIFO基础上增加一个额外的特性：从栈顶（栈低）到栈低（栈顶）的元素是严格递增或者严格递减。
### 单调递减栈-从栈低到栈顶单调递减
- 只要比栈顶元素小的元素才能直接进栈，否则需要先将栈中比当前元素小的元素都出栈，再将当前元素入栈
- 保证栈中保留的都是比当前入栈元素大的值
- 从栈低到栈顶的元素值是单调递减的
- 模板
```java
// 单调递减栈 bottom -> top
Deque<Integer> stack = new ArrayDeque<>();
for (int i = 0; i < nums.length; i++) {
    while (!stack.isEmpty() && stack.peek() < nums[i]) {
        stack.pop();
    }
    stack.push(nums[i]);
}
```

### 单调递减栈-从栈低到栈顶单调递增
- 只要比栈顶元素大的元素才能直接进栈，否则需要先将栈中比当前元素大的元素都出栈，再将当前元素入栈
- 保证栈中保留的都是比当前入栈元素小的值
- 从栈低到栈顶的元素值是单调递减的
- 模板
```java
// 单调递增栈 bottom -> top
Deque<Integer> stack = new ArrayDeque<>();
for (int i = 0; i < nums.length; i++) {
    while (!stack.isEmpty() && stack.peek() > nums[i]) {
        stack.pop();
    }
    stack.push(nums[i]);
}
```

## 3.6 重要：Next Greater Number问题  

给定一个整型数组nums,要求打印出所有元素右边第一个大于该元素的值。  
case1: 数组nums[1,5,3,6,4,8,9,10] -> [5,6,6,8,8,9,10,-1]  
case2: 数组nums[8.2.5.4.3.9.7.2.5] -> [5,9,9,9,-1,-1,5,-1]    

> 大单减小单增，遍历方向题目中

```java
public int[] findRightNextGreater(int[] nums) {
    // 找大的，单减栈。遍历方向题目中是右
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);

    // 这个栈不装value了，装索引。因为要给result的赋值的时候需要用到索引
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < nums.length; i++) {
        
        // 因为是递减栈，所以新元素 > 顶元素的时候不满足单减，要把栈里面的元素pop掉
        for (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            int index = stack.pop();
            result[index] = nums[i];
        }

        stack.push(i);
    }

    return result;
}

public int[] findRightNextSmaill(int[] nums) {
    // 找小的，单增栈。遍历方向题目中是右
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);

    // 这个栈不装value了，装索引。因为要给result的赋值的时候需要用到索引
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < nums.length; i++) {
        
        // 因为是递增栈，所以新元素 < 顶元素的时候不满足单增，要把栈里面的元素pop掉
        for (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
            int index = stack.pop();
            result[index] = nums[i];
        }

        stack.push(i);
    }

    return result;
}

public int[] findLeftNextGreater(int[] nums) {
    // 找大的，单减栈。遍历方向题目中是左
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);

    // 这个栈不装value了，装索引。因为要给result的赋值的时候需要用到索引
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = nums.length - 1; i >= 0; i--) {
        
        // 因为是递减栈，所以新元素 > 顶元素的时候不满足单减，要把栈里面的元素pop掉
        for (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            int index = stack.pop();
            result[index] = nums[i];
        }

        stack.push(i);
    }

    return result;
}

public int[] findLeftNextSmaill(int[] nums) {
    // 找小的，单增栈。遍历方向题目中是左
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);

    // 这个栈不装value了，装索引。因为要给result的赋值的时候需要用到索引
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = nums.length - 1; i >= 0; i--) {
        
        // 因为是递增栈，所以新元素 < 顶元素的时候不满足单增，要把栈里面的元素pop掉
        for (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
            int index = stack.pop();
            result[index] = nums[i];
        }

        stack.push(i);
    }

    return result;
}
```



## 3.7 每日温度

[739. 每日温度](https://leetcode.cn/problems/daily-temperatures/)

```java
class Solution {
  	// Time: O(n)  Space: O(n)
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return null;
        }

        int[] result = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int index = stack.pop();
                result[index] = i - index;
            }

            stack.push(i);
        }

        return result;
    }
}
```



## 3.8 下一个更大元素1

[496. 下一个更大元素 I](https://leetcode.cn/problems/next-greater-element-i/)

```java
class Solution {
    // 因为nums2是超集，只要把nums2的下一个最大元素找出来，那么就能找到nums1
  	// Time: O(n)  Space: O(n)
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        // 用Map来记录nums2中每个元素的下一个最大元素
        Map<Integer, Integer> map = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {	// 主要不要写成 || 脑子要清醒
                int smallNum = stack.pop();
                map.put(smallNum, nums2[i]);
            }

            stack.push(nums2[i]);
        }

        // 遍历num1找到下一个最大元素
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = map.getOrDefault(nums1[i], -1);
        }
        return result;
    }
}
```



## 3.9 下一个更大元素2

[503. 下一个更大元素 II](https://leetcode.cn/problems/next-greater-element-ii/)

```java
class Solution {
    // 因为是循环的数组，所以遍历的时候把长度编程两倍，这样最后一个元素就可以在下一遍遍历里面找比他大的元素了
    // 索引怎么办呢？ 用当前索引对长度取模，就能找到第二圈的元素的值
  	// Time: O(n)  Space: O(n)
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();
        int len = nums.length;
        for (int i = 0; i < len * 2; i++) {	// 这里是len * 2
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % len]) { // 取余数的时候要对原来的length取
                int index = stack.pop();
                result[index] = nums[i % len];	// 下一个更大的值，就是还未入栈的新值
            }

            stack.push(i % len);
        }
        return result;
    }
}
```



## 4.0 柱状图中最大矩形

[84. 柱状图中最大的矩形](https://leetcode.cn/problems/largest-rectangle-in-histogram/)

```java
class Solution {
    // 思路： 求最大矩形的面积，就看每根柱子两侧首次小于此柱子高度的位置。记录下这两个位置right和left
    // 那么right - left - 1就是宽度（为什么要减去1呢？ 带入特殊值计算一下即可得到right - left是多着一个1的）
    // 高度就是当前柱子的高度
    // 那么问题就转化成为了求每个元素右边下个小值 -> 递增栈，左到右
    //                  求每个元素左边下个小值 -> 递增栈，右到左
    // 然后遍历height数组，求醉最大面积即可
    // PS：需要注意的是特殊的边界情况。对于第一个元素，他左边的高度设置为0，且索引为-1. 最后一个元素高度为0，索引为length
    //     更加极端的一些情况是如果每个元素都相等，那么每个元素左边的第一个较小值都是-1，右边的较大值都是length，所以初始化left和right数组的时候都填充上-1， length即可
  	// Time: O(n)  Space: O(n)
    public int largestRectangleArea(int[] heights) {
        
        int[] left = new int[heights.length];
        int[] right = new int[heights.length];
        // 处理特殊边界
        Arrays.fill(left, -1);
        Arrays.fill(right, heights.length);

        // 每个元素右边下个小元素边界
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int index = stack.pop();
                right[index] = i;
            }
            stack.push(i);
        }

        // 每个元素左边下个小元素边界
        stack.clear();
        for (int i = heights.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int index = stack.pop();
                left[index] = i;
            }
            stack.push(i);
        }

        // 计算每个柱子能构成的最大面积，注意宽度是right - left - 1，别忘了-1
        int result = 0;
        for (int i = 0; i < heights.length; i++) {
            result = Math.max(result, (right[i] - left[i] - 1) * heights[i]);
        }
        return result;
    }
}
```

```java
class Solution {

    // 一次栈遍历
    public int largestRectangleArea(int[] heights) {

        int len = heights.length;
        int[] left = new int[len];
        int[] right = new int[len];
        Arrays.fill(right, len);
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {   // 绝对单调递增栈
                int index = stack.pop();
                right[index] = i;
            }
            // 每次入栈就得要计算左边界, 如果栈是空的，那么-1就是边界，如果栈不是空的，因为站内是递增的，所以栈顶的元素一定是弹出元素的左边界
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // 计算面积
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = Math.max(result, (right[i] - left[i] - 1) * heights[i]);   // 记得写result = ，低级错误
        }
        return result;
    }
}
```

## 4.1 
