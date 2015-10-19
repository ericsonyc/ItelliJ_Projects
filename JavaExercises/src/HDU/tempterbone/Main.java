package HDU.tempterbone;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int N, M, T;
            N = scanner.nextInt();
            M = scanner.nextInt();
            T = scanner.nextInt();
            if (M + N + T == 0)
                break;
            scanner.nextLine();
            char[][] maze = new char[N][M];
            int[][] numbers = new int[N][M];
            int a = 0, b = 0;
            int da = 0, db = 0;
            for (int i = 0; i < N; i++) {
                String temp = scanner.nextLine();
                maze[i] = temp.toCharArray();
                for (int j = 0; j < temp.length(); j++) {
                    if (maze[i][j] == 'S') {
                        a = i;
                        b = j;
                    }
                    if (maze[i][j] == 'D') {
                        da = i;
                        db = j;
                    }
                }
            }
            if (Math.abs(a - da) + Math.abs(b - db) > T || (a + b + da + db + T) % 2 == 1) {
                System.out.println("NO");
            } else
                System.out.println(mm.getSurvived(maze, numbers, T, a, b));
        }
    }

    private String getSurvived(char[][] maze, int[][] numbers, int T, int i, int j) {
        if (T == 0) {
            if (maze[i][j] != 'D')
                return "NO";
            return "YES";
        }
        if (i > 0 && numbers[i - 1][j] == 0) {
            numbers[i][j] = 1;
            String result = getSurvived(maze, numbers, T - 1, i - 1, j);
            if (result == "YES")
                return "YES";
        }
        if (i < maze.length - 1 && numbers[i + 1][j] == 0) {
            numbers[i][j] = 1;
            String result = getSurvived(maze, numbers, T - 1, i + 1, j);
            if (result == "YES")
                return "YES";
        }
        if (j > 0 && numbers[i][j - 1] == 0) {
            numbers[i][j] = 1;
            String result = getSurvived(maze, numbers, T - 1, i, j - 1);
            if (result == "YES")
                return "YES";
        }
        if (j < maze[0].length - 1 && numbers[i][j + 1] == 0) {
            numbers[i][j] = 1;
            String result = getSurvived(maze, numbers, T - 1, i, j + 1);
            if (result == "YES")
                return "YES";
        }
        return "NO";
    }
}
