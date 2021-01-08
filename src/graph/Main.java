package graph;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

    }

    // DFS
    private void dfs(int[][] m, boolean[] visited, int i) {
        visited[i] = true;
        for(int j = 0; j < m.length; j++) {
            if(m[i][j] == 1 && !visited[j]) {
                dfs(m, visited, j);
            }
        }
    }

    // BFS
    public void bfs(int[][] m, boolean[] visited, int i) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(i);
        while (!q.isEmpty()) {
            int j = q.poll();
            visited[j] = true;
            for (int k = 0; k < m.length; k++) {
                if (m[j][k] == 1 && !visited[k]) {
                    q.offer(k);
                }
            }
        }
    }

}
