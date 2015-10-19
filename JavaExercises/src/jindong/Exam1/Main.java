package jindong.Exam1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int M, N;
        byte[] temp;
        while (scanner.hasNext()) {
            M = scanner.nextInt();
            N = scanner.nextInt();
            temp = new byte[M + 1];
            int left, right;
            for (int i = 0; i < N; i++) {
                left = scanner.nextInt();
                right = scanner.nextInt();
                for (int j = left; j <= right; j++) {
                    temp[j] |= 1;
                }
            }
            int count = 0;
            for (int i = 0; i < temp.length; i++) {
                if ((temp[i] & 1) == 0)
                    count++;
            }
            System.out.println(count);
        }
    }
}
