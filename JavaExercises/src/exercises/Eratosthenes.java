package exercises;

/**
 * Created by ericson on 2015/2/3 0003.
 */
public class Eratosthenes {
    public static void main(String[] args) {
        int N = 20000;
        Eratosthenes eratosthenes = new Eratosthenes();
        long startTime = System.currentTimeMillis();
        int[] data = eratosthenes.eratosthenes(N);
        long endTime = System.currentTimeMillis();
        System.out.println("\nTime:" + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        eratosthenes.volidateData(data);
        endTime = System.currentTimeMillis();
        System.out.println("\nTime:" + (endTime - startTime) + "ms");
    }

    private void volidateData(int[] data) {
        for (int i = 2; i < data.length; i++) {
            if (data[i] != 0) {
                int j;
                for (j = 2; j * j < i; j++) {
                    if (i % j == 0) {
                        break;
                    }
                }
                if (j * j >= i) {
                    System.out.print(i + "  ");
                }
            }
        }
    }

    private int[] eratosthenes(int N) {
        int[] mark = new int[N + 2];
        for (int i = 2; i < mark.length; i++) {
            mark[i] = 1;
        }
        for (int i = 2; i * i <= N; i++) {
            if (mark[i] == 0) {
                continue;
            }
            for (int j = 2; j * i <= N; j++) {
                mark[j * i] = 0;
            }
        }
        for (int i = 2; i <= N; i++) {
            if (mark[i] == 1) {
                System.out.print(i + "  ");
            }
        }
        return mark;
    }
}
