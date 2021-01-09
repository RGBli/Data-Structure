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

}
