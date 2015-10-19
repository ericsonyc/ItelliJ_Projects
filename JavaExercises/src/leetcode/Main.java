package leetcode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ericson on 2015/3/16 0016.
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
//        long start = System.currentTimeMillis();
//        int result = main.reverse(1 << 31 - 1);
//        System.out.println("result:" + result);
//
//        int m = main.atoi("2147483648");
//        System.out.println(m);
//
//        m = -2147447412;
//        System.out.println(main.isPalindrome(m));
//        String s = "aa";
//        String p = ".";
//        System.out.println("match:" + main.isMatch2(s, p));

//        int[] height = new int[10000];
//        for (int i = 0; i < height.length; i++) {
//            height[i] = i;
//        }
//        System.out.println(main.maxArea(height));
//        System.out.println(main.maxArea1(height));
//        System.out.println(main.maxArea2(height));

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            queue.add(i);
        }
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        queue.add(10);
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.add(queue.poll());
        }
        Iterator<Integer> ites = queue.iterator();
        while (ites.hasNext()) {
            System.out.println(ites.next());
        }
    }

    //Container With Most Water
    private int maxArea2(int[] height) {
        if (height == null || height.length < 1)
            return 0;
        int result = 0;
        ArrayList<Integer> seq = new ArrayList<Integer>();
        seq.add(new Integer(0));
        for (int i = 1; i < height.length; i++) {
            for (Integer idx : seq) {
                int ht = height[i] > height[idx.intValue()] ? height[idx.intValue()] : height[i];
                int area = (i - idx.intValue()) * ht;
                if (area > result) result = area;
            }
            int lastIdx = seq.get(seq.size() - 1).intValue();
            if (height[i] > height[lastIdx]) {
                seq.add(new Integer(i));
            }
        }
        return result;
    }

    private int maxArea1(int[] height) {
        int len = height.length, low = 0, high = len - 1;
        int maxArea = 0;
        while (low < high) {
            maxArea = Math.max(maxArea, (high - low) * Math.min(height[low], height[high]));
            if (height[low] < height[high]) {
                low++;
            } else {
                high--;
            }
        }
        return maxArea;
    }

    public int maxArea(int[] height) {
        int[] indexes = new int[height.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        int temp = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = 0; j < height.length - i - 1; j++) {
                if (height[j] > height[j + 1]) {
                    temp = height[j];
                    height[j] = height[j + 1];
                    height[j + 1] = temp;
                    temp = indexes[j];
                    indexes[j] = indexes[j + 1];
                    indexes[j + 1] = temp;
                }
            }
        }

        int maxwater = 0;
        int min = 0;
        for (int i = 0; i < indexes.length; i++) {
            min = 0;
            for (int j = i + 1; j < indexes.length; j++) {
                if (Math.abs(indexes[j] - indexes[i]) > min) {
                    min = Math.abs(indexes[j] - indexes[i]);
                }
            }
            if (maxwater < min * height[i]) {
                maxwater = min * height[i];
            }
        }
        return maxwater;
    }

    //palindrome Number
    public boolean isPalindrome(int x) {
        int s = Math.abs(x);
        int output = 0;
        while (s != 0) {
            output = output * 10 + s % 10;
            s = s / 10;
        }
        return output == Math.abs(x);
    }

    //Regular Expression Matching
    private boolean isMatch2(String s, String p) {
        if (p.length() == 0) return s.length() == 0;
        if (p.length() == 1) return s.equals(p) || (p.charAt(0) == '.' && s.length() == 1);
        if (p.charAt(1) != '*') {
            if (!matchFirst(s, p)) return false;
            return isMatch2(s.substring(1), p.substring(1));
        } else {
            while (s.length() > 0 && matchFirst(s, p)) {
                if (isMatch2(s, p.substring(2))) {
                    return true;
                }
                s = s.substring(1);
            }
            return isMatch2(s, p.substring(2));
        }
    }

    private boolean matchFirst(String s, String p) {
        return s.length() > 0 && (s.charAt(0) == p.charAt(0) || (p.charAt(0) == '.') && s.length() > 0);
    }

    private boolean isMatch(String s, String p) {
//        if (p.length() <= 1 || s.length() == 0)
//            return s.equals(p);
//        if (p.charAt(1) != '*') {
//            if (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')
//                return isMatch(s.substring(1), p.substring(1));
//            else
//                return false;
//        }
//
//        int count = 0;
//        while (s.length() > count && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
//            if (isMatch(s.substring(count), p.substring(2)))
//                return true;
//            count++;
//        }
//        return isMatch(s.substring(0), p.substring(2));
        if (s.length() == 0 || p.length() <= 1) {
            if (p.length() <= 1)
                return s.equals(p);
            else if (p.charAt(1) == '*')
                return isMatch(s, p.substring(2));
            else
                return false;

        }

        if (p.charAt(1) != '*') {
            return ((p.charAt(0) == s.charAt(0)) || (p.charAt(0) == '.' && s.length() > 0)) && isMatch(s.substring(1), p.substring(1));
        }
        int count = 0;
        while ((s.length() > count && p.charAt(0) == s.charAt(0)) || (p.charAt(0) == '.' && s.length() > count)) {
            if (isMatch(s.substring(count), p.substring(2)))
                return true;
            count++;
        }
        return isMatch(s, p.substring(2));
    }

    //String to Integer
    private int atoi(String str) {
        boolean flag = false;
        str = str.trim();
        char[] chs = str.toCharArray();
        if (chs.length == 0)
            return 0;
        int count = 0;
        if (chs[0] == '-' || chs[0] == '+') {
            flag = true;
            count++;
        }
        long sum = 0;
        for (int i = count; i < chs.length; i++) {
            if (chs[i] >= '0' && chs[i] <= '9') {
                sum = sum * 10 + chs[i] - '0';
                if (flag && chs[0] == '-') {
                    if (-sum < Integer.MIN_VALUE)
                        return Integer.MIN_VALUE;
                } else {
                    if (sum > Integer.MAX_VALUE)
                        return Integer.MAX_VALUE;
                }
            } else {
                break;
            }
        }
        return flag ? (chs[0] == '+' ? (int) sum : -(int) sum) : (int) sum;
    }

    //Reverse Integer
    private int reverse(int x) {
//        int count = 0;
//        while (x > 0) {
//            x = x / 10;
//            count++;
//        }
        boolean flag = false;
        long border = 2L << 31;
        int t = x;
        int sum = 0;
        while (t != 0) {
            int y = t % 10;
            if (sum * 10L + y > border || sum * 10L + y < -border) {
                flag = true;
                break;
            }
            sum = sum * 10 + y;
            t = t / 10;
        }
        if (!flag)
            return sum;
        else
            return 0;
    }
}
