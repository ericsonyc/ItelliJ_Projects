package HDU.sum;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/19 0019.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n;
        while (sc.hasNext()) {
            n = sc.nextInt();
            System.out.println((1 + n) * n / 2);
            System.out.println();
        }
    }
}
