package microsoft.exam4;

import java.math.BigDecimal;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        float x, y, r;
        while (scanner.hasNext()) {
            x = scanner.nextFloat();
            y = scanner.nextFloat();
            r = scanner.nextFloat();
            int[] result = mm.getOutput(x, y, r);
            System.out.println(result[0] + " " + result[1]);
        }
    }

    private int[] getOutput(float x, float y, float r) {
        int left = (int) (x - r - 2);
        int right = (int) (x + r + 2);
        int top = (int) (y + r + 2);
        int bottom = (int) (y - r - 2);
        float max = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(21, new java.util.Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return Integer.compare(o2[1], o1[1]);
                }
                return Integer.compare(o2[0], o1[0]);
            }
        });
        int length = (int) (r / 2);
        getLength(top, bottom, left, left + length, x, y, r, queue, max);
        getLength(top, top - length, left, right, x, y, r, queue, max);
        getLength(top, bottom, right - length, right, x, y, r, queue, max);
        getLength(bottom + length, bottom, left, right, x, y, r, queue, max);
        return queue.poll();
    }

    private void getLength(int top, int bottom, int left, int right, float x, float y, float r, PriorityQueue<int[]> queue, float max) {
        for (int i = top; i >= bottom; i--) {
            for (int j = left; j <= right; j++) {
                BigDecimal distance = getDistance(x, y, j, i);
                BigDecimal dd = new BigDecimal(r);
                dd = dd.multiply(dd);
                if (distance.compareTo(dd) <= 0) {
                    queue.offer(new int[]{j, i});
                    int[] temp = queue.poll();
                    queue.clear();
                    queue.offer(temp);
                }
            }
        }
    }

    private BigDecimal getDistance(float x, float y, int i, int j) {
        BigDecimal decimal = new BigDecimal(i - x);
        BigDecimal dd = new BigDecimal(j - y);
        BigDecimal temp = decimal.multiply(decimal).add(dd.multiply(dd));
        return temp;
    }
}
