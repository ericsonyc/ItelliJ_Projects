package microsoft.exam3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int[] f = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025};
        long max = 1000000007;
        long[] s = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int n = scanner.nextInt();
        scanner.nextLine();
        if (n <= 0) {
            System.out.println(0);
        } else {
            int a;
            for (int i = 0; i < n; i++) {
                a = scanner.nextInt();
                if (a == 1) {
                    s[1] += s[0];
                    if (s[1] > max)
                        s[1] = s[1] % max;
                    s[0]++;
                    continue;
                }
                for (int j = 2; j < 25; j++)
                    if (a == f[j]) {
                        s[j] += s[j - 1];
                        if (s[j] > max)
                            s[j] = s[j] % max;
                        break;
                    }
            }
            long sum = 0;
            for (int i = 0; i < 25; i++) {
                sum += s[i];
                if (sum > max)
                    sum = sum % max;
            }
            System.out.println(sum);
        }
    }
}
