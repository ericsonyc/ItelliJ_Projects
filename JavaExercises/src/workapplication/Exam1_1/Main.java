package workapplication.Exam1_1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n, m;
        int[][] grid;
        n = scanner.nextInt();
        m = scanner.nextInt();
        grid = new int[n][m];
        if (n > 0 && m > 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = scanner.nextInt();
                }
            }
            long startTime = System.currentTimeMillis();
            System.out.println(mm.getScore(grid));
            long endTime = System.currentTimeMillis();
            System.out.println("ex:"+(endTime - startTime));
        }
    }

    private long getScore(int[][] grid) {
        long max = 0;
        long[] result = new long[1];
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] != -1) {
                result[0] = 0;
                int temp = grid[i][0];
                grid[i][0] = -1;
                getScoreRecur(grid, i, 0, temp, result);
                max = Math.max(result[0], max);
                grid[i][0] = temp;
            }
        }
        return max;
    }

    private void getScoreRecur(int[][] grid, int row, int col, long max, long[] result) {
        int up = (row - 1 + grid.length) % grid.length;
        int down = (row + 1) % grid.length;
        int right = col + 1;
        if (grid[up][col] != -1) {
            int temp = grid[up][col];
            grid[up][col] = -1;
            getScoreRecur(grid, up, col, row == 0 ? temp : max + temp, result);
            grid[up][col] = temp;
        }
        if (grid[down][col] != -1) {
            int temp = grid[down][col];
            grid[down][col] = -1;
            getScoreRecur(grid, down, col, row == grid.length - 1 ? temp : max + temp, result);
            grid[down][col] = temp;
        }
        if (right < grid[0].length && grid[row][right] != -1) {
            int temp = grid[row][right];
            grid[row][right] = -1;
            getScoreRecur(grid, row, right, max + temp, result);
            grid[row][right] = temp;
        }
        if (right == grid[0].length) {
            result[0] = Math.max(result[0], max);
        }
    }
}
