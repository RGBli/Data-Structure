package graph;

import java.util.*;

public class Main {
    public static void main(String[] args) {

    }

    // DFS, 递归实现
    public void dfs(int[][] m, boolean[] visited, int i, List<Integer> res) {
        visited[i] = true;
        res.add(i);
        for(int j = 0; j < m.length; j++) {
            if(m[i][j] == 1 && !visited[j]) {
                dfs(m, visited, j, res);
            }
        }
    }

    // BF, 用队列实现
    public List<Integer> bfs(int[][] m, int i) {
        List<Integer> res = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[m.length];
        q.offer(i);
        while (!q.isEmpty()) {
            int j = q.poll();
            visited[j] = true;
            res.add(j);
            for (int k = 0; k < m.length; k++) {
                if (m[j][k] == 1 && !visited[k]) {
                    q.offer(k);
                }
            }
        }
        return res;
    }

    /*岛屿的数量
    * 思路是深度优先搜索，通过 dfs 来将陆地置0，然后判断有几块即可
    * P200*/
    public int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rowNum = grid.length;
        int colNum = grid[0].length;
        int res = 0;
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (grid[row][col]== '0') {
                    dfsForNumIslands(grid, row, col);
                    res++;
                }
            }
        }
        return res;
    }
    public void dfsForNumIslands(char[][] grid, int row, int col) {
        int rowNum = grid.length;
        int colNum = grid[0].length;
        if (row < 0 || col < 0 || row >= rowNum || col >= colNum || grid[row][col] == '0') {
            return;
        }
        grid[row][col] = '0';
        dfsForNumIslands(grid, row - 1, col);
        dfsForNumIslands(grid, row + 1, col);
        dfsForNumIslands(grid, row, col - 1);
        dfsForNumIslands(grid, row, col + 1);
    }

    /*单词搜索
    * 思路是 dfs + 回溯
    * P79*/
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++) {
                if (dfsForExists(board, visited, new StringBuilder(), i, j, word)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean dfsForExists(char[][] board, boolean[][] visited, StringBuilder sb, int i, int j, String word) {
        if (sb.toString().equals(word)) {
            return true;
        }
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j] || !word.startsWith(sb.toString())) {
            return false;
        }
        visited[i][j] = true;
        sb.append(board[i][j]);
        if (dfsForExists(board, visited, sb, i, j + 1, word)) {
            return true;
        } else if (dfsForExists(board, visited, sb,i + 1, j, word)) {
            return true;
        } else if (dfsForExists(board, visited, sb, i,j - 1, word)) {
            return true;
        } else if (dfsForExists(board, visited, sb,i - 1, j, word)) {
            return true;
        } else {
            // 都不成功，则回溯并返回false，
            visited[i][j] = false;
            sb.deleteCharAt(sb.length() - 1);
            return false;
        }
    }

    /*最大正方形面积
    * 思路是动态规划
    * dp[i][j] 表示以 (i, j) 为右下角的最大正方形边长
    * 最后返回 dp[i][j] 的最大值的平方即可
    * P221*/
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    res = Math.max(res, dp[i][j]);
                }
            }
        }
        return res;
    }

    /*课程表1
    * 思路是深度优先搜索 + 拓扑排序
    * P207*/
    List<List<Integer>> edgesForCanFinish;
    int[] visitedForCanFinish;
    boolean isValidForCanFinish = true;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edgesForCanFinish = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            edgesForCanFinish.add(new ArrayList<>());
        }
        visitedForCanFinish = new int[numCourses];
        for (int[] info : prerequisites) {
            edgesForCanFinish.get(info[1]).add(info[0]);
        }
        for (int i = 0; i < numCourses && isValidForCanFinish; i++) {
            if (visitedForCanFinish[i] == 0) {
                dfsForCanFinish(i);
            }
        }
        return isValidForCanFinish;
    }
    public void dfsForCanFinish(int u) {
        visitedForCanFinish[u] = 1;
        for (int v : edgesForCanFinish.get(u)) {
            if (visitedForCanFinish[v] == 0) {
                dfsForCanFinish(v);
                if (!isValidForCanFinish) {
                    return;
                }
            } else if (visitedForCanFinish[v] == 1) {
                isValidForCanFinish = false;
                return;
            }
        }
        visitedForCanFinish[u] = 2;
    }

    /*课程表2
    * 思路是拓扑排序，并用栈来保存排序结果
    * P210*/
    boolean isValidForFindOrder = true;
    List<List<Integer>> edgesForFindOrder;
    int[] visitedForFindOrder;
    // 用数组从后向前插入来模拟栈
    int[] resForFindOrder;
    int indexForFindOrder;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        edgesForFindOrder = new ArrayList<>();
        visitedForFindOrder = new int[numCourses];
        resForFindOrder = new int[numCourses];
        indexForFindOrder = numCourses - 1;
        for (int i = 0; i < numCourses; i++) {
            edgesForFindOrder.add(new ArrayList<>());
        }
        for (int[] info : prerequisites) {
            edgesForFindOrder.get(info[1]).add(info[0]);
        }
        for (int i = 0; i < numCourses && isValidForFindOrder; i++) {
            if (visitedForFindOrder[i] == 0) {
                dfsForFindOrder(i);
            }
        }
        if (isValidForFindOrder) {
            return resForFindOrder;
        } else {
            return new int[0];
        }
    }
    public void dfsForFindOrder(int u) {
        visitedForFindOrder[u] = 1;
        for (Integer v : edgesForFindOrder.get(u)) {
            if (visitedForFindOrder[v] == 0) {
                dfsForFindOrder(v);
                if (!isValidForFindOrder) {
                    return;
                }
            } else if (visitedForFindOrder[v] == 1) {
                isValidForFindOrder = false;
                return;
            }
        }
        visitedForFindOrder[u] = 2;
        resForFindOrder[indexForFindOrder--] = u;
    }

    /*最小路径和
    * 思路是动态规划
    * P64*/
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = grid[0][0];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 && j != 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0 && i != 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else if (i != 0) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
        }
        return dp[row - 1][col - 1];
    }
}
