package exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by ericson on 2015/3/15 0015.
 */
public class LongestString {
    public static void main(String[] args) {
        LongestString longest = new LongestString();
        String str = "aaja";
        int result = longest.lengthOfLongestSubstring(str);
        System.out.println(result);
    }

    private int lengthOfLongestSubstring(String s) {
        Hashtable hash = new Hashtable();
        int length = s.length();
        int max = 0;
        int availablefrom = 0;
        for (int i = 0; i < length; i++) {
            if (hash.containsKey(s.charAt(i))) {
                int last = (Integer) hash.get(s.charAt(i));
                availablefrom = Math.max(availablefrom, last + 1);
            }
            max = Math.max(max, i - availablefrom + 1);
            hash.put(s.charAt(i), i);
        }
        return max;
    }

    private String longestString(String str) {
        int left = 0, right = -1;
        int maxlength = 0;
        int length = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        List<Character> lists = new ArrayList<Character>();
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (!lists.contains(ch)) {
                lists.add(ch);
                right = i;
            } else {
                length = right - left + 1;
                maxlength = Math.max(maxlength, right - left + 1);
                map.put(length, left);
                while (str.charAt(left) != ch) {
                    lists.remove((Character) str.charAt(left));
                    left++;
                }
                left++;
            }
        }
        if (left < str.length() - 1) {
            map.put(str.length() - left, left);
        }
        left = map.get(maxlength);
        return str.substring(left, left + maxlength);
    }
}
