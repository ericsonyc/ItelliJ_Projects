package HDU.aplusb;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/19 0019.
 */
public class Main {
    public static void main(String[] args) {
        int a, b;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            a = Integer.parseInt(sc.next());
            b = Integer.parseInt(sc.next());
            System.out.println(a + b);
        }
    }
}