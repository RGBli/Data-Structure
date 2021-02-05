package graph;

import java.util.*;

public class Main {
    public static void main(String[] args) {

    }

    // DFS 递归实现
    public void dfs(int[][] m, boolean[] visited, int i, List<Integer> res) {
        visited[i] = true;
        res.add(i);
        for(int j = 0; j < m.length; j++) {
            if(m[i][j] == 1 && !visited[j]) {
                dfs(m, visited, j, res);
            }
        }
    }

    // BFS，用队列实现
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
}
