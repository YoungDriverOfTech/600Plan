# 1 DFS 深度优先

## 1.1 概述

除了树和一位数组之外，面试中还会考察下列数据结构上的DFS

- 一般图
- 矩阵（二位数组）

【重点】DFS+涂色标记（避免节点重复访问）

【重点】搜索节点如何移动

【重要】同时能用DFS/BFS求解的图或者矩阵问题，请优先考虑BFS



## 1.2 图的基本概念

- 图G(V, E): 由一系列定点（Vertices）和边（Edges）连接而成
- 有向边与有向图
  - 有向边e = （u, v） -> u为边的起始定点，v为边的终止定点
  - 有向图: 有向边组成的图
  - 有向无环图（DAG）: 无法从某个定点出发经过若干条边回到该点的有向图
- 无向图：边不带方向
- 带权图：边带着权重



## 1.3 图的表示

- 邻接矩阵

![linjiejuzhen](./images/linjiejuzhen.png)

---

![linjiejuzhen](./images/youxiangtujuzhen.png)

- 邻接表

对某一个节点来说，有几条边和他相接，那么相接后的节点组成了邻接表

![linjiejuzhen](./images/linjiebiao.png)



## 1.4 图搜索

![linjiejuzhen](./images/graphsearch.png)

Time complexity:

BFS: 

- 邻接表：O（节点数 + 边数）
- 邻接矩阵：O（节点数^2）

DFS:

- 邻接表：O（节点数 + 边数）
- 邻接矩阵：O（节点数^2）

Space complexity:

DFS: O(节点数)

BFS: O(节点数)

### 结论

如果题目属于图搜索问题，既能采用DFS，也能采用BFS，优先考虑BFS



## 1.5 图DFS/BFS模板
```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Undirected graph
public class Graph {

    class Node {
        int no;
        int value;

        public Node(int no, int value) {
            this.no = no;
            this.value = value;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    // 邻接表  Key: 当前节点   Value： 邻接节点
    private final Map<Node, List<Node>> adjacencyList;

    // 是否被访问涂色过
    private boolean[] marked;

    public Graph(int vertexCount) {
        this.adjacencyList = new HashMap<>();
        this.marked = new boolean[vertexCount];
    }

    public Graph(Map<Node, List<Node>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public Graph(Map<Node, List<Node>> adjacencyList, boolean[] marked) {
        this.adjacencyList = adjacencyList;
        this.marked = marked;
    }

    // 方法
    // 加节点
    public void addVertex(Node v) {
        this.adjacencyList.put(v, new ArrayList<>());
    }

    // 给两个节点加上边
    public void addEdge(Node u, Node v) {
        this.adjacencyList.get(u).add(v);
        this.adjacencyList.get(v).add(u);
    }

    public void printGraph() {
        for (Node key : adjacencyList.keySet()) {
            List<Node> adjNodes = adjacencyList.get(key);
            System.out.println("key = " + key.getNo() + " adjNodes = " + adjNodes);
        }
    }

    // dfs模板
    public void dfs(Node start) {

        // 标记本节点已经被访问
        marked[start.getNo()] = true;

        // 邻接节点
        for (Node adjNode : adjacencyList.get(start)) {
            if (!marked[adjNode.getNo()]) {
                dfs(adjNode);
            }
        }

    }


    // bfs
    public void bfs() {

    }
}
```

## 1.6 实战

### 无向图中连通分量的数目

![recursion](./images/323.png)
```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        // count：做了几次DFS
        int count = 0;

        // step 0: 构建邻接表
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // 因为要构造成无向图，所以节点都需要双连
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // step 1: 套模板
        boolean[] marked = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 只有没有被标记过，才能进行dfs
            if (!marked[i]) {
                dfs(adj, marked, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(Map<Integer, List<Integer>> adj, boolean[] marked, int nodeNum) {
        // 首先标记节点
        marked[nodeNum] = true;

        // dfs
        for (int node : adj.get(nodeNum)) {
            if (!marked[node]) {
                dfs(adj, marked, node);
            }
        }
    }
}
```



### 省份数量

