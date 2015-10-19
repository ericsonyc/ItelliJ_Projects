package hihocode.marshtomp;

import java.util.Scanner;

public class Main {
    private String orign = "marshtomp";
    private String replace = "fjxmlhx";

    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String temp = scanner.nextLine();
            System.out.println(mm.getString(temp));
        }
    }

    private String getString(String strs) {
        int left = 0;
        StringBuffer sb = new StringBuffer(strs);
        while (left < sb.length()) {
            if (Character.toLowerCase(sb.charAt(left)) == orign.charAt(0)) {
                int point = 1;
                int temp = ++left;
                while (temp < sb.length() && point < orign.length()) {
                    if (Character.toLowerCase(sb.charAt(temp)) == orign.charAt(point)) {
                        temp++;
                        point++;
                    } else
                        break;
                }
                if (point == orign.length()) {
                    sb.replace(left - 1, temp, replace);
                    left = temp - (orign.length() - replace.length());
                }
            } else
                left++;
        }
        return sb.toString();
    }
}
