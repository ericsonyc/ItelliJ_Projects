package exercises;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by ericson on 2015/3/2 0002.
 */
public class RegularExp {
    public static void main(String[] args) throws Exception {
        RegularExp regularExp = new RegularExp();
        System.out.println("Please enter two numbers:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine().trim();
        String p = reader.readLine().trim();
        boolean result = regularExp.isMatch(s, p);
        System.out.println("The result is: " + result);
    }

    boolean isMatch(String s, String p) {
        assert (s == null && p == null);
        if (p.length() == 0) return s.length() == 0;
        if (p.length() == 1) return s.equals(p);
        if (p.charAt(1) != '*') {
            assert (s != "*");
            return (p.charAt(0)==s.charAt(0) || (p.charAt(0) == '.' && s.length() != 0)) && isMatch(s.substring(1), p.substring(1));
        }

        while (s.length()==0||(p.charAt(0) == s.charAt(0)) || (p.charAt(0) == '.' && s.length() != 0)) {
            if (isMatch(s, p.substring(2))) return true;
            s = s.substring(1);
        }
        return isMatch(s, p.substring(2));
    }
}