[547. 省份数量](https://leetcode.cn/problems/number-of-provinces/)

```java
class Solution {
    // 求无向图中联通分量的个数
    // 第一个例子：现在就是有3个城市，isConnected这个二维数组就是用来描述这3个城市的相连状况
    // 第一行是第一个城市和其他城市的相连情况
    // 第二行是第二个城市和其他城市的相连情况
    // 第三行是第三个城市和其他城市的相连情况

    // 如果横坐标纵坐标一样，说明城市自己是相连的
    public int findCircleNum(int[][] isConnected) {
        int count = 0;

        // 创建标记城市数量的数组
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int cityNum) {
        visited[cityNum] = true;

        for (int i = 0; i < isConnected[cityNum].length; i++) {
            int connectStatus = isConnected[cityNum][i];
            if (connectStatus == 1 && !visited[i]) {
                dfs(isConnected, visited, i);
            }
        }
    }
}
```
```java
class Solution {
    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        int citys = isConnected.length; 
        for (int i = 0; i < citys; i++) {
            List<Integer> adjacentCitys = map.getOrDefault(i, new ArrayList<>());
            int[] conections = isConnected[i];

            for (int j = 0; j < conections.length; j++) {
                if (isConnected[i][j] == 1) {
                    adjacentCitys.add(j);
                }
            }
            map.put(i, adjacentCitys);
        }

        boolean[] visited = new boolean[citys];
        int count = 0;
        for (int i = 0; i < citys; i++) {
            if (!visited[i]) {
                dfs(map, visited, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(Map<Integer, List<Integer>> map, boolean[] visited, int i) {
        visited[i] = true;

        for (int city : map.get(i)) {
            if (!visited[city]) {
                dfs(map, visited, city);
            }
        }
    }
}
```


### 以图判树

![recursion](./images/261-1.png)

![recursion](./images/261-2.png)

![recursion](./images/261-3.png)





```java
class Solution {
    // 树：边树=节点数-1
    // 联通分量必须得是1
    public int sulution(int n, int[][] edges) {
        if (n == 0) {
          return false;
        }
      	
      	// 树：边树=节点数-1
      	if (edges.length != n - 1) {
          return false;
        }
      
      	// step 1 构建邻接表
      	Map<Integer, List<Integer>> map = new HashMap<>();
      	for (int i = 0; i < n; i++) {
        	map.put(i, new ArrayList<Integer>());
        }
      
      	// step 2 填充邻接节点到邻接表
      	for (int i = 0; i < edges.length; i++) {
          int u = edges[i][0];
          int v = edges[i][1];
          
          map.get(u).add(v);
          map.get(v).add(u);
        }
      
      	// step 计算联通分量
      	int count = 0;
      	boolean[] visited = new boolean[edges.length];
      	for (int i = 0; i < n; i++) {
          if (!visited[i]) {
            dfs(map, visited, i);
            count++;
          }
        }
    }
  
  
    private void dfs(Map<Integer, List<Integer>> map, boolean[] visited, int node) {
      visited[node] = true;
      
      for (int adjNode : map.get(node)) {
        if (!visited[adjNode]) {
          dfs(map, visited, adjNode);
        }
      }
    }
}
```



### 133 克隆图

[133. 克隆图](https://leetcode.cn/problems/clone-graph/)

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {

    // 用Map<Old, New>来做映射，然后神拷贝每个节点，最后返回即可
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        return dfs(map, node);
    }

    private Node dfs(Map<Node, Node> map, Node node) {
        if (map.containsKey(node)) {
            return map.get(node);
        }

        // map中不包含的话，就需要进行神拷贝了
        Node newNode = new Node(node.val); // 邻接的list回自动被创建出来
        map.put(node, newNode);

        for (Node neighbor : node.neighbors) {
            // 需要对每一个邻接的节点进行神拷贝，然后加到newNode的邻接list中
            Node newNeighbor = dfs(map, neighbor);
            newNode.neighbors.add(newNeighbor);
        }
        return newNode;
    }
}
```



# 2 二维矩阵题目

## 2.1 分类及特点

- 分类
  - 【常见】迷宫问题
  - 【重点】岛屿问题
- 特点
  - 二维数组/矩阵问题有区域限制和移动方向限制
  - 能移动的方向集合即位邻接节点集合
  - 题目有特定条件限制
- 重点
  - dfs+涂色标记
  - 有可能需要剪枝



## 2.2 技巧：怎么在二维空间移动

![recursion](./images/erweiyidong.png)

代码实现

```java
// version
final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
for (int[] offset : directions) {
  Point next = new Point(cur.x + offset[0], cur.y + offset[1]);
}

// version
int[] dx = {1, 0, -1, 0};
int[] dy = {0, 1, 0, -1};
for (int i = 0; i < 4; i++) {
  Point next = new Point(cur.x + dx[i] + cur.y + dy[i]);
}
```



## 2.3 二维DFS模板代码

```java
class Point {
  int x;
  int y;
}

private boolean checkRange(int[][] matrix, Point point) {
  return point.x >= 0 
    && point.x <= matrix.length
  	&& point.y >= 0
    && point.y < matrix[0].length;
}

public void dfsInMatrix(int[][] matrix) {
  if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
    return;
  }
  
  int m = matrix.length;
  int n = matrix[0].length;
  boolean[][] visited = new boolean[m][n];
  for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
      Point start = new Point(i, j);
      if (special-condition && !visited[i][j]) {
        dfs(matrix, visited, start);
      }
    }
  }
}

// version 1
public void dfs(int[][] matrix, boolean[][] visited, Point point) {
  // 剪枝
  if (condition) {
    return;
  }
  
  // 标记
  visited[point.x][point.y] = true;
  
  // 移动
  int[] dx = {1, 0, -1, 0};
  int[] dy = {0, 1, 0, -1};
  for (int i = 0; i > 4; i++) {
    Point newPoint = new Point(point.x + dx[i], point.y + dy[i]);
    if (checkRange(matrix, newPoint) && !visited[newPoint.x][newPoint.y]) {
      dfs(matrix, visited, newPoint);
    }
  }
}

// version 2
public void dfs(int[][] matrix, boolean[][] visited, Point point) {
  if (!checkRange(matrix, point)) {
    return;
  }
  
  // 剪枝
  if (condition 1) {
    return;
  }
  
  // 标记
  visited[point.x][point.y] = true;
  
  // 移动
  int[] dx = {1, 0, -1, 0};
  int[] dy = {0, 1, 0, -1};
  for (int i = 0; i < 4; i++) {
    Point newPoint = new Point(point.x + dx[i], point.y + dy[i]);
    dfs(matrix, visited, newPoint);
  }
}
```



## 2.4 实战

### 迷宫类问题

#### 基础迷宫

![recursion](./images/jichumigong.png)

```java
// 找到s->e 所有路径，然后移动只能上下左右
public class Maze {
  class Point {
    int x;
    int y;
  }
  
  public List<List<Point>> solveMaze(int[][] maze, Point start, Point end) {
    List<List<Point>> result = new ArrayList<>();
    if (maze == null || maze.length == 0 || maze[0] == null || maze[0].length == 0) {
      return result;
    }
    
    int m = maze.length;
    int n = maze[0].length;
    boolean[][] visited = new boolean[m][n];
    
    List<Point> path = new ArrayList<>();
    path.add(start);
    dfs(result, path, maze, visited, start, end);
    return result;
  }
  
  private void dfs(List<List<Point>> result, 
                   List<Point> path, 
                   int[][] maze, 
                   boolean[][] visited, 
                   Point cur, 
                   Point end) {
    
    // 剪枝：当前是黑块
    if (maze[cur.x][cur.y] == 1) {
      return;
    }
    
    // 标记
    visited[cur.x][cur.y] = true;
    
    // 找到单一解
    if (cur.x == end.x && cur.y == end.y) {
      result.add(new ArrayList(path));
      // 取消标记，因为回溯需要
    	visited[cur.x][cur.y] = false;
      return;
    }
    
    // 移动
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    for (int i = 0; i < 4; i++) {
      Point newPoint = new Point(cur.x + dx[i], cur.y + dy[i]);
      if (checkRange(maze, newPoint) && !visited[newPoint.x][newPoint.y]) {
        
        // 因为要求所有的路径，所以要进行回溯
        path.add(newPoint);
        dfs(result, path, maze, visited, newPoint, end);
        path.remove(path.size() - 1);
      }
    }
    
    // 取消标记，因为回溯需要
    visited[cur.x][cur.y] = false;
  }
  
  
  private boolean checkRange(int[][] maze, Point point) {
    return point.x >= 0 
      && point.x < maze.length
      && point.y >= 0 
      && point.y < maze[0].length;
  }
}
```



#### 迷宫

![recursion](./images/490.png)

```java
class Solution {
  private int[] dx = {1, 0, -1, 0};
  private int[] dy = {0, 1, 0, -1};
  
