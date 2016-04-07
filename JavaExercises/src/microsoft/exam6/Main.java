package microsoft.exam6;

import javafx.embed.swing.JFXPanelBuilder;

import java.util.Scanner;

/**
 * Created by ericson on 2016/4/6 0006.
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        int N, M;
        N = scanner.nextInt();
        M = scanner.nextInt();
        scanner.nextLine();
        char[][] maze = new char[N][M];
        for (int i = 0; i < N; i++) {
            maze[i] = scanner.nextLine().toCharArray();
        }
        int count = main.getCountBlock(maze);
    }

    public int getCountBlock(char[][] maze) {
        int count = 0;
        int[][] dp=new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if(maze[i][j]=='.'){

                }
            }
        }
        return count;
    }
}
