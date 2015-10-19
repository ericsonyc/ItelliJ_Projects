package jiuhuo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int m, n;
        m = scanner.nextInt();
        n = scanner.nextInt();
        scanner.nextLine();
        int[][] paths = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                paths[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }
        int[][] dp = new int[m][n];
        mm.getAllPaths(dp, m, n, paths);
        System.out.println(dp[0][n - 1]);
    }

    private void getAllPaths(int[][] dp, int m, int n, int[][] paths) {
        dp[m-1][0] = 1;
        for (int i = 1; i < n; i++) {
            if (paths[m-1][i] == 0)
                break;
            else
                dp[m-1][i] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            if (paths[i][0] == 0)
                break;
            else
                dp[i][0] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = 1; j < n; j++) {
                if (paths[i][j] == 1) {
                    dp[i][j] = dp[i + 1][j - 1] + dp[i + 1][j] + dp[i][j - 1];
                }
            }
        }
    }
}