  // 因为只用返回有没有路径，所以不需要回溯，只要能找到一条路径就行
  // 当开始运动的时候，先朝着一个方向走，途中不能换方向，直到碰到墙壁才能换方向
  public boolean hasPath(int[][] maze, int[] start, int[] dest) {
    if (maze == null || maze.length == 0 || maze[0] == null || maze[0].length == 0) {
      return false;
    }
    
    int m = maze.length;
    int n = maze[0].length;
    boolean[][] visited = new boolean[m][n];
    
    return dfs(maze, visited, start[0], start[1], dest[0], dest[1]);
  }
  
  private boolean dfs(int[][] maze, boolean[][] visited, int x, int y, int destX, int destY) {
    // 当找到重点的时候，返回true. 边界的检查和是否访问在for循环里面做
    if (x == destX && y == destY) {
      return true;
    }
    
    // 标记, 因为不需要回溯，所以不用重置回false
    visited[x][y] = true;
    
    boolean result = false;
    for (int i = 0; i < 4; i++) {
      // 因为当开始运动的时候，先朝着一个方向走，途中不能换方向，直到碰到墙壁才能换方向
      // 所以先朝着一个方向走, 直到走到了墙壁上面
      int newX = x + dx[i];
      int newY = y + dy[i];
      while (checkRange(maze, newX, newY) && maze[newX][newY] != 1) {
        newX++;
        newY++;
      }
      
      // 现在因为已经走到了墙壁上面，所以需要回退一步
      newX = newX - dx[i];
      newY = newY - dy[i];
      if (!visited[newX][newY]) {
        result = result || dfs(maze, visited, newX, newY, destX, destY);
      }
    }
    
    return result;
  }
  
  private boolean checkRange(int[][] maze, Point point) {
    return point.x >= 0 
      && point.x < maze.length
      && point.y >= 0 
      && point.y < maze[0].length;
  }
}
```



### 79 单词搜索

[79. 单词搜索](https://leetcode.cn/problems/word-search/)

```java
class Solution {
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        // 因为可以从任意位置开始，所以需要遍历。但是可以剪枝：遍历到的字符和word的首字符要相等才行
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0) && !visited[i][j]) {
                    boolean result = dfs(board, visited, i, j, word, 0);
                    // 如果已经存在一个答案了，直接返回
                    if (result) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, boolean[][] visited, int x, int y, String word, int index) {
        // 剪枝
        if (board[x][y] != word.charAt(index)) {
            return false;
        }

        // 标记
        visited[x][y] = true;

        // 已经找到全部的字符
        // 这里只需要到-1就行了，因为最上面的剪枝条件已经判断了最后一个字符的相等
        if (index == word.length() - 1) {
            return true;
        }

        // dfs
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (checkRange(board, newX, newY) && !visited[newX][newY]) {
                result = result || dfs(board, visited, newX, newY, word, index + 1);
            }
        }

        // 重置标记，因为回溯
        visited[x][y] = false;
        return result;
    }

    private boolean checkRange(char[][] board, int x, int y) {
        return x >= 0
            && x < board.length
            && y >= 0
            && y < board[0].length;
    }
}
```



### 矩阵中的最长递增路径

[329. 矩阵中的最长递增路径](https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/)

```java
class Solution {
    
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        
        // 记忆化搜索，对于【i，j】如果访问过了就记住它的最大递增长度，因为visited是用来给记忆化搜索服务的，并不需要回溯
        int result = 0;
        int[][] memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result = Math.max(result, dfs(matrix, visited, memo, i, j));
            }
        }

        return result;
    }

    private int dfs(int[][] matrix, boolean[][] visited, int[][] memo, int x, int y) {
        // 如果已经被访问过了，那么就直接返回记忆化搜索的结果
        if (visited[x][y]) {
            return memo[x][y];
        }

        int len = 1; // 因为当前节点要被计算，所以初始化是1
        visited[x][y] = true;

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // 只有满足递增的才能计算
            if (checkRange(matrix, newX, newY) && matrix[newX][newY] > matrix[x][y]) {
                len = Math.max(len, dfs(matrix, visited, memo, newX, newY) + 1); // +1是因为要计算本节点
            }
        }

        // 更新记忆化搜索
        memo[x][y] = len;
        return len;
    }

    private boolean checkRange(int[][] matrix, int x, int y) {
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length;
    }
}
```



### 岛屿类问题

#### 岛屿数量

[200. 岛屿数量](https://leetcode.cn/problems/number-of-islands/)

```java
class Solution {
    // 其实就是求二维矩阵的联通分量

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    dfs(grid, visited, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(char[][] grid, boolean[][] visited, int x, int y) {
        if (grid[x][y] == '0') {
            return;
        }

        visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (checkRange(grid, newX, newY) && !visited[newX][newY]) {
                dfs(grid, visited, newX, newY);
            }
        }
    }

    private boolean checkRange(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}
```



#### 岛屿的最大面积

[695. 岛屿的最大面积](https://leetcode.cn/problems/max-area-of-island/)

```java
class Solution {

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    count = Math.max(count, dfs(grid, visited, i, j));
                }
            }
        }

        return count;
    }

    private int dfs(int[][] grid, boolean[][] visited, int x, int y) {
        if (grid[x][y] == 0) {
            return 0;
        }

        visited[x][y] = true;
        int result = 1;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (checkRange(grid, newX, newY) && !visited[newX][newY]) {
                result += dfs(grid, visited, newX, newY);
            }
        }
        return result;
    }

    private boolean checkRange(int[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}
```



#### 被围绕的区域

[130. 被围绕的区域](https://leetcode.cn/problems/surrounded-regions/)

```java
class Solution {

    // 先求出边界的O的联通分量的节点，全部换成B
    // 然后遍历矩阵，O换成X，B换成O即可

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isBorder(board, i, j) && board[i][j] == 'O' && !visited[i][j]) {
                    dfs(board, visited, i, j);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'B') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, boolean[][] visited, int x, int y) {
        if (board[x][y] == 'X') {
            return;
        }

        visited[x][y] = true;
        board[x][y] = 'B';

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (checkRange(board, newX, newY) && !visited[newX][newY]) {
                dfs(board, visited, newX, newY);
            }
        }
    }

    private boolean isBorder(char[][] board, int x, int y) {
        return x == 0 || x == board.length - 1 || y == 0 || y == board[0].length - 1;
    }

    private boolean checkRange(char[][] board, int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }
}
```



# 3 BFS广度优先



## 3.1 实战

### 二叉树层序遍历

[102. 二叉树的层序遍历](https://leetcode.cn/problems/binary-tree-level-order-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // step 1: 构建队列
        Queue<TreeNode> queue = new LinkedList<>();

        // step 2: 把跟节点加入队列
        queue.offer(root);

        // step 3: while判断队列是否是空，实现BFS，直到队列为空
        while (!queue.isEmpty()) {
            // (1) 先确定每一层又多少元素，然后在层序遍历
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();

            // for循环层级遍历：需要分层必须要有for循环
            for (int i = 0; i < size; i++) {
                // 出队：以node为圆心画圆
                TreeNode node = queue.poll();
                levelList.add(node.val);

                // 左右子树入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(levelList);
        }
        return result;
    }
}
```



