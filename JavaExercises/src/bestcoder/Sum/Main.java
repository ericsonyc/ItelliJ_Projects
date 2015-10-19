package bestcoder.Sum;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n, m;
        while (scanner.hasNext()) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            mm.getNumber(n, m);
        }
    }

    private void getNumber(int n, int m) {
        int count = 0;
        int temp;
        int sum = 0;
        for (int i = n; i <= m; i++) {
            temp = i;
            while (temp > 0) {
                int t = temp % 10;
                sum += t * t * t;
                temp = t;
            }
            if (sum == i) {
                if (count != 0)
                    System.out.print(" ");
                System.out.print(sum);
                count++;
            }
        }
        if (count == 0)
            System.out.println("no");
        else {
            System.out.println();
        }
    }
}
