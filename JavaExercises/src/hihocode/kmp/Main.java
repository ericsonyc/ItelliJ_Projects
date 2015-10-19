package hihocode.kmp;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String pattern = sc.nextLine();
            int[] next = new int[pattern.length()];
            next[0] = 0;
            for (int j = 1; j < next.length; j++) {

                if (pattern.charAt(next[j - 1]) == pattern.charAt(j)) {
                    next[j] = next[j - 1] + 1;
                } else {
                    next[j] = pattern.charAt(0) == pattern.charAt(j) ? 1 : 0;
                }
            }
            int count = 0;
            String str = sc.nextLine();
            int t = 0, j = 0;
            for (; t < pattern.length() && j < str.length(); ) {
                if (pattern.charAt(t) == str.charAt(j)) {
                    t++;
                    j++;
                } else {
                    j = t == 0 ? j + 1 : j;
                    t = t == 0 ? 0 : next[t - 1];
                }
                if (t >= pattern.length()) {
                    count++;
                    t = next[t - 1];
                }
            }
            System.out.println(count);
        }
    }
}