###  二叉树的层序遍历 II

[107. 二叉树的层序遍历 II](https://leetcode.cn/problems/binary-tree-level-order-traversal-ii/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        // step 1: 构建队列
        Queue<TreeNode> queue = new LinkedList<>();

        // step 2: 把跟节点加入队列
        queue.offer(root);

        // step 3: while判断队列是否是空，实现BFS，直到队列为空
        while (!queue.isEmpty()) {
            // (1) 先确定每一层又多少元素，然后在层序遍历
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();

            // for循环层级遍历：需要分层必须要有for循环
            for (int i = 0; i < size; i++) {
                // 出队：以node为圆心画圆
                TreeNode node = queue.poll();
                levelList.add(node.val);

                // 左右子树入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.addFirst(levelList);
        }
        return result;
    }
}
```



### 二叉树的锯齿形层序遍历

[103. 二叉树的锯齿形层序遍历](https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        // step 1: 构建队列
        Queue<TreeNode> queue = new LinkedList<>();

        // step 2: 把跟节点加入队列
        queue.offer(root);

        // step 3: while判断队列是否是空，实现BFS，直到队列为空
        while (!queue.isEmpty()) {
            // (1) 先确定每一层又多少元素，然后在层序遍历
            int size = queue.size();
            LinkedList<Integer> levelList = new LinkedList<>();

            // for循环层级遍历：需要分层必须要有for循环
            for (int i = 0; i < size; i++) {
                // 出队：以node为圆心画圆
                TreeNode node = queue.poll();
                if (result.size() % 2 == 0) {
                    levelList.addLast(node.val);
                } else {
                    levelList.addFirst(node.val);
                }

                // 左右子树入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(levelList);
        }
        return result;
    }
}
```



### N 叉树的层序遍历

[429. N 叉树的层序遍历](https://leetcode.cn/problems/n-ary-tree-level-order-traversal/)

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // step 1: 构建队列
        Queue<Node> queue = new LinkedList<>();

        // step 2: 把跟节点加入队列
        queue.offer(root);

        // step 3: while判断队列是否是空，实现BFS，直到队列为空
        while (!queue.isEmpty()) {
            // (1) 先确定每一层又多少元素，然后在层序遍历
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();

            // for循环层级遍历：需要分层必须要有for循环
            for (int i = 0; i < size; i++) {
                // 出队：以node为圆心画圆
                Node node = queue.poll();
                levelList.add(node.val);

                // 子树入队
                for (Node child : node.children) {
                    queue.offer(child);
                }
            }

            result.add(levelList);
        }
        return result;
    }
}
```



###  二叉树的最小深度

[111. 二叉树的最小深度](https://leetcode.cn/problems/minimum-depth-of-binary-tree/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // step 1: 构建队列
        Queue<TreeNode> queue = new LinkedList<>();

        // step 2: 把跟节点加入队列
        queue.offer(root);

        // step 3: while判断队列是否是空，实现BFS，直到队列为空
        int result = 0; // 只有看到一层的时候才会进行++
        while (!queue.isEmpty()) {
            // (1) 先确定每一层又多少元素，然后在层序遍历
            int size = queue.size();
            result++;

            // for循环层级遍历：需要分层必须要有for循环
            for (int i = 0; i < size; i++) {
                // 出队：以node为圆心画圆
                TreeNode node = queue.poll();

                if (node.left == null && node.right == null) {
                    return result;
                }

                // 左右子树入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return result;
    }
}
```



## 3.2 一般图的BFS模板

```java
public void bfs(Node start) {
  // create queue
  Queue<Node> queue = new LinkedList<>();

  // put start node into queue
  queue.offer(start);
  marked[start.getNo()] = true;

  // process bfs until queue is empty
  while (queue.isEmpty()) {
    Node cur = queue.poll();
    
    // 以cur Node为圆心画圆，找邻接节点
    for(Node adjNode : adjacencyList.get(start)) {
      if (!marked[adjNode.getNo()]) {
        marked[adjNode.getNo()] = true;
        queue.offer(adjNode);
      }
    }
  }
}
```



## 3.3 实战

### 无向图中连通分量



![recursion](./images/323.png)

```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        // count：做了几次DFS
        int count = 0;

        // step 0: 构建邻接表
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // 因为要构造成无向图，所以节点都需要双连
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // step 1: 套模板
        boolean[] marked = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 只有没有被标记过，才能进行dfs
            if (!marked[i]) {
                bfs(adj, marked, i);
                count++;
            }
        }
        return count;
    }

    private void bfs(Map<Integer, List<Integer>> adj, boolean[] marked, int nodeNum) {
      Queue<Integer> queue = new LinkedList<>();
      queue.offer(nodeNum);
      marked[nodeNum] = true;
      
      while (!queue.isEmpty()) {
        Integer curNode = queue.poll();
        
        for (Integer adjNode : adj.get(curNode)) {
          if (!marked[aadjNode]) {
            marked[adjNode] = true;
            queue.offer(adjNode);
          }
        }
      }
    }
}
```



### 省份数量

