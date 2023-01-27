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

