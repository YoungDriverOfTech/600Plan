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

