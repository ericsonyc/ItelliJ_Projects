package exercises;

/**
 * Created by ericson on 2015/3/15 0015.
 */
public class LongPalardim {
    public static void main(String[] args) {
        String str = "adoiuytrtyuiopl";
        LongPalardim palin = new LongPalardim();
        String result = palin.longpa(str);
        System.out.println(result);
    }

    private String longpa(String s) {
        int i = 0;
        int l = 0;
        int n = s.length();
        for (int j = 0; j < n; j++) {
            if (isPalindrome(s, j - l, j)) {
                i = j - l;
                l = l + 1;
            } else if (j - l - 1 >= 0 && isPalindrome(s, j - l - 1, j)) {
                i = j - l - 1;
                l = l + 2;
            }
        }
        return s.substring(i, i + l);
    }

    private boolean isPalindrome(String s, int index, int end) {
        while (index < end) {
            if (s.charAt(index) == s.charAt(end)) {
                index++;
                end--;
            } else {
                break;
            }
        }
        if (index != end) {
            return false;
        } else {
            return true;
        }
    }

}
