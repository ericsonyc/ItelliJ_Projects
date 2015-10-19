package hihocode.manacher;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String word = sc.nextLine();
            StringBuffer sb = new StringBuffer("#");
            for (int j = 0; j < word.length(); j++) {
                sb.append(word.charAt(j) + "#");
            }
            word = sb.toString();
            int[] len = new int[word.length()];
            len[0] = 1;
            int max = 0, po = 0;
            for (int j = 1; j < word.length(); j++) {
                if (max > j) {
                    if (len[2 * po - j] < max - j) {
                        len[j] = len[2 * po - j];
                    } else {
                        len[j] = max - j;
                        int index = 1;
                        while (max + index < word.length() && 2 * j - max - index >= 0 && word.charAt(max + index) == word.charAt(2 * j - max - index)) {
                            len[j]++;
                            index++;
                        }
                        if (index > 1) {
                            max = j + len[j];
                            po = j;
                        }
                    }
                } else {
                    int index = 1;
                    while (j + index < word.length() && j - index >= 0 && word.charAt(j + index) == word.charAt(j - index)) {
                        len[j]++;
                        index++;
                    }
                    max = j + len[j];
                    po = j;
                }
            }
            int m = 0;
            for (int j = 0; j < len.length; j++) {
                m = Math.max(len[j], m);
            }
            System.out.println(m);
        }
    }
}
