package exercises;

/**
 * Created by ericson on 2015/3/7 0007.
 */
public class LongString {
    public static void main(String[] args) {
        LongString str = new LongString();
        String temp = "gaqadhdswergasdf";
        int result = str.lengthOfLongestSubstring(temp);
        System.out.println("result:" + result);
    }

    int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int i = 0, j = 0;
        int maxLen = 0;
        boolean[] exist = new boolean[26];
        while (j < n) {
            if (exist[s.charAt(j) - 'a']) {
                maxLen = Math.max(maxLen, j - i);
                while (s.charAt(i) != s.charAt(j)) {
                    exist[s.charAt(i) - 'a'] = false;
                    i++;
                }
                i++;
                j++;
            } else {
                exist[s.charAt(j) - 'a'] = true;
                j++;
            }
        }
        maxLen = Math.max(maxLen, n - i);
        return maxLen;
    }
}
