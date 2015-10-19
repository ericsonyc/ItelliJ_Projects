package yamaxun;

// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// CLASS BEGINS, THIS CLASS IS REQUIRED

public class Homes {
    //METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public static int countHomes(int grid[][]) {
        // INSERT YOUR CODE HERE
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = 2;
                    count++;
                    judge(grid, i, j);
                }
            }
        }
        return count;
    }

    public static void judge(int grid[][], int row, int col) {
        int top = row == 0 ? 0 : row - 1;
        int down = row == grid.length - 1 ? row : row + 1;
        int left = col == 0 ? 0 : col - 1;
        int right = col == grid[0].length - 1 ? col : col + 1;
        if (grid[top][col] == 1) {
            grid[top][col] = 2;
            judge(grid, top, col);
        }
        if (grid[row][left] == 1) {
            grid[row][left] = 2;
            judge(grid, row, left);
        }
        if (grid[down][col] == 1) {
            grid[down][col] = 2;
            judge(grid, down, col);
        }
        if (grid[row][right] == 1) {
            grid[row][right] = 2;
            judge(grid, row, right);
        }
    }
// METHOD SIGNATURE ENDS
}