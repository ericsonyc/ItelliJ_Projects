package exercises;

/**
 * Created by ericson on 2015/3/1 0001.
 */
public class Palindronmic {

    public static void main(String[] args) {
        Palindronmic palin = new Palindronmic();
        String str = "caababaaf";
        //String result = palin.longestPalindromeDP(str);
        //String result = palin.longestPalindromeSimple(str);
        //String result = palin.longestPalindrome(str);
        //System.out.println("result:" + result);

        int x = 123321;
        System.out.println("result:" + palin.isPalindromeStack(x));
    }

    boolean isPalindromeStack(int x) {
        return isPalindromeStack(x, new int[]{x});
    }

    boolean isPalindromeStack(int x, int[] y) {
        if (x < 0) return false;
        if (x == 0) return true;
        if (isPalindromeStack(x / 10, y) && (x % 10 == y[0] % 10)) {
            y[0] /= 10;
            return true;
        } else {
            return false;
        }
    }

    boolean isPalindrome(int x) {
        if (x < 0) return false;
        int div = 1;
        while (x / div >= 10) {
            div *= 10;
        }
        while (x != 0) {
            int l = x / div;
            int r = x % 10;
            if (l != r) return false;
            x = (x % div) / 10;
            div /= 100;
        }
        return true;
    }

    String longestPalindrome(String s) {
        String T = preProgress(s);
        int n = T.length();
        int[] P = new int[n];
        int C = 0, R = 0;
        for (int i = 1; i < n - 1; i++) {
            int i_mirror = 2 * C - i;
            P[i] = (R > i) ? Math.min(R - i, P[i_mirror]) : 0;
            while (T.charAt(i + 1 + P[i]) == T.charAt(i - 1 - P[i])) {
                P[i]++;
            }
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }
        }

        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        return s.substring((centerIndex - 1 - maxLen) / 2, (centerIndex + maxLen - 1) / 2);
    }

    String preProgress(String s) {
        int n = s.length();
        if (n == 0) return "^$";
        String ret = "^";
        for (int i = 0; i < n; i++) {
            ret += "#" + s.substring(i, i + 1);
        }
        ret += "#$";
        return ret;
    }

    String longestPalindromeSimple(String s) {
        int n = s.length();
        if (n == 0) return "";
        String longest = s.substring(0, 1);
        for (int i = 0; i < n - 1; i++) {
            String p1 = expandAroundCenter(s, i, i);
            if (p1.length() > longest.length())
                longest = p1;
            String p2 = expandAroundCenter(s, i, i + 1);
            if (p2.length() > longest.length())
                longest = p2;
        }
        return longest;
    }

    String expandAroundCenter(String s, int c1, int c2) {
        int l = c1, r = c2;
        int n = s.length();
        while (l >= 0 && r <= n - 1 && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }

    String longestPalindromeDP(String s) {
        int n = s.length();
        int longestBegin = 0;
        int maxLen = 1;
        boolean[][] table = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            table[i][i] = true;
        }

        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                table[i][i + 1] = true;
                longestBegin = i;
                maxLen = 2;
            }
        }
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && table[i + 1][j - 1]) {
                    table[i][j] = true;
                    longestBegin = i;
                    maxLen = len;
                }
            }
        }
        return s.substring(longestBegin, longestBegin + maxLen);
    }
}
