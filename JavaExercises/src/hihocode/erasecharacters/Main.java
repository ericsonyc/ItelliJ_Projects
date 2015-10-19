package hihocode.erasecharacters;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String str = scanner.nextLine();
            System.out.println(mm.getResult(str));
        }
    }

    private int getResult(String handle) {
        int result = 0;
        for (int i = 0; i <= handle.length(); i++) {
            String temp = eraseString(handle.substring(0, i) + 'A' + handle.substring(i));
            result = Math.max(handle.length() - temp.length(), result);
            temp = eraseString(handle.substring(0, i) + 'B' + handle.substring(i));
            result = Math.max(handle.length() - temp.length(), result);
            temp = eraseString(handle.substring(0, i) + 'C' + handle.substring(i));
            result = Math.max(handle.length() - temp.length(), result);
        }
        return result + 1;
    }

    private String eraseString(String value) {
        StringBuffer buffer = new StringBuffer(value);
        int count = buffer.length();
        int tempcount = 0;
        while (count != tempcount) {
            count = buffer.length();
            if (count == 0) break;
            char ch = buffer.charAt(0);
            int start = 1;
            tempcount = 0;
            while (start < buffer.length()) {
                if (buffer.charAt(start) == ch) {
                    tempcount++;
                    buffer.deleteCharAt(start);
                } else {
                    ch = buffer.charAt(start);
                    if (tempcount > 0) {
                        buffer.deleteCharAt(start - 1);
                        tempcount = 0;
                    } else
                        start++;
                }
            }
            if (tempcount > 0)
                buffer.deleteCharAt(start - 1);
            tempcount = buffer.length();
        }
        return buffer.toString();
    }
}