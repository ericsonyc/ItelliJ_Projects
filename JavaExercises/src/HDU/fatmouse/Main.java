package HDU.fatmouse;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        double[] J = new double[1000];
        double[] F = new double[1000];
        while (scanner.hasNext()) {
            int M = scanner.nextInt();
            int N = scanner.nextInt();
            if (M == -1 && N == -1)
                break;
            for (int i = 0; i < N; i++) {
                J[i] = scanner.nextDouble();
                F[i] = scanner.nextDouble();
                J[i] /= F[i];
                scanner.nextLine();
            }
            mm.sort(J, F, N);
            System.out.printf("%.3f",mm.getMaxBeans(J, F, M, N));
            System.out.println();
        }
    }

    private void sort(double[] J, double[] F, int N) {
        double temp = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - i - 1; j++) {
                if (J[j] < J[j + 1]) {
                    temp = J[j];
                    J[j] = J[j + 1];
                    J[j + 1] = temp;
                    temp = F[j];
                    F[j] = F[j + 1];
                    F[j + 1] = temp;
                }
            }
        }
    }

    private double getMaxBeans(double[] J, double[] F, int M, int N) {
        double beans = 0;
        for (int i = 0; i < N && M > 0; i++) {
            if (M >= F[i]) {
                beans += F[i] * J[i];
                M -= F[i];
            } else {
                beans += M * J[i];
                M = 0;
            }
        }
        return beans;
    }
}
