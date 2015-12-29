package pacman;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public final class MazeData {

    public static final int BLOCK = 1;
    public static final int EMPTY = 0;
    public static final int GRID_GAP = 16;
    public static final int GRID_SIZE_X = 29;
    public static final int GRID_SIZE_Y = 31;
    public static final int GRID_STROKE = 2;
    public static final int BIG_DOT = 3;
    public static final int NORMAL_DOT = 2;

    public static final Object[][] DOT_POINTERS = new Object[GRID_SIZE_X + 1][GRID_SIZE_Y + 1];
    private static final int[][] MAZE_DATA = new int[GRID_SIZE_X + 1][GRID_SIZE_Y + 1];
    private static final int X_OFFSET = GRID_GAP * 2;
    private static final int Y_OFFSET = GRID_GAP * 2;

    private static int dotTotal = 0;

    public static List<TopScore> queue = new ArrayList<TopScore>(5);//保存5个最高分数

    static {
        for (int i = 0; i < 5; i++)
            queue.add(new TopScore("", 0));
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private MazeData() {
    }

    private static int makeInRange(int a, char coordinate) {
        if (a < 0) {
            return 0;
        } else if ((coordinate == 'X') && (a > GRID_SIZE_X)) {
            return GRID_SIZE_X;
        } else if ((coordinate == 'Y') && (a > GRID_SIZE_Y)) {
            return GRID_SIZE_Y;
        }
        return a;
    }


    // set the grid of maze data to be BLOCK
    public static void setBlockMazeData(int x1, int y1, int x2, int y2) {
        x1 = makeInRange(x1, 'X');
        y1 = makeInRange(y1, 'Y');
        x2 = makeInRange(x2, 'X');
        y2 = makeInRange(y2, 'Y');

        for (int i = x1; i <= x2; i++) {
            MAZE_DATA[i][y1] = BLOCK;
            MAZE_DATA[i][y2] = BLOCK;
        }

        for (int i = y1; i <= y2; i++) {
            MAZE_DATA[x1][i] = BLOCK;
            MAZE_DATA[x2][i] = BLOCK;
        }

    }

    public static int calcGridX(int x) {
        return GRID_GAP * x + X_OFFSET;
    }

    // calcGridX float version
    public static float calcGridXFloat(final float x) {
        return GRID_GAP * x + X_OFFSET;
    }

    public static int calcGridY(int y) {
        return GRID_GAP * y + Y_OFFSET;
    }

    // calcGridY float version
    public static float calcGridYFloat(final float y) {
        return GRID_GAP * y + Y_OFFSET;
    }

    public static int getData(int x, int y) {
        return MAZE_DATA[x][y];
    }

    public static void setData(int x, int y, int value) {
        MAZE_DATA[x][y] = value;

        if ((value == BIG_DOT) || (value == NORMAL_DOT)) {
            dotTotal++;
        }
    } // end setData

    public static Object getDot(int x, int y) {
        return DOT_POINTERS[x][y];
    }

    public static void setDot(int x, int y, Object dot) {
        DOT_POINTERS[x][y] = dot;
    }

    public static int getDotTotal() {
        return dotTotal;
    }

    public static void printData() {
        for (int i = 0; i <= GRID_SIZE_Y; i++) {
            for (int j = 0; j <= GRID_SIZE_X; j++) {
                System.out.print(MAZE_DATA[j][i] + " ");
            }
            System.out.println("");
        }
    }

    public static void saveData(String filename, Maze maze) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            String save = maze.saveMaze();
            bw.write(save);
            bw.newLine();
            bw.flush();
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i <= GRID_SIZE_Y; i++) {
                sb.delete(0, sb.length());
                for (int j = 0; j <= GRID_SIZE_X; j++) {
                    if (null != DOT_POINTERS[j][i]) {
                        sb.append(((Dot) DOT_POINTERS[j][i]).isVisible() + ",");
                    } else {
                        sb.append(",");
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
                bw.write(sb.toString());
                bw.newLine();
                bw.flush();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readData(String filename, Maze maze) {
        maze.setMaze(filename);
    }

//    public static void saveData(String filename) {
//        try {
//            RandomAccessFile randomFile = new RandomAccessFile(filename, "rw");
//            StringBuilder sb = new StringBuilder("");
//            for (int i = 0; i <= GRID_SIZE_Y; i++) {
//                sb.delete(0, sb.length());
//                for (int j = 0; j <= GRID_SIZE_X; j++) {
//                    sb.append(MAZE_DATA[j][i] + ",");
//                }
//                sb.deleteCharAt(sb.length() - 1);
//                randomFile.writeChars(sb.toString() + "\n");
//            }
//            randomFile.close();
//
////            saveDots(filename);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void saveDots(String filename) {
//        try {
//            RandomAccessFile randomFile = new RandomAccessFile(filename, "rw");
//            StringBuilder sb = new StringBuilder("");
//            randomFile.seek(randomFile.length());
//            for (int i = 0; i <= GRID_SIZE_Y; i++) {
//                sb.delete(0, sb.length());
//                for (int j = 0; j <= GRID_SIZE_X; j++) {
//                    if (null != DOT_POINTERS[j][i]) {
//                        sb.append(((Dot) DOT_POINTERS[j][i]).dotType + ",");
//                    } else {
//                        sb.append("-1,");
//                    }
//                }
//                sb.deleteCharAt(sb.length() - 1);
//                randomFile.writeChars(sb.toString() + "\n");
//            }
//            randomFile.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void operateDots(){
//        for (int i = 0; i <= GRID_SIZE_Y; i++) {
//            for (int j = 0; j <= GRID_SIZE_X; j++) {
//                if (null != DOT_POINTERS[j][i]) {
//                    ((Dot) DOT_POINTERS[j][i]).setVisible(true);
//                } else {
//                    System.out.print("  ");
//                }
//            }
//        }
//    }

//    public static void operateGhosts(Maze maze){
//        final Ghost ghostInky = new Ghost(
//                new Image(maze.getClass().getResourceAsStream("images/ghosthollow2.png")),
//                new Image(maze.getClass().getResourceAsStream("images/ghosthollow3.png")),
//                maze,
//                maze.pacMan,
//                12,
//                15,
//                1,
//                0,
//                20);
//    }

    public static void printDots() {
        for (int i = 0; i <= GRID_SIZE_Y; i++) {
            for (int j = 0; j <= GRID_SIZE_X; j++) {
                if (null != DOT_POINTERS[j][i]) {
                    System.out.print(((Dot) DOT_POINTERS[j][i]).dotType + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
    }

//    public static void readData1(String filename, Maze maze) {
//        try {
//            String[] strs = null;
//            RandomAccessFile random = new RandomAccessFile(filename, "r");
//            for (int i = 0; i <= GRID_SIZE_Y; i++) {
//                strs = random.readLine().split(",");
//                for (int j = 0; j <= GRID_SIZE_X; j++) {
//                    MAZE_DATA[j][i] = Integer.parseInt(strs[j].trim());
//                }
//            }
//            for (int i = 0; i <= GRID_SIZE_Y; i++) {
//                strs = random.readLine().split(",");
//                for (int j = 0; j <= GRID_SIZE_X; j++) {
//                    if (null != DOT_POINTERS[j][i]) {
//                        ((Dot) DOT_POINTERS[j][i]).dotType = Integer.parseInt(strs[j].trim());
//                    } else {
//                    }
//                }
//            }
//            random.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
