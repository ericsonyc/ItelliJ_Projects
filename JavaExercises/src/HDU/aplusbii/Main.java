package HDU.aplusbii;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by ericson on 2015/8/19 0019.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        BigDecimal a;
        BigDecimal b;
        for (int i = 1; i <= n; i++) {
            a = sc.nextBigDecimal();
            b = sc.nextBigDecimal();
            System.out.println("Case " + i + ":");
            System.out.println(a + " + " + b + " = " + a.add(b));
            if (i < n)
                System.out.println();
        }
    }
}
