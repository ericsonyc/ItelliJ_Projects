package mogujie;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int r, x, y, x1, y1;
        while (scanner.hasNext()) {
            r = scanner.nextInt();
            x = scanner.nextInt();
            y = scanner.nextInt();
            x1 = scanner.nextInt();
            y1 = scanner.nextInt();
            double distance = Math.sqrt((y1 - y) * (y1 - y) + (x1 - x) * (x1 - x));
            System.out.println(mm.getStep(r, distance));
        }
    }

    private int getStep(int r, double distance) {
        int result = (int) distance / (2 * r);
        distance -= result * r * 2;
        if (distance > 0)
            result += 1;
        return result;
    }
}
