package mogujie;

import java.util.Scanner;

public class Talk {
    public static void main(String[] args) {
        Talk tt = new Talk();
        Scanner scanner = new Scanner(System.in);
        int p, q, l, r;
        int[][] mo;
        int[][] gu;
        while (scanner.hasNext()) {
            p = scanner.nextInt();
            q = scanner.nextInt();
            l = scanner.nextInt();
            r = scanner.nextInt();
            scanner.nextLine();
            mo = new int[p][2];
            for (int i = 0; i < p; i++) {
                mo[i][0] = scanner.nextInt();
                mo[i][1] = scanner.nextInt();
                scanner.nextLine();
            }
            gu = new int[q][2];
            for (int i = 0; i < q; i++) {
                gu[i][0] = scanner.nextInt();
                gu[i][0] = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println(tt.getCountTime(mo, gu, l, r));
        }
    }

    private int getCountTime(int[][] mo, int[][] gu, int l, int r) {
        int left = mo[0][0] -gu[gu.length - 1][1];
        int right = mo[mo.length - 1][1] - gu[0][0];
        if (right < l || left > r)
            return 0;
        int count = 0;
        L:
        for (int i = left; i <= right; i++) {
            for (int j = 0; j < gu.length; j++) {
                for (int k = 0; k < mo.length; k++) {
                    if (!(gu[j][1] + i > mo[k][0] || mo[k][1] > gu[j][0] + i)) {
                        count++;
                        continue L;
                    }
                }
            }
        }
        return count;
    }
}
