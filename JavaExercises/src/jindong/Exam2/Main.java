package jindong.Exam2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m, k;
        int[][] value;
        long[][][] dp;
        int[][][] tt;
        while (scanner.hasNext()) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            k = scanner.nextInt();
            value = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    value[i][j] = scanner.nextInt();
                }
                scanner.nextLine();
            }
            dp = new long[k + 1][n + 1][m + 1];
            tt = new int[k + 1][n + 1][m + 1];
            dp[1][0][0] = 1;
            tt[1][0][0] = value[0][0];
            for (int i = 1; i <= k; i++) {
                for (int j = 1; j < n; j++) {
                    for (int t = 1; t < m; t++) {
                        if(value[j-1][t-1]>tt[i-1][j-1][t]){

                        }
                    }
                }
            }
            System.out.println(dp[k][n][m]);
        }

    }
}
