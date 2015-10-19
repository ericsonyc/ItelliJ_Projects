package exercises;

/**
 * Created by ericson on 2015/3/8 0008.
 */
public class RectangleOverlap {
    public static void main(String[] args) {

    }

    class Point {
        int x;
        int y;
    }

    class Rect {
        Point p1;
        Point p2;
    }

    class Coords {
        int left;
        int right;
        int up;
        int down;
    }

    private void swap(int x, int y) {
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
    }

    Coords getCoords(Rect r) {
        int lx = r.p1.x;
        int rx = r.p2.x;
        int uy = r.p1.y;
        int dy = r.p2.y;
        if (lx > rx) swap(lx,rx);
        if(dy>uy) swap(dy,uy);
        return new Coords();
    }
}
