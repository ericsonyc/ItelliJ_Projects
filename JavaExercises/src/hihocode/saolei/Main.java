package hihocode.saolei;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//cannot right

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        scanner.nextLine();
        while (T-- > 0) {
            int N, M;
            N = scanner.nextInt();
            M = scanner.nextInt();
            scanner.nextLine();
            int[][] nums = new int[N][M];
            List<Point> kmaps = new LinkedList<Point>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    nums[i][j] = scanner.nextInt();
                    if (nums[i][j] == -1) {
                        Point pp = new Point(i, j, nums[i][j]);
                        kmaps.add(pp);
                    }
                }
                scanner.nextLine();
            }
        }
    }

    static class Point {
        int x;
        int y;
        int value;

        public Point(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            Point pp = (Point) obj;
            return pp.x == this.x && pp.y == this.y;
        }
    }
}
