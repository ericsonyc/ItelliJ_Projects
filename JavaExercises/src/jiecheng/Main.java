package jiecheng;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int m, n;
        while (scanner.hasNext()) {
            m = scanner.nextInt();
            n = scanner.nextInt();
            mm.getShuixianhua(m, n);
        }
    }

    private void getShuixianhua(int m, int n) {
        int x = 0;
        for (int i = m; i <= n; i++) {
            int b = i / 100;
            int s = (i - 100 * b) / 10;
            int g = (i - s * 10 - b * 100);

            if (i == g * g * g + s * s * s + b * b * b) {
                x++;
                System.out.print(i + " ");
            }
        }
        if (x == 0)
            System.out.println("no");
        else
            System.out.println();
    }

}