[547. 省份数量](https://leetcode.cn/problems/number-of-provinces/)

```java
class Solution {
    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0 || isConnected[0].length == 9) {
            return 0;
        }

        boolean[] marked = new boolean[isConnected[0].length];
        int result = 0;

        for (int i = 0; i < isConnected.length; i++) {
            if (!marked[i]) {
                bfs(isConnected, marked, i);
                result++;
            }
        }
        return result;
    }

    private void bfs(int[][] isConnected, boolean[] marked, int cityNum) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(cityNum);
        marked[cityNum] = true;

        while (!queue.isEmpty()) {
            // 只有二维数组的值为0的时候，才能是邻接城市
            Integer cityNo = queue.poll();

            for (int i = 0; i < isConnected[cityNo].length; i++) {
                if (isConnected[cityNo][i] == 1 && !marked[i]) {
                    marked[i] = true;
                    queue.offer(i);
                }
            }
        }
    }
}
```



### 以图判树

![recursion](./images/261-1.png)

![recursion](./images/261-3.png)

```java
class Solution {
  
  	// 树：边树=节点数-1
		// 联通分量必须得是1
    public int sulution(int n, int[][] edges) {
        if (n == 0) {
          return false;
        }
      	
      	// 树：边树=节点数-1
      	if (edges.length != n - 1) {
          return false;
        }
      
      	// step 1 构建邻接表
      	Map<Integer, List<Integer>> map = new HashMap<>();
      	for (int i = 0; i < n; i++) {
        	map.put(i, new ArrayList<Integer>());
        }
      
      	// step 2 填充邻接节点到邻接表
      	for (int i = 0; i < edges.length; i++) {
          int u = edges[i][0];
          int v = edges[i][1];
          
          map.get(u).add(v);
          map.get(v).add(u);
        }
      
      	// step 计算联通分量
      	int count = 0;
      	boolean[] visited = new boolean[edges.length];
      	for (int i = 0; i < n; i++) {
          if (!visited[i]) {
            bfs(map, visited, i);
            count++;
          }
        }
    }
  
  
  	private void bfs(Map<Integer, List<Integer>> map, boolean[] visited, int node) {
      Queue<Integer> queue = new LinkedList<>();
      queue.offer(node);
      visited[node] = true;
      
      while (!queue.isEmpty()) {
        Integer curNode = queue.poll();
        
        for (int adj : map.get(curNode)) {
          if (!visited[ajd]) {
            visited[ajd] = true;
            queue.offer(adj);
          }
        }
      }
    }
}
```



### 克隆图

[133. 克隆图](https://leetcode.cn/problems/clone-graph/)

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        // key: old node  value: new node
        Map<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();

        // clone node
        Node cloneNode = new Node(node.val);
        map.put(node, cloneNode);
        queue.offer(node);

        // bfs
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            
            for (Node curNeighbor : cur.neighbors) {
                // 如果当前邻居节点没有被克隆，那么先进行克隆
                if (!map.containsKey(curNeighbor)) {
                    Node cloneCurNeighbor = new Node(curNeighbor.val);
                    map.put(curNeighbor, cloneCurNeighbor);
                    queue.offer(curNeighbor);
                }

                // 如果当前邻居节点已经被克隆，那么就应该把被克隆的邻居节点加入到，被克隆的当前节点上面
                Node cloneCur = map.get(cur);
                Node cloneCurNeighbor = map.get(curNeighbor);

                // 这个其实就是双向绑定。可以想象节点1， 2。首次遍历1，然后取出克隆1，然后克隆2，把克隆好的2放到1的邻居节点里面
                // 形成这样 1‘ -> 2'
                // 第二次遍历先取出2'，因为1‘已经在map里面了，所以直接取出来1’，放到2‘的邻居里面最后形成的就是
                // 1' -> 2' 2' -> 1'
                cloneCur.neighbors.add(cloneCurNeighbor);
            }
        }
        return cloneNode;
    }
}
```



## 3.4 矩阵类BFS

### 二维BFS模板

```java
class Point {
  int x;
  int y;
}

private boolean checkRange(int[][] matrix, Point point) {
  return point.x >= 0 && point.x < matrix.length && point.y >= 0 && point.y < matrix[0].length;
}

public void bfsInMatrix(int[][] matrix) {
  if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
    return false;
  }
  
  int m = matrix.length;
  int n = matrix[0].length;
  boolean[][] visited = new boolean[m][n];
  
  for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
      Point start = new Point(i, j);
      if (special-condition && !visited[i][j]) {
        bfs(matrix, visited, start);
      }
    }
  }
}

public void bfs(int[][] matrix, boolean[][] visited, Point point) {
  Queue<Point> queue = new LinkedList<>();
  
  // 标记并且加入队列
  visited[point.x][point.y] = true;
  
  // bfs && move
  int[] dx = {1, 0, -1, 0};
  int[] dy = {0, 1, 0, -1};
  while (!queue.isEmpty()) {
    Point curPoint = queue.poll();
    for (int i = 0; i < 4; i++) {
      Point newPoint = new Point(curPoint.x + dx[i], curPoint.y + dy[i]);
      if (checkRange(matrix, newPoint) && !visited[newPoint.x][newPoint.y] && special-condition) {
        visited[newPoint.x][newPoint.y] = true;
        queue.offer(newPoint);
      }
    }
  }
}
```



### 岛屿数量

[200. 岛屿数量](https://leetcode.cn/problems/number-of-islands/)

```java
class Solution {
    class Point {
        int x;
        int y;

        public Point (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Point start = new Point(i, j);

                if (grid[i][j] == '1' && !visited[i][j]) {
                    bfs(grid, visited, start);
                    result++;
                }
            }
        }

        return result;
    }

    private void bfs(char[][] grid, boolean[][] visited, Point start) {
        visited[start.x][start.y] = true;
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            for (int i = 0; i < 4; i++) {
                Point newPoint = new Point(cur.x + dx[i], cur.y + dy[i]);
                if (checkRange(grid, newPoint) 
                    && !visited[newPoint.x][newPoint.y] 
                    && grid[newPoint.x][newPoint.y] == '1') {
                    
                    visited[newPoint.x][newPoint.y] = true;
                    queue.offer(newPoint);
                }
            }
        }
    }

    private boolean checkRange(char[][] grid, Point point) {
        return point.x >= 0 && point.x < grid.length && point.y >= 0 && point.y < grid[0].length;
    }
}
```



### 被围绕的区域

[130. 被围绕的区域](https://leetcode.cn/problems/surrounded-regions/)

```java
class Solution {
    class Point {
        int x;
        int y;

        public Point (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }

        int m = board.length;
        int n =  board[0].length;
        boolean[][] visited = new boolean[m][n];

