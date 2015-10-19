package workapplication.Exam1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n, m;
        int[][] grid;
        n = scanner.nextInt();
        m = scanner.nextInt();
        grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }
        System.out.println(mm.getScore(grid));
    }

    private long getScore(int[][] grid) {
        long[] dp = new long[grid.length];
        long[] topTobottom = new long[grid.length];
        long[] bottomTotop = new long[grid.length];
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] == -1)
                    topTobottom[j] = -1;
                else {
                    if (j == 0) {
                        topTobottom[j] = Math.max(dp[j] + grid[j][i], grid[j][i]);
                    } else {
                        topTobottom[j] = Math.max(Math.max(dp[j] + grid[j][i], topTobottom[j - 1] + grid[j][i]), grid[j][i]);
                    }
                }
            }
            for (int j = grid.length - 1; j >= 0; j--) {
                if (grid[j][i] == -1)
                    bottomTotop[j] = -1;
                else {
                    if (j == grid.length - 1) {
                        bottomTotop[j] = Math.max(dp[j] + grid[j][i], grid[j][i]);
                    } else {
                        bottomTotop[j] = Math.max(Math.max(dp[j] + grid[j][i], bottomTotop[j + 1] + grid[j][i]), grid[j][i]);
                    }
                }
            }
            for (int j = 0; j < grid.length; j++) {
                dp[j] = Math.max(topTobottom[j], bottomTotop[j]);
            }
        }
        long max = 0;
        for (int i = 0; i < dp.length; i++) {
            max = Math.max(dp[i], max);
        }
        return max;
    }
}