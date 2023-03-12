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

