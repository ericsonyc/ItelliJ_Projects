package microsoft.exam5;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by ericson on 2016/4/6 0006.
 */
public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        scanner.nextLine();
        boolean[] a = new boolean[N];
        int[][] b = new int[N][5];
        for (int i = 0; i < N; i++) {
            String[] temp = scanner.nextLine().split(" ");
            if (temp[0].equals("allow")) {
                a[i] = true;
                mm.getNewBytes(temp[1], b[i]);
            } else {
                a[i] = false;
                mm.getNewBytes(temp[1], b[i]);
            }
        }
        for (int i = 0; i < M; i++) {
            String ip = scanner.nextLine();
            if (mm.judgeNewIP(ip, a, b)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    public void getNewBytes(String ip, int[] b) {
        if (ip.indexOf("/") != -1) {
            String[] two = ip.split("/");
            String[] parts = two[0].split("\\.");
            int mask = Integer.parseInt(two[1]);
            int count = mask / 8;
            mask = mask % 8;
            int cc = 0;
            while (count > 0) {
                b[cc] = Integer.parseInt(parts[cc]);
                mask = count;
                count = mask - 8;
                cc++;
            }
            mask = 8 - mask;
            int temp = Integer.parseInt(parts[cc]) & (~((1 << mask) - 1));
            b[cc] = temp;
            b[4] = cc;
            if (cc < 3) {
                int max = temp + (1 << mask);
                b[cc + 1] = max;
            }
        } else {
            String[] parts = ip.split("\\.");
            for (int i = 0; i < parts.length; i++) {
                b[i] = Integer.parseInt(parts[i]);
            }
            b[4] = 4;
        }
    }

    public boolean judgeNewIP(String ip, boolean[] a, int[][] b) {
        String[] parts = ip.split("\\.");
        for (int i = 0; i < a.length; i++) {
            int j = 0;
            for (; j < b[i][4]; j++) {
                if (parts[j].equals(String.valueOf(b[i][j]))) {
                    break;
                }
            }
            if (j != 4)
                if (j == b[i][4] && b[i][b[i][4]] <= Integer.parseInt(parts[b[i][4]])) {
                    if (!(b[i][4] < 3 && Integer.parseInt(parts[b[i][4]]) >= b[i][b[i][4] + 1])) {
                        return a[i];
                    }
                }
        }
        return true;
    }

}