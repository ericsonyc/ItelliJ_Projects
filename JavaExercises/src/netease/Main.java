package netease;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int hp1;
        int hp2;
        int a1;
        int a2;
        int t;
        hp1 = scanner.nextInt();
        hp2 = scanner.nextInt();
        a1 = scanner.nextInt();
        a2 = scanner.nextInt();
        t = scanner.nextInt();
        scanner.nextLine();
        boolean count = false;
        String result = "";
        while (t-- > 0) {
            String[] attack = scanner.nextLine().split(" ");
            if (attack[0].equals("B")) {
                if (attack[1].equals("A")) {
                    count = true;
                }
            } else {
                if (attack[1].equals("A")) {
                    if (count) {
                        count = false;
                        hp2 = hp2 - a1 < 0 ? 0 : hp2 - a1;
                        if (hp2 == 0) {
                            result = "NO";
                            break;
                        }
                    } else {
                        hp1 = hp1 - a2 < 0 ? 0 : hp1 - a2;
                        if (hp1 == 0) {
                            result = "YES";
                            break;
                        }
                        hp2 = hp2 - a1 < 0 ? 0 : hp2 - a1;
                        if (hp2 == 0) {
                            result = "NO";
                            break;
                        }
                    }
                } else {
                    count = false;
                }
            }
        }
        if (result.equals("")) {
            result = hp2 >= hp1 ? "YES" : "NO";
        }
        System.out.println(result);
    }

}