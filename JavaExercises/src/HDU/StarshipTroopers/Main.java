package HDU.StarshipTroopers;

import java.util.Scanner;

/**
 * Created by ericson on 2015/9/10 0010.
 */
public class Main {

    int MAXN = 100;
    int N, M;
    int[][] dp = new int[MAXN][MAXN];
    int[][] adj = new int[MAXN][MAXN];
    boolean[] vis = new boolean[MAXN];
    Node[] node = new Node[MAXN];

    class Node {
        int number, p;

        public Node(int number, int p) {
            this.number = number;
            this.p = p;
        }
    }

    void dfs(int root) {
        vis[root] = true;
        int num = (node[root].number + 19) / 20;
        for (int i = num; i < M; i++) {
            dp[root][i] = node[root].p;
        }
        for (int i = 1; i <= adj[root][0]; i++) {
            int u = adj[root][i];
            if (vis[u]) continue;
            dfs(u);
            for (int j = M; j >= num; j--) {
                for (int k = 1; j + k <= M; k++) {
                    if (dp[u][k] > 0)
                        dp[root][j + k] = Math.max(dp[root][j + k], dp[root][j] + dp[u][k]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            mm.N = scanner.nextInt();
            mm.M = scanner.nextInt();
            if (mm.N == -1 && mm.M == -1)
                break;
            for (int i = 1; i <= mm.N; i++) {
                mm.node[i] = mm.new Node(scanner.nextInt(), scanner.nextInt());
            }
            for (int i = 1; i < mm.N; i++) {
                int t = scanner.nextInt();
                int d = scanner.nextInt();
                mm.adj[t][0]++;
                mm.adj[t][mm.adj[t][0]] = d;
                mm.adj[d][0]++;
                mm.adj[d][mm.adj[d][0]] = t;
            }
            if (mm.M == 0)
                System.out.println("0\n");
            else {
                mm.dfs(1);
                System.out.println(mm.dp[1][mm.M]);
            }
        }
    }
}