        // 找到和边缘相接的O，全部换成B，然后再次遍历board，把剩余的O，也就是没有和边缘O接壤的O，都换成X，把B再换回O
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isBorder(m, n, i, j) && board[i][j] == 'O' && !visited[i][j]) {
                    Point start = new Point(i, j);
                    bfs(board, visited, start);
                }
            }
        }

        // 置换所有的O到X，B到O
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'B') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void bfs(char[][] board, boolean[][] visited, Point start) {
        visited[start.x][start.y] = true;
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);

        // 因为要把边缘以及边缘接壤的O换成B，所以在这里先更新一下
        board[start.x][start.y] = 'B';

        // bfs
        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            for (int i = 0; i < 4; i++) {
                Point newCur = new Point(cur.x + dx[i], cur.y + dy[i]);
                if (checkRange(board, newCur) && !visited[newCur.x][newCur.y] && board[newCur.x][newCur.y] == 'O') {
                    visited[newCur.x][newCur.y] = true;
                    board[newCur.x][newCur.y] = 'B';
                    queue.offer(newCur);
                }
            }
        }
    }

    private boolean isBorder(int m, int n, int x, int y) {
        return x == 0 || x == m - 1 || y == 0 || y == n - 1;
    }

    private boolean checkRange(char[][] board, Point point) {
        return point.x >= 0 && point.x < board.length && point.y >= 0 && point.y < board[0].length;
    }
}
```



# 4 拓扑排序

## 4.1 概念

对一个有向无环图DAG进行拓扑排序，是将DAG中所有顶点拍成一个线性序列，是的对途中任意一条边（u，v），u在线性序列中出现在v之前

通常，这样的线性序列成为满足拓扑排序（Topological Order）的序列，简称拓扑序列



【重要】必须是有向无环图中才有拓扑排序

【重要】若存在一条从顶点A到顶点B的路径，那么在序列中顶点A出现在顶点B的前面

- 拓扑排序通常用来‘’排序“具有以来关系的任务
- 拓扑排序并不唯一



## 4.2 Kahn算法

### 代码模板

- 计算每个节点入度，用map维护
- 将入度为0的节点均加入队列
- while循环队列，取出节点
- 得到节点的邻接节点，将所有邻接节点的入度-1，并且更新map
- 若邻接节点更新后入度为0，加入队列



## 4.3 实战

### Kahn算法代码实现

```java
package graph;

import java.util.List;

public class DirectedGraphNode {

    private int no;

    // 指向了这些节点
    private List<DirectedGraphNode> adjNodes;

    public DirectedGraphNode(int no, List<DirectedGraphNode> adjNodes) {
        this.no = no;
        this.adjNodes = adjNodes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public List<DirectedGraphNode> getAdjNodes() {
        return adjNodes;
    }

    public void setAdjNodes(List<DirectedGraphNode> adjNodes) {
        this.adjNodes = adjNodes;
    }
}
```

```java
package graph;

import java.util.*;

public class DirectedGraph {

    private final List<DirectedGraphNode> graphNodes;

    public DirectedGraph(List<DirectedGraphNode> graphNodes) {
        this.graphNodes = graphNodes;
    }

    public List<DirectedGraphNode> topologicalSort() {
        List<DirectedGraphNode> result = new ArrayList<>();

        // step 1 计算每个图节点的入度，用map维护。 Key：当前图节点 Value：入度值
        Map<DirectedGraphNode, Integer> inDegreeMap = new HashMap<>();
        for (DirectedGraphNode node : graphNodes) {
            // 入度：谁指向我, node 会指向他的所有的邻接节点
            for (DirectedGraphNode adjNode : node.getAdjNodes()) {
                if (!inDegreeMap.containsKey(adjNode)) {
                    inDegreeMap.put(adjNode, 1);
                } else {
                    Integer integer = inDegreeMap.get(adjNode);
                    inDegreeMap.put(adjNode, integer + 1);
                }
            }
        }

        // step 2 将入度为0的节点入队
        Queue<DirectedGraphNode> queue = new LinkedList<>();
        for (DirectedGraphNode node : graphNodes) {
            // 判断每个节点是不是在map中，不在map中的一定是入度为0。因为map中的节点，入度至少是1
            if (!inDegreeMap.containsKey(node)) {
                queue.offer(node);
                result.add(node);
            }
        }

        // step 3 BFS
        while (!queue.isEmpty()) {
            DirectedGraphNode cur = queue.poll();
            // 把当前的cur从图中擦掉
            for (DirectedGraphNode adj : cur.getAdjNodes()) {
                // 邻接节点入度-1
                int inDegree = inDegreeMap.get(adj);
                int updatedInDegree = inDegree - 1;
                inDegreeMap.put(adj, updatedInDegree);

                // 如果入度为0，则入队
                if (updatedInDegree == 0) {
                    queue.offer(adj);
                    result.add(adj);
                }
            }
        }

        return result;
    }
}
```



### 课程表 II

[210. 课程表 II](https://leetcode.cn/problems/course-schedule-ii/)



![recursion](./images/210.png)



```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 构造邻接表 Map<Key, Value> Key: 第几门课程  Value: 这门课修完后，才能修的课程
        Map<Integer, List<Integer>> adjTable = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            adjTable.put(i, new ArrayList<>());
        }

        // 填充邻接表
        for (int[] pre : prerequisites) {
            int start = pre[1];
            int end = pre[0];
            adjTable.get(start).add(end);
        }

        // 计算每个邻接节点的入度
        Map<Integer, Integer> inDegreeMap = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : adjTable.entrySet()) {
            for (Integer adjNode : entry.getValue()) {
                if (!inDegreeMap.containsKey(adjNode)) {
                    inDegreeMap.put(adjNode, 1);
                } else {
                    inDegreeMap.put(adjNode, inDegreeMap.get(adjNode) + 1);
                }
            }
        }

        // 结果数组
        int[] result = new int[numCourses];
        int index = 0;

        // 把入度为0的节点装进队列，然后bfs
        Queue<Integer> queue = new LinkedList<>();
        for (Integer course : adjTable.keySet()) {
            // 邻接表里面取出没门课程，判断是不是在入度map里面存在，如果不存在，那么说明这门课程的入度就是0
            if (!inDegreeMap.containsKey(course)) {
                queue.offer(course);
                result[index++] = course;
            }
        }

        // BFS
        while (!queue.isEmpty()) {
            Integer curCourse = queue.poll();

            // 减去当前课程的邻接课程的入度
            List<Integer> adjCourses = adjTable.get(curCourse);
            for (Integer ajdCourse : adjCourses) {
                int updatedInDegree = inDegreeMap.get(ajdCourse) - 1;
                inDegreeMap.put(ajdCourse, updatedInDegree);

                // 如果出现入度==0的课程，那么需要加入队列中
                if (updatedInDegree == 0) {
                    queue.offer(ajdCourse);
                    result[index++] = ajdCourse;
                }
            }
        }

        // 如果所有课程都学完，那么index应该正好等于numCourses，如果不等于的话，说明没有学完，那就是没有接，返回空数组即可
        if (index == numCourses) {
            return result;
        }
        return new int[0];
    }
}
```
```java

