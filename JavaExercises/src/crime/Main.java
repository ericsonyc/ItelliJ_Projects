package crime;

import java.util.Scanner;

/**
 * Created by ericson on 2015/9/12 0012.
 */
public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n, c;
        long t;
        n = scanner.nextInt();
        t = scanner.nextLong();
        c = scanner.nextInt();
        scanner.nextLine();
        long[] nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextLong();
        }

    }

    private int getNum(int[] dp, long t, int c) {
        int local = 0;
        int global = 0;
        for (int i = c; i < dp.length; i++) {
            int count=0;

        }
        return 0;
    }
}
