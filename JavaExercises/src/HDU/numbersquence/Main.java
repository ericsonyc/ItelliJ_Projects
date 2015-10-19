package HDU.numbersquence;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int l, r, n;
        int[] result = new int[50];
        int count;
        while (scanner.hasNext()) {
            count = 2;
            l = scanner.nextInt();
            r = scanner.nextInt();
            n = scanner.nextInt();
            if (l == 0 && r == 0 && n == 0)
                break;
            result[0] = 1;
            result[1] = 1;
            while (count < result.length) {
                result[count] = (l * result[count - 1] + r * result[count - 2]) % 7;
                count++;
            }
            System.out.println(result[n % 49 - 1]);
        }
    }
}