/**
构造有向图：是从start指向end，类似于这样,0指向1和2
	0
       / \
      1   2
而入度表则是：被指向的节点为key，value是用来记录一共有多少个节点是指向本节点的，例如1，2的入度就是1. 因为只有0指向他们
而0的入度是0，因为没有别的节点指向他，所以0这个节点不会被存放在入度表中 or 存在入度表中的value是0
*/
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 构建有向图
        Map<Integer, List<Integer>> table = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            table.put(i, new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            int start = prerequisite[1];
            int end = prerequisite[0];

            List<Integer> desList = table.getOrDefault(start, new ArrayList<>());
            desList.add(end);
            table.put(start, desList);
        }

        // 构建课程的入度表
        Map<Integer, Integer> inDegreeTable = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : table.entrySet()) {
            List<Integer> adjList = entry.getValue();
            for (int course : adjList) {
                if (!inDegreeTable.containsKey(course)) {
                    inDegreeTable.put(course, 1);
                } else {
                    int desInDegree = inDegreeTable.get(course);
                    desInDegree += 1;
                    inDegreeTable.put(course, desInDegree);
                }
            }
        }

        // 没门课程入度是0的进入队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (!inDegreeTable.containsKey(i)) {
                queue.offer(i);
            }
        }

        // 队列出队，代表学了一门课程，然后其对应的后置课程的入度-1， 如果后置课程入度为0的则进入队列
        int[] result = new int[numCourses];
        int index = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[index++] = course;

            List<Integer> adjList = table.get(course);
            for (int adjNode : adjList) {
                if (!inDegreeTable.containsKey(adjNode)) {
                    continue;
                }

                int desInDegree = inDegreeTable.get(adjNode);
                desInDegree -= 1;
                inDegreeTable.put(adjNode, desInDegree);

                if (desInDegree == 0) {
                    queue.offer(adjNode);
                    inDegreeTable.remove(adjNode);
                }
            }
        }

        if (index != numCourses) {
            return new int[0];
        }
        return result;
    }
}
```



### 课程表

[207. 课程表](https://leetcode.cn/problems/course-schedule/)

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 构造邻接表 Map<Key, Value> Key: 第几门课程  Value: 这门课修完后，才能修的课程
        Map<Integer, List<Integer>> adjTable = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            adjTable.put(i, new ArrayList<>());
        }

        // 填充邻接表
        for (int[] pre : prerequisites) {
            int start = pre[1];
            int end = pre[0];
            adjTable.get(start).add(end);
        }

        // 计算每个邻接节点的入度
        Map<Integer, Integer> inDegreeMap = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : adjTable.entrySet()) {
            for (Integer adjNode : entry.getValue()) {
                if (!inDegreeMap.containsKey(adjNode)) {
                    inDegreeMap.put(adjNode, 1);
                } else {
                    inDegreeMap.put(adjNode, inDegreeMap.get(adjNode) + 1);
                }
            }
        }

        // 结果数组
        int index = 0;

        // 把入度为0的节点装进队列，然后bfs
        Queue<Integer> queue = new LinkedList<>();
        for (Integer course : adjTable.keySet()) {
            // 邻接表里面取出没门课程，判断是不是在入度map里面存在，如果不存在，那么说明这门课程的入度就是0
            if (!inDegreeMap.containsKey(course)) {
                queue.offer(course);
                index++;
            }
        }

        // BFS
        while (!queue.isEmpty()) {
            Integer curCourse = queue.poll();

            // 减去当前课程的邻接课程的入度
            List<Integer> adjCourses = adjTable.get(curCourse);
            for (Integer ajdCourse : adjCourses) {
                int updatedInDegree = inDegreeMap.get(ajdCourse) - 1;
                inDegreeMap.put(ajdCourse, updatedInDegree);

                // 如果出现入度==0的课程，那么需要加入队列中
                if (updatedInDegree == 0) {
                    queue.offer(ajdCourse);
                    index++;
                }
            }
        }

        // 如果所有课程都学完，那么index应该正好等于numCourses，如果不等于的话，说明没有学完，那就是没有接，返回空数组即可
        return numCourses == index;
    }
}
```



# 5 最短路径

## 5.1 实战

### 迷宫

![recursion](./images/migong.png)

```java
public int steps(int[][] maze, Point start, Point end) {
  
  int steps = 0;
  int m = maze.length;
  int n = maze[0].length;
  boolean[][] visited = new boolean[m][n];
  
  int[] dx = {1, 0, -1, 0};
  int[] dy = {0, 1, 0, -1};
  
  Queue<Point> queue = new LinkedList<>();
  queue.offer(start);
  visited[start.x][start.y] = true;
  int level = 1;
  
  while (!queue.isEmpty()) {
    int size = queue.size();
    level++;

    for (int i = 0; i < size; i++) {
      Point curPoint = queue.poll();
      // 如果已经走到了end，说明已经找到了最后的点
      if (curPoint.x == end.x && curPoint.y == end.y) {
        steps = Math.min(steps, level);
        continue;
      }
      
      for (int j = 0; j < 4; j++) {
        int newX = curPoint.x + dx[j];
        int newY = curPoint.y + dy[j];
        Point newPoint = new Point(newX, newY);
        
        if (checkRange(maze, newPoint) && !visited[newX][newY] && maze[newX][newY] != 1) {
          visited[newX][newY] = true;
          queue.offer(newPoint);
        }
      }
    }
  }
  
  return steps;
}
```



### 迷宫 II

由空地和墙组成的迷宫中有一个球。
球可以向上下左右四个方向滚动，但在遇到墙壁前不会停止滚动。
当球停下时，可以选择下一个方向。

给定球的起始位置，目的地和迷宫，找出让球停在目的地的最短距离。
距离的定义是球从起始位置（不包括）到目的地（包括）经过的空地个数。
如果球无法停在目的地，返回 -1。

迷宫由一个0和1的二维数组表示。 1表示墙壁，0表示空地。
你可以假定迷宫的边缘都是墙壁。
起始位置和目的地的坐标通过行号和列号给出。
![recursion](./images/505.png)

