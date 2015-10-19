package beauty;

import java.util.Scanner;

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
        long sum = 0;
        int count = 0;
        long[] nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextLong();
            if (i < c - 1)
                sum += nums[i];
            else if (i == c - 1) {
                sum += nums[i];
                if (sum <= t)
                    count++;
            } else {
                sum += nums[i] - nums[i - c];
                if (sum <= t)
                    count++;
            }

        }

        System.out.println(2*count);
    }
}
