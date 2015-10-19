package HDU.QuoitDesign;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/26 0026.
 */
public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = Integer.parseInt(scanner.nextLine());
            if (n == 0)
                break;
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                String[] str = scanner.nextLine().split(" ");
                points[i] = mm.new Point(Double.parseDouble(str[0]), Double.parseDouble(str[1]));
            }
            mm.sortPoints(points);
            System.out.println(mm.getMinLength(points, 0, n - 1) / 2);
        }
    }

    private double getMinLength(Point[] points, int left, int right) {
        if (right - left == 0) {
            return Double.MAX_VALUE;
        }
        if (right - left == 1) {
            return getLength(points[left], points[right]);
        }
        int middle = left + (right - left) / 2;
        double lev = getMinLength(points, left, middle);
        double riv = getMinLength(points, middle + 1, right);
        double min = Math.min(lev, riv);
        Point[] area = new Point[right - left + 1];
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - middle) < min) {
                area[count++] = points[i];
            }
        }
        sortByY(area, 0, count);
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (area[j].y - area[i].y >= min)
                    break;
                min = Math.min(min, getLength(area[j], area[i]));
            }
        }
        return min;
    }

    private void sortByY(Point[] points, int left, int right) {
        Point temp;
        for (int i = left; i < right; i++) {
            for (int j = left; j < right - i - 1; j++) {
                if (points[j].y > points[j + 1].y) {
                    temp = points[j];
                    points[j] = points[j + 1];
                    points[j + 1] = temp;
                }
            }
        }
    }

    private double getLength(Point l, Point r) {
        return Math.sqrt(Math.pow(l.x - r.x, 2) + Math.pow(l.y - r.y, 2));
    }

    private void sortPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length - i - 1; j++) {
                if (points[j + 1].x < points[j].x) {
                    Point p = points[j];
                    points[j] = points[j + 1];
                    points[j + 1] = p;
                }
            }
        }
    }

    class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
