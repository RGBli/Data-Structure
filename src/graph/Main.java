package graph;

import java.util.*;

public class Main {
    // DFS, 递归实现
    public void dfs(int[][] m, boolean[] visited, int i, List<Integer> res) {
        visited[i] = true;
        res.add(i);
        for (int j = 0; j < m.length; j++) {
            if (m[i][j] == 1 && !visited[j]) {
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

    /**岛屿的数量
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
                if (grid[row][col] == '0') {
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

    /**单词搜索
     * 思路是 dfs + 回溯
     * P79*/
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
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
        } else if (dfsForExists(board, visited, sb, i + 1, j, word)) {
            return true;
        } else if (dfsForExists(board, visited, sb, i, j - 1, word)) {
            return true;
        } else if (dfsForExists(board, visited, sb, i - 1, j, word)) {
            return true;
        } else {
            // 都不成功，则回溯并返回false，
            visited[i][j] = false;
            sb.deleteCharAt(sb.length() - 1);
            return false;
        }
    }

    /**最大正方形面积
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

    /**课程表1
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

    /**课程表2
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

    /**最小路径和
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

    /**腐烂的橘子
     * 思路是 BFS
     * P994*/
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 将腐烂的橘子入队，并统计新鲜橘子的数量
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    count++;
                }
            }
        }
        int time = 0;
        // 当队不为空并且有新鲜橘子时循环
        while (!queue.isEmpty() && count > 0) {
            // 一层一层的传染，每传染一层，时间+1
            time++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                int x = pos[0];
                int y = pos[1];
                // 上
                if (x - 1 >= 0 && grid[x - 1][y] == 1) {
                    // 每传染一个，更新新鲜橘子的数量
                    count--;
                    grid[x - 1][y] = 2;
                    queue.offer(new int[]{x - 1, y});
                }
                // 下
                if (x + 1 < m && grid[x + 1][y] == 1) {
                    count--;
                    grid[x + 1][y] = 2;
                    queue.offer(new int[]{x + 1, y});
                }
                // 左
                if (y - 1 >= 0 && grid[x][y - 1] == 1) {
                    count--;
                    grid[x][y - 1] = 2;
                    queue.offer(new int[]{x, y - 1});
                }
                // 右
                if (y + 1 < n && grid[x][y + 1] == 1) {
                    count--;
                    grid[x][y + 1] = 2;
                    queue.offer(new int[]{x, y + 1});
                }
            }
        }
        return count == 0 ? time : -1;
    }

    /**N 皇后，在 N x N 的棋盘上放置 N 个皇后，使得任意两个皇后不在同一行同一列和同一斜线
     * 思路是回溯
     * 对于每一做坐标创建了三个集合，分别存储列，对角方向和反对角方向的皇后
     * 首先固定行号，然后遍历列，通过回溯来增加行号
     * 时间复杂度 O(n!)，空间复杂度 O(n)
     * P51*/
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        Set<Integer> columns = new HashSet<>();
        Set<Integer> diagonals1 = new HashSet<>();
        Set<Integer> diagonals2 = new HashSet<>();
        backtrackForSolveNQueens(res, queens, n, 0, columns, diagonals1, diagonals2);
        return res;
    }

    public void backtrackForSolveNQueens(List<List<String>> res, int[] queens, int n, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == n) {
            List<String> board = generateBoard(queens, n);
            res.add(board);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (columns.contains(i)) {
                continue;
            }
            int diagonal1 = row - i;
            if (diagonals1.contains(diagonal1)) {
                continue;
            }
            int diagonal2 = row + i;
            if (diagonals2.contains(diagonal2)) {
                continue;
            }
            queens[row] = i;
            columns.add(i);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);
            backtrackForSolveNQueens(res, queens, n, row + 1, columns, diagonals1, diagonals2);
            queens[row] = -1;
            columns.remove(i);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
        }
    }

    // 生成棋盘
    public List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }


    /**被包围的区域
     * 思路是深度优先遍历
     * 从四条边界的 O 出发，能遍历到的 O 就标记为 A
     * 然后遍历图中所有元素修改 O 为 X，A 为 O 即可
     * P130*/
    public void surroundArea(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < n; i++) {
            dfsForSurroundArea(board, 0, i, m, n);
        }
        for (int i = 0; i < n; i++) {
            dfsForSurroundArea(board, m - 1, i, m, n);
        }
        for (int i = 1; i < m - 1; i++) {
            dfsForSurroundArea(board, i, 0, m, n);
        }
        for (int i = 1; i < m - 1; i++) {
            dfsForSurroundArea(board, i, n - 1, m, n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfsForSurroundArea(char[][] board, int x, int y, int m, int n) {
        // 递归出口
        if (x < 0 || x >= n || y < 0 || y >= m || board[x][y] != 'O') {
            return;
        }
        board[x][y] = 'A';
        dfsForSurroundArea(board, x + 1, y, m, n);
        dfsForSurroundArea(board, x - 1, y, m, n);
        dfsForSurroundArea(board, x, y + 1, m, n);
        dfsForSurroundArea(board, x, y - 1, m, n);
    }

    public static void main(String[] args) {

    }
}