```java
public class Solution {
    public int shortestDistance(int[][] maze, int[] start, int[] end) {
        if (maze == null || maze.length == 0 || maze[0] == null || maze[0].length == 0) {
            return -1;
        }

        // 记忆化搜索：记录start到该点(x, y)的最小步数
        int m = maze.length;
        int n = maze[0].length;
        int[][] memo = new int[m][n];

        for (int[] row : memo) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        memo[start[0]][start[1]] = 0;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newX = point[0] + dx[i];
                int newY = point[1] + dy[i];

                // 直到移动到墙上, 最后上墙了，需要在往后走一步
                int step = 0;
                while (changeRange(maze, newX, newY) && maze[newX][newY] == 0) {
                    newX += dx[i];
                    newY += dy[i];
                    step++;
                }
                newX -= dx[i];
                newY -= dy[i];

                // 什么时候能够入队
                if (memo[newX][newY] > memo[point[0]][point[1]] + step) {
                    memo[newX][newY] = memo[point[0]][point[1]] + step;
                    queue.offer(new int[] {newX, newY});
                }
            }
        }

        return memo[end[0]][end[1]] == Integer.MAX_VALUE ? -1 : memo[end[0]][end[1]];
    }

    private boolean changeRange(int[][] maze, int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
    }
}
```



# 6 双向BFS

## 6.1 概念相关

- 单项BFS的局限，搜索空间巨大
- 一种BFS的优化
- 想法：分别从起点和终点出发进行BFS，看是否能相遇
- 需要维护两个队列，用数组或者哈市表记录搜索状态
- 当某个节点被两个BFS同时标记则搜索结束



## 6.2 实战

### 单词接龙

[127. 单词接龙](https://leetcode.cn/problems/word-ladder/)

#### 单向BFS

```java
class Solution {

    // 利用广度优先遍历，去wordList找出startWord，然后变换每一个字符，找到了就加入队列，然后就是按着这个单词
    // 画圆，在找下一层的单词，直到找到endword。那么返回层数就可以了
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 构造字典
        Set<String> dict = new HashSet<>(wordList);
        if (beginWord.equals(endWord)) {
            return 0;
        }

        // 构造BFS的队列以及visited标记容器
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
      	visited.add(beginWord);
        int level = 1;  // 为什么要初始化为1，因为首个单词已经进入了队列

        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();    // 因为要求的是层数，所以需要用for循环来从queue中去单词

            for (int i = 0; i < size; i++) {
                // 如果是进入的第一个单词，则不用判断，因为上面最开始的部分已经进行判断了，只需要判断邻接节点
                // 是不是和end相等即可
                String curWord = queue.poll();

                // 取到当前单词的邻接单词list（就是以当前单词画圆）
                List<String> adjWords = getAdjWords(curWord, dict);
                for (String adjWord : adjWords) {
                    // 邻接节点只有没有被访问过，才能进行入队
                    if (!visited.contains(adjWord)) {
                        if (adjWord.equals(endWord)) {
                            return level;
                        }

                        queue.offer(adjWord);
                        visited.add(adjWord);
                    }
                }
            }
        }
        return 0;
    }

    private List<String> getAdjWords(String curWord, Set<String> dict) {
        List<String> result = new ArrayList<>();

        // 从a到z依次替换curWord的每个字符，去查看在字典中存在不存在，存在的话加入到结果集
        for (int i = 0; i < curWord.length(); i++) {
            int ch = curWord.charAt(i);

            // 如果遍历到的字符和ch相等的话就跳过
            for (char j = 'a'; j <= 'z'; j++) {
                if (j == ch) {
                    continue;
                }

                String replaceddWord = replacedWord(curWord, i, j);
                if (dict.contains(replaceddWord)) {
                    result.add(replaceddWord);
                }
            }
        }
        return result;
    }

    private String replacedWord(String curWord, int index, char j) {
        char[] curWordArr = curWord.toCharArray();
        curWordArr[index] = j;
        return new String(curWordArr);
    }
}
```



#### 双向BFS

```java
class Solution {

    // 双向BFS，从头部尾部开始进行BFS，然后遍历的时候尽量让邻接节点的数量相当
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (beginWord.equals(endWord)) {
            return 0;
        }
        
        // 注意， beginWord 不需要在 wordList 中 -> 在进行双向BFS的时候，把begin word加到字典里面
        Set<String> dict = new HashSet<>(wordList);
        dict.add(beginWord);
        if (!dict.contains(endWord)) {
            return 0;
        }

        // 双队列来进行BFS. q1 = left to right; q2 = right to left
        Queue<String> q1 = new LinkedList<>();
        Set<String> visited1 = new HashSet<>();
        q1.offer(beginWord);
        visited1.add(beginWord);
        int level1 = 1;

        Queue<String> q2 = new LinkedList<>();
        Set<String> visited2 = new HashSet<>();
        q2.offer(endWord);
        visited2.add(endWord);
        int level2 = 1;

        while (!q1.isEmpty() && !q2.isEmpty()) {
            // 哪边节点少，就去扩展哪边，尽量要让节点的数量相当
            if (q1.size() <= q2.size()) {
                if (update(dict, q1, visited1, visited2)) {
                    return level1 + level2;
                }
                level1++;
            } else {
                if (update(dict, q2, visited2, visited1)) {
                    return level1 + level2;
                }
                level2++;
            }
        }
        return 0;
    }

    private boolean update(Set<String> dict, Queue<String> updateQueue, Set<String> visited, Set<String> checkVisited) {
        int size = updateQueue.size();
        for (int i = 0; i < size; i++) {
            String curWord = updateQueue.poll();
            List<String> adjWords = getAdjWords(curWord, dict);

            for (String adjWord : adjWords) {
                if (!visited.contains(adjWord)) {
                    if (checkVisited.contains(adjWord)) {
                        return true;
                    }

                    visited.add(adjWord);
                    updateQueue.offer(adjWord);
                }
            }
        }

        return false;
    }

    private List<String> getAdjWords(String curWord, Set<String> dict) {
        List<String> result = new ArrayList<>();

        // 从a到z依次替换curWord的每个字符，去查看在字典中存在不存在，存在的话加入到结果集
        for (int i = 0; i < curWord.length(); i++) {
            int ch = curWord.charAt(i);

            // 如果遍历到的字符和ch相等的话就跳过
            for (char j = 'a'; j <= 'z'; j++) {
                if (j == ch) {
                    continue;
                }

                String replaceddWord = replacedWord(curWord, i, j);
                if (dict.contains(replaceddWord)) {
                    result.add(replaceddWord);
                }
            }
        }
        return result;
    }

    private String replacedWord(String curWord, int index, char j) {
        char[] curWordArr = curWord.toCharArray();
        curWordArr[index] = j;
        return new String(curWordArr);
    }
}
```

