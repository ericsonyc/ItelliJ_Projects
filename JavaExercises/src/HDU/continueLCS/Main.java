package HDU.continueLCS;

/**
 * Created by ericson on 2015/8/21 0021.
 */
public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        int result = mm.getCommonLength("acaccbabb","acbac");
        System.out.println(result);
    }

    private int getCommonLength(String a, String b) {
        if (a == null || b == null) return 0;
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[m][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = a.charAt(i) == b.charAt(0) ? 1 : 0;
        }
        for (int i = 0; i < m; i++) {
            dp[i][0] = a.charAt(0) == b.charAt(i) ? 1 : 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (a.charAt(j) == b.charAt(i)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(dp[i][j], max);
            }
        }
        return max;
    }
}
