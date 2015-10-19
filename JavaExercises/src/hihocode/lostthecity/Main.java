package hihocode.lostthecity;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int N, M;
        char[][] chs;
        char[][] hi = new char[3][3];
        while (scanner.hasNext()) {
            N = scanner.nextInt();
            M = scanner.nextInt();
            scanner.nextLine();
            chs = new char[N][M];
            for (int i = 0; i < N; i++) {
                chs[i] = scanner.nextLine().toCharArray();
            }
            for (int i = 0; i < 3; i++) {
                hi[i] = scanner.nextLine().toCharArray();
            }
            List<int[]> list = mm.getPosition(chs, hi);
            mm.print(list);
        }
    }

    private void print(List<int[]> list) {
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i);
            System.out.println(temp[0] + " " + temp[1]);
        }
    }

    private List<int[]> getPosition(char[][] chs, char[][] hi) {
        String right = hi[0][0] + "" + hi[0][1] + hi[0][2] + hi[1][2] + hi[2][2] + hi[2][1] + hi[2][0] + hi[1][0];
        List<int[]> list = new LinkedList<int[]>();
        for (int i = 1; i < chs.length - 1; i++) {
            for (int j = 1; j < chs[0].length - 1; j++) {
                String left = chs[i - 1][j - 1] + "" + chs[i - 1][j] + chs[i - 1][j + 1] + chs[i][j + 1] + chs[i + 1][j + 1] + chs[i + 1][j] + chs[i + 1][j - 1] + chs[i][j - 1];
                if (getString(left, right)) {
                    int[] tt = {i + 1, j + 1};
                    list.add(tt);
                }
            }
        }
        return list;
    }

    private boolean getString(String left, String right) {
        int count = -1;
        while (count < left.length()) {
            count = left.indexOf(right.charAt(0), count + 1);
            if (count == -1)
                return false;
            String temp = left.substring(count) + left.substring(0, count);
            if (temp.equals(right))
                return true;
        }
        return false;
    }
}
