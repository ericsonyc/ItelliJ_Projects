package HDU.maxsum;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/19 0019.
 */
public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i <= n; i++) {
            String[] temp = scanner.nextLine().split(" ");
            int[] num = new int[temp.length - 1];
            for (int j = 0; j < num.length; j++) {
                num[j] = Integer.parseInt(temp[j + 1]);
            }
            int[] dp = new int[num.length];
            int[] index = new int[num.length];
            int[] max = mm.getMaxSum(num, dp, index);
            System.out.println("Case " + i + ":");
            System.out.println(max[0] + " " + (max[1] + 1) + " " + (max[2] + 1));
            if (i < n)
                System.out.println();
        }
    }

    private int[] getMaxSum(int[] num, int[] dp, int[] index) {
        dp[0] = num[0];
        index[0] = 0;
        int max = num[0];
        int indexStart = 0;
        int indexEnd = 0;
        for (int i = 1; i < num.length; i++) {
            dp[i] = Math.max(num[i], dp[i - 1] + num[i]);
            if (dp[i] == dp[i - 1] + num[i])
                index[i] = index[i - 1];
            else
                index[i] = i;
            max = Math.max(max, dp[i]);
            if (max == dp[i]) {
                indexStart = index[i];
                indexEnd = i;
            }
        }
        return new int[]{max, indexStart, indexEnd};
    }
}
