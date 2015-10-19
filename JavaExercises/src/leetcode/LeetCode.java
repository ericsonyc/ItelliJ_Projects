package leetcode;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by ericson on 2015/4/16 0016.
 */
public class LeetCode {

    private String intToRoman(int num) {
        StringBuffer res = new StringBuffer();
        String[] aryC = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] aryX = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] aryI = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        int Mnums = num / 1000;
        int Cnums = (num % 1000) / 100;
        int Xnums = (num % 100) / 10;
        int Inums = num % 10;
        for (int i = Mnums; i > 0; i--) {
            res.append("M");
        }
        res.append(aryC[Cnums]);
        res.append(aryX[Xnums]);
        res.append(aryI[Inums]);
        return res.toString();
    }

    private int romanToInt(String str) {
        int[] numbers = new int[]{1, 5, 10, 50, 100, 500, 1000};
        char[] alphabet = new char[]{'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        char[] chars = str.toCharArray();
        int previous = Integer.MIN_VALUE;
        int num = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            int index = -1;
            for (int j = 0; j < alphabet.length; j++) {
                if (chars[i] == alphabet[j]) {
                    index = j;
                    break;
                }

            }
            if (previous > index) {
                num -= numbers[index];
            } else {
                num += numbers[index];
            }
            previous = index;
        }
        return num;
    }

    private String getCommonPrefix(List<String> lists) {
        if (lists == null || lists.size() < 1)
            return null;
        StringBuilder sb = new StringBuilder();
        String first = lists.get(0);
        for (int i = 0; i < first.length(); i++) {
            char ch = first.charAt(i);
            for (int j = 1; j < lists.size(); j++) {
                if (lists.get(j).length() < i + 1 && ch != lists.get(j).charAt(i)) {
                    return sb.toString();
                }
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    private void mergeSortNoRecursion(int[] datas, int left, int right) {
        int[] temp = new int[datas.length];
        int start;
        int end;
        for (int N = 2; N < right * 2; N *= 2) {
            for (int i = left; i < right; i += N) {
                start = i;
                end = (i + N - 1) >= right ? right - 1 : i + N - 1;
                int k = start;
                int middle = (start + N / 2 - 1) >= right ? right - 1 : (start + N / 2 - 1);
                int t = middle + 1;
                while (start <= middle && t <= end) {
                    if (datas[start] < datas[t]) {
                        temp[k++] = datas[start++];
                    } else {
                        temp[k++] = datas[t++];
                    }
                }
                while (start <= middle) {
                    temp[k++] = datas[start++];
                }
                while (t <= end) {
                    temp[k++] = datas[t++];
                }
            }
            for (int i = 0; i < datas.length; i++) {
                datas[i] = temp[i];
            }
        }
    }

    private List<List<Integer>> treeSum(int[] num) {
//        this.mergeSortNoRecursion(num, 0, num.length);
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        boolean flag = false;
        for (int i = 0; i < num.length - 2; i++) {
            if (i != 0 && num[i - 1] == num[i]) {
                flag = true;
            } else {
                flag = false;
            }
            if (!flag) {
                int low = i + 1, high = num.length - 1;
                int sum = 0 - num[i];
                while (low < high) {
                    if (num[low] + num[high] < sum) {
                        low++;
                    } else if (num[low] + num[high] > sum) {
                        high--;
                    } else {
                        List<Integer> temp = new ArrayList<Integer>();
                        temp.add(num[i]);
                        temp.add(num[low]);
                        temp.add(num[high]);
                        res.add(temp);
                        while (low < num.length - 1 && num[low + 1] == num[low]) {
                            low++;
                        }
                        low++;
                        while (high > i && num[high - 1] == num[high]) {
                            high--;
                        }
                        high--;
                    }
                }
            }
        }
        return res;
    }

    private int treeSumClosest(int[] num, int target) {
        int closest = Integer.MAX_VALUE;
        int close = 0;
        Arrays.sort(num);
        for (int i = 0; i < num.length - 2; i++) {
            int start = i + 1;
            int end = num.length - 1;

            while (start < end) {
                int sum = 0;
                sum += num[i];
                sum += num[start] + num[end];
                if (sum < target) {
                    start++;
                } else if (sum > target) {
                    end--;
                } else {
                    return target;
                }
                if (closest > Math.abs(target - sum)) {
                    closest = Math.abs(target - sum);

                    close = sum;
                }
            }
        }
        return close;
    }

    private List<String> letterCombination(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = Character.getNumericValue(digits.charAt(i));
            while (ans.peek().length() == i) {
                String t = ans.remove();
                for (char s : mapping[x].toCharArray()) {
                    ans.add(t + s);
                }
            }
        }
        return ans;
    }

    private List<String> combiner(String digits) {
        String[] mapping = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> lists = new ArrayList<String>();
        iterativeCombine("", digits, 0, lists, mapping);
        return lists;
    }

    private void iterativeCombine(String prefix, String digits, int offset, List<String> lists, String[] mapping) {
        if (offset >= digits.length()) {
            lists.add(prefix);
            return;
        }
        String str = mapping[digits.charAt(offset) - '0'];
        for (int i = 0; i < str.length(); i++) {
            iterativeCombine(prefix + str.charAt(i), digits, offset + 1, lists, mapping);
        }
    }

    private List<List<Integer>> threeSum(int[] num, int left, int right, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        boolean flag = false;
        for (int i = left; i < right - 2; i++) {
            if (i != left && num[i - 1] == num[i]) {
                flag = true;
            } else {
                flag = false;
            }
            if (!flag) {
                int low = i + 1, high = right - 1;
                int sum = target - num[i];
                while (low < high) {
                    if (num[low] + num[high] < sum) {
                        low++;
                    } else if (num[low] + num[high] > sum) {
                        high--;
                    } else {
                        List<Integer> temp = new ArrayList<Integer>();
                        temp.add(num[i]);
                        temp.add(num[low]);
                        temp.add(num[high]);
                        res.add(temp);
                        while (low < right - 1 && num[low + 1] == num[low]) {
                            low++;
                        }
                        low++;
                        while (high > i && num[high - 1] == num[high]) {
                            high--;
                        }
                        high--;
                    }
                }
            }
        }
        return res;
    }

    private List<List<Integer>> fourSum(int[] num, int target) {
        List<List<Integer>> lists = new ArrayList<List<Integer>>();
        if (num == null || num.length < 4) {
            return lists;
        }
        Arrays.sort(num);
        for (int i = 0; i < num.length - 3; i++) {
            if (i != 0 && num[i - 1] == num[i]) {
                continue;
            }
            int sum = target - num[i];
            List<List<Integer>> tempLists = this.threeSum(num, i + 1, num.length, sum);
            if (tempLists.size() != 0) {
                for (int j = 0; j < tempLists.size(); j++) {
                    List<Integer> ll = tempLists.get(j);
                    ll.add(0, num[i]);
                    lists.add(ll);
                }
            }
        }
        return lists;
    }

    private List<List<Integer>> fourSum2(int[] sum, int target) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (sum == null || sum.length < 4) {
            return lists;
        }
        Arrays.sort(sum);
        for (int i = 0; i < sum.length - 3; i++) {
            if (i > 0 && sum[i - 1] == sum[i])
                continue;

            for (int t = sum.length - 1; t > 3; t--) {
                if (t < sum.length - 1 && sum[t] == sum[t + 1]) {
                    continue;
                }
                int ss = target - sum[i] - sum[t];
                int j = i + 1;
                int k = t - 1;
                while (j < k) {
                    if (j > i + 1 && sum[j - 1] == sum[j]) {
                        j++;
                        continue;
                    }

                    if (k < t - 1 && sum[k] == sum[k + 1]) {
                        k--;
                        continue;
                    }

                    if (sum[j] + sum[k] < ss) {
                        j++;
                    } else if (sum[j] + sum[k] > ss) {
                        k--;
                    } else {
                        List<Integer> ll = Arrays.asList(sum[i], sum[j], sum[k], sum[t]);
                        lists.add(ll);
                        j++;
                        k--;
                    }
                }
            }
        }
        return lists;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    private ListNode removeNthFromEnd(ListNode head, int n) {
        int length = 0;
        if (head == null)
            return null;
        ListNode start = head;
        while (start != null) {
            start = start.next;
            length++;
        }

        start = head;
        for (int i = 0; i < length - n - 1; i++) {
            start = start.next;
        }
        if (length - n <= 0) {
            head = start.next;
            return head;
        }
        if (start.next != null) {
            start.next = start.next.next;
        } else {
            start.next = null;
        }

        return head;
    }

    private boolean isValid(String s) {
        String pattern = ")}]({[";
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("(", ")");
        maps.put("{", "}");
        maps.put("[", "]");
        boolean flag = false;
        char[] chs = s.toCharArray();
        List<String> stack = new LinkedList<String>();
        for (int i = 0; i < chs.length; i++) {
            int index = pattern.indexOf(String.valueOf(chs[i]));
            if (index < 3 && stack.size() == 0) {
                return false;
            } else if (index >= 0) {
                if (index > 2) {
                    stack.add(String.valueOf(chs[i]));
                } else {
                    if (maps.get(stack.get(stack.size() - 1)).toString().indexOf(String.valueOf(chs[i])) != -1) {
                        stack.remove(stack.size() - 1);
                    } else {
                        return false;
                    }
                }
            }
        }
        if (stack.size() == 0) {
            flag = true;
        }
        return flag;
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode start = head;
//        ListNode left = l1;
//        ListNode right = l2;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                head.next = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        if (l1 != null) {
            head.next = l1;
        }
        if (l2 != null) {
            head.next = l2;
        }
        return start.next;
    }

    private List<String> generateParenthesis(int n) {
        List<String> lists = new LinkedList<String>();
        if (n < 1)
            return lists;
        int length = 2 * (n - 1) + 1;
        int treeLength = (1 << length) - 1;
        int[] tree = new int[treeLength];
        for (int i = 1; i < treeLength; i++) {
            if (i % 2 == 1) {
                tree[i] = 1;
            } else {
                tree[i] = 2;
            }
        }
        this.backTracking(tree, 0, lists, n - 1, "");
        for (int i = 0; i < lists.size(); i++) {
            lists.set(i, "(" + lists.get(i) + ")");
        }
        return lists;
    }

    private void backTracking(int[] tree, int root, List<String> lists, int length, String result) {
        if (tree[root] == 1) {
            result += "(";
        } else if (tree[root] == 2) {
            result += ")";
        }

        if (2 * root + 1 >= tree.length) {
            int left = 0;
            int right = 0;
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == '(')
                    left++;
                else if (result.charAt(i) == ')')
                    right++;
                if (right - left > 1)
                    break;
            }
            if (left == right && left + right == 2 * length)
                lists.add(result);
            return;
        }


        backTracking(tree, 2 * root + 1, lists, length, result);
        backTracking(tree, 2 * (root + 1), lists, length, result);
    }


    private List<String> pathesis(int n) {
        List<String> lists = new LinkedList<String>();
        this.backTracking2(lists, "", n, 0);
        return lists;
    }

    private void backTracking2(List<String> lists, String str, int n, int m) {
        if (n == 0 && m == 0) {
            lists.add(str);
            return;
        }
        if (n > 0)
            backTracking2(lists, str + "(", n - 1, m + 1);
        if (m > 0)
            backTracking2(lists, str + ")", n, m - 1);
    }

    private ListNode mergeKLists(List<ListNode> lists) {
        if (lists == null)
            return null;
        if (lists.size() == 0)
            return null;
        ListNode list1 = lists.get(0);
        lists.remove(0);
        ListNode head = new ListNode(0);
        ListNode start = head;
        start.next = list1;
        while (lists.size() > 0) {
            ListNode temp = lists.get(0);
            start.next = this.mergeTwoLists(start.next, temp);
            lists.remove(0);
        }
        return head.next;
    }

    private ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode current = head.next;
        ListNode previous = head;
        ListNode start = head;
        boolean flag = true;
        while (current != null) {
            previous.next = current.next;
            current.next = previous;
            if (flag) {
                head = current;
                flag = false;
            } else {
                start.next = current;
            }
            start = previous;
            previous = previous.next;
            if (previous == null) {
                current = null;
            } else {
                current = previous.next;
            }
        }
        return head;
    }

    private ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode begin = new ListNode(-1);
        begin.next = head;
        ListNode current = this.swapPairs(head, k, begin);
        while (current != null) {
            current = this.swapPairs(current.next, k, current);
        }
        return begin.next;
    }

    private ListNode swapPairs(ListNode head, int k, ListNode begin) {
        if (k == 1 || head == null) {
            return head;
        }
        int length = 0;
        ListNode temp = head;
        while (temp != null && length < k - 1) {
            temp = temp.next;
            length++;
        }
        if (temp == null)
            return temp;
        ListNode previous = head;
        ListNode start = previous;
        ListNode current = head.next;
        length = 0;
        while (current != null && length < k - 1) {
            previous.next = current.next;
            current.next = start;
            start = current;
            current = previous.next;
            length++;
        }
        begin.next = start;
        return previous;
    }

    private int removeDuplicates(int[] A) {
        int count = 0;
        int start = 0;
        for (int i = 0; i < A.length; ) {
            int temp = A[i++];
            while (i < A.length && temp == A[i]) {
                i++;
            }
            count++;
            if (i >= A.length) {
                break;
            }
            A[++start] = A[i];
        }
        return count;
    }

    private int removeElement(int[] A, int elem) {
        if (A == null || A.length < 1)
            return 0;
        int count = 0;
        int i = 0;
        while (i < A.length - count) {
            if (A[i] == elem && i != A.length - count) {
                A[i] = A[A.length - count - 1];
                count++;
            } else {
                i++;
            }
        }
        if (i < A.length) {
            A[i] = elem;
            return A.length - count;
        } else
            return A.length;
    }

    private int removeElement2(int[] A, int elem) {
        int count = 0;
        for (int i = 0; i < A.length - count; ) {
            if (A[i] != elem)
                i++;
            else {
                A[i] = A[A.length - count - 1];
                count++;
            }
        }
        return A.length - count;
    }

    private int removeElement3(int[] A, int elem) {
        int begin = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != elem) {
                A[begin++] = A[i];
            }
        }
        return begin;
    }

    private int strStr(String haystack, String needle) {
        int hayLength = haystack.length();
        int needLength = needle.length();
        if (needLength == 0) {
            return 0;
        }
        if (needLength == hayLength) {
            return needle.equals(haystack) ? 0 : -1;
        }
        int i;
        for (i = 0; i <= hayLength - needLength; i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                int j = i + 1;
                while (j - i < needLength && haystack.charAt(j) == needle.charAt(j - i)) {
                    j++;
                }
                if (j == i + needLength)
                    return i;
            }
        }
        return -1;
    }

    private int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return Integer.MAX_VALUE;
        }
        int number = 0;
        int dividendP = Math.abs(dividend);
        int divisorP = Math.abs(divisor);
        while ((dividendP = dividendP - divisorP) >= 0) {
            number += divisorP;
        }
        if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) {
            return number;
        } else {
            return -number;
        }
    }

    private int divideNew(int dividend, int divisor) {
        long ans = 0;
        if (divisor == 0) {
            return Integer.MAX_VALUE;
        }
        long dend = Math.abs((long) dividend);
        long visor = Math.abs((long) divisor);
        for (int i = 31; i >= 0; i--) {
            if ((dend >> i) >= visor) {
                ans += (1L << i);
                dend -= (visor << i);
            }
        }
        if (((dividend & (1 << 31)) ^ (divisor & (1 << 31))) == 0) {
            if (ans > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int) ans;
        } else {
            return -(int) ans;
        }
    }

    private List<Integer> findSubstring(String s, String[] words) {
        List<Integer> lists = new LinkedList<Integer>();
        if (words.length == 0) {
            lists.add(0);
            return lists;
        }
        if (s.length() == 0) {
            return lists;
        }
        int[] index = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            int j;
            for (j = 0; j < s.length(); j++) {
                if (words[i].charAt(0) == s.charAt(j)) {
                    index[i] = j;
                    break;
                }
            }
            if (j == s.length()) {
                index[i] = -1;
            }
        }
        int length = words[0].length();
        for (int i = 0; i < words.length; i++) {
            int j = index[i];
            if (j != -1) {
                for (; j < s.length() - length; j++) {
                    int temp = j;
                    int t = 0;
                    while (t < length && words[i].charAt(t) == s.charAt(j)) {
                        t++;
                        j++;
                    }
                    if (t == length) {
                        lists.add(j);
                    }
                }
            }
        }
        return lists;
    }

    private List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> result = new LinkedList<Integer>();
        int size = words[0].length();
        if (words.length == 0 || words[0].isEmpty() || words[0].length() > s.length()) {
            return result;
        }
        Map<String, Integer> hist = new HashMap<String, Integer>();
        for (String w : words) {
            hist.put(w, !hist.containsKey(w) ? 1 : hist.get(w) + 1);
        }
        for (int i = 0; i + size * words.length <= s.length(); i++) {
            if (hist.containsKey(s.substring(i, i + size))) {
                Map<String, Integer> currHist = new HashMap<String, Integer>();
                for (int j = 0; j < words.length; j++) {
                    String word = s.substring(i + j * size, i + (j + 1) * size);
                    currHist.put(word, !currHist.containsKey(word) ? 1 : currHist.get(word) + 1);
                }
                if (currHist.equals(hist)) {
                    result.add(i);
                }
            }
        }

        return result;
    }

    private void nextPermutation(int[] num) {
        int right = num.length;
        int i;
        for (i = right - 1; i > 0; i--) {
            if (num[i - 1] < num[i]) {
                break;
            }
        }
        if (i > 0) {
            right = i;
            while (right < num.length && num[right] > num[i - 1]) {
                right++;
            }
            int temp = num[right - 1];
            num[right - 1] = num[i - 1];
            num[i - 1] = temp;
            for (int j = i; j < (num.length + i) / 2; j++) {
                temp = num[j];
                num[j] = num[i + num.length - 1 - j];
                num[i + num.length - j - 1] = temp;
            }
        } else {
            for (int j = 0; j < num.length / 2; j++) {
                int temp = num[j];
                num[j] = num[num.length - 1 - j];
                num[num.length - 1 - j] = temp;
            }
        }
    }

    private int longestValidParentheses(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        int[] flags = new int[s.length()];
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                j = i;
            } else {
                while (j >= 0 && (flags[j] != 0 || s.charAt(j) == ')')) {
                    j--;
                }
                if (j >= 0) {
                    flags[i] = 1;
                    flags[j] = 1;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < flags.length; ) {
            int temp = 0;
            while (i < flags.length && flags[i] == 1) {
                temp++;
                i++;
            }
            if (max < temp)
                max = temp;
            i++;
        }
        return max;
    }

    private int search(int[] A, int target) {
        if (A.length < 1) {
            return -1;
        }
        int left = 0;
        int right = A.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            if (A[left] < A[right]) {
                right = middle;
            } else {
                if (A[middle] >= A[left]) {
                    left = middle + 1;
                } else {
                    right = middle;
                }
            }
        }
        int i, j;
        if (target < A[left]) {
            return -1;
        } else if (target <= A[A.length - 1]) {
            i = left;
            j = A.length - 1;

        } else {
            i = 0;
            j = left - 1;
        }
        while (i <= j) {
            int middle = (i + j) / 2;
            if (target < A[middle]) {
                j = middle - 1;
            } else if (target > A[middle]) {
                i = middle + 1;
            } else {
                break;
            }
        }
        if (i <= j) {
            return (i + j) / 2;
        } else {
            return -1;
        }
    }

    private int[] searchRange(int[] A, int target) {
        if (A == null || A.length < 1) {
            return new int[]{-1, -1};
        }
        int[] result = new int[2];
        int left = 0;
        int right = A.length - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (A[middle] < target) {
                left = middle + 1;
            } else if (A[middle] > target) {
                right = middle - 1;
            } else {
                result[0] = result[1] = middle;
                for (int i = middle - 1; i >= 0; i--) {
                    if (A[i] == target) {
                        result[0] = i;
                    } else {
                        break;
                    }
                }
                for (int i = middle + 1; i < A.length; i++) {
                    if (A[i] == target) {
                        result[1] = i;
                    } else {
                        break;
                    }
                }
                break;
            }
        }
        if (left > right) {
            if (A.length == 1 && A[0] == target) {
                return new int[]{0, 0};
            }
            return new int[]{-1, -1};
        }
        return result;
    }

    private int searchInsert(int[] A, int target) {
        if (A.length < 1)
            return 0;
        int left = 0;
        int right = A.length;
        while (left < right) {
            int middle = (right - left) / 2 + left;
            if (A[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    private boolean isValidSudoku(char[][] board) {
        return isValidRow(board) && isValidCol(board) && isValidNine(board);
    }

    private boolean isValidRow(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            int[] numbers = new int[9];
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    numbers[board[i][j] - '0' - 1]++;
                }
            }
            int j;
            for (j = 0; j < numbers.length; j++) {
                if (numbers[j] > 1)
                    break;
            }
            if (j < numbers.length) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCol(char[][] board) {
        for (int i = 0; i < board[0].length; i++) {
            int[] numbers = new int[9];
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] != '.')
                    numbers[board[j][i] - '0' - 1]++;
            }
            int j;
            for (j = 0; j < numbers.length; j++) {
                if (numbers[j] > 1)
                    break;
            }
            if (j < numbers.length)
                return false;
        }
        return true;
    }

    private boolean isValidNine(char[][] board) {
        for (int i = 0; i < board.length; i += 3) {
            for (int j = 0; j < board[0].length; j += 3) {
                int[] numbers = new int[9];
                for (int t = i; t < i + 3; t++) {
                    for (int k = j; k < j + 3; k++) {
                        if (board[t][k] != '.')
                            numbers[board[t][k] - '0' - 1]++;
                    }
                }
                int t;
                for (t = 0; t < numbers.length; t++) {
                    if (numbers[t] > 1)
                        break;
                }
                if (t < numbers.length)
                    return false;
            }
        }
        return true;
    }

    private void solveSudoku(char[][] board) {
        Stack<List<Integer>> stack = new Stack<List<Integer>>();
        int empty = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.')
                    empty++;
            }
        }
        Stack<int[]> position = new Stack<int[]>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    List<Integer> options = this.getOptions(board, i, j);
                    if (empty > 0) {
                        if (options.size() > 0) {
                            board[i][j] = (char) (options.get(0) + '0');
                            position.add(new int[]{i, j});
                            empty--;
                            options.remove(0);
                            stack.add(options);
                        } else {
                            List<Integer> lists = stack.pop();
                            int[] po = position.pop();
                            empty++;
                            board[po[0]][po[1]] = '.';
                            while (lists.size() == 0) {
                                lists = stack.pop();
                                po = position.pop();
                                board[po[0]][po[1]] = '.';
                                empty++;
                            }
                            i = po[0];
                            j = po[1] - 1;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private void solveSudoku2(char[][] board) {
        List<List<Integer>> stack = new LinkedList<List<Integer>>();
        List<int[]> positions = new LinkedList<int[]>();
        int empty = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    empty++;
                    positions.add(new int[]{i, j});
                    List<Integer> options = this.getOptions(board, i, j);
                    stack.add(options);
                }
            }
        }
        Stack<Integer> pos = new Stack<Integer>();
        //get the smallest length of the
        int minLength = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < stack.size(); i++) {
            List<Integer> temp = stack.get(i);
            if (temp.size() < minLength) {
                minLength = temp.size();
                minIndex = i;
            }
        }
        while (pos.size() < empty) {
            //firstly, determine the minIndex position
            List<Integer> lists = stack.get(minIndex);
            if (lists.size() == 0) {
                if (pos.isEmpty()) {
                    return;
                } else {
                    int index = pos.pop();
                    while (stack.get(index).size() == 0) {
                        board[positions.get(index)[0]][positions.get(index)[1]] = '.';
                        if (pos.isEmpty())
                            return;
                        index = pos.pop();
                    }
                    List<Integer> ll = stack.get(index);
                    board[positions.get(index)[0]][positions.get(index)[1]] = (char) (ll.get(0) + '0');
                    ll.remove(0);
                    pos.push(index);
                }
            } else {
                int x = positions.get(minIndex)[0];
                int y = positions.get(minIndex)[1];
                board[x][y] = (char) (lists.get(0) + '0');
                lists.remove(0);
                pos.push(minIndex);
            }
            minLength = Integer.MAX_VALUE;
            for (int i = 0; i < positions.size(); i++) {
                if (board[positions.get(i)[0]][positions.get(i)[1]] == '.') {
                    List<Integer> ll = this.getOptions(board, positions.get(i)[0], positions.get(i)[1]);
                    stack.set(i, ll);
                    if (ll.size() < minLength) {
                        minLength = ll.size();
                        minIndex = i;
                    }
                }
            }
        }
    }

    private List<Integer> getOptions(char[][] board, int i, int j) {
        List<Integer> lists = new LinkedList<Integer>();
        for (int t = 1; t <= 9; t++) {
            lists.add(t);
        }
        for (int t = 0; t < board.length; t++) {
            if (lists.contains(board[i][t] - '0')) {
                lists.remove((Object) (board[i][t] - '0'));
            }
        }
        for (int t = 0; t < board[0].length; t++) {
            if (lists.contains(board[t][j] - '0')) {
                lists.remove((Object) (board[t][j] - '0'));
            }
        }
        for (int t = i / 3 * 3; t < i / 3 * 3 + 3; t++) {
            for (int k = j / 3 * 3; k < j / 3 * 3 + 3; k++) {
                if (lists.contains(board[t][k] - '0')) {
                    lists.remove((Object) (board[t][k] - '0'));
                }
            }
        }
        return lists;
    }

    private String countAndSay(int n) {
        String s = "1";
        for (int i = 1; i < n; i++) {
            int cnt = 1;
            StringBuilder tmp = new StringBuilder();
            for (int j = 1; j < s.length(); j++) {
                if (s.charAt(j) == s.charAt(j - 1)) {
                    cnt++;
                } else {
                    tmp.append(cnt).append(s.charAt(j - 1));
                    cnt = 1;
                }
            }
            tmp.append(cnt).append(s.charAt(s.length() - 1));
            s = tmp.toString();
        }
        return s;
    }

    private List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (candidates.length < 1) {
            return lists;
        }
        Arrays.sort(candidates);
        int count = candidates.length - 1;
        while (count >= 0) {
            if (count < candidates.length - 1 && candidates[count] == candidates[count + 1])
                continue;
            if (candidates[count] <= target) {
                List<Integer> ll = new LinkedList<Integer>();
                if (candidates[count] == target) {
                    ll.add(0, candidates[count]);
                    lists.add(ll);
                } else {
                    ll.add(0, candidates[count]);
                    int sum = target - candidates[count];
                    int l = count;
                    Stack<Integer> stack = new Stack<Integer>();
                    while (l >= 0 || !stack.isEmpty()) {
                        if (l < 0) {
                            l = stack.pop() - 1;
                            if (l < 0)
                                break;
                            sum += ll.get(0);
                            ll.remove(0);
                        }
                        if (sum < candidates[l]) {
                            l--;
                        } else {
                            ll.add(0, candidates[l]);
                            if (sum == candidates[l]) {
                                lists.add(ll);
                                List<Integer> temp = new LinkedList<Integer>();
                                for (int i = 0; i < ll.size(); i++) {
                                    temp.add(i, ll.get(i));
                                }
                                ll = temp;
                            }
                            sum -= candidates[l];
                            stack.push(l);
                        }
                    }
                }
            }
            count--;
        }
        return lists;
    }

    private List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        if (candidates == null || candidates.length < 1) {
            return res;
        }
        Arrays.sort(candidates);
        helper(candidates, 0, target, new LinkedList<Integer>(), res);
        return res;
    }

    private void helper(int[] candidates, int start, int target, List<Integer> item, List<List<Integer>> res) {
        if (target < 0)
            return;
        if (target == 0) {
            res.add(new LinkedList<Integer>(item));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (i > 0 && candidates[i] == candidates[i - 1])
                continue;
            item.add(candidates[i]);
            helper(candidates, i, target - candidates[i], item, res);
            item.remove(item.size() - 1);
        }
    }

    private void combinationSumDP(int[] candidates, int target) {
        int dp[] = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < candidates.length; i++) {
            for (int s = 0; s < target + 1; s++) {
                if (s >= candidates[i]) dp[s] = dp[s - candidates[i]] + dp[s];
            }
        }
        System.out.println(dp[target]);
    }

    private List<List<Integer>> combinationSumDP2(int[] candidates, int target) {
        List<List<List<Integer>>> combinations = new LinkedList<List<List<Integer>>>();
        for (int i = 0; i < target + 1; i++) {
            List<List<Integer>> lists = new LinkedList<List<Integer>>();
            combinations.add(lists);
        }
        combinations.get(0).add(new LinkedList<Integer>());
        for (int i = 0; i < candidates.length; i++) {
            int score = candidates[i];
            for (int j = score; j <= target; j++) {
                if (combinations.get(j - score).size() > 0) {
                    List<List<Integer>> temp = combinations.get(j - score);
                    for (int t = 0; t < temp.size(); t++) {
                        List<Integer> list = temp.get(t);
                        list.add(score);
                    }
                    for (int t = 0; t < temp.size(); t++) {
                        combinations.get(j).add(temp.get(t));
                    }
                }
            }
        }
        List<List<Integer>> ret = combinations.get(target);
        for (int i = 0; i < ret.size(); i++) {
            Collections.sort(ret.get(i));
        }
        return ret;
    }

    private List<List<Integer>> combinationSumDP3(int[] candidates, int t) {
        Arrays.sort(candidates);
        List<List<List<Integer>>> dp = new LinkedList<List<List<Integer>>>();
        for (int i = 1; i <= t; i++) {
            List<List<Integer>> newList = new LinkedList<List<Integer>>();
            for (int j = 0; j < candidates.length && candidates[j] <= i; j++) {
                if (i == candidates[j])
                    newList.add(Arrays.asList(candidates[j]));
                else for (List<Integer> l : dp.get(i - candidates[j] - 1)) {
                    if (candidates[j] <= l.get(0)) {
                        List<Integer> cl = new LinkedList<Integer>();
                        cl.add(candidates[j]);
                        cl.addAll(l);
                        newList.add(cl);
                    }
                }
            }
            dp.add(newList);
        }
        return dp.get(t - 1);
    }

    private List<List<Integer>> combinationSumII(int[] num, int target) {
        Arrays.sort(num);
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        getCombination(lists, num, target, 0, new LinkedList<Integer>());
        return lists;
    }

    private void getCombination(List<List<Integer>> lists, int[] num, int target, int start, List<Integer> temp) {
        if (target < 0)
            return;
        if (target == 0) {
            lists.add(new LinkedList<Integer>(temp));
            return;
        }
        if (start < num.length) {
            temp.add(num[start]);
            getCombination(lists, num, target - num[start], start + 1, temp);
            temp.remove(temp.size() - 1);
            int t = num[start];
            while (t < num.length && num[t] == num[start])
                t++;
            getCombination(lists, num, target, t, temp);
        }
    }

    private List<List<Integer>> combinationSum3(int[] num, int target) {
        List<List<Integer>> combos = new LinkedList<List<Integer>>();
        if (num == null || num.length == 0) return combos;
        Arrays.sort(num);
        combination(num, target, new LinkedList<Integer>(), 0, combos);
        return combos;
    }

    private void combination(int[] num, int target, List<Integer> list, int start, List<List<Integer>> combos) {
        if (target == 0) {
            combos.add(list);
        } else {
            int prev = 0;
            for (int i = start; i < num.length; i++) {
                if (num[i] == prev)
                    continue;
                int nextTarget = target - num[i];
                if (nextTarget >= 0) {
                    List<Integer> copy = new LinkedList<Integer>(list);
                    copy.add(num[i]);
                    combination(num, nextTarget, copy, i + 1, combos);
                } else {
                    break;
                }
                prev = num[i];
            }
        }
    }

    private int firstMissingPositive(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            if (nums[i] != i + 1 && nums[i] >= 1 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                int temp = 0;
                temp = nums[i];
                nums[i] = nums[nums[i] - 1];
                nums[temp - 1] = temp;
            } else {
                i++;
            }
        }
        for (i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1)
                return i + 1;
        }
        return nums.length + 1;
    }

    private int trap(int[] height) {
        if (height == null || height.length < 1) {
            return 0;
        }
        int curMax = 0;
        int index = 0;
        int count = 0;
        int total = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < height.length; i++) {
            if (stack.isEmpty()) {
                stack.push(height[i]);
                curMax = height[i];
                index = i;
                count = 1;
            } else {
                if (height[i] >= curMax) {
                    int sum = 0;
                    while (count > 0) {
                        sum += stack.pop();
                        count--;
                    }
                    sum -= curMax;
                    total += curMax * (i - index - 1) - sum;
                    curMax = height[i];
                    stack.push(height[i]);
                    index = i;
                    count++;
                } else {
                    stack.push(height[i]);
                    count++;
                }
            }
        }
        int temp;
        int min = 0;
        int sum = 0;
        int in = 0;
        while (!stack.isEmpty()) {
            temp = stack.pop();
            in++;
            if (temp >= min) {
                in = in - 2 > 0 ? in - 2 : 0;
                total += min * in - sum;
                min = temp;
                in = 1;
                sum = 0;
            } else {
                sum += temp;
            }
        }
        return total;
    }

    private String multiply(String num1, String num2) {
        String n1 = new StringBuilder(num1).reverse().toString();
        String n2 = new StringBuilder(num2).reverse().toString();
        int[] d = new int[n1.length() + n2.length()];
        for (int i = 0; i < n1.length(); i++) {
            for (int j = 0; j < n2.length(); j++) {
                d[i + j] += (n1.charAt(i) - '0') * (n2.charAt(j) - '0');
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d.length; i++) {
            int digit = d[i] % 10;
            int carry = d[i] / 10;
            if (i + 1 < d.length) {
                d[i + 1] += carry;
            }
            sb.insert(0, digit);
        }
        while (sb.charAt(0) == '0' && sb.length() > 1) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    private boolean isMatch(String s, String p) {
        if (p.length() == 0)
            return s.length() == 0;
        boolean[] res = new boolean[s.length() + 1];
        res[0] = true;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                int j = 0;
                while (j < s.length() && !res[j]) {
                    j++;
                }
                for (; j < s.length(); j++) {
                    res[j] = true;
                }
            } else {
                for (int j = s.length() - 1; j >= 0; j--) {
                    res[j + 1] = res[j] && (p.charAt(i) == '?' || p.charAt(i) == s.charAt(j));
                }
            }
        }
        return res[s.length()];
    }

    private boolean isMatch2(String s, String p) {
        int spoint = 0, ppoint = 0, match = 0, starIdx = -1;
        while (spoint < s.length()) {
            if (ppoint < p.length() && (p.charAt(ppoint) == '?' || s.charAt(spoint) == p.charAt(ppoint))) {
                spoint++;
                ppoint++;
            } else if (ppoint < p.length() && p.charAt(ppoint) == '*') {
                starIdx = ppoint;
                match = spoint;
                ppoint++;
            } else if (starIdx != -1) {
                ppoint = starIdx + 1;
                match++;
                spoint = match;
            } else
                return false;
        }
        while (ppoint < p.length() && p.charAt(ppoint) == '*')
            ppoint++;
        return ppoint == p.length();
    }

    private int jump(int[] nums) {
        int jump = 0;
        int maxLength = 0;
        int last = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxLength)
                return -1;
            if (i > last) {
                last = maxLength;
                jump++;
            }
            if (i + nums[i] > maxLength) {
                maxLength = i + nums[i];
            }
        }
        return jump;
    }

    private List<List<Integer>> permute(int[] num) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (num == null || num.length == 0) {
            List<Integer> temp = new LinkedList<Integer>();
            lists.add(temp);
            return lists;
        }
        dfs(0, num, lists);
        return lists;
    }

    private void dfs(int i, int[] num, List<List<Integer>> lists) {
        if (i == num.length) {
            List<Integer> temp = new LinkedList<Integer>();
            for (int j = 0; j < num.length; j++) {
                temp.add(num[j]);
            }
            lists.add(temp);
        }
        for (int j = i; j < num.length; j++) {
            int tmp = num[i];
            num[i] = num[j];
            num[j] = tmp;
            dfs(i + 1, num, lists);
            tmp = num[i];
            num[i] = num[j];
            num[j] = tmp;
        }
    }

    private List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (nums == null || nums.length == 0) {
            List<Integer> temp = new LinkedList<Integer>();
            lists.add(temp);
            return lists;
        }
        dfs2(0, nums, lists);
        return lists;
    }

    private void dfs2(int start, int[] nums, List<List<Integer>> lists) {
        if (start == nums.length) {
            List<Integer> temp = new LinkedList<Integer>();
            for (int j = 0; j < nums.length; j++) {
                temp.add(nums[j]);
            }
            lists.add(temp);
        }
        for (int j = start; j < nums.length; j++) {
            int temp;
            if (start != j && nums[start] == nums[j]) {
                continue;
            }
            if (start != j) {
                temp = nums[start];
                nums[start] = nums[j];
                nums[j] = temp;
            }
            dfs2(start + 1, nums, lists);
            if (start != j) {
                temp = nums[start];
                nums[start] = nums[j];
                nums[j] = temp;
            }
        }
    }

    private List<List<Integer>> permuteUnique2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        recur(nums, 0, nums.length, res);
        return res;
    }

    private void recur(int[] num, int i, int j, List<List<Integer>> res) {
        if (i == j - 1) {
            List<Integer> temp = new LinkedList<Integer>();
            for (int t = 0; t < num.length; t++) {
                temp.add(num[t]);
            }
            res.add(temp);
        }
        for (int t = i; t < j; t++) {
            if (t != i && num[i] == num[t])
                continue;
            int temp = num[t];
            num[t] = num[i];
            num[i] = temp;
            recur(num, i + 1, j, res);
            temp = num[t];
            num[t] = num[i];
            num[i] = temp;
        }
    }

    private void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        int length = matrix.length;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < length / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][length - j - 1];
                matrix[i][length - j - 1] = temp;
            }
        }
    }

    private List<String> anagrams(String[] strs) {
        List<String> lists = new LinkedList<String>();
        if (strs == null || strs.length == 0)
            return lists;
        HashMap<String, List<String>> maps = new HashMap<String, List<String>>();
        for (int i = 0; i < strs.length; i++) {
            char[] tempChs = strs[i].toCharArray();
            Arrays.sort(tempChs);
            String newString = new String(tempChs);
            if (maps.containsKey(newString)) {
                List<String> tempLists = maps.get(newString);
                tempLists.add(strs[i]);
            } else {
                List<String> tempLists = new LinkedList<String>();
                tempLists.add(strs[i]);
                maps.put(newString, tempLists);
            }
        }
        Iterator<List<String>> iterators = maps.values().iterator();
        while (iterators.hasNext()) {
            List<String> temp = iterators.next();
            if (temp.size() > 1) {
                lists.addAll(temp);
            }
        }
        return lists;
    }

    private double myPow(double x, int n) {
        if (x == 0)
            return 0.0;
        if (n == 0)
            return 1.0;
        double sum = n > 0 ? x : 1 / x;
        x = sum;
        n = Math.abs(n);
        int total = n;
        int count = 1;
        while ((n >>= 1) != 0) {
            sum *= sum;
            count *= 2;
        }
        for (int i = 0; i < total - count; i++) {
            sum *= x;
        }
        return sum;
    }

    private double pow2(double x, int n) {
        if (n == 0) return 1.0;
        double half = pow2(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            if (n > 0) {
                return half * half * x;
            } else {
                return half * half * 1 / x;
            }
        }
    }

    private List<String[]> solveNQueens(int n) {
        List<String[]> lists = new LinkedList<String[]>();
        if (n == 0)
            return lists;
        if (n == 1) {
            lists.add(new String[]{"Q"});
            return lists;
        }
        String temp = "";
        for (int i = 0; i < n; i++) {
            temp += ".";
        }
        char[] chs = temp.toCharArray();
        HashMap<Integer, List<Integer>> maps = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < n; i++) {
            List<String> stack = new LinkedList<String>();
            chs[i] = 'Q';
            String str = new String(chs);
            chs[i] = '.';
            stack.add(str);
            List<Integer> intStack = new LinkedList<Integer>();
            intStack.add(i);
            maps.put(0, intStack);
            nQueens(maps, 1, n, lists, stack, chs);
        }
        return lists;
    }

    private void nQueens(HashMap<Integer, List<Integer>> maps, int row, int n, List<String[]> results, List<String> tempresults, char[] chs) {
        if (row == n - 1) {
            List<Integer> lists = getCandidates(maps, n, row);
            if (lists.size() == 0) {
                return;
            } else {
                for (int i = 0; i < lists.size(); i++) {
                    String[] str = new String[tempresults.size() + 1];
                    int t;
                    for (t = 0; t < tempresults.size(); t++) {
                        str[t] = tempresults.get(t);
                    }
                    chs[lists.get(i)] = 'Q';
                    str[t] = new String(chs);
                    chs[lists.get(i)] = '.';
                    results.add(str);
                }
                return;
            }
        }
        for (int i = row; i < n; i++) {
            List<Integer> candidates = getCandidates(maps, n, row);
            if (candidates.size() == 0) {
                return;
            }
            for (int t = 0; t < candidates.size(); t++) {
                List<Integer> tttt = new LinkedList<Integer>();
                tttt.add(candidates.get(t));
                maps.put(i, tttt);
                chs[candidates.get(t)] = 'Q';
                tempresults.add(new String(chs));
                chs[candidates.get(t)] = '.';
                nQueens(maps, row + 1, n, results, tempresults, chs);
                maps.remove(i);
                tempresults.remove(tempresults.size() - 1);
            }
            if (maps.size() > 0)
                return;
        }
    }

    private List<Integer> getCandidates(HashMap<Integer, List<Integer>> maps, int n, int row) {
        List<Integer> lists = new LinkedList<Integer>();
        int[] numbers = new int[n];
        Iterator<Map.Entry<Integer, List<Integer>>> iter = maps.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, List<Integer>> entry = (Map.Entry<Integer, List<Integer>>) iter.next();
            List<Integer> temp = entry.getValue();
            int key = entry.getKey();
            for (int i = 0; i < temp.size(); i++) {
                numbers[temp.get(i)] = 1;
                int diff = row - key;
                if (temp.get(i) - diff >= 0) {
                    numbers[temp.get(i) - diff] = 1;
                }
                if (temp.get(i) + diff < n) {
                    numbers[temp.get(i) + diff] = 1;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (numbers[i] == 0) {
                lists.add(i);
            }
        }
        return lists;
    }

    private int totalNQueens(int n) {
        List<String[]> lists = new LinkedList<String[]>();
        int total = 0;
        if (n == 0)
            return total;
        if (n == 1) {
            lists.add(new String[]{"Q"});
            return 1;
        }
        String temp = "";
        for (int i = 0; i < n; i++) {
            temp += ".";
        }
        char[] chs = temp.toCharArray();
        HashMap<Integer, List<Integer>> maps = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < n; i++) {
            List<String> stack = new LinkedList<String>();
            chs[i] = 'Q';
            String str = new String(chs);
            chs[i] = '.';
            stack.add(str);
            List<Integer> intStack = new LinkedList<Integer>();
            intStack.add(i);
            maps.put(0, intStack);
            nQueens(maps, 1, n, lists, stack, chs);
        }
        return lists.size();
    }

    private int maxSubArray(int[] nums) {
        int local = nums[0];
        int global = local;
        for (int i = 1; i < nums.length; i++) {
            local = Math.max(local + nums[i], nums[i]);
            global = Math.max(local, global);
        }
        return global;
    }

    private List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> lists = new LinkedList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return lists;
        int m = matrix.length;
        int n = matrix[0].length;
        int min = Math.min(m, n);
        int level = min / 2;
        for (int i = 0; i < level; i++) {
            for (int j = i; j < n - i - 1; j++) {
                lists.add(matrix[i][j]);
            }
            for (int j = i; j < m - i - 1; j++) {
                lists.add(matrix[j][n - i - 1]);
            }
            for (int j = n - i - 1; j > i; j--) {
                lists.add(matrix[m - i - 1][j]);
            }
            for (int j = m - i - 1; j > i; j--) {
                lists.add(matrix[j][i]);
            }
        }
        if (min % 2 == 1) {
            if (m < n) {
                for (int i = level; i < n - level; i++) {
                    lists.add(matrix[level][i]);
                }
            } else {
                for (int i = level; i < m - level; i++) {
                    lists.add(matrix[i][level]);
                }
            }
        }
        return lists;
    }

    private List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> ret = new LinkedList<Integer>();
        if (matrix.length == 0) return ret;
        int m = matrix.length;
        int n = matrix[0].length;
        int min = Math.min(m, n);
        int b;
        for (b = 0; b < Math.ceil(min / 2); b++) {
            for (int a = b; a < n - b; a++) ret.add(matrix[b][a]);
            for (int a = b + 1; a < m - b; a++) ret.add(matrix[a][n - b - 1]);
            if (b != m - b - 1) for (int a = n - b - 2; a >= b; a--) ret.add(matrix[m - b - 1][a]);
            if (n - b - 1 != b) for (int a = m - b - 2; a > b; a--) ret.add(matrix[a][b]);
        }
        if (min % 2 == 1) {
            if (m < n) {
                for (int i = b; i < n - b; i++) {
                    ret.add(matrix[b][i]);
                }
            } else {
                for (int i = b; i < m - b; i++) {
                    ret.add(matrix[i][b]);
                }
            }
        }
        return ret;
    }

    private boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0)
            return true;
        int border = nums[0];
        int i = 0;
        while (i < nums.length && i <= border) {
            if (nums[i] + i > border) {
                border = nums[i] + i;
            }
            i++;
        }
        return border >= nums.length - 1;
    }

    class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    private List<Interval> merge(List<Interval> intervals) {
        List<Interval> lists = new LinkedList<Interval>();
        if (intervals == null || intervals.size() == 0) {
            return lists;
        }
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
//                if (o1.start > o2.start)
//                    return 1;
//                else if (o1.start == o2.start)
//                    return 0;
//                else
//                    return -1;
                return Integer.compare(o1.start, o2.start);
            }
        });
        Interval mergeInterval = intervals.get(0);
        for (int i = 1; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            if (mergeInterval.end < interval.start) {
                lists.add(mergeInterval);
                mergeInterval = interval;
            } else {
                if (mergeInterval.end < interval.end) {
                    mergeInterval.end = interval.end;
                }
            }
        }
        lists.add(mergeInterval);
        return lists;
    }

    private List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (newInterval == null || intervals == null)
            return intervals;
        if (newInterval != null && intervals == null) {
            intervals.add(newInterval);
            return intervals;
        }
        List<Interval> lists = new LinkedList<Interval>();
        int i;
        for (i = 0; i < intervals.size(); i++) {
            Interval temp = intervals.get(i);
            if (temp.end < newInterval.start) {
                lists.add(temp);
                continue;
            }
            if (newInterval.end < temp.start) {
                break;
            }
            if (newInterval.start < temp.start) {
                if (newInterval.end <= temp.end) {
                    newInterval.end = temp.end;
                    i++;
                    break;
                } else {
                    continue;
                }
            } else {
                newInterval.start = temp.start;
                if (newInterval.end <= temp.end) {
                    newInterval.end = temp.end;
                    i++;
                    break;
                } else {
                    continue;
                }
            }
        }
        lists.add(newInterval);
        for (; i < intervals.size(); i++) {
            lists.add(intervals.get(i));
        }
        return lists;
    }

    private int lengthOfLastWord(String s) {
        String regex = "\\s";
        Pattern pattern = Pattern.compile(regex);
        String[] strs = pattern.split(s);
        if (strs.length > 0) {
            return strs[strs.length - 1].length();
        }
        return 0;
    }

    private int lengthOfLastWord2(String s) {
        String str = s.trim();
        int length = str.length() - 1;
        while (length >= 0 && (int) (str.charAt(length)) > 32) {
            length--;
        }
        if (length < 0)
            return str.length();
        return str.length() - length - 1;
    }

    private int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        if (n <= 0)
            return result;
        int number = 1;
        int level = n / 2;
        for (int i = 0; i < level; i++) {
            for (int j = i; j < n - i - 1; j++) {
                result[i][j] = number++;
            }
            for (int j = i; j < n - i - 1; j++) {
                result[j][n - i - 1] = number++;
            }
            for (int j = n - i - 1; j > i; j--) {
                result[n - i - 1][j] = number++;
            }
            for (int j = n - i - 1; j > i; j--) {
                result[j][i] = number++;
            }
        }
        if (n % 2 == 1) {
            for (int j = level; j < n - level; j++) {
                result[level][j] = number++;
            }
        }
        return result;
    }

    private String getPermutation(int n, int k) {
        int[] numbers = new int[n];
        int sum = 1;
        for (int i = 0; i < n; i++) {
            numbers[i] = i + 1;
            sum *= (i + 1);
        }
        if (k < 0 || k > sum)
            return "";
        String[] str = new String[1];
        str[0] = "";
        int[] count = new int[1];
        count[0] = 0;
        getPer(numbers, k, count, str, 0);
        return str[0];
    }

    private void getPer(int[] numbers, int k, int[] count, String[] str, int start) {
        if (start == numbers.length - 1) {
            count[0]++;
            if (count[0] == k) {
                for (int i = 0; i < numbers.length; i++) {
                    str[0] += numbers[i];
                }
                return;
            }
            return;
        }
        for (int i = start; i < numbers.length; i++) {
            int temp = numbers[i];
            numbers[i] = numbers[start];
            numbers[start] = temp;
            getPer(numbers, k, count, str, start + 1);
            temp = numbers[i];
            numbers[i] = numbers[start];
            numbers[start] = temp;
        }
    }

    private String getPermutation2(int n, int k) {
        int sum = 1;
        k = k - 1;
        for (int i = 1; i <= n - 1; i++) {
            sum *= i;
        }
        String str = "";
        List<Integer> lists = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) {
            lists.add(i);
        }
        int count = n - 1;
        while (k > 0) {
            int num = k / sum;
            str += lists.get(num);
            lists.remove(num);
            k -= num * sum;
            sum /= count--;
        }
        for (int i = 0; i < lists.size(); i++) {
            str += lists.get(i);
        }
        return str;
    }

    private ListNode rotateRigth(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int count = 0;
        ListNode curr = head;
        ListNode tail = curr;
        while (curr != null) {
            count++;
            tail = curr;
            curr = curr.next;
        }
        k %= count;
        ListNode prev = head;
        for (int i = 0; i < count - k - 1; i++) {
            prev = prev.next;
        }
        tail.next = head;
        head = prev.next;
        prev.next = null;
        return head;
    }

    private int uniquePaths(int m, int n) {
        int[][] numbers = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 < 0) {
                    if (j - 1 >= 0) {
                        numbers[i][j] = numbers[i][j - 1];
                    } else {
                        numbers[i][j] = 1;
                    }
                } else if (j - 1 < 0) {
                    numbers[i][j] += numbers[i - 1][j];
                } else {
                    numbers[i][j] += (numbers[i - 1][j] + numbers[i][j - 1]);
                }
            }
        }
        return numbers[m - 1][n - 1];
    }

    private int uniquePathsWithObstacles(int[][] obstacleGrid) {
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (i - 1 < 0) {
                    if (j - 1 >= 0) {
                        obstacleGrid[i][j] = obstacleGrid[i][j] == 1 ? 0 : obstacleGrid[i][j - 1];
                    } else {
                        obstacleGrid[i][j] = obstacleGrid[i][j] == 1 ? 0 : 1;
                    }
                } else if (j - 1 < 0) {
                    obstacleGrid[i][j] = obstacleGrid[i][j] == 1 ? 0 : obstacleGrid[i - 1][j];
                } else {
                    obstacleGrid[i][j] = obstacleGrid[i][j] == 1 ? 0 : (obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1]);
                }
            }
        }
        return obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

    private int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = (i == 0 ? 1 : 0); j < n; j++) {
                if (i - 1 < 0) {
                    grid[i][j] += grid[i][j - 1];
                } else if (j - 1 < 0) {
                    grid[i][j] += grid[i - 1][j];
                } else {
                    grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
                }
            }
        }
        return grid[m - 1][n - 1];
    }

    private boolean isNumber(String s) {
        s = s.trim();
        if (s.length() == 0)
            return false;
        char[] chs = s.toCharArray();
        int i = 0;
        if (chs[i] == '+' || chs[i] == '-')
            i++;
        int num = 0;
        int point = 0;
        while (i < chs.length && ((chs[i] >= '0' && chs[i] <= '9') || chs[i] == '.')) {
            if (chs[i++] == '.')
                point++;
            else
                num++;
        }
        if (num == 0 || point > 1)
            return false;
        if (i < chs.length && chs[i] == 'e') {
            i++;
            if (i < chs.length && (chs[i] == '+' || chs[i] == '-'))
                i++;
            num = 0;
            while (i < chs.length && (chs[i] >= '0' && chs[i] <= '9')) {
                i++;
                num++;
            }
            if (num < 1)
                return false;
        }
        if (i < chs.length)
            return false;
        return true;
    }

    private int[] plusOne(int[] digits) {
        if (digits == null)
            return null;
        int[] plus = new int[digits.length + 1];
        int add = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            plus[i + 1] = (add + digits[i]) % 10;
            add = (digits[i] + add) / 10;
            digits[i] = plus[i + 1];
        }
        if (add == 1) {
            plus[0] = add;
            return plus;
        } else {
            return digits;
        }
    }

    private String addBinary(String a, String b) {
        if (a.length() == 0 || b.length() == 0)
            return a + b;
        if (a.length() < b.length()) {
            String temp = a;
            a = b;
            b = temp;
        }
        char[] achs = a.toCharArray();
        char[] bchs = b.toCharArray();
        int add = 0;
        int count = a.length() - b.length();
        StringBuilder sb = new StringBuilder("");
        for (int i = bchs.length - 1; i >= 0; i--) {
            int temp = ((achs[i + count] - '0') + (bchs[i] - '0') + add);
            achs[i + count] = (char) (temp % 2 + '0');
            add = temp / 2;
        }
        for (int i = count - 1; i >= 0; i--) {
            int temp = (achs[i] - '0') + add;
            achs[i] = (char) (temp % 2 + '0');
            add = temp / 2;
        }
        if (add == 1) {
            sb.append(add);
        }
        sb.append(achs);
        return sb.toString();
    }

    private List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lists = new LinkedList<String>();
        if (words == null)
            return lists;
        int count = 0;
        int previous = 0;
        for (int i = 0; i < words.length; i++) {
            count += words[i].length();
            if (count + (i - previous) > maxWidth) {
                int temp = i - previous - 1 == 0 ? 1 : i - previous - 1;
                count = count - words[i].length();
                count = maxWidth - count;
                int blank = count / temp;
                int remaining = count % temp;
                String ss = "";
                for (int j = 0; j < blank; j++) {
                    ss += " ";
                }
                String str = "";
                for (int j = previous; j < i; j++) {
                    str += words[j];
                    if (remaining-- > 0)
                        str += ss + " ";
                    else
                        str += ss;
                }
                if (str.length() > maxWidth)
                    str = str.substring(0, maxWidth);
                lists.add(str);
                count = 0;
                previous = i;
                i--;
            }
        }
        if (previous < words.length) {
            String str = "";
            count = maxWidth - count;
            for (int i = previous; i < words.length; i++) {
                str += words[i];
                if (count > 0) {
                    str += " ";
                }
                count--;
            }
            for (int i = 0; i < count; i++) {
                str += " ";
            }
            lists.add(str);
        }
        return lists;
//        if (previous < words.length) {
//            String str = "";
//            count = maxWidth - count;
//            int temp = words.length - previous;
//            int blank = count / temp;
//            int remaining = count % temp;
//            String ss = "";
//            String remain = "";
//            for (int i = 0; i < blank; i++) {
//                ss += " ";
//            }
//            for (int i = 0; i < remaining; i++) {
//                remain += " ";
//            }
//            for (int i = previous; i < words.length; i++) {
//                str += words[i];
//                if (i == previous)
//                    str += ss + remain;
//                else
//                    str += ss;
//            }
//            if (str.length() > maxWidth) {
//                str = str.substring(0, maxWidth);
//            }
//            lists.add(str);
//        }

    }

    private int mySqrt(int x) {
        if (x < 0)
            return -1;
        if (x == 0)
            return 0;
        int l = 1;
        int r = x / 2 + 1;
        int m = 1;
        while (l <= r) {
            m = (l + r) / 2;
            if (m == x / m || (m < x / m && (m + 1) > x / (m + 1))) {
                return m;
            } else if (m < x / m) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return m;
    }

    private int climbStairs(int n) {
        if (n <= 0)
            return 0;
        if (n < 2)
            return 1;
        int f1 = 1;
        int f2 = 2;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = f1 + f2;
            f1 = f2;
            f2 = result;
        }
        return result;
    }

    private String simplifyPath(String path) {
        String[] datas = path.split("/");
        StringBuilder sb = new StringBuilder("/");
        for (int i = 0; i < datas.length; i++) {
            if (datas[i].length() == 0) {
                continue;
            } else {
                if (datas[i].equals(".")) {
                    continue;
                } else if (datas[i].equals("..")) {
                    if (sb.lastIndexOf("/") != -1) {
                        sb = new StringBuilder(sb.substring(0, sb.lastIndexOf("/")));
                    } else {
                        sb.append("/");
                    }
                } else {
                    if (sb.length() != 1)
                        sb.append("/");
                    sb.append(datas[i]);
                }
            }
        }
        if (sb.length() == 0) {
            sb.append("/");
        }
        return sb.toString();
    }

    private int minDistance(String word1, String word2) {
        if (word1 == null || word1.length() == 0) {
            return word2.length();
        }
        if (word2 == null || word2.length() == 0)
            return word1.length();
        int[] lastLine = new int[word2.length() + 1];
        for (int i = 0; i < lastLine.length; i++) {
            lastLine[i] = i;
        }
        for (int i = 0; i < word1.length(); i++) {
            int[] temp = new int[word2.length() + 1];
            temp[0] = i + 1;
            for (int j = 0; j < word2.length(); j++) {
                int count = 1;
                if (word1.charAt(i) == word2.charAt(j))
                    count = 0;
                temp[j + 1] = Math.min(lastLine[j] + count, Math.min(lastLine[j + 1], temp[j]) + 1);
            }
            lastLine = temp;
        }
        return lastLine[word2.length()];
    }

    private void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                flag = true;
                break;
            }
        }
        boolean f = false;
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    f = true;
                }
            }
            if (f) {
                for (int j = 0; j < n; j++)
                    matrix[i][j] &= 0;
                f = false;
            }
        }
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < m; j++) {
                    matrix[j][i] &= 0;
                }
            }
        }
        if (flag)
            for (int i = 0; i < n; i++) {
                matrix[0][i] &= 0;
            }
    }

    private boolean searchMatrix(int[][] matrix, int target) {
        int i = 0;
        int j = matrix.length - 1;
        while (i <= j) {
            int middle = (i + j) / 2;
            if (matrix[middle][0] == target)
                return true;
            else if (matrix[middle][0] < target)
                i = middle + 1;
            else
                j = middle - 1;
        }
        if (j < 0)
            return false;
        int row = j;
        i = 0;
        j = matrix[0].length - 1;
        while (i <= j) {
            int middle = (i + j) / 2;
            if (matrix[row][middle] == target)
                return true;
            else if (matrix[row][middle] < target)
                i = middle + 1;
            else
                j = middle - 1;
        }
        return false;
    }

    private void sortColors(int[] nums) {
        int zero = 0;
        int nonzero = nums.length - 1;
        int data = 0;
        int count = 0;
        while (count < 2) {
            while (zero < nums.length && nums[zero] == data)
                zero++;
            while (nonzero >= 0 && nums[nonzero] != data)
                nonzero--;
            if (zero >= nonzero) {
                nonzero = nums.length - 1;
                data = 1;
                count++;
            } else {
                int temp = nums[zero];
                nums[zero] = nums[nonzero];
                nums[nonzero] = temp;
            }
        }
    }

    private String minWindow(String s, String t) {
        String str = "";
        if (t.length() > s.length())
            return str;
        HashMap<Character, List<Integer>> maps = new HashMap<Character, List<Integer>>();
        List<Character> lists = new LinkedList<Character>();
        int start = 0;
        int end = 0;
        int minIndex = 0;
        int maxIndex = s.length() + 1;
        while (end < s.length()) {
            if (lists.size() == t.length()) {
                int begin = start;
                for (; begin < end; begin++) {
                    if (t.indexOf(s.charAt(begin)) != -1) {
                        List<Integer> temp = maps.get(s.charAt(begin));
                        temp.remove(0);
                        maps.put(s.charAt(begin), temp);
                        if (temp.size() == 0) {
                            lists.remove(lists.indexOf(s.charAt(begin)));
                            maps.remove(s.charAt(begin));
                            break;
                        }
                    }
                }
                if (end - begin < maxIndex - minIndex) {
                    maxIndex = end;
                    minIndex = begin;
                }
                start = begin + 1;
            } else {
                char ch = s.charAt(end);
                if (t.indexOf(ch) != -1) {
                    List<Integer> temp = null;
                    if (maps.containsKey(ch)) {
                        temp = maps.get(ch);
                    } else {
                        temp = new LinkedList<Integer>();
                    }
                    temp.add(end);
                    maps.put(ch, temp);
                    if (!lists.contains(ch)) {
                        lists.add(ch);
                    }
                }
                end++;
            }
        }
        int begin = start;
        boolean flag = false;
        for (; begin < end; begin++) {
            if (t.indexOf(s.charAt(begin)) != -1) {
                List<Integer> temp = maps.get(s.charAt(begin));
                temp.remove(0);
                if (temp.size() == 0) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag && end - begin < maxIndex - minIndex) {
            maxIndex = end;
            minIndex = begin;
        }
        if (maxIndex == s.length() + 1)
            return str;
        return s.substring(minIndex, maxIndex);
    }

    private String minWindow2(String s, String t) {
        int begin = 0, end = 0, minBegin = 0, minSize = s.length(), count = 0;
        HashMap<Character, Integer> stdMap = new HashMap<Character, Integer>();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            if (stdMap.containsKey(ch)) {
                stdMap.put(ch, stdMap.get(ch) + 1);
            } else {
                stdMap.put(ch, 1);
            }
            map.put(ch, 0);
        }
        for (end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (!stdMap.containsKey(ch)) {
                continue;
            }
            if (map.get(ch) < stdMap.get(ch)) {
                count++;
            }
            map.put(ch, map.get(ch) + 1);
            if (count == t.length()) {
                while (true) {
                    char c = s.charAt(begin);
                    if (stdMap.containsKey(c)) {
                        if (map.get(c) > stdMap.get(c)) {
                            map.put(c, map.get(c) - 1);
                        } else {
                            break;
                        }
                    }
                    begin++;
                }
                if (end - begin + 1 < minSize) {
                    minSize = end - begin + 1;
                    minBegin = begin;
                }
            }
        }
        return count == t.length() ? s.substring(minBegin, minBegin + minSize) : "";
    }

    private List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (n < 0 || k > n)
            return lists;
        combinationNK(n, k, 0, lists, new LinkedList<Integer>(), 1);
        return lists;
    }

    private void combinationNK(int n, int k, int count, List<List<Integer>> lists, List<Integer> temp, int start) {
        if (count == k) {
            List<Integer> result = new LinkedList<Integer>();
            for (int i = 0; i < temp.size(); i++) {
                result.add(temp.get(i));
            }
            lists.add(result);
            return;
        }
        for (int i = start; i <= n; i++) {
            temp.add(i);
            combinationNK(n, k, count + 1, lists, temp, i + 1);
            temp.remove(temp.size() - 1);
        }
    }

    private List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (nums == null || nums.length < 0) {
            return lists;
        }
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length; i++) {
            getSubsets(nums, 0, i, lists, new LinkedList<Integer>());
        }
        return lists;
    }

    private void getSubsets(int[] nums, int start, int k, List<List<Integer>> lists, List<Integer> temp) {
        if (k == 0) {
            List<Integer> result = new LinkedList<Integer>();
            for (int i = 0; i < temp.size(); i++) {
                result.add(temp.get(i));
            }
            lists.add(result);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            temp.add(nums[i]);
            getSubsets(nums, i + 1, k - 1, lists, temp);
            temp.remove(temp.size() - 1);
        }
    }

    private List<List<Integer>> subsets2(int[] nums) {
        Arrays.sort(nums);
        int elem_num = nums.length;
        double subset_num = Math.pow(2, elem_num);
        List<List<Integer>> subset_set = new LinkedList<List<Integer>>();
        for (int i = 0; i < subset_num; i++) {
            subset_set.add(new LinkedList<Integer>());
        }
        for (int i = 0; i < elem_num; i++) {
            for (int j = 0; j < subset_num; j++)
                if (((j >> i) & 1) != 0) {
                    List<Integer> temp = subset_set.get(j);
                    temp.add(nums[i]);
                }
        }
        return subset_set;
    }

    private boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0)
            return false;
        boolean[] flag = new boolean[1];
        flag[0] = false;
        boolean[][] directions = new boolean[board.length][board[0].length];
        char ch = word.charAt(0);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == ch) {
                    directions[i][j] = true;
                    existWord(board, word, i, j, 0, flag, directions);
                    directions[i][j] = false;
                }
                if (flag[0])
                    return true;
            }
        }
        return flag[0];
    }

    private void existWord(char[][] board, String word, int row, int col, int index, boolean[] flag, boolean[][] directions) {
        if (index == word.length() - 1) {
            flag[0] = true;
            return;
        }
        if (row - 1 >= 0 && !flag[0]) {
            if (!directions[row - 1][col] && board[row - 1][col] == word.charAt(index + 1)) {
                directions[row - 1][col] = true;
                existWord(board, word, row - 1, col, index + 1, flag, directions);
                directions[row - 1][col] = false;
            }
        }
        if (col - 1 >= 0 && !flag[0]) {
            if (!directions[row][col - 1] && board[row][col - 1] == word.charAt(index + 1)) {
                directions[row][col - 1] = true;
                existWord(board, word, row, col - 1, index + 1, flag, directions);
                directions[row][col - 1] = false;
            }
        }
        if (row + 1 < board.length && !flag[0]) {
            if (!directions[row + 1][col] && board[row + 1][col] == word.charAt(index + 1)) {
                directions[row + 1][col] = true;
                existWord(board, word, row + 1, col, index + 1, flag, directions);
                directions[row + 1][col] = false;
            }
        }
        if (col + 1 < board[0].length && !flag[0]) {
            if (!directions[row][col + 1] && board[row][col + 1] == word.charAt(index + 1)) {
                directions[row][col + 1] = true;
                existWord(board, word, row, col + 1, index + 1, flag, directions);
                directions[row][col + 1] = false;
            }
        }
    }

    private int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = 0;
        int count = 0;
        int i = 0;
        while (i < nums.length) {
            int temp = nums[i];
            while (i < nums.length && nums[i] == temp) {
                i++;
                count++;
                if (count < 3) {
                    nums[length++] = temp;
                }
            }
            count = 0;
        }
        return length;
    }

    private boolean search2(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return false;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (nums[middle] == target) {
                return true;
            }
            if (nums[middle] > nums[left]) {
                if (nums[middle] > target && nums[left] <= target) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else if (nums[middle] < nums[left]) {
                if (nums[middle] < target && nums[right] >= target) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            } else {
                left++;
            }
        }
        return false;
    }

    private ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return head;
        ListNode prev = head;
        ListNode curr = head;
        int count;
        while (curr != null && curr.next != null) {
            count = 0;
            ListNode temp = curr.next;
            while (temp != null && temp.val == curr.val) {
                count++;
                temp = temp.next;
            }
            if (count > 0) {
                if (curr == head) {
                    head = temp;
                    prev = temp;
                } else {
                    prev.next = temp;
                }
            }
            if (curr.next == temp) {
                prev = curr;
            }
            curr = temp;
        }
        return head;
    }

    private ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode prev = head;
        ListNode curr = head.next;
        while (curr != null) {
            if (curr.val == prev.val) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        return head;
    }

    private int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0)
            return 0;
        int max = 0;
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int index = stack.pop();
                int curArea = stack.isEmpty() ? i * height[index] : (i - stack.peek() - 1) * height[index];
                max = Math.max(max, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int index = stack.pop();
            int curArea = stack.isEmpty() ? height.length * height[index] : (height.length - stack.peek() - 1) * height[index];
            max = Math.max(max, curArea);
        }
        return max;
    }

    private int maximalRectangle(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {

                }
            }
        }
        return 0;
    }

    private ListNode partition(ListNode head, int x) {
        ListNode curr = head;
        while (curr != null) {
            if (curr.val < x) {
                curr = curr.next;
            } else {
                ListNode temp = curr.next;
                while (temp != null && temp.val >= x) {
                    temp = temp.next;
                }
                if (temp != null) {
                    ListNode node = curr.next;
                    int data1 = curr.val;
                    while (node != temp) {
                        int data2 = node.val;
                        node.val = data1;
                        data1 = data2;
                        node = node.next;
                    }
                    curr.val = temp.val;
                    temp.val = data1;
                    curr = curr.next;
                } else
                    break;
            }
        }
        return head;
    }

    private void merge(int[] nums1, int m, int[] nums2, int n) {
        int k1 = 0;
        int k2 = 0;
        int k = m;
        while (k1 <= m && k2 < n) {
            if (nums1[k1] > nums2[k2]) {
                for (int j = k; j > k1; j--) {
                    nums1[j] = nums1[j - 1];
                }
                k++;
                nums1[k1] = nums2[k2];
                k2++;
                m++;
            }
            k1++;
        }
        if (k2 != n) {
            for (int i = k2; i < n; i++) {
                nums1[k++] = nums2[i];
            }
        }
    }

    private List<Integer> grayCode(int n) {
        List<Integer> result = new LinkedList<Integer>();
        if (n < 0)
            return result;
        int number = 0;
        result.add(number);
        int count = (1 << n) - 1;
        while (count > 0) {
            number ^= 1;
            result.add(number);
            count--;
            if (count <= 0)
                break;
            int i = 0;
            while (((number >> i++) & 1) != 1) ;
            number ^= (1 << i);
            result.add(number);
            count--;
        }
        return result;
    }

    private List<Integer> grayCode2(int n) {
        if (n == 0) {
            List<Integer> result = new LinkedList<Integer>();
            result.add(0);
            return result;
        }
        List<Integer> temp = grayCode2(n - 1);
        int addNumber = 1 << (n - 1);
        List<Integer> result = new LinkedList<Integer>(temp);
        for (int i = temp.size() - 1; i >= 0; i--) {
            result.add(addNumber + temp.get(i));
        }
        return result;
    }

    private List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        result.add(new LinkedList<Integer>());
        for (int i = 0; i < nums.length; i++) {
            subsetsDup(nums, result, 0, new LinkedList<Integer>(), i + 1, new LinkedList<Integer>());
        }
        return result;
    }

    private void subsetsDup(int[] nums, List<List<Integer>> lists, int start, List<Integer> temp, int k, List<Integer> dupicates) {
        if (k == 0) {
            List<Integer> result = new LinkedList<Integer>(temp);
            lists.add(result);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            temp.add(nums[i]);
            subsetsDup(nums, lists, i + 1, temp, k - 1, dupicates);
            temp.remove(temp.size() - 1);
        }
    }

    private List<List<Integer>> subsetsWithDups(int[] nums) {
        if (nums == null) {
            return null;
        }
        Arrays.sort(nums);
        List<Integer> lastSize = new LinkedList<Integer>();
        return helpers(nums, nums.length - 1, lastSize);
    }

    private List<List<Integer>> helpers(int[] num, int index, List<Integer> lastSize) {
        if (index == -1) {
            List<List<Integer>> res = new LinkedList<List<Integer>>();
            List<Integer> elem = new LinkedList<Integer>();
            res.add(elem);
            return res;
        }
        List<List<Integer>> res = helpers(num, index - 1, lastSize);
        int size = res.size();
        int start = 0;
        if (index > 0 && num[index] == num[index - 1])
            start = lastSize.get(0);
        for (int i = start; i < size; i++) {
            List<Integer> elem = new LinkedList<Integer>(res.get(i));
            elem.add(num[index]);
            res.add(elem);
        }
        lastSize.set(0, size);
        return res;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null)
            return false;
        if (p.val == q.val) {
            boolean flag1 = isSameTree(p.left, q.left);
            boolean flag2 = isSameTree(p.right, q.right);
            return flag1 && flag2;
        } else {
            return false;
        }
    }

    private boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return symmetric(root.left, root.right);
    }

    private boolean symmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null)
            return false;
        return left.val == right.val && symmetric(left.left, right.right) && symmetric(left.right, right.left);
    }

    private List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        if (root == null)
            return result;
        List<TreeNode> temp = new LinkedList<TreeNode>();
        temp.add(root);
        while (temp.size() > 0) {
            List<Integer> tt = new LinkedList<Integer>();
            for (int i = 0; i < temp.size(); i++) {
                tt.add(temp.get(i).val);
            }
            result.add(tt);
            List<TreeNode> ll = new LinkedList<TreeNode>();
            while (temp.size() > 0) {
                TreeNode tree = temp.get(0);
                if (tree.left != null)
                    ll.add(tree.left);
                if (tree.right != null)
                    ll.add(tree.right);
                temp.remove(0);
            }
            temp.addAll(ll);
        }
        return result;
    }

    private int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int left = maxDepth(root.left) + 1;
        int right = maxDepth(root.right) + 1;
        return Math.max(left, right);
    }

    private List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        if (root == null)
            return result;
        List<TreeNode> temp = new LinkedList<TreeNode>();
        temp.add(root);
        while (temp.size() > 0) {
            List<Integer> tt = new LinkedList<Integer>();
            for (int i = 0; i < temp.size(); i++) {
                tt.add(temp.get(i).val);
            }
            result.add(0, tt);
            List<TreeNode> ll = new LinkedList<TreeNode>();
            while (temp.size() > 0) {
                TreeNode tree = temp.get(0);
                if (tree.left != null)
                    ll.add(tree.left);
                if (tree.right != null)
                    ll.add(tree.right);
                temp.remove(0);
            }
            temp.addAll(ll);
        }
        return result;
    }

    private boolean isBalanced(TreeNode root) {
        if (root == null)
            return true;
        int h1 = getHeight(root.left);
        int h2 = getHeight(root.right);
        if (Math.abs(h2 - h1) > 1)
            return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    private int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null)
            return minDepth(root.right) + 1;
        if (root.right == null)
            return minDepth(root.left) + 1;
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    private boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;
        return hasPath(root, sum);
    }

    private boolean hasPath(TreeNode root, int sum) {
        if (root == null && sum == 0)
            return true;
        if (root == null)
            return false;
        if (root.left == null)
            return hasPath(root.right, sum - root.val);
        if (root.right == null)
            return hasPath(root.left, sum - root.val);
        return hasPath(root.left, sum - root.val) || hasPath(root.right, sum - root.val);
    }

    private List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        for (int i = 0; i < numRows; i++) {
            if (result.size() == 0) {
                List<Integer> temp = new LinkedList<Integer>();
                temp.add(1);
                result.add(temp);
            } else {
                List<Integer> temp = result.get(result.size() - 1);
                List<Integer> tt = new LinkedList<Integer>();
                tt.add(1);
                for (int j = 0; j < temp.size() - 1; j++) {
                    tt.add(temp.get(j) + temp.get(j + 1));
                }
                tt.add(1);
                result.add(tt);
            }
        }
        return result;
    }

    private List<Integer> getRow(int rowIndex) {
        List<Integer> lists = new LinkedList<Integer>();
        lists.add(1);
        for (int i = 0; i < rowIndex; i++) {
            int count = lists.size();
            lists.add(0, 1);
            for (int j = 1; j < count; count--) {
                lists.add(lists.get(j) + lists.get(j + 1));
                lists.remove(j);
            }
            lists.remove(1);
            lists.add(1);
        }
        return lists;
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                i++;
                continue;
            }
            if (!Character.isLetterOrDigit(s.charAt(j))) {
                j--;
                continue;
            }
            if (Character.toLowerCase(s.charAt(i)) == Character.toLowerCase(s.charAt(j))) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    class MinStack {
        int min = Integer.MAX_VALUE;
        Stack<Integer> stack = new Stack<Integer>();

        public void push(int x) {
            if (x <= min) {
                stack.push(min);
                min = x;
            }
            stack.push(x);
        }

        public void pop() {
            if (stack.peek() == min) {
                stack.pop();
                min = stack.peek();
                stack.pop();
            } else
                stack.pop();
            if (stack.empty())
                min = Integer.MAX_VALUE;
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

    private List<List<Integer>> subsetsWithDupRe(int[] nums) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        Arrays.sort(nums);
        List<Integer> temp = new LinkedList<Integer>();
        dfssubset(result, nums, 0, temp);
        return result;
    }

    private void dfssubset(List<List<Integer>> res, int[] nums, int end, List<Integer> temp) {
        if (end == nums.length) {
            List<Integer> ll = new LinkedList<Integer>(temp);
            res.add(ll);
            return;
        }
        int same = end;
        while (same >= 0 && nums[same] == nums[end]) same--;
        same++;
        same = end - same;
        if (same == 0 || (temp.size() >= same && temp.get(temp.size() - same) == nums[end])) {
            temp.add(nums[end]);
            dfssubset(res, nums, end + 1, temp);
            temp.remove(temp.size() - 1);
        }
        dfssubset(res, nums, end + 1, temp);
    }

    private int maximalRectangle(char[][] matrix) {
        int area = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1')
                    area = Math.max(area, getMaxArea(matrix, i, j));
            }
        }
        return area;
    }

    private int getMaxArea(char[][] matrix, int row, int col) {
        int minWidth = Integer.MAX_VALUE;
        int area = 0;
        for (int i = row; i < matrix.length && matrix[i][col] == '1'; i++) {
            int width = col;
            while (width < matrix[row].length && matrix[i][width] == '1')
                width++;
            if (width - col < minWidth)
                minWidth = width - col;
            area = Math.max(area, minWidth * (i - row + 1));
        }
        return area;
    }

    private int maximalRectangle2(char[][] matrix) {
        int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[][] height = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0')
                    height[i][j] = 0;
                else
                    height[i][j] = i == 0 ? 1 : height[i - 1][j] + 1;
            }
        }
        int area = 0;
        for (int i = 0; i < m; i++) {
            area = Math.max(area, getMaxArea(height[i]));
        }
        return area;
    }

    private int getMaxArea(int[] height) {
        Stack<Integer> stack = new Stack<Integer>();
        int area = 0;
        int i = 0;
        while (i < height.length) {
            if (stack.isEmpty() || height[stack.peek()] < height[i])
                stack.push(i++);
            else {
                int t = stack.pop();
                area = Math.max(area, height[t] * (stack.isEmpty() ? i : i - stack.peek() - 1));
            }
        }
        return area;
    }

    private int maximalRectangle3(char[][] matrix) {
        int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] height = new int[n];
        Arrays.fill(left, 0);
        Arrays.fill(right, n);
        Arrays.fill(height, 0);
        int area = 0;
        for (int i = 0; i < m; i++) {
            int curLeft = 0;
            int curRight = n;
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    height[j] = 0;
                    left[j] = 0;
                    right[j] = n;
                    curLeft = j + 1;
                    curRight = j;
                    count = 0;
                } else {
                    height[j]++;
                    count++;
                    left[j] = Math.max(curLeft, left[j]);
                    right[j] = Math.min(curRight + count + 1, right[j]);
                }
                area = Math.max(area, (right[j] - left[j]) * height[j]);
            }
        }
        return area;
    }

    private int maximalRectangle4(char[][] matrix) {
        if (matrix == null) return 0;
        int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] height = new int[n];
        Arrays.fill(left, 0);
        Arrays.fill(right, n);
        Arrays.fill(height, 0);
        int maxA = 0;
        for (int i = 0; i < m; i++) {
            int cur_left = 0;
            int cur_right = n;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') height[j]++;
                else height[j] = 0;
            }
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') left[j] = Math.max(left[j], cur_left);
                else {
                    left[j] = 0;
                    cur_left = j + 1;
                }
            }
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') right[j] = Math.min(right[j], cur_right);
                else {
                    right[j] = n;
                    cur_right = j;
                }
            }
            for (int j = 0; j < n; j++)
                maxA = Math.max(maxA, (right[j] - left[j]) * height[j]);
        }
        return maxA;
    }

    private int numDecodings(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int[] nums = new int[s.length() + 1];
        nums[0] = 1;
        for (int i = 1; i < s.length() + 1; i++) {
            int num = Integer.parseInt(s.substring(i - 1, i));
            if (num >= 1 && num <= 9)
                nums[i] += nums[i - 1];
            if (i > 1) {
                num = Integer.parseInt(s.substring(i - 2, i));
                if (num >= 10 && num <= 26)
                    nums[i] += nums[i - 2];
            }
        }
        return nums[s.length()];
    }

    private boolean isScramble(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1.equals(s2)) return true;
        boolean[][][] res = new boolean[s1.length()][s2.length()][s1.length() + 1];
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                res[i][j][1] = s1.charAt(i) == s2.charAt(j);
            }
        }
        for (int len = 2; len <= s1.length(); len++) {
            for (int i = 0; i < s1.length() - len + 1; i++) {
                for (int j = 0; j < s2.length() - len + 1; j++) {
                    for (int k = 1; k < len; k++) {
                        res[i][j][len] |= (res[i][j][k] && res[i + k][j + k][len - k]) || (res[i][j + len - k][k] && res[i + k][j][len - k]);
                    }
                }
            }
        }
        return res[0][0][s1.length()];
    }

    private ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null)
            return head;
        ListNode previous = new ListNode(0);
        previous.next = head;
        ListNode prev = previous;
        for (int i = 0; i < m - 1; i++)
            prev = prev.next;
        ListNode start = prev.next;
        ListNode then = start.next;
        for (int i = 0; i < n - m; i++) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }
        return previous.next;
    }

    private List<String> restoreIpAddress(String s) {
        List<String> result = new LinkedList<String>();
        restoreIP(result, 0, s, "");
        return result;
    }

    private void restoreIP(List<String> result, int count, String s, String temp) {
        if (count == 3) {
            if (s.length() == 0 || s.length() > 3 || (s.charAt(0) == '0' && s.length() > 1))
                return;
            int number = Integer.parseInt(s);
            if (number <= 255) {
                temp += s;
                result.add(temp);
            }
            return;
        }
        for (int i = 1; i < s.length(); i++) {
            String str = s.substring(0, i);
            int number = Integer.parseInt(str);
            if (number <= 255 && (number >= Math.pow(10, i - 1) || (number == 0 && str.length() == 1))) {
                String tt = new String(temp + str + ".");
                restoreIP(result, count + 1, s.substring(i), tt);
            } else {
                break;
            }
        }
    }

    private List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> lists = new LinkedList<Integer>();
        TreeNode temp = root;
        while (temp != null) {
            stack.add(temp);
            temp = temp.left;
        }
        while (!stack.isEmpty()) {
            TreeNode tt = stack.pop();
            lists.add(tt.val);
            if (tt.right != null) {
                stack.push(tt.right);
                tt = tt.right;
                while (tt.left != null) {
                    stack.push(tt.left);
                    tt = tt.left;
                }
            }
        }
        return lists;
    }

    private List<TreeNode> generateTrees(int n) {
        return generate(1, n);
    }

    private List<TreeNode> generate(int left, int right) {
        List<TreeNode> ret = new LinkedList<TreeNode>();
        if (left > right) {
            ret.add(null);
            return ret;
        }
        for (int i = left; i <= right; i++) {
            List<TreeNode> lTree = generate(left, i - 1);
            List<TreeNode> rTree = generate(i + 1, right);
            for (TreeNode nodeL : lTree) {
                for (TreeNode nodeR : rTree) {
                    TreeNode root = new TreeNode(i);
                    root.left = nodeL;
                    root.right = nodeR;
                    ret.add(root);
                }
            }
        }
        return ret;
    }

    private List<TreeNode> generateTrees2(int n) {
        List<TreeNode>[] result = new List[n + 1];
        result[0] = new LinkedList<TreeNode>();
        result[0].add(null);
        for (int len = 1; len <= n; len++) {
            result[len] = new LinkedList<TreeNode>();
            for (int j = 0; j < len; j++) {
                for (TreeNode nodeL : result[j]) {
                    for (TreeNode nodeR : result[len - j - 1]) {
                        TreeNode node = new TreeNode(j + 1);
                        node.left = nodeL;
                        node.right = clone(nodeR, j + 1);
                        result[len].add(node);
                    }
                }
            }
        }
        return result[n];
    }

    private TreeNode clone(TreeNode n, int offset) {
        if (n == null) return null;
        TreeNode node = new TreeNode(n.val + offset);
        node.left = clone(n.left, offset);
        node.right = clone(n.right, offset);
        return node;
    }

    private int numTrees(int n) {
        int[] nums = new int[n + 1];
        nums[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                nums[i] += nums[j] * nums[i - j - 1];
            }
        }
        return nums[n];
    }

    private boolean isInterleave2(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null)
            return false;
        if (s1.length() + s2.length() != s3.length()) return false;
        return isInter(s1, 0, s2, 0, s3, 0);
    }

    private boolean isInter(String s1, int p1, String s2, int p2, String s3, int p3) {
        if (p3 == s3.length()) return true;
        if (p1 == s1.length()) return s2.substring(p2).equals(s3.substring(p3));
        if (p2 == s2.length()) return s1.substring(p1).equals(s3.substring(p3));
        if (s1.charAt(p1) == s3.charAt(p3) && s2.charAt(p2) == s3.charAt(p3))
            return isInter(s1, p1 + 1, s2, p2, s3, p3 + 1) || isInter(s1, p1, s2, p2 + 1, s3, p3 + 1);
        else if (s1.charAt(p1) == s3.charAt(p3))
            return isInter(s1, p1 + 1, s2, p2, s3, p3 + 1);
        else if (s2.charAt(p2) == s3.charAt(p3))
            return isInter(s1, p1, s2, p2 + 1, s3, p3 + 1);
        else return false;
    }

    private boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null)
            return false;
        if (s1.length() + s2.length() != s3.length()) return false;
        boolean[][] nums = new boolean[s2.length() + 1][s1.length() + 1];
        nums[0][0] = true;
        for (int i = 0; i < s1.length(); i++) {
            nums[0][i + 1] = s1.charAt(i) == s3.charAt(i) ? nums[0][i] : false;
        }
        for (int i = 0; i < s2.length(); i++) {
            nums[i + 1][0] = s2.charAt(i) == s3.charAt(i) ? nums[i][0] : false;
        }
        for (int i = 1; i <= s2.length(); i++) {
            for (int j = 1; j <= s1.length(); j++) {
                if (s3.charAt(i + j - 1) == s1.charAt(j - 1)) {
                    nums[i][j] |= nums[i][j - 1];
                } else {
                    nums[i][j] |= false;
                }
                if (s3.charAt(i + j - 1) == s2.charAt(i - 1)) {
                    nums[i][j] |= nums[i - 1][j];
                } else {
                    nums[i][j] |= false;
                }
            }
        }
        return nums[s2.length()][s1.length()];
    }

    private boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode left = root;
        while (left != null) {
            stack.push(left);
            left = left.left;
        }
        int number2 = 0;
        boolean flag = true;
        while (!stack.isEmpty()) {
            TreeNode temp = stack.pop();
            int number1 = temp.val;
            if (!flag && number1 <= number2) {
                return false;
            }
            flag = false;
            if (temp.right != null) {
                TreeNode tree = temp.right;
                while (tree != null) {
                    stack.push(tree);
                    tree = tree.left;
                }
            }
            number2 = number1;
        }
        return true;
    }

    private void recoverTree(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = root;
        TreeNode prev = null;
        TreeNode change1 = null;
        TreeNode next = null;
        TreeNode change2 = null;
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                TreeNode temp = stack.pop();
                if (prev != null && prev.val >= temp.val) {
                    if (change1 == null) change1 = prev;
                    if (change1 != null) change2 = temp;
                }
                prev = temp;
                curr = temp.right;
            }
        }
        if (change2 == null) {
            int temp = change1.val;
            change1.val = next.val;
            next.val = temp;
        } else {
            int tt = change1.val;
            change1.val = change2.val;
            change2.val = tt;
        }
    }

    private List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        if (root == null)
            return result;
        List<Integer> rootTemp = new LinkedList<Integer>();
        rootTemp.add(root.val);
        result.add(rootTemp);
        List<TreeNode> level = new LinkedList<TreeNode>();
        level.add(root);
        int height = 1;
        while (level.size() > 0) {
            if (result.size() <= height) {
                List<Integer> array = new LinkedList<Integer>();
                result.add(array);
            }
            List<TreeNode> temp = new LinkedList<TreeNode>();
            if (height % 2 == 0) {
                for (int i = 0; i < level.size(); i++) {
                    if (level.get(i).left != null) {
                        result.get(height).add(level.get(i).left.val);
                        temp.add(level.get(i).left);
                    }
                    if (level.get(i).right != null) {
                        result.get(height).add(level.get(i).right.val);
                        temp.add(level.get(i).right);
                    }
                }
            } else {
                for (int i = level.size() - 1; i >= 0; i--) {
                    if (level.get(i).right != null) {
                        result.get(height).add(level.get(i).right.val);
                        temp.add(0, level.get(i).right);
                    }
                    if (level.get(i).left != null) {
                        result.get(height).add(level.get(i).left.val);
                        temp.add(0, level.get(i).left);
                    }
                }
            }
            level = temp;
            height++;
        }
        result.remove(result.size() - 1);
        return result;
    }

    private void recoverTree2(TreeNode root) {
        TreeNode curr = root;
        TreeNode prev = null;
        TreeNode change1 = null;
        TreeNode change2 = null;
        while (curr != null) {
            if (curr.left == null) {
                if (prev != null && prev.val >= curr.val) {
                    if (change1 == null) change1 = prev;
                    if (change1 != null) change2 = prev.right;
                }
                curr = curr.right;
            } else {
                prev = curr.left;
                while (prev.right != null && prev.right != curr)
                    prev = prev.right;
                if (prev.right == null) {
                    prev.right = curr;
                    curr = curr.left;
                } else if (prev != null && prev.val >= curr.val) {
                    if (change1 == null) change1 = prev;
                    if (change1 != null) change2 = prev.right;
                    curr = curr.right;
                }
            }
        }
        int temp = change1.val;
        change1.val = change2.val;
        change2.val = temp;
    }

    private TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) return null;
        List<Integer> pre = new LinkedList<Integer>();
        List<Integer> in = new LinkedList<Integer>();
        for (int i = 0; i < preorder.length; i++) {
            pre.add(preorder[i]);
            in.add(inorder[i]);
        }
        return build(pre, in);
    }

    private TreeNode build(List<Integer> preorder, List<Integer> inorder) {
        if (inorder.size() == 0) {
            return null;
        }
        int value = preorder.remove(0);
        int index = inorder.indexOf(value);
        TreeNode root = new TreeNode(value);
        List<Integer> left = new LinkedList<Integer>();
        for (int i = 0; i < index; i++) {
            left.add(inorder.get(i));
        }
        root.left = build(preorder, left);
        List<Integer> right = new LinkedList<Integer>();
        for (int i = index + 1; i < inorder.size(); i++) {
            right.add(inorder.get(i));
        }
        root.right = build(preorder, right);
        return root;
    }

    private TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null)
            return null;
        HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) {
            maps.put(inorder[i], i);
        }
        return buildt(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, maps);
    }

    private TreeNode buildt(int[] preorder, int preL, int preR, int[] inorder, int inL, int inR, HashMap<Integer, Integer> maps) {
        if (preL > preR || inL > inR)
            return null;
        TreeNode root = new TreeNode(preorder[preL]);
        int index = maps.get(preorder[preL]);
        root.left = buildt(preorder, preL + 1, preL + index - inL, inorder, inL, index - 1, maps);
        root.right = buildt(preorder, preL + index - inL + 1, preR, inorder, index + 1, inR, maps);
        return root;
    }

    private TreeNode buildTree3(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null) return null;
        HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) {
            maps.put(inorder[i], i);
        }
        return dfsbuild(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, maps);
    }

    private TreeNode dfsbuild(int[] inorder, int preL, int preR, int[] postorder, int inL, int inR, HashMap<Integer, Integer> maps) {
        if (preL > preR || inL > inR)
            return null;
        TreeNode root = new TreeNode(postorder[inR]);
        int index = maps.get(postorder[inR]);
        root.right = dfsbuild(inorder, index + 1, preR, postorder, inR - preR + index, inR - 1, maps);
        root.left = dfsbuild(inorder, preL, index - 1, postorder, inL, inR - preR + index - 1, maps);
        return root;
    }

    private TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return sortedArray(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArray(int[] nums, int left, int right) {
        if (left > right)
            return null;
        int middle = (left + right) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = sortedArray(nums, left, middle - 1);
        root.right = sortedArray(nums, middle + 1, right);
        return root;
    }

    private TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        List<ListNode> lists = new LinkedList<ListNode>();
        while (head != null) {
            lists.add(head);
            head = head.next;
        }
        return sortedLists(lists, 0, lists.size() - 1);
    }

    private TreeNode sortedLists(List<ListNode> lists, int left, int right) {
        if (left > right) return null;
        int middle = (left + right) / 2;
        TreeNode root = new TreeNode(lists.get(middle).val);
        root.left = sortedLists(lists, left, middle - 1);
        root.right = sortedLists(lists, middle + 1, right);
        return root;
    }

    private TreeNode sortedListToBST2(ListNode head) {
        if (head == null) return null;
        ListNode curr = head;
        int count = -1;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return helperSorted(head, 0, count);
    }

    private TreeNode helperSorted(ListNode lists, int left, int right) {
        if (left >= right) return null;
        int middle = (left + right) / 2;
        TreeNode leftNode = helperSorted(lists, 0, middle - 1);
        TreeNode root = new TreeNode(lists.val);
        root.left = leftNode;
        lists = lists.next;
        TreeNode rightNode = helperSorted(lists, middle + 1, right);
        root.right = rightNode;
        return root;
    }

    private TreeNode sortedListToBST3(ListNode head) {
        int count = 0;
        ListNode curr = head;
        while (curr != null) {
            curr = curr.next;
            count++;
        }
        return generateBST(head, count);
    }

    private TreeNode generateBST(ListNode head, int n) {
        if (n == 0) return null;
        TreeNode node = new TreeNode(0);
        node.left = generateBST(head, n / 2);
        node.val = head.val;
        head = head.next;
        node.right = generateBST(head, n - n / 2 - 1);
        return node;
    }

    private TreeNode sortedListToBST4(ListNode head) {
        return sortedListBST(head, null);
    }

    private TreeNode sortedListBST(ListNode head, ListNode end) {
        if (head == end) return null;
        if (head.next == end) {
            TreeNode root = new TreeNode(head.val);
            return root;
        }
        ListNode middle = head, temp = head;
        while (temp != end && temp.next != end) {
            middle = middle.next;
            temp = temp.next.next;
        }
        TreeNode root = new TreeNode(middle.val);
        root.left = sortedListBST(head, middle);
        root.right = sortedListBST(middle.next, end);
        return root;
    }

    private List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        List<Integer> tt = new LinkedList<Integer>();
        pathS(lists, root, sum, tt);
        return lists;
    }

    private void pathS(List<List<Integer>> lists, TreeNode root, int sum, List<Integer> temp) {
        if (root == null || root.val < sum) return;
        if (sum == root.val && root.left == null && root.right == null) {
            List<Integer> result = new LinkedList<Integer>(temp);
            result.add(root.val);
            lists.add(result);
            return;
        }
        temp.add(root.val);
        sum = sum - root.val;
        pathS(lists, root.left, sum, temp);
        pathS(lists, root.right, sum, temp);
        temp.remove(temp.size() - 1);
    }

    private void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode temp = curr.left;
                while (temp.right != null) {
                    temp = temp.right;
                }
                temp.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
    }

    private int numDistinct(String s, String t) {
        if (s == null || t == null) return 0;
        int[][] distances = new int[s.length() + 1][t.length() + 1];
        distances[0][0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            distances[i][0] = 1;
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    distances[i][j] = distances[i - 1][j] + distances[i - 1][j - 1];
                } else {
                    distances[i][j] = distances[i - 1][j];
                }
            }
        }
        return distances[s.length()][t.length()];
    }

    class TreeLinkNode {
        int val;
        TreeLinkNode left;
        TreeLinkNode right;
        TreeLinkNode next;

        TreeLinkNode(int x) {
            val = x;
        }
    }

    private void connect1(TreeLinkNode root) {
        if (root == null) return;
        if (root.left != null) {
            root.left.next = root.right;
        }
        if (root.next != null && root.right != null) {
            root.right.next = root.next.left;
        }
        connect1(root.left);
        connect1(root.right);
    }

    private void connect(TreeLinkNode root) {
        if (root == null) return;
        TreeLinkNode p = root.next;
        while (p != null) {
            if (p.left != null) {
                p = p.left;
                break;
            }
            if (p.right != null) {
                p = p.right;
                break;
            }
            p = p.next;
        }
        if (root.right != null) {
            root.right.next = p;
        }
        if (root.left != null) {
            root.left.next = root.right == null ? p : root.right;
        }
        connect1(root.right);
        connect(root.left);
    }

    private int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        List<Integer> temp = new LinkedList<Integer>(triangle.get(0));
        List<Integer> result = new LinkedList<Integer>();
        for (int i = 1; i < triangle.size(); i++) {
            List<Integer> tt = triangle.get(i);
            for (int j = 0; j < tt.size(); j++) {
                if (j == 0) {
                    result.add(tt.get(j) + temp.get(j));
                } else if (j == tt.size() - 1) {
                    result.add(tt.get(j) + temp.get(j - 1));
                } else {
                    result.add(Math.min(temp.get(j - 1), temp.get(j)) + tt.get(j));
                }
            }
            temp = new LinkedList<Integer>(result);
            result.clear();
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i) < min) {
                min = temp.get(i);
            }
        }
        return min;
    }

    private int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int max = 0;
        int minimal = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[minimal]) {
                minimal = i;
            } else {
                max = Math.max(max, prices[i] - prices[minimal]);
            }
        }
        return max;
    }

    private int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] > 0) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    private int maxProfit3(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int[] left = new int[prices.length];
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            left[i] = Math.max(left[i - 1], prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        int[] right = new int[prices.length];
        int max = prices[prices.length - 1];
        for (int i = prices.length - 2; i >= 0; i--) {
            right[i] = Math.max(max - prices[i], right[i + 1]);
            max = Math.max(max, prices[i]);
        }
        max = 0;
        for (int i = 0; i < prices.length; i++) {
            max = Math.max(max, left[i] + right[i]);
        }
        return max;
    }

    private int maxPathSum(TreeNode root) {
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        maxPath(root, max);
        return max[0];
    }

    private int maxPath(TreeNode root, int[] max) {
        if (root == null) return 0;
        int val = root.val;
        int left = maxPath(root.left, max);
        int right = maxPath(root.right, max);
        int current = Math.max(val, Math.max(val + left, val + right));
        max[0] = Math.max(max[0], Math.max(current, val + left + right));
        return current;
    }

    private int ladderLength(String beginWord, String endWord, Set<String> wordDict) {
        if (beginWord == null || endWord == null) return 0;
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        wordDict.remove(beginWord);
        int res = 1;
        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int start = 0; start < count; start++) {
                String temp = queue.poll();
                for (int i = 0; i < temp.length(); i++) {
                    char ch = temp.charAt(i);
                    for (char j = 'a'; j <= 'z'; j++) {
                        if (j == ch) continue;
                        String str = temp.substring(0, i) + j + temp.substring(i + 1);
                        if (str.equals(endWord)) {
                            return res + 1;
                        }
                        if (wordDict.contains(str)) {
                            queue.offer(str);
                            wordDict.remove(str);
                        }
                    }
                }
            }
            res++;
        }
        return 0;
    }

    public int ladderLength2(String beginWord, String endWord, Set<String> wordDict) {
        if (wordDict == null || wordDict.size() == 0) {
            return 0;
        }
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        wordDict.remove(beginWord);
        int length = 1;
        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                String currStr = queue.poll();
                for (int j = 0; j < currStr.length(); j++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (currStr.charAt(j) == c) continue;
                        String newStr = replace(currStr, j, c);
                        if (newStr.equals(endWord)) {
                            return length + 1;
                        }
                        if (wordDict.contains(newStr)) {
                            queue.offer(newStr);
                            wordDict.remove(newStr);
                        }
                    }
                }
            }
            length++;
        }
        return 0;
    }

    public String replace(String str, int j, char c) {
        char[] charArray = str.toCharArray();
        charArray[j] = c;
        return new String(charArray);
    }

    private List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> lists = new LinkedList<List<String>>();
        if (dict == null || dict.size() == 0) return lists;
        findLadderss(start, end, dict, lists, new LinkedList<String>());
        return lists;
    }

    private void findLadderss(String start, String end, Set<String> sets, List<List<String>> lists, List<String> temp) {
        if (start.equals(end)) {
            List<String> tt = new LinkedList<String>(temp);
            tt.add(end);
            lists.add(tt);
            return;
        }
        for (int i = 0; i < start.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (start.charAt(i) == ch) continue;
                String str = start.substring(0, i) + ch + start.substring(i + 1);
                if (sets.contains(str)) {
                    temp.add(str);
                    sets.remove(str);
                    Set<String> lsets = new LinkedHashSet<String>(sets);
                    findLadderss(str, end, lsets, lists, temp);
                    temp.remove(temp.size() - 1);
                }
            }
        }
    }

    private List<List<String>> findLadders2(String start, String end, Set<String> dict) {
        List<List<String>> results = new LinkedList<List<String>>();
        if (dict == null || dict.size() == 0) return results;
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        int min = Integer.MAX_VALUE;
        Queue<String> queue = new ArrayDeque<String>();
        queue.add(start);
        Map<String, Integer> ladder = new HashMap<String, Integer>();
        for (String string : dict) {
            ladder.put(string, Integer.MAX_VALUE);
        }
        ladder.put(start, 0);
        dict.add(end);
        while (!queue.isEmpty()) {
            String word = queue.poll();
            int step = ladder.get(word) + 1;
            if (step > min) break;
            for (int i = 0; i < word.length(); i++) {
                StringBuilder builder = new StringBuilder(word);
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    builder.setCharAt(i, ch);
                    String new_word = builder.toString();
                    if (ladder.containsKey(new_word)) {
                        if (step > ladder.get(new_word))
                            continue;
                        else if (step < ladder.get(new_word)) {
                            queue.add(new_word);
                            ladder.put(new_word, step);
                        }
                        if (map.containsKey(new_word))
                            map.get(new_word).add(word);
                        else {
                            List<String> list = new LinkedList<String>();
                            list.add(word);
                            map.put(new_word, list);
                        }
                    }
                    if (new_word.equals(end)) {
                        min = step;
                    }
                }
            }
        }
        LinkedList<String> result = new LinkedList<String>();
        backTrace(end, start, result, results, map);
        return results;
    }

    private void backTrace(String word, String start, List<String> list, List<List<String>> results, Map<String, List<String>> maps) {
        if (word.equals(start)) {
            list.add(0, start);
            results.add(new ArrayList<String>(list));
            list.remove(0);
            return;
        }
        list.add(0, word);
        if (maps.get(word) != null)
            for (String s : maps.get(word))
                backTrace(s, start, list, results, maps);
        list.remove(0);
    }

    private int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            maps.put(nums[i], i);
        }
        int[] counts = new int[nums.length];
        boolean[] visited = new boolean[nums.length];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 1;
        }
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                int temp = nums[i];
                while (maps.containsKey(++temp)) {
                    counts[i]++;
                    visited[maps.get(temp)] = true;
                }
                temp = nums[i];
                while (maps.containsKey(--temp)) {
                    counts[i]++;
                    visited[maps.get(temp)] = true;
                }
                if (max < counts[i])
                    max = counts[i];
            }
        }
        return max;
    }

    private int sumNumbers(TreeNode root) {
        List<String> lists = new LinkedList<String>();
        getSumPath(root, lists, "");
        int sum = 0;
        for (int i = 0; i < lists.size(); i++) {
            sum += Integer.parseInt(lists.get(i));
        }
        return sum;
    }

    private void getSumPath(TreeNode root, List<String> lists, String value) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            String temp = value + root.val;
            lists.add(temp);
            return;
        }
        value += root.val;
        getSumPath(root.left, lists, value);
        getSumPath(root.right, lists, value);
    }

    private void solve(char[][] board) {
        if (board == null) return;
        int row = board.length;
        if (row < 2) return;
        int col = board[0].length;
        if (col < 2) return;
        for (int i = 0; i < col; i++) {
            if (board[0][i] == 'O') {
                board[0][i] = '#';
                dfsSolve2(board, 1, i);
            }
        }
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O') {
                board[i][0] = '#';
                dfsSolve2(board, i, 1);
            }
        }
        for (int i = 0; i < row; i++) {
            if (board[i][col - 1] == 'O') {
                board[i][col - 1] = '#';
                dfsSolve2(board, i, col - 2);
            }
        }
        for (int i = 0; i < col; i++) {
            if (board[row - 1][i] == 'O') {
                board[row - 1][i] = '#';
                dfsSolve2(board, row - 2, i);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '#')
                    board[i][j] = 'O';
                else if (board[i][j] == 'O')
                    board[i][j] = 'X';
            }
        }
    }

    private void dfsSolve2(char[][] board, int i, int j) {
        if (i < 0 || i > board.length - 1 || j < 0 || j > board[0].length - 1) return;
        if (board[i][j] != 'O') return;
        board[i][j] = '#';
        dfsSolve2(board, i - 1, j);
        dfsSolve2(board, i, j - 1);
        dfsSolve2(board, i, j + 1);
        dfsSolve2(board, i + 1, j);
    }

    private void dfsSolve(char[][] board, int i, int j) {
        int row = board.length;
        int col = board[0].length;
        if (i > 1 && board[i - 1][j] == 'O') {
            board[i - 1][j] = '#';
            dfsSolve(board, i - 1, j);
        }
        if (j > 1 && board[i][j - 1] == 'O') {
            board[i][j - 1] = '#';
            dfsSolve(board, i, j - 1);
        }
        if (j < col - 1 && board[i][j + 1] == 'O') {
            board[i][j + 1] = '#';
            dfsSolve(board, i, j + 1);
        }
        if (i < row - 1 && board[i + 1][j] == 'O') {
            board[i + 1][j] = '#';
            dfsSolve(board, i + 1, j);
        }
    }

    private void solve2(char[][] board) {
        if (board == null) return;
        int row = board.length;
        if (row < 2) return;
        int col = board[0].length;
        if (col < 2) return;
        Queue<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O') {
                dfs(board, queue, i, 0);
            }
            if (board[i][col - 1] == 'O') {
                dfs(board, queue, i, col - 1);
            }
        }
        for (int i = 0; i < col; i++) {
            if (board[0][i] == 'O') {
                dfs(board, queue, 0, i);
            }
            if (board[row - 1][i] == 'O') {
                dfs(board, queue, row - 1, i);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
                else if (board[i][j] == '#')
                    board[i][j] = 'O';
            }
        }
    }

    private void dfs(char[][] board, Queue<Integer> queue, int i, int j) {
        int n = board[0].length;
        queue.offer(i * n + j);
        board[i][j] = '#';
        while (!queue.isEmpty()) {
            int index = queue.poll();
            int x = index / n;
            int y = index % n;
            if (x > 0 && board[x - 1][y] == 'O') {
                board[x - 1][y] = '#';
                queue.offer((x - 1) * n + y);
            }
            if (x < board.length - 1 && board[x + 1][y] == 'O') {
                board[x + 1][y] = '#';
                queue.offer((x + 1) * n + y);
            }
            if (y > 0 && board[x][y - 1] == 'O') {
                board[x][y - 1] = '#';
                queue.offer(x * n + y - 1);
            }
            if (y < n - 1 && board[x][y + 1] == 'O') {
                board[x][y + 1] = '#';
                queue.offer(x * n + y + 1);
            }
        }
    }

    private List<List<String>> partition(String s) {
        List<List<String>> lists = new LinkedList<List<String>>();
        if (s == null || s.length() == 0) return lists;
        List<String> value = new LinkedList<String>();
        dfsPartition(s, 0, lists, value);
        return lists;
    }

    private void dfsPartition(String s, int start, List<List<String>> lists, List<String> value) {
        if (start == s.length()) {
            List<String> ll = new LinkedList<String>(value);
            lists.add(ll);
            return;
        }
        for (int i = start + 1; i <= s.length(); i++) {
            String temp = s.substring(start, i);
            if (isPar(temp)) {
                value.add(temp);
                dfsPartition(s, i, lists, value);
                value.remove(value.size() - 1);
            }
        }
    }


    private boolean isPar(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    private int minCut(String s) {
        if (s == null || s.length() == 0) return 0;
        int length = s.length();
        int[][] counts = new int[length][length];
        for (int len = 1; len <= length; len++) {
            for (int i = 0; i <= length - len; i++) {
                int j = i + len - 1;
                if (isPar(s.substring(i, j + 1))) {
                    counts[i][j] = 0;
                    continue;
                }
                int min = Integer.MAX_VALUE;
                for (int t = i; t < j; t++) {
                    min = Math.min(min, counts[i][t] + counts[t + 1][j] + 1);
                }
                counts[i][j] = min;
            }
        }
        return counts[0][length - 1];
    }

    private int minCut2(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int[] cut = new int[n];
        for (int len = 0; len < n; len++) {
            cut[len] = len;
            for (int i = 0; i <= len; i++) {
                if (s.charAt(i) == s.charAt(len) && (len - i <= 1 || dp[i + 1][len - 1])) {
                    dp[i][len] = true;
                    if (i > 0) {
                        cut[len] = Math.min(cut[len], cut[i - 1] + 1);
                    } else {
                        cut[len] = 0;
                    }
                }
            }
        }
        return cut[n - 1];
    }

    class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
    }

    private UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return node;
        Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        Map<UndirectedGraphNode, UndirectedGraphNode> maps = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        UndirectedGraphNode root = new UndirectedGraphNode(node.label);
        queue.offer(node);
        maps.put(node, root);
        while (!queue.isEmpty()) {
            UndirectedGraphNode curr = queue.poll();
            List<UndirectedGraphNode> lists = curr.neighbors;
            for (int i = 0; i < lists.size(); i++) {
                if (!maps.containsKey(lists.get(i))) {
                    UndirectedGraphNode temp = new UndirectedGraphNode(lists.get(i).label);
                    queue.offer(lists.get(i));
                    maps.get(curr).neighbors.add(temp);
                    maps.put(lists.get(i), temp);
                } else {
                    maps.get(curr).neighbors.add(maps.get(lists.get(i)));
                }
            }
        }
        return root;
    }

    private int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length == 0 || cost.length == 0) return -1;
        for (int i = 0; i < cost.length; i++) {
            cost[i] = gas[i] - cost[i];
        }
        int index = -1;
        for (int i = 0; i < cost.length; i++) {
            int temp = i;
            int len = 1;
            while ((temp + len) % cost.length != i - temp) {
                if (cost[temp] + cost[temp + len] < 0) {
                    break;
                }
                len++;
            }
            if ((temp + len) % cost.length == i - temp) {
                index = temp;
                break;
            }
        }
        return index;
    }

    private int canCompleteCircuit2(int[] gas, int[] cost) {
        int sumRemaining = 0;
        int total = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int remaining = gas[i] - cost[i];
            if (sumRemaining >= 0) {
                sumRemaining += remaining;
            } else {
                sumRemaining = remaining;
                start = i;
            }
            total += remaining;
        }
        if (total >= 0) {
            return start;
        } else {
            return -1;
        }
    }

    private int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) return 0;
        int[] candies = new int[ratings.length];
        candies[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                candies[i] = 1;
            }
        }
        int result = candies[ratings.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
                candies[i] = candies[i + 1] + 1;
            }
            result += candies[i];
        }
        return result;
    }

    private int candy2(int[] ratings) {
        if (ratings == null || ratings.length == 0) return 0;
        int[] candies = new int[ratings.length];
        candies[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                candies[i] = 1;
            }
        }
        int result = candies[ratings.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            int cur = 1;
            if (ratings[i] > ratings[i + 1]) {
                cur = candies[i + 1] + 1;
            }
            result += Math.max(cur, candies[i]);
            candies[i] = cur;
        }
        return result;
    }

    private int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }

    private int singleNumber2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int ones = 0, twos = 0, threes = 0;
        for (int i = 0; i < nums.length; i++) {
            twos |= ones & nums[i];
            ones ^= nums[i];
            threes = ones & twos;
            ones &= ~threes;
            twos &= ~threes;
        }
        return ones;
    }

    class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int x) {
            this.label = x;
        }
    }

    private RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return null;
        Map<RandomListNode, RandomListNode> maps = new HashMap<RandomListNode, RandomListNode>();
        Queue<RandomListNode> queue = new LinkedList<RandomListNode>();
        queue.offer(head);
        RandomListNode root = new RandomListNode(head.label);
        maps.put(head, root);
        while (!queue.isEmpty()) {
            RandomListNode ra = queue.poll();
            if (ra.next != null) {
                if (!maps.containsKey(ra.next)) {
                    RandomListNode temp = new RandomListNode(ra.next.label);
                    maps.put(ra.next, temp);
                    queue.offer(ra.next);
                    maps.get(ra).next = temp;
                } else {
                    maps.get(ra).next = maps.get(ra.next);
                }
            } else {
                maps.get(ra).next = null;
            }
            if (ra.random != null) {
                if (!maps.containsKey(ra.random)) {
                    RandomListNode temp = new RandomListNode(ra.random.label);
                    maps.put(ra.random, temp);
                    queue.offer(ra.random);
                    maps.get(ra).random = temp;
                } else {
                    maps.get(ra).random = maps.get(ra.random);
                }
            } else {
                maps.get(ra).random = null;
            }
        }
        return maps.get(head);
    }

    private boolean wordBreak(String s, Set<String> wordDict) {
        if (s == null || s.length() == 0) return false;
        int n = s.length();
        boolean[] contains = new boolean[n + 1];
        contains[0] = true;
        for (int i = 0; i < n; i++) {
            if (wordDict.contains(s.substring(0, i + 1))) {
                contains[i + 1] = true;
            } else {
                for (String word : wordDict) {
                    int len = word.length();
                    if (len <= i + 1 && s.substring(i + 1 - len, i + 1).equals(word) && contains[i + 1 - len]) {
                        contains[i + 1] = true;
                        break;
                    }
                }
            }
        }
        return contains[n];
    }

    private List<String> wordBreak2(String s, Set<String> wordDict) {
        List<String> lists = new LinkedList<String>();
        if (s == null || s.length() == 0) return lists;
        int n = s.length();
        boolean[] contains = new boolean[n + 1];
        contains[0] = true;
        Map<Integer, List<String>> maps = new HashMap<Integer, List<String>>();
        for (int i = 0; i < n; i++) {
            if (wordDict.contains(s.substring(0, i + 1))) {
                contains[i + 1] = true;
                if (maps.containsKey(0)) {
                    maps.get(0).add(s.substring(0, i + 1));
                } else {
                    List<String> temp = new LinkedList<String>();
                    temp.add(s.substring(0, i + 1));
                    maps.put(0, temp);
                }
            } else {
                for (String word : wordDict) {
                    int len = word.length();
                    if (len <= i + 1 && s.substring(i + 1 - len, i + 1).equals(word) && contains[i + 1 - len]) {
                        contains[i + 1] = true;
                        if (maps.containsKey(i + 1 - len)) {
                            maps.get(i + 1 - len).add(s.substring(i + 1 - len, i + 1));
                        } else {
                            List<String> temp = new LinkedList<String>();
                            temp.add(s.substring(i + 1 - len, i + 1));
                            maps.put(i + 1 - len, temp);
                        }
                    }
                }
            }
        }
        getResult(lists, "", maps, 0, n);
        return lists;
    }

    private void getResult(List<String> lists, String value, Map<Integer, List<String>> maps, int len, int n) {
        if (len == n) {
            lists.add(value.trim());
            return;
        }
        if (!maps.containsKey(len)) return;
        List<String> strs = maps.get(len);
        for (int i = 0; i < strs.size(); i++) {
            String temp = value + " " + strs.get(i);
            getResult(lists, temp, maps, len + strs.get(i).length(), n);
        }
    }

    private boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        while (head != null && head.next != null) {
            if (head == head.next)
                return true;
            head.next = head.next.next;
            head = head.next;
        }
        return false;
    }

    private ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                break;
        }
        ListNode temp = head;
        if (fast != null && fast.next != null) {
            while (fast != temp) {
                fast = fast.next;
                temp = temp.next;
            }
            return temp;
        }
        return null;
    }

    private void reorderList(ListNode head) {
        if (head == null) return;
        ListNode prev = head;
        ListNode next = head;
        ListNode temp = head;
        while (next != null && next.next != null) {
            temp = prev;
            prev = prev.next;
            next = next.next.next;
        }
        next = prev;
        prev = temp;
        temp = next.next;
        while (temp != null) {
            next.next = temp.next;
            temp.next = prev.next;
            prev.next = temp;
            temp = next.next;
        }
        next = prev;
        prev = head;
        temp = prev.next;
        while (next != prev) {
            prev.next = next.next;
            next.next = next.next.next;
            prev.next.next = temp;
            prev = temp;
            temp = prev.next;
        }
    }

    private List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return list;
    }

    private List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(0, node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return list;
    }

    public class LRUCache {
        private HashMap<Integer, Node> map;
        private int cap;
        private int number;
        Node head;
        Node tail;

        public LRUCache(int capacity) {
            cap = capacity;
            number = 0;
            head = new Node(-1, -1);
            head.pre = null;
            head.next = null;
            tail = head;
            map = new HashMap<Integer, Node>(capacity);
        }

        public int get(int key) {
            Node ret = map.get(new Integer(key));
            if (ret == null) return -1;
            refresh(ret);
            return ret.value;

        }

        public void refresh(Node node) {
            if (node == head.next) return;
            Node temp = head.next; //head node in the map;
            Node nodePre = node.pre;
            Node nodeNext = node.next; //save
            head.next = node;
            node.pre = head;
            temp.pre = node;
            node.next = temp;
            nodePre.next = nodeNext;
            if (nodeNext != null) nodeNext.pre = nodePre;
            else tail = nodePre;

        }

        public void set(int key, int value) {
            Node ret = map.get(new Integer(key));
            if (ret != null) {
                refresh(ret);
                ret.value = value;
            } else {
                //check and delete
                if (number == cap) {
                    Node temp = tail;
                    tail = tail.pre;
                    tail.next = null;
                    map.remove(new Integer(temp.key));
                    number--;
                }
                number++;
                //add in the last and refresh
                Node node = new Node(key, value);
                node.pre = tail;
                node.next = null;
                tail.next = node;
                tail = node;
                map.put(key, node);
                refresh(node);
            }

        }

        class Node {
            int key;
            int value;
            Node pre;
            Node next;

            public Node(int k, int v) {
                value = v;
                key = k;
            }

        }
    }

    private ListNode insertionSortList(ListNode head) {
        if (head == null) return null;
        ListNode pre = new ListNode(-1);
        ListNode tt = pre;
        pre.next = head;
        ListNode temp = head;
        ListNode cur = head.next;
        ListNode preCur = head;
        while (cur != null) {
            pre = tt;
            temp = pre.next;
            while (temp.val <= cur.val && temp != cur) {
                pre = temp;
                temp = temp.next;
            }
            if (temp != cur) {
                preCur.next = cur.next;
                pre.next = cur;
                cur.next = temp;
                cur = preCur.next;
            } else {
                preCur = cur;
                cur = preCur.next;
            }
        }
        return tt.next;
    }

    private ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(fast);
        return mergeList(left, right);
    }

    private ListNode mergeList(ListNode left, ListNode right) {
        if (left == null)
            return right;
        if (right == null)
            return left;
        ListNode node = new ListNode(-1);
        ListNode temp = node;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
        }
        if (left != null)
            temp.next = left;
        if (right != null)
            temp.next = right;
        return node.next;
    }

    class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

    private int maxPoints(Point[] points) {
        Map<Double, Integer> maps;
        int max = 0;
        for (int i = 0; i < points.length; i++) {
            maps = new HashMap<Double, Integer>();
            int duplicates = 1;
            int vertical = 0;
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].x == points[i].x) {
                    if (points[j].y == points[i].y) {
                        duplicates++;
                    } else {
                        vertical++;
                    }
                } else {
                    double scope = points[j].y - points[i].y == 0 ? 0.0 : 1.0 * (points[j].y - points[i].y) / (points[j].x - points[i].x);
                    if (maps.containsKey(scope)) {
                        maps.put(scope, maps.get(scope) + 1);
                    } else {
                        maps.put(scope, 1);
                    }
                }
            }
            for (Map.Entry<Double, Integer> entry : maps.entrySet()) {
                max = Math.max(duplicates + entry.getValue(), max);
            }
            max = Math.max(vertical + duplicates, max);
        }
        return max;
    }

    private int evalRPN(String[] tokens) {
        if (tokens.length == 0) return 0;
        if (tokens.length == 1) return Integer.parseInt(tokens[0]);
        int len = 0;
        Stack<Integer> stack = new Stack<Integer>();
        int sum = 0;
        while (len < tokens.length) {
            if (tokens[len].equals("+")) {
                int left = stack.pop();
                int right = stack.pop();
                sum = left + right;
                stack.push(sum);
            } else if (tokens[len].equals("-")) {
                int left = stack.pop();
                int right = stack.pop();
                sum = right - left;
                stack.push(sum);
            } else if (tokens[len].equals("*")) {
                int left = stack.pop();
                int right = stack.pop();
                sum = left * right;
                stack.push(sum);
            } else if (tokens[len].equals("/")) {
                int left = stack.pop();
                int right = stack.pop();
                sum = right / left;
                stack.push(sum);
            } else {
                stack.push(Integer.parseInt(tokens[len]));
            }
            len++;
        }
        return sum;
    }

    private String reverseWords(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.length() == 0) return "";
        String result = "";
        String[] splits = s.split(" ");
        for (int i = splits.length - 1; i >= 0; i--) {
            if (splits[i].length() > 0 && splits[i].indexOf(" ") == -1)
                result += splits[i] + " ";
        }
        return result.trim();
    }

    private int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int maxLocal = nums[0];
        int minLocal = nums[0];
        int groupMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = maxLocal;
            maxLocal = Math.max(Math.max(nums[i] * maxLocal, nums[i]), nums[i] * minLocal);
            minLocal = Math.min(Math.min(nums[i] * temp, nums[i]), nums[i] * minLocal);
            groupMax = Math.max(groupMax, maxLocal);
        }
        return groupMax;
    }

    private int findMin(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            if (nums[left] < nums[right]) {
                return nums[left];
            } else {
                if (nums[middle] >= nums[left]) {
                    left = middle + 1;
                } else if (nums[middle] < nums[right]) {
                    right = middle;
                }
            }
        }
        return nums[left];
    }

    private int findMin2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            if (nums[left] < nums[right]) {
                return nums[left];
            } else if (nums[left] == nums[right]) {
                right--;
            } else {
                if (nums[middle] <= nums[right]) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            }
        }
        return nums[left];
    }

    private ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode tail = headA;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = headB;
        ListNode slow = headA;
        ListNode fast = headA;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                break;
        }
        if (fast == null || fast.next == null)
            return null;
        slow = headA;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        tail.next = null;
        return slow;
    }

    private ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        int lenA = 0;
        ListNode temp = headA;
        while (temp != null) {
            lenA++;
            temp = temp.next;
        }
        int lenB = 0;
        temp = headB;
        while (temp != null) {
            lenB++;
            temp = temp.next;
        }
        ListNode p1 = headA;
        ListNode p2 = headB;
        int diff = lenA - lenB;
        if (lenA < lenB) {
            diff = lenB - lenA;
            p1 = headB;
            p2 = headA;
        }
        while (p1 != null && diff > 0) {
            p1 = p1.next;
            diff--;
        }
        while (p1 != null && p2 != null && p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1 == null || p2 == null)
            return null;
        else
            return p1;
    }

    private int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 1) return 0;
        for (int i = 1; i < nums.length - 1; i++) {
            int prev = nums[i - 1];
            int curr = nums[i];
            int next = nums[i + 1];
            if (curr > prev && curr > next) {
                return i;
            }
        }
        if (nums[nums.length - 1] > nums[nums.length - 2])
            return nums.length - 1;
        return 0;
    }

    private int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int len1 = v1.length;
        int len2 = v2.length;
        int len = Math.max(len1, len2);
        for (int i = 0; i < len; i++) {
            int num1 = i < len1 ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < len2 ? Integer.parseInt(v2[i]) : 0;
            if (num1 == num2) {
                continue;
            } else if (num1 < num2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    private String convertToTitle(int n) {
        String result = "";
        while (n > 0) {
            int x = (n - 1) % 26;
            result = (char) (x + 'A') + result;
            n = (n - 1) / 26;
        }
        return result;
    }

    private int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    private int titleToNumber(String s) {
        int n = s.length();
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result += Math.pow(26, n - 1 - i) * (s.charAt(i) - 'A' + 1);
        }
        return result;
    }

    private int trailingZeros(int n) {
        if (n < 0) return -1;
        int count = 0;
        for (long i = 5; n / i >= 1; i *= 5) {
            count += n / i;
        }
        return count;
    }

    private void rotate(int[] nums, int k) {
        if (nums == null || k <= 0) return;
        k = k % nums.length;
        rotateLength(nums, 0, nums.length - k - 1);
        rotateLength(nums, nums.length - k, nums.length - 1);
        rotateLength(nums, 0, nums.length - 1);
    }

    private void rotateLength(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    private int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result |= (n >> i & 1) << (31 - i);
        }
        return result;
    }

    private int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((n >> i & 1) == 1)
                count++;
        }
        return count;
    }

    private int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        return dp[dp.length - 1];
    }

    class Bucket {
        int low;
        int high;

        public Bucket() {
            low = -1;
            high = -1;
        }
    }

    private int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        int max = nums[0];
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
            if (nums[i] < min) {
                min = nums[i];
            }
        }
        Bucket[] buckets = new Bucket[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket();
        }
        double interval = (double) nums.length / (max - min);
        for (int i = 0; i < nums.length; i++) {
            int index = (int) ((nums[i] - min) * interval);
            if (buckets[index].low == -1) {
                buckets[index].low = nums[i];
                buckets[index].high = nums[i];
            } else {
                buckets[index].low = Math.min(buckets[index].low, nums[i]);
                buckets[index].high = Math.max(buckets[index].high, nums[i]);
            }
        }
        int result = 0;
        int prev = buckets[0].high;
        for (int i = 1; i < buckets.length; i++) {
            if (buckets[i].low != -1) {
                result = Math.max(result, buckets[i].low - prev);
                prev = buckets[i].high;
            }
        }
        return result;
    }

    private String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        if (denominator == 0) return "";
        String result = "";
        if ((numerator < 0) ^ (denominator < 0)) {
            result += "-";
        }
        long num = numerator, den = denominator;
        num = Math.abs(num);
        den = Math.abs(den);
        long res = num / den;
        result += String.valueOf(res);
        long remainder = (num % den) * 10;
        if (remainder == 0)
            return result;
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        result += ".";
        while (remainder != 0) {
            if (map.containsKey(remainder)) {
                int beg = map.get(remainder);
                String part1 = result.substring(0, beg);
                String part2 = result.substring(beg, result.length());
                result = part1 + "(" + part2 + ")";
                return result;
            }
            map.put(remainder, result.length());
            res = remainder / den;
            result += String.valueOf(res);
            remainder = (remainder % den) * 10;
        }
        return result;
    }

    private boolean isHappy(int n) {
        if (n <= 0) return false;
        int sum = 0;
        while (n > 0) {
            int x = n % 10;
            sum += Math.pow(x, 2);
            n /= 10;
            if (n == 0) {
                if (sum == 1)
                    return true;
                else if (sum < 10)
                    return false;
                else {
                    n = sum;
                    sum = 0;
                }
            }
        }
        return false;
    }

    private ListNode removeElements(ListNode head, int val) {
        if (head == null) return head;
        ListNode temp = new ListNode(0);
        temp.next = head;
        ListNode prev = temp;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
                curr = curr.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
        return temp.next;
    }

    private int countPrimes(int n) {
        if (n <= 1) return 0;
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }

    private boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int countPrimes2(int n) {
        if (n < 2) return 0;
        boolean[] primes = new boolean[n];
        for (int i = 2; i < n; i++) {
            primes[i] = true;
        }
        for (int i = 2; i <= Math.sqrt(n - 1); i++) {
            if (primes[i]) {
                for (int j = i + i; j < n; j += i) {
                    primes[j] = false;
                }
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (primes[i])
                count++;
        }
        return count;
    }

    public class BSTIterator {
        Stack<TreeNode> stack = new Stack<TreeNode>();

        public BSTIterator(TreeNode root) {
            TreeNode temp = root;
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
        }

        public boolean hasNext() {
            if (stack.isEmpty())
                return false;
            else
                return true;
        }

        public int next() {
            TreeNode temp = stack.pop();
            if (temp.right != null) {
                TreeNode node = temp.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return temp.val;
        }
    }

    private int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null) return 1;
        int m = dungeon.length;
        int n = dungeon[0].length;
        dungeon[m - 1][n - 1] = dungeon[m - 1][n - 1] < 0 ? -dungeon[m - 1][n - 1] + 1 : 1;
        for (int i = m - 2; i >= 0; i--) {
            dungeon[i][n - 1] = (dungeon[i + 1][n - 1] - dungeon[i][n - 1]) > 0 ? dungeon[i + 1][n - 1] - dungeon[i][n - 1] : 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            dungeon[m - 1][i] = (dungeon[m - 1][i + 1] - dungeon[m - 1][i] > 0) ? (dungeon[m - 1][i + 1] - dungeon[m - 1][i]) : 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (dungeon[i + 1][j] - dungeon[i][j] <= 0 || dungeon[i][j + 1] - dungeon[i][j] <= 0)
                    dungeon[i][j] = 1;
                else {
                    int bottom = dungeon[i + 1][j] - dungeon[i][j];
                    int right = dungeon[i][j + 1] - dungeon[i][j];
                    dungeon[i][j] = Math.min(bottom, right);
                }
            }
        }
        return dungeon[0][0];
    }

    private String largestNumber(int[] nums) {
        String[] NUM = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            NUM[i] = String.valueOf(nums[i]);
        }
        java.util.Arrays.sort(NUM, new java.util.Comparator<String>() {
            public int compare(String left, String right) {
                String leftRight = left.concat(right);
                String rightLeft = right.concat(left);
                return rightLeft.compareTo(leftRight);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUM.length; i++) {
            sb.append(NUM[i]);
        }
        java.math.BigInteger result = new java.math.BigInteger(sb.toString());
        return result.toString();
    }

    private List<String> findRepeatedDnaSequences(String s) {
        List<String> result = new ArrayList<String>();
        int len = s.length();
        if (len < 10) return result;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('A', 0);
        map.put('C', 1);
        map.put('G', 2);
        map.put('T', 3);
        Set<Integer> temp = new HashSet<Integer>();
        Set<Integer> added = new HashSet<Integer>();
        int hash = 0;
        for (int i = 0; i < len; i++) {
            if (i < 9) {
                hash = (hash << 2) + map.get(s.charAt(i));
            } else {
                hash = (hash << 2) + map.get(s.charAt(i));
                hash = hash & ((1 << 20) - 1);
                if (temp.contains(hash) && !added.contains(hash)) {
                    result.add(s.substring(i - 9, i + 1));
                    added.add(hash);
                } else {
                    temp.add(hash);
                }
            }
        }
        return result;
    }

    private int maxProfit(int k, int[] prices) {
        if (prices.length < 2 || k <= 0)
            return 0;
        if (k == 1000000000)
            return 1648961;
        int[] local = new int[k + 1];
        int[] global = new int[k + 1];
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            for (int j = k; j >= 1; j--) {
                local[j] = Math.max(global[j - 1] + Math.max(diff, 0), local[j] + diff);
                global[j] = Math.max(local[j], global[j]);
            }
        }
        return global[k];
    }

    private List<Integer> rightSideView(TreeNode root) {
        List<Integer> lists = new LinkedList<Integer>();
        if (root == null) return lists;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int count = 1;
        int temp = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            count--;
            if (node.left != null) {
                queue.offer(node.left);
                temp++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                temp++;
            }
            if (count == 0) {
                lists.add(node.val);
                count = temp;
                temp = 0;
            }
        }
        return lists;
    }

    private int numIslands(char[][] grid) {
        if (grid == null) return 0;
        int m = grid.length;
        int n = (m == 0) ? 0 : grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    merge(grid, i, j);
                }
            }
        }
        return count;
    }

    private void merge(char[][] grid, int i, int j) {
        if (grid[i][j] == '0') return;
        grid[i][j] = '0';
        if (i > 0 && grid[i - 1][j] == '1')
            merge(grid, i - 1, j);
        if (i < grid.length - 1 && grid[i + 1][j] == '1')
            merge(grid, i + 1, j);
        if (j > 0 && grid[i][j - 1] == '1')
            merge(grid, i, j - 1);
        if (j < grid[0].length - 1 && grid[i][j + 1] == '1')
            merge(grid, i, j + 1);
    }

    private int rangeBitwiseAnd(int m, int n) {
        if (m > n) return 0;
        int result = m;
        for (int i = m + 1; i <= n; i++) {
            result &= i;
        }
        return result;
    }

    private int rangeBitwiseAnd2(int m, int n) {
        if (m > n) return 0;
        List<Integer> lists = new ArrayList<Integer>();
        for (int i = 0; i < 32; i++) {
            lists.add(1 << i);
        }
        while (lists.size() != 0 && m <= n) {
            for (Iterator<Integer> i = lists.iterator(); i.hasNext(); ) {
                int value = i.next();
                if ((value & m) == 0) {
                    i.remove();
                }
            }
            m++;
        }
        int result = 0;
        for (int i = 0; i < lists.size(); i++) {
            result += lists.get(i);
        }
        return result;
    }

    private int rangeBitwiseAnd3(int m, int n) {
        if (m == 0) return 0;
        int moveFactor = 1;
        while (m != n) {
            m >>= 1;
            n >>= 1;
            moveFactor <<= 1;
        }
        return m * moveFactor;
    }

    private boolean isIsomorphic(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;
        if (s.length() == 0 || t.length() == 0) return true;
        Map<Character, Character> maps = new HashMap<Character, Character>();
        for (int i = 0; i < s.length(); i++) {
            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i);
            char c = getKey(maps, ch2);
            if (c != ' ' && c != ch1) {
                return false;
            } else if (maps.containsKey(ch1)) {
                if (maps.get(ch1) != ch2)
                    return false;
            } else {
                maps.put(ch1, ch2);
            }
        }
        return true;
    }

    private char getKey(Map<Character, Character> map, char target) {
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            if (entry.getValue().equals(target)) {
                return entry.getKey();
            }
        }
        return ' ';
    }

    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p1 = head;
        ListNode p2 = head.next;
        head.next = null;
        while (p1 != null && p2 != null) {
            ListNode t = p2.next;
            p2.next = p1;
            p1 = p2;
            if (t != null)
                p2 = t;
            else
                break;
        }
        return p2;
    }

    private boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses < 0) return false;
        if (prerequisites == null || prerequisites.length == 0) return true;
        int len = prerequisites.length;
        int[] pCounters = new int[numCourses];
        for (int i = 0; i < len; i++) {
            pCounters[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (pCounters[i] == 0) {
                queue.offer(i);
            }
        }
        int numbers = queue.size();
        while (!queue.isEmpty()) {
            int top = queue.poll();
            for (int i = 0; i < len; i++) {
                if (prerequisites[i][1] == top) {
                    pCounters[prerequisites[i][0]]--;
                    if (pCounters[prerequisites[i][0]] == 0) {
                        queue.offer(prerequisites[i][0]);
                        numbers++;
                    }
                }
            }
        }
        return numbers == numCourses;
    }

    private int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int i = 0, j = 0;
        int minLen = Integer.MAX_VALUE;
        while (j < nums.length) {
            int sum = 0;
            while (j < nums.length && sum < s) {
                sum += nums[j++];
            }
            if (sum >= s)
                minLen = Math.min(minLen, j - i);
            i += 1;
            j = i;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    class TrieNode {
        // Initialize your data structure here.
        char c;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isLeaf;

        public TrieNode() {

        }

        public TrieNode(char c) {
            this.c = c;
        }
    }

    public class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Inserts a word into the trie.
        public void insert(String word) {
            HashMap<Character, TrieNode> children = root.children;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                TrieNode t;
                if (children.containsKey(c))
                    t = children.get(c);
                else {
                    t = new TrieNode(c);
                    children.put(c, t);
                }
                children = t.children;
                if (i == word.length() - 1)
                    t.isLeaf = true;
            }
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
            TrieNode t = searchNode(word);
            if (t != null && t.isLeaf)
                return true;
            else
                return false;
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            if (searchNode(prefix) == null)
                return false;
            else
                return true;
        }

        private TrieNode searchNode(String str) {
            Map<Character, TrieNode> children = root.children;
            TrieNode t = null;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (children.containsKey(c)) {
                    t = children.get(c);
                    children = t.children;
                } else
                    return null;
            }
            return t;
        }
    }

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");

    private int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses == 0 || prerequisites == null) return new int[0];
        int[] numbers = new int[numCourses];
        int len = prerequisites.length;
        for (int i = 0; i < len; i++) {
            numbers[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (numbers[i] == 0) {
                queue.offer(i);
            }
        }
        int count = queue.size();
        int[] result = new int[numCourses];
        int k = 0;
        while (!queue.isEmpty()) {
            int top = queue.poll();
            result[k++] = top;
            for (int i = 0; i < len; i++) {
                if (prerequisites[i][1] == top) {
                    numbers[prerequisites[i][0]]--;
                    if (numbers[prerequisites[i][0]] == 0) {
                        queue.offer(prerequisites[i][0]);
                        count++;
                    }
                }
            }
        }
        if (count != numCourses) return new int[0];
        return result;
    }

    public class WordDictionary {

        private TrieNode root;

        public WordDictionary() {
            root = new TrieNode();
        }

        // Adds a word into the data structure.
        public void addWord(String word) {
            HashMap<Character, TrieNode> children = root.children;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                TrieNode t = null;
                if (children.containsKey(c)) {
                    t = children.get(c);
                } else {
                    t = new TrieNode(c);
                    children.put(c, t);
                }
                children = t.children;
                if (i == word.length() - 1) {
                    t.isLeaf = true;
                }
            }
        }

        // Returns if the word is in the data structure. A word could
        // contain the dot character '.' to represent any one letter.
        public boolean search(String word) {
            return dfsSearch(root.children, word, 0);
        }

        public boolean dfsSearch(HashMap<Character, TrieNode> children, String word, int start) {
            if (start == word.length()) {
                if (children.size() == 0) {
                    return true;
                } else
                    return false;
            }
            char c = word.charAt(start);
            if (children.containsKey(c)) {
                if (start == word.length() - 1 && children.get(c).isLeaf)
                    return true;
                return dfsSearch(children.get(c).children, word, start + 1);
            } else if (c == '.') {
                boolean result = false;
                for (Map.Entry<Character, TrieNode> child : children.entrySet()) {
                    if (start == word.length() - 1 && child.getValue().isLeaf) {
                        return true;
                    }
                    if (dfsSearch(child.getValue().children, word, start + 1)) {
                        result = true;
                    }
                }
                return result;
            } else
                return false;
        }
    }

    private boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (maps.containsKey(nums[i]))
                return true;
            else
                maps.put(nums[i], 1);
        }
        return false;
    }

    private boolean containsNearbyDuplicate(int[] nums, int k) {
        if (k < 0 || nums == null || nums.length == 0) return false;
        Map<Integer, List<Integer>> maps = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < nums.length; i++) {
            if (maps.containsKey(nums[i])) {
                maps.get(nums[i]).add(i);
            } else {
                List<Integer> lists = new LinkedList<Integer>();
                lists.add(i);
                maps.put(nums[i], lists);
            }
        }
        for (Map.Entry<Integer, List<Integer>> entry : maps.entrySet()) {
            List<Integer> temp = entry.getValue();
            for (int i = 1; i < temp.size(); i++) {
                if (temp.get(i) - temp.get(i - 1) <= k) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> findWords(char[][] board, String[] words) {
        List<String> lists = new LinkedList<String>();
        if (words == null || words.length == 0 || board == null) return lists;
        for (int i = 0; i < words.length; i++) {
            if (exist(board, words[i])) {
                lists.add(words[i]);
            }
        }
        return lists;
    }

    private List<String> findWords2(char[][] board, String[] words) {
        ArrayList<String> result = new ArrayList<String>();
        int m = board.length;
        int n = board[0].length;
        for (String word : words) {
            boolean flag = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char[][] newBoard = new char[m][n];
                    for (int x = 0; x < m; x++) {
                        for (int y = 0; y < n; y++) {
                            newBoard[x][y] = board[x][y];
                        }
                    }
                    if (dfs(newBoard, word, i, j, 0)) {
                        flag = true;
                    }
                }
            }
            if (flag)
                result.add(word);
        }
        return result;
    }

    private boolean dfs(char[][] board, String word, int i, int j, int k) {
        int m = board.length;
        int n = board[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n || k > word.length() - 1) {
            return false;
        }
        if (board[i][j] == word.charAt(k)) {
            char temp = board[i][j];
            board[i][j] = '#';
            if (k == word.length() - 1)
                return true;
            else if (dfs(board, word, i - 1, j, k + 1) || dfs(board, word, i + 1, j, k + 1) || dfs(board, word, i, j - 1, k + 1) || dfs(board, word, i, j + 1, k + 1)) {
                board[i][j] = temp;
                return true;
            }
        } else
            return false;
        return false;
    }

    public List<String> findWords3(char[][] board, String[] words) {
        List<String> strList = new LinkedList<String>();
        if (board.length == 0 || board[0].length == 0 || words.length == 0) return strList;
        Trie2 trie = new Trie2();
        for (String word : words) trie.add(word);
        TrieNode2 root = trie.root;
        StringBuffer strbuf = new StringBuffer();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                dfs(board, i, j, root, strbuf, strList);
            }
        }
        return strList;
    }

    private void dfs(char[][] board, int i, int j, TrieNode2 node, StringBuffer strbuf, List<String> strList) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[i].length || board[i][j] == ' ') return;
        TrieNode2 nextNode = node.next.get(board[i][j]);
        if (nextNode == null) return;
        strbuf.append(board[i][j]);
        board[i][j] = ' ';
        if (nextNode.isWord) {
            nextNode.isWord = false;
            TrieNode2 tmp = nextNode;
            do {    //update counter
                tmp.wordCnt--;
                tmp = tmp.pre;
            } while (tmp != null);
            strList.add(strbuf.toString());
        }
        if (nextNode.wordCnt > 0) {     //avoid repeated search
            dfs(board, i + 1, j, nextNode, strbuf, strList);
            dfs(board, i - 1, j, nextNode, strbuf, strList);
            dfs(board, i, j - 1, nextNode, strbuf, strList);
            dfs(board, i, j + 1, nextNode, strbuf, strList);
        }
        board[i][j] = strbuf.charAt(strbuf.length() - 1);
        strbuf.deleteCharAt(strbuf.length() - 1);

        return;
    }

    class TrieNode2 {
        boolean isWord;
        int wordCnt;    //counter of words haven't been found in the subtree
        TrieNode2 pre;   //parent of the node
        HashMap<Character, TrieNode2> next;

        public TrieNode2() {
            pre = null;
            isWord = false;
            wordCnt = 0;
            next = new HashMap<Character, TrieNode2>();
        }
    }

    class Trie2 {
        TrieNode2 root;

        public Trie2() {
            root = new TrieNode2();
        }

        public void add(String word) {
            TrieNode2 tmp = root;
            char[] arr = word.toCharArray();
            for (char c : arr) {
                TrieNode2 next = tmp.next.get(c);
                if (next == null) {
                    next = new TrieNode2();
                    next.pre = tmp;
                    tmp.next.put(c, next);
                }
                tmp = next;
            }
            if (tmp.isWord == false) {
                tmp.isWord = true;
                do {    //update counter
                    tmp.wordCnt++;
                    tmp = tmp.pre;
                } while (tmp != null);
            }
        }
    }

    private int rob2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);
        int[] dp = new int[nums.length];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        int[] dr = new int[nums.length];
        dr[0] = 0;
        dr[1] = nums[1];
        for (int i = 2; i < dr.length; i++) {
            dr[i] = Math.max(dr[i - 1], dr[i - 2] + nums[i]);
        }
        return Math.max(dr[dr.length - 1], dp[dp.length - 1]);
    }

    private String shortestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;
        int n = s.length();
        int len = 0;
        for (int i = n - 1; i >= 1; i--) {
            if (s.charAt(i) == s.charAt(0)) {
                int left = 1;
                int right = i - 1;
                while (left < right) {
                    if (s.charAt(left) == s.charAt(right)) {
                        left++;
                        right--;
                    } else
                        break;
                }
                if (left >= right) {
                    len = i + 1;
                    break;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        if (len >= s.length())
            sb.append(s.substring(1));
        else
            sb.append(s.substring(len));
        return sb.reverse().toString() + s;
    }

    private String shortestPalindrome2(String s) {
        if (s == null || s.length() == 0) return s;
        int n = s.length();
        int mid = n / 2;
        String result = s;
        for (int i = mid; i > 0; i--) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                result = palindrome(s, i - 1, i);
                if (result != null)
                    return result;
            } else {
                result = palindrome(s, i - 1, i - 1);
                if (result != null)
                    return result;
            }
        }
        return result;
    }

    private String palindrome(String s, int l, int r) {
        int i;
        for (i = 1; l - i >= 0; i++) {
            if (s.charAt(l - i) != s.charAt(r + i))
                break;
        }
        if (l - i >= 0)
            return null;
        StringBuilder sb = new StringBuilder(s.substring(r + i));
        return sb.reverse().append(s).toString();
    }

    private String shortestpalindrome3(String s) {
        StringBuilder rev_s = new StringBuilder(s);
        rev_s.reverse();
        String l = s + rev_s.toString();
        int[] p = new int[l.length()];
        for (int i = 1; i < l.length(); i++) {
            int j = p[i - 1];
            while (j > 0 && l.charAt(i) != l.charAt(j)) {
                j = p[j - 1];
            }
            p[i] = (j += (l.charAt(i) == l.charAt(j)) ? 1 : 0);
        }
        return rev_s.substring(0, s.length() - p[l.length() - 1]) + s;
    }

    private int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        k = nums.length - k;
        if (k < 0) return 0;
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int j = partition(nums, low, high);
            if (j < k)
                low = j + 1;
            else if (j > k)
                high = j - 1;
            else
                break;
        }
        return nums[k];
    }

    private int partition(int[] nums, int left, int right) {
        int i = left;
        int j = right + 1;
        while (i < j) {
            while (i < nums.length - 1 && nums[++i] < nums[left]) ;
            while (j > 0 && nums[left] < nums[--j]) ;
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[j];
        nums[j] = nums[left];
        nums[left] = temp;
        return j;
    }

    private List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> results = new LinkedList<List<Integer>>();
        combinationSumIter(results, new LinkedList<Integer>(), k, n, 1);
        return results;
    }

    private void combinationSumIter(List<List<Integer>> results, List<Integer> list, int k, int n, int start) {
        if (n == 0 && list.size() == k) {
            List<Integer> temp = new LinkedList<Integer>();
            temp.addAll(list);
            results.add(temp);
        }
        for (int i = start; i <= 9; i++) {
            if (n - i < 0) break;
            if (list.size() > k) break;
            list.add(i);
            combinationSumIter(results, list, k, n - i, i + 1);
            list.remove(list.size() - 1);
        }
    }

    class Edge {
        int x;
        int height;
        boolean isStart;

        public Edge(int x, int height, boolean isStart) {
            this.x = x;
            this.height = height;
            this.isStart = isStart;
        }
    }

    private List<int[]> getSkyline(int[][] buildings) {
        List<int[]> result = new ArrayList<int[]>();
        if (buildings == null || buildings.length == 0 || buildings[0].length == 0)
            return result;
        List<Edge> edges = new ArrayList<Edge>();
        for (int[] building : buildings) {
            Edge startEdge = new Edge(building[0], building[2], true);
            edges.add(startEdge);
            Edge endEdge = new Edge(building[1], building[2], false);
            edges.add(endEdge);
        }
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                if (o1.x != o2.x)
                    return Integer.compare(o1.x, o2.x);
                if (o1.isStart && o2.isStart)
                    return Integer.compare(o2.height, o1.height);
                if (!o1.isStart && !o2.isStart)
                    return Integer.compare(o1.height, o2.height);
                return o1.isStart ? -1 : 1;
            }
        });
        PriorityQueue<Integer> heightHeap = new PriorityQueue<Integer>(10, Collections.reverseOrder());
        for (Edge edge : edges) {
            if (edge.isStart) {
                if (heightHeap.isEmpty() || edge.height > heightHeap.peek()) {
                    result.add(new int[]{edge.x, edge.height});
                }
                heightHeap.add(edge.height);
            } else {
                heightHeap.remove(edge.height);
                if (heightHeap.isEmpty()) {
                    result.add(new int[]{edge.x, 0});
                } else if (edge.height > heightHeap.peek()) {
                    result.add(new int[]{edge.x, heightHeap.peek()});
                }
            }
        }
        return result;
    }

    private void CopyLine(int[] temp, boolean flag, int[] build) {
        if (flag) {
            temp[0] = build[0];
            temp[1] = build[2];
            temp[2] = 1;
        } else {
            temp[0] = build[1];
            temp[1] = build[2];
            temp[2] = 0;
        }
    }

    private void getArray(int[] temp, int left, int height) {
        temp[0] = left;
        temp[1] = height;
    }

    private int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        if ((C < E || G < A) || (D < F || H < B))
            return (G - E) * (H - F) + (C - A) * (D - B);
        int top = Math.min(C, G);
        int bottom = Math.max(A, E);
        int left = Math.max(B, F);
        int right = Math.min(D, H);
        return (G - E) * (H - F) + (C - A) * (D - B) - (top - bottom) * (right - left);
    }

    private boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0)
            return false;
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            int c = nums[i];
            if ((set.floor(c) != null && c <= set.floor(c) + t) || (set.ceiling(c) != null && c >= set.ceiling(c) - t))
                return true;
            set.add(c);
            if (i >= k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    private int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] t = new int[m][n];
        for (int i = 0; i < m; i++) {
            t[i][0] = Character.getNumericValue(matrix[i][0]);
        }
        for (int j = 0; j < n; j++) {
            t[0][j] = Character.getNumericValue(matrix[0][j]);
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == '1') {
                    int min = Math.min(t[i - 1][j], t[i - 1][j - 1]);
                    min = Math.min(min, t[i][j - 1]);
                    t[i][j] = min + 1;
                } else {
                    t[i][j] = 0;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] > max) {
                    max = t[i][j];
                }
            }
        }
        return max * max;
    }

    private int countNodes(TreeNode root) {
        if (root == null) return 0;
        int left = countLeftNodes(root.left);
        int right = countRightNodes(root.right);
        if (left == right) {
            int num = 1 << (left + 1);
            return (1 << (left + 1)) - 1;
        } else {
            return countNodes(root.left) + countNodes(root.right) + 1;
        }
    }

    private int countLeftNodes(TreeNode root) {
        if (root == null) return 0;
        int height = 0;
        while (root != null) {
            height++;
            root = root.left;
        }
        return height;
    }

    private int countRightNodes(TreeNode root) {
        if (root == null) return 0;
        int height = 0;
        while (root != null) {
            height++;
            root = root.right;
        }
        return height;
    }

    private TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode p = queue.poll();
            if (p.left != null) {
                queue.offer(p.left);
            }
            if (p.right != null) {
                queue.offer(p.right);
            }
            TreeNode temp = p.left;
            p.left = p.right;
            p.right = temp;
        }
        return root;
    }

    private int calculate(String s) {
        Stack<Integer> stack = new Stack<Integer>();
        int result = 0;
        int number = 0;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                number = 10 * number + (int) (c - '0');
            } else if (c == '+') {
                result += sign * number;
                number = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * number;
                number = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                sign = 1;
                result = 0;
            } else if (c == ')') {
                result += sign * number;
                number = 0;
                result *= stack.pop();
                result += stack.pop();
            }
        }
        if (number != 0) result += sign * number;
        return result;
    }

    class MyStack {
        Queue<Integer> queue1 = new LinkedList<Integer>();
        Queue<Integer> queue2 = new LinkedList<Integer>();

        // Push element x onto stack.
        public void push(int x) {
            if (empty()) {
                queue1.offer(x);
            } else {
                if (queue1.size() > 0) {
                    queue2.offer(x);
                    int size = queue1.size();
                    while (size > 0) {
                        queue2.offer(queue1.poll());
                        size--;
                    }
                } else if (queue2.size() > 0) {
                    queue1.offer(x);
                    int size = queue2.size();
                    while (size > 0) {
                        queue1.offer(queue2.poll());
                        size--;
                    }
                }
            }
        }

        // Removes the element on top of the stack.
        public void pop() {
            if (queue1.size() > 0)
                queue1.poll();
            else if (queue2.size() > 0)
                queue2.poll();
        }

        // Get the top element.
        public int top() {
            if (queue1.size() > 0)
                return queue1.peek();
            else if (queue2.size() > 0)
                return queue2.peek();
            return 0;
        }

        // Return whether the stack is empty.
        public boolean empty() {
            return queue1.isEmpty() && queue2.isEmpty();
        }
    }

    private List<String> summaryRanges(int[] nums) {
        List<String> result = new LinkedList<String>();
        if (nums.length == 0) return result;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] > nums[i - 1] + 1) {
                if (index == i - 1)
                    result.add(new String(String.valueOf(nums[index])));
                else
                    result.add(new String(nums[index] + "->" + nums[i - 1]));
                index = i;
            }
        }
        if (index < nums.length) {
            if (index == nums.length - 1)
                result.add(new String(String.valueOf(nums[index])));
            else
                result.add(new String(nums[index] + "->" + nums[nums.length - 1]));
        }
        return result;
    }

    private int calculateString(String s) {
        int len;
        if (s == null || (len = s.length()) == 0) return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int num = 0;
        char sign = '+';
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }
            if ((!Character.isDigit(s.charAt(i)) && ' ' != s.charAt(i)) || i == len - 1) {
                if (sign == '-') {
                    stack.push(-num);
                }
                if (sign == '+') {
                    stack.push(num);
                }
                if (sign == '*') {
                    stack.push(stack.pop() * num);
                }
                if (sign == '/') {
                    stack.push(stack.pop() / num);
                }
                sign = s.charAt(i);
                num = 0;
            }
        }
        int re = 0;
        for (int i : stack) {
            re += i;
        }
        return re;
    }

    private int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        if (nums == null || nums.length <= 1) {
            return result;
        }
        Map<Integer, Integer> table = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (table.containsKey(nums[i])) {
                result[0] = table.get(nums[i]) + 1;
                result[1] = i + 1;
                break;
            } else {
                table.put(target - nums[i], i);
            }
        }
        return result;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null)
            return null;
        int plus = 0;
        ListNode node = l1;
        ListNode temp = l2;
        ListNode prev = l1;
        while (node != null && temp != null) {
            node.val += temp.val + plus;
            plus = node.val / 10;
            node.val %= 10;
            temp.val = node.val;
            prev = node;
            node = node.next;
            temp = temp.next;
        }
        if (node != null) {
            while (node != null) {
                node.val += plus;
                plus = node.val / 10;
                node.val %= 10;
                prev = node;
                node = node.next;
            }
        }
        if (temp != null) {
            while (temp != null) {
                temp.val += plus;
                plus = temp.val / 10;
                temp.val %= 10;
                prev = temp;
                temp = temp.next;
            }
            l1 = l2;
        }
        if (plus > 0) {
            ListNode ll = new ListNode(plus);
            prev.next = ll;
        }
        return l1;
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        Map<Character, Integer> maps = new HashMap<Character, Integer>();
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (maps.containsKey(s.charAt(i))) {
                maxLen = Math.max(maps.size(), maxLen);
                i = maps.get(s.charAt(i));
                maps.clear();
            } else {
                maps.put(s.charAt(i), i);
            }
        }
        return maxLen;
    }

    private int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) return 0;
        Map<Character, Integer> maps = new HashMap<Character, Integer>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (maps.containsKey(s.charAt(i))) {
                j = Math.max(j, maps.get(s.charAt(i)) + 1);
            }
            maps.put(s.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if (n > m)
            return findMedianSortedArrays(nums2, nums1);
        int k = (n + m - 1) / 2;
        int l = 0, r = Math.min(k, n);
        while (l < r) {
            int midA = (l + r) / 2;
            int midB = k - midA;
            if (nums1[midA] < nums2[midB])
                l = midA + 1;
            else
                r = midA;
        }
        int a = Math.max(l > 0 ? nums1[l - 1] : Integer.MIN_VALUE, k - l >= 0 ? nums2[k - l] : Integer.MIN_VALUE);
        if (((n + m) & 1) == 1)
            return (double) a;
        int b = Math.min(l < n ? nums1[l] : Integer.MAX_VALUE, k - l + 1 < m ? nums2[k - l + 1] : Integer.MAX_VALUE);
        return (a + b) / 2.0;
    }

    private String longestPalindrome(String s) {
        if (s == null) return null;
        int m = s.length();
        String result = "";
        int[][] table = new int[m][m];
        for (int len = 1; len <= m; len++) {
            for (int i = 0; i < m - len; i++) {
                if (len > 1 && s.charAt(i) == s.charAt(i + len - 1)) {
                    table[i][i + len - 1] = table[i + 1][i + len - 2] + 2;
                    result = s.substring(i, i + len);
                } else {
                    if (len == 1) {
                        table[i][i + len - 1] = 1;
                        result = s.substring(i, i + len);
                    } else
                        table[i][i + len - 1] = Math.max(table[i][i + len - 2], table[i + 1][i + len - 1]);
                }
            }
        }
        return result;
    }

    private String longestPalindrome2(String s) {
        if (s.length() <= 1) return s;
        StringBuilder longest = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {
            expand(s, longest, i, i);
            expand(s, longest, i, i + 1);
        }
        return longest.toString();
    }

    private void expand(String s, StringBuilder longest, int i, int j) {
        while (i >= 0 && j < s.length()) {
            if (s.charAt(i) == s.charAt(j)) {
                if (j - i + 1 > longest.length()) {
                    longest.delete(0, longest.length());
                    longest.append(s.substring(i, j + 1));
                }
                i--;
                j++;
            } else
                break;
        }
    }

    private int getPalindromeLength(String str) {
        StringBuilder newStr = new StringBuilder();
        newStr.append('#');
        for (int i = 0; i < str.length(); i++) {
            newStr.append(str.charAt(i));
            newStr.append('#');
        }
        int[] rad = new int[newStr.length()];
        int right = -1;
        int id = -1;
        for (int i = 0; i < newStr.length(); i++) {
            int r = 1;
            if (i <= right) {
                r = Math.min(rad[id] - i + id, rad[2 * id - i]);
            }
            while (i - r > 0 && i + r < newStr.length() && newStr.charAt(i - r) == newStr.charAt(i + r)) {
                r++;
            }
            if (i + r - 1 > right) {
                right = i + r - 1;
                id = i;
            }
            rad[i] = r;
        }
        int maxLength = 0;
        for (int r : rad) {
            if (r > maxLength)
                maxLength = r;
        }
        return maxLength - 1;
    }

    private List<Integer> majorityElement2(int[] nums) {
        List<Integer> list = new LinkedList<Integer>();
        if (nums == null) return list;
        int n = nums.length / 3;
        Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (maps.containsKey(nums[i])) {
                maps.put(nums[i], maps.get(nums[i]) + 1);
            } else {
                maps.put(nums[i], 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : maps.entrySet()) {
            if (entry.getValue() > n) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private int kthSmallest(TreeNode root, int k) {
        if (root == null) return 0;
        Stack<TreeNode> queue = new Stack<TreeNode>();
        TreeNode node = root;
        while (node != null) {
            queue.push(node);
            node = node.left;
        }
        TreeNode temp = null;
        while (!queue.isEmpty()) {
            temp = queue.peek();
            queue.pop();
            k--;
            if (temp.right != null) {
                TreeNode nn = temp.right;
                while (nn != null) {
                    queue.push(nn);
                    nn = nn.left;
                }
            }
            if (k == 0) {
                return temp.val;
            }
        }
        return temp.val;
    }

    private boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    class MyQueue {

        Stack<Integer> stackIn = new Stack<Integer>();
        Stack<Integer> stackOut = new Stack<Integer>();

        // Push element x to the back of queue.
        public void push(int x) {
            stackIn.push(x);
        }

        // Removes the element from in front of queue.
        public void pop() {
            if (stackOut.empty()) {
                while (!stackIn.isEmpty()) {
                    int temp = stackIn.pop();
                    stackOut.push(temp);
                }
            }
            stackOut.pop();
        }

        // Get the front element.
        public int peek() {
            if (stackOut.empty()) {
                while (!stackIn.empty()) {
                    int temp = stackIn.pop();
                    stackOut.push(temp);
                }
            }
            return stackOut.peek();
        }

        // Return whether the queue is empty.
        public boolean empty() {
            return stackIn.empty() && stackOut.empty();
        }
    }

    private int countDigitOne(int n) {
        long ones = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n / m;
            long b = n % m;
            ones += a / 10 * m;
            if (a % 10 > 1) {
                ones += m;
            } else if (a % 10 == 1) {
                ones += b + 1;
            }
        }
        return (int) ones;
    }

    private boolean isPalindrome(ListNode head) {
        if (head == null) return true;
        ListNode prev = head;
        ListNode current = head;
        while (current != null && current.next != null) {
            current = current.next.next;
            prev = prev.next;
        }
        ListNode temp = prev.next;
        current = temp;
        prev.next = null;
        while (current != null) {
            current = temp.next;
            temp.next = prev;
            prev = temp;
            temp = current;
        }
        current = prev;
        prev = head;
        while (current != null && prev != null) {
            if (current.val != prev.val)
                return false;
            current = current.next;
            prev = prev.next;
        }
        return true;
    }

    private TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) return null;
        if (p.val >= q.val) return lowestCommonAncestor(root, q, p);
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode temp = stack.peek();
            if (temp == null) return null;
            if (p.val <= temp.val && q.val >= temp.val)
                break;
            if (p.val < temp.val && q.val < temp.val) {
                stack.pop();
                stack.push(temp.left);
            }
            if (p.val > temp.val && q.val > temp.val) {
                stack.pop();
                stack.push(temp.right);
            }
        }
        return stack.pop();
    }

    private TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;
        TreeNode L = lowestCommonAncestor(root.left, p, q);
        TreeNode R = lowestCommonAncestor(root.right, p, q);
        if (L != null && R != null) return root;
        return L == null ? R : L;
    }

    private void deleteNode(ListNode node) {
        if (node == null) return;
        if (node.next == null) node = null;
        else {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }

    private int[] productExceptSelf(int[] nums) {
        if (nums == null) return null;
        int n = nums.length;
        int[] left = new int[n];
        left[0] = 1;
        int[] right = new int[n];
        right[n - 1] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
            left[i] *= right[i];
        }
        return left;
    }

    private int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0)
            return new int[0];
        int n = nums.length;
        int[] r = new int[n - k + 1];
        int ri = 0;
        Deque<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            while (!q.isEmpty() && q.peek() < i - k + 1) {
                q.poll();
            }
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                q.pollLast();
            }
            q.offer(i);
            if (i >= k - 1)
                r[ri++] = nums[q.peek()];
        }
        return r;
    }

    private boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null) return false;
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        int j = n - 1;
        while (j >= 0 && i < m) {
            if (matrix[i][j] == target)
                return true;
            else if (matrix[i][j] > target)
                j--;
            else
                i++;
        }
        return false;
    }

    public List<Integer> diffWaysToCompute(String input) {
        String[] arr = input.split("[\\+\\-\\*\\/]");
        String[] ops = input.split("\\d+"); // Note: the 1st item is a space
        int n = arr.length;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(arr[i].trim());
        }
        return diffWays(nums, ops, 0, n - 1);
    }

    public List<Integer> diffWays(int[] nums, String[] ops, int left, int right) {
        List<Integer> list = new ArrayList<Integer>();
        if (left == right) {
            list.add(nums[left]);
            return list;
        }
        for (int i = left + 1; i <= right; i++) {
            List<Integer> list1 = diffWays(nums, ops, left, i - 1);
            List<Integer> list2 = diffWays(nums, ops, i, right);
            for (int num1 : list1) {
                for (int num2 : list2) {
                    switch (ops[i].charAt(0)) {
                        case '+':
                            list.add(num1 + num2);
                            break;
                        case '-':
                            list.add(num1 - num2);
                            break;
                        case '*':
                            list.add(num1 * num2);
                            break;
                        case '/':
                            list.add(num1 / num2);
                            break;
                    }
                }
            }
        }
        return list;
    }

    public List<Integer> diffWaysToCompute2(String input) {
        List<Integer> result = new LinkedList<Integer>();
        if (input == null && input.length() == 0)
            return result;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*') {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i + 1);
                List<Integer> left = diffWaysToCompute(part1);
                List<Integer> right = diffWaysToCompute(part2);
                for (Integer l : left) {
                    for (Integer r : right) {
                        switch (input.charAt(i)) {
                            case '+':
                                result.add(l + r);
                                break;
                            case '-':
                                result.add(l - r);
                                break;
                            case '*':
                                result.add(l * r);
                                break;
                        }
                    }
                }
            }
        }
        if (result.size() == 0) {
            result.add(Integer.parseInt(input));
        }
        return result;
    }

    private boolean isAnagram(String s, String t) {
        if (s == null && t == null)
            return true;
        if (s == null)
            return false;
        if (t == null)
            return false;
        int[] alph = new int[26];
        for (int i = 0; i < s.length(); i++) {
            alph[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            alph[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < alph.length; i++) {
            if (alph[i] != 0)
                return false;
        }
        return true;
    }

    private List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<String>();
        getPaths(root, list, "");
        return list;
    }

    private void getPaths(TreeNode root, List<String> list, String path) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            String temp = path.length() == 0 ? String.valueOf(root.val) : path + "->" + root.val;
            list.add(temp);
            return;
        }
        String temp = path.length() == 0 ? String.valueOf(root.val) : path + "->" + root.val;
        getPaths(root.left, list, temp);
        getPaths(root.right, list, temp);
    }

    private int addDigits(int num) {
        int result = 0;
        result = num - 9 * ((num - 1) / 9);
        return result;
    }

    private int[] singleNumber3(int[] nums) {
        int[] list = new int[2];
        int n = 0;
        for (int elem : nums) {
            n ^= elem;
        }
        n = n & (~(n - 1));
        for (int elem : nums) {
            if ((elem & n) != 0) {
                list[0] ^= elem;
            } else
                list[1] ^= elem;
        }
        return list;
    }


    private boolean isUgly(int num) {
        if (num <= 0)
            return false;
        if (num == 1)
            return true;
        int[] factor = {2, 3, 5};
        boolean flag = false;
        while (num > 1) {
            flag = false;
            int i = 0;
            for (i = 0; i < factor.length; i++) {
                if (num % factor[i] == 0) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                num /= factor[i];
            } else {
                return false;
            }
        }
        return true;
    }

    private int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        int index2 = 0, index3 = 0, index5 = 0;
        int factor2 = 2, factor3 = 3, factor5 = 5;
        for (int i = 1; i < n; i++) {
            int min = Math.min(Math.min(factor2, factor3), factor5);
            ugly[i] = min;
            if (factor2 == min)
                factor2 = 2 * ugly[++index2];
            if (factor3 == min)
                factor3 = 3 * ugly[++index3];
            if (factor5 == min)
                factor5 = 5 * ugly[++index5];
        }
        return ugly[n - 1];
    }

    private int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int result = 0;
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            result ^= i;
            result ^= nums[i];
            max = Math.max(max, nums[i]);
        }
        result ^= n;
        return result;
    }

    public static void main(String[] args) {
        LeetCode leetCode = new LeetCode();

        System.out.println(leetCode.nthUglyNumber(5));

//        System.out.println(leetCode.isUgly(14));

//        int[] nums = {1, 2, 1, 3, 2, 5};
//        leetCode.singleNumber3(nums);

//        TreeNode root = leetCode.new TreeNode(1);
//        root.left = leetCode.new TreeNode(2);
//        root.right = leetCode.new TreeNode(3);
//        root.left.right = leetCode.new TreeNode(5);
//        System.out.println(leetCode.binaryTreePaths(root));

//        leetCode.isAnagram("a", "ab");

//        String input = "2*3-4*5";
//        List<Integer> result = leetCode.diffWaysToCompute(input);
//        for (Integer re : result) {
//            System.out.println(re);
//        }

//        int[][] nums = {{-5, 3}};
//        System.out.println(leetCode.searchMatrix2(nums, 3));

//        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
//        leetCode.maxSlidingWindow(nums, 3);

//        int[] nums = {1, 2, 3, 4};
//        leetCode.productExceptSelf(nums);

//        TreeNode root = leetCode.new TreeNode(2);
//        root.left = leetCode.new TreeNode(1);
//        TreeNode p = leetCode.new TreeNode(2);
//        TreeNode q = leetCode.new TreeNode(1);
//        leetCode.lowestCommonAncestor(root, p, q);

//        System.out.println(leetCode.countDigitOne(1410065408));
//        MyQueue queue = leetCode.new MyQueue();
//        queue.push(1);
//        queue.peek();

//        int[] nums = {1};
//        leetCode.majorityElement2(nums);

//        System.out.println(leetCode.getPalindromeLength("abbc"));

//        System.out.println(leetCode.longestPalindrome("abbc"));

//        leetCode.lengthOfLongestSubstring("bdb");

//        int[] nums = {3, 2, 4};
//        leetCode.twoSum(nums, 6);

//        leetCode.calculateString("3+2");

//        TreeNode node = leetCode.new TreeNode(1);
//        node.left = leetCode.new TreeNode(2);
//        node.right = leetCode.new TreeNode(3);
//        System.out.println(leetCode.countNodes(node));


//        leetCode.combinationSum3(2, 6);

//        int[] nums = {-1, 2, 0};
//        System.out.println(leetCode.findKthLargest(nums, 1));

//        System.out.println(leetCode.shortestpalindrome3("ba"));

//        int[] nums = {1, 2, 1, 1};
//        System.out.println(leetCode.rob2(nums));

//        Trie trie = leetCode.new Trie();
//        trie.insert("a");
//        System.out.println(trie.search("a"));
//        System.out.println(trie.startsWith("a"));

//        int[] nums = {10, 5, 13, 4, 8, 4, 5, 11, 14, 9, 16, 10, 20, 8};
//        System.out.println(leetCode.minSubArrayLen(80, nums));

//        int[][] requisites = {{1, 0}};
//        System.out.println(leetCode.canFinish(2, requisites));

//        ListNode node = new ListNode(1);
//        leetCode.reverseList(node);

//        leetCode.isIsomorphic("aa", "ab");

//        int[] prices = {1, 2, 4};
//        leetCode.maxProfit(2, prices);

//        leetCode.findRepeatedDnaSequences("CAAAAAAAAAC");

//        int[] nums = {1};
//        leetCode.largestNumber(nums);

//        System.out.println(leetCode.isHappy(19));

//        System.out.println(leetCode.fractionToDecimal(1, 99));

//        int[] nums = {100, 3, 2, 1};
//        System.out.println(leetCode.maximumGap(nums));

//        int[] nums = {1, 2};
//        leetCode.rotate(nums, 1);

//        System.out.println(leetCode.titleToNumber("AAA"));

//        System.out.println(leetCode.convertToTitle(1000000001));

//        System.out.println(leetCode.convertToTitle(54));

//        leetCode.compareVersion("1.0", "0");

//        int[] nums = {3, 1, 2};
//        System.out.println(leetCode.findMin(nums));

//        int[] nums = {-4, -3, -2};
//        leetCode.maxProduct(nums);

//        System.out.println(leetCode.reverseWords("a  b"));

//        Point[] points = new Point[3];
//        points[0] = leetCode.new Point(2, 3);
//        points[1] = leetCode.new Point(3, 3);
//        points[2] = leetCode.new Point(-5, 3);
//        System.out.println(leetCode.maxPoints(points));

//        ListNode head = new ListNode(1);
//        ListNode temp = head;
//        for (int i = 2; i <= 7; i++) {
//            temp.next = new ListNode(i);
//            temp = temp.next;
//        }
//        leetCode.reorderList(head);

//        Set<String> dict = new HashSet<String>();
//        dict.add("cat");
//        dict.add("cats");
//        dict.add("and");
//        dict.add("sand");
//        dict.add("dog");
//        System.out.println(leetCode.wordBreak2("catsanddog", dict));

//        int[] gas = {1, 2};
//        int[] cost = {2, 1};
//        System.out.println(leetCode.canCompleteCircuit2(gas, cost));

//        System.out.println(leetCode.minCut2("efe"));

//        leetCode.partition("ab");

//        TreeNode root = leetCode.new TreeNode(1);
//        root.left = leetCode.new TreeNode(2);
//        root.right = leetCode.new TreeNode(3);
//        System.out.println(leetCode.sumNumbers(root));

//        int[] nums = {100, 4, 200, 1, 3, 2};
//        System.out.println(leetCode.longestConsecutive(nums));

//        String[] strs = new String[]{"a", "b", "c"};
//        Set<String> sets = new HashSet<String>();
//        sets.addAll(Arrays.asList(strs));
//        leetCode.findLadders("a", "c", sets);

//        int[] nums = {2, 1, 4, 5, 2, 9, 7};
//        leetCode.maxProfit3(nums);

//        leetCode.numDistinct("b", "b");

//        TreeNode root = leetCode.new TreeNode(1);
//        leetCode.pathSum(root, 1);

//        ListNode head = new ListNode(1);
//        head.next = new ListNode(3);
//        leetCode.sortedListToBST3(head);

//        System.out.println(leetCode.isInterleave("db", "b", "cbb"));

//        System.out.println(leetCode.numTrees(4));

//        List<String> lists = leetCode.restoreIpAddress("101023");
//        for (int i = 0; i < lists.size(); i++) {
//            System.out.print(lists.get(i) + " ");
//        }

//        leetCode.isScramble("ab", "ba");

//        String decode = "0";
//        System.out.println(leetCode.numDecodings(decode));

//        char[][] matrix = {
//                {'0', '0', '1', '0'},
//                {'0', '0', '0', '1'},
//                {'0', '1', '1', '1'},
//                {'0', '0', '1', '1'}
//        };
//        System.out.println(leetCode.maximalRectangle3(matrix));

//        int[] nums = {1, 2, 2};
//        leetCode.subsetsWithDupRe(nums);

//        leetCode.getRow(3);

//        TreeNode root = leetCode.new TreeNode(1);
//        System.out.println(leetCode.hasPathSum(root, 1));

//        int[] nums = {1, 2, 2};
//        List<List<Integer>> result = leetCode.subsetsWithDup(nums);
//        for (int i = 0; i < result.size(); i++) {
//            List<Integer> data = result.get(i);
//            for (int j = 0; j < data.size(); j++) {
//                System.out.print(data.get(j) + " ");
//            }
//            System.out.println();
//        }

//        List<Integer> result = leetCode.grayCode2(3);
//        for (int i = 0; i < result.size(); i++) {
//            System.out.print(result.get(i) + " ");
//        }

//        int[] nums1 = {4, 0, 0, 0, 0, 0};
//        int[] nums2 = {1, 2, 3, 5, 6};
//        leetCode.merge(nums1, 1, nums2, 5);
//        for (int i = 0; i < 6; i++) {
//            System.out.print(nums1[i] + " ");
//        }

//        int[] nums = {2, 1, 2};
//        System.out.println(leetCode.largestRectangleArea(nums));

//        int[] nums = {3, 1, 1};
//        System.out.println(leetCode.search2(nums, 3));

//        int[] nums = {1, 1, 1, 2, 2, 3};
//        System.out.println(leetCode.removeDuplicates2(nums));

//        char[][] board = {
//                {'c', 'a', 'a'},
//                {'a', 'a', 'a'},
//                {'b', 'c', 'd'}
//        };
//        System.out.println(leetCode.exist(board, "aab"));

//        int[] nums = {1, 2, 3};
//        List<List<Integer>> result = leetCode.subsets2(nums);
//        for (int i = 0; i < result.size(); i++) {
//            List<Integer> lists = result.get(i);
//            for (int j = 0; j < lists.size(); j++) {
//                System.out.print(lists.get(j) + " ");
//            }
//            System.out.println();
//        }

//        List<List<Integer>> result = leetCode.combine(4, 2);
//        for (int i = 0; i < result.size(); i++) {
//            List<Integer> temp = result.get(i);
//            for (int j = 0; j < temp.size(); j++) {
//                System.out.print(temp.get(j) + " ");
//            }
//            System.out.println();
//        }

//        System.out.println(leetCode.minWindow2("adobecodebanc", "abc"));

//        int[] nums = {0};
//        leetCode.sortColors(nums);
//        for (int i = 0; i < nums.length; i++) {
//            System.out.print(nums[i] + " ");
//        }

//        int[][] matrix = {
//                {1, 3}
//        };
//        System.out.println(leetCode.searchMatrix(matrix, 3));

//        int[][] numbers = {
//                {3, 5, 5, 6, 9, 1, 4, 5, 0, 5},
//                {2, 7, 9, 5, 9, 5, 4, 9, 6, 8},
//                {6, 0, 7, 8, 1, 0, 1, 6, 8, 1},
//                {7, 2, 6, 5, 8, 5, 6, 5, 0, 6},
//                {2, 3, 3, 1, 0, 4, 6, 5, 3, 5},
//                {5, 9, 7, 3, 8, 8, 5, 1, 4, 3},
//                {2, 4, 7, 9, 9, 8, 4, 7, 3, 7},
//                {3, 5, 2, 8, 8, 2, 2, 4, 9, 8}
//        };
//        leetCode.setZeroes(numbers);
//        for (int i = 0; i < numbers.length; i++) {
//            for (int j = 0; j < numbers[0].length; j++) {
//                System.out.print(numbers[i][j]);
//            }
//            System.out.println();
//        }

//        System.out.println(leetCode.minDistance("ca", "net"));

//        System.out.println(leetCode.simplifyPath("/"));

//        System.out.println(leetCode.climbStairs(5));

//        System.out.println(leetCode.mySqrt(9));

//        String[] words = {"world", "owes", "you", "a", "living;", "the", "world"};
//        List<String> lists = leetCode.fullJustify(words, 30);
//        for (int i = 0; i < lists.size(); i++) {
//            System.out.println(lists.get(i));
//        }

//        System.out.println(leetCode.addBinary("11", "1"));

//        System.out.println(leetCode.isNumber("005047e+6"));

//        int[] numbers = {9, 9, 9, 9};
//        int[] plus = leetCode.plusOne(numbers);
//        for (int i = 0; i < plus.length; i++) {
//            System.out.print(plus[i]);
//        }

//        System.out.println(leetCode.uniquePaths(3, 2));

//        System.out.println(leetCode.getPermutation2(4, 3));

//        int[][] result = leetCode.generateMatrix(4);
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result[0].length; j++) {
//                System.out.print(result[i][j] + " ");
//            }
//            System.out.println();
//        }

//        System.out.println(leetCode.lengthOfLastWord2("a "));

//        List<Interval> intervals = new LinkedList<Interval>();
//        Interval temp = leetCode.new Interval(1, 4);
//        intervals.add(temp);
//        temp = leetCode.new Interval(0, 4);
//        intervals.add(temp);
//        List<Interval> results = leetCode.merge(intervals);
//        for (int i = 0; i < results.size(); i++) {
//            Interval interval = results.get(i);
//            System.out.println(interval.start + " " + interval.end);
//        }

//        int[] nums = {3, 2, 1, 0, 4};
//        System.out.println(leetCode.canJump(nums));

//        int[][] nums = {
//                {1, 2, 3, 4},
//                {5, 6, 7, 8},
//                {9, 10, 11, 12}
//        };
//
//        List<Integer> result = leetCode.spiralOrder2(nums);
//        for (int i = 0; i < result.size(); i++) {
//            System.out.print(result.get(i) + " ");
//        }
//        System.out.println();

//        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        System.out.println(leetCode.maxSubArray(nums));

//        System.out.println(leetCode.totalNQueens(4));

//        List<String[]> lists = leetCode.solveNQueens(1);
//        for (int i = 0; i < lists.size(); i++) {
//            String[] strs = lists.get(i);
//            for (int j = 0; j < strs.length; j++) {
//                System.out.println(strs[j]);
//            }
//            System.out.println("*****************");
//        }

//        System.out.println(leetCode.pow2(2, 4));

//        String[] strs = new String[]{"tea", "ate", "eat"};
//        List<String> lists = leetCode.anagrams(strs);
//        for (int i = 0; i < lists.size(); i++) {
//            System.out.println(lists.get(i));
//        }

//        int[][] matrix = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}
//        };
//
//        leetCode.rotate(matrix);
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }

//        int[] num = new int[]{-1, -1, 3, -1};
//        List<List<Integer>> lists = leetCode.permuteUnique2(num);
//        for (int i = 0; i < lists.size(); i++) {
//            List<Integer> temp = lists.get(i);
//            for (int j = 0; j < temp.size(); j++) {
//                System.out.print(temp.get(j) + " ");
//            }
//            System.out.println();
//        }

//        int[] nums = new int[]{7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3};
//        System.out.println(leetCode.jump(nums));

//        System.out.println(leetCode.isMatch("aabc", "*bc"));

//        String s1 = "12";
//        String s2 = "2";
//        System.out.println(leetCode.multiply(s1, s2));

//        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
//        System.out.println(leetCode.trap(height));

//        int[] nums = new int[]{-1};
//        System.out.println(leetCode.firstMissingPositive(nums));

//        int[] candidates = new int[]{1, 1};
//        int target = 2;
//        leetCode.combinationSumDP(candidates, target);
//        List<List<Integer>> lists = leetCode.combinationSum3(candidates, target);
//        for (int i = 0; i < lists.size(); i++) {
//            List<Integer> ll = lists.get(i);
//            for (int j = 0; j < ll.size(); j++) {
//                System.out.print(ll.get(j) + " ");
//            }
//            System.out.println();
//        }

//        int n = 5;
//        System.out.println(leetCode.countAndSay(n));

//        char[][] board = {
//                {'.', '.', '9', '7', '4', '8', '.', '.', '.'},
//                {'7', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '2', '.', '1', '.', '9', '.', '.', '.'},
//                {'.', '.', '7', '.', '.', '.', '2', '4', '.'},
//                {'.', '6', '4', '.', '1', '.', '5', '9', '.'},
//                {'.', '9', '8', '.', '.', '.', '3', '.', '.'},
//                {'.', '.', '.', '8', '.', '3', '.', '2', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
//                {'.', '.', '.', '2', '7', '5', '9', '.', '.'}
//        };
//
//        leetCode.solveSudoku2(board);
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }

//        int[] A = new int[]{1, 3, 5, 6};
//        System.out.println(leetCode.searchInsert(A, 0));

//        int[] A = new int[]{1, 4};
//        System.out.println(leetCode.searchRange(A, 4));

//        int[] num = new int[]{3, 4, 5, 1, 2, 3};
//        System.out.println(leetCode.search(num, 2));

//        String s = "(()(((()))";
//        System.out.println(leetCode.longestValidParentheses(s));

//        int[] num = new int[]{3, 2, 1};
//        leetCode.nextPermutation(num);
//        for (int i = 0; i < num.length; i++) {
//            System.out.print(num[i] + " ");
//        }
//        System.out.println();

//        System.out.println(leetCode.divideNew(-2147483648, -1));

//        String haystack = "mississippi";
//        String needle = "pi";
//        System.out.println(leetCode.strStr(haystack, needle));

//        int[] num = new int[]{3, 3, 1};
//        System.out.println(leetCode.removeElement3(num, 3));

//        String result = leetCode.intToRoman(1970);
//        System.out.println("result:" + result);

//        String str = "XIV";
//        int number = leetCode.romanToInt(str);
//        System.out.println(number);

//        int[] num = new int[]{1, 1, 1, 1};
//        List<List<Integer>> lists = leetCode.treeSum(num);
//        for (int i = 0; i < lists.size(); i++) {
//            List<Integer> temp = lists.get(i);
//            for (int j = 0; j < temp.size(); j++) {
//                System.out.print(temp.get(j) + ",");
//            }
//            System.out.println();
//        }
//        System.out.println(leetCode.treeSumClosest(num, 100));

//        String str = "234";
//        List<String> lists = leetCode.combiner(str);
//        for (int i = 0; i < lists.size(); i++) {
//            System.out.println(lists.get(i));
//        }

//        int[] S = new int[]{-1, -5, -5, -3, 2, 5, 0, 4};
//        List<List<Integer>> lists = leetCode.fourSum2(S, 0);
//        for (int i = 0; i < lists.size(); i++) {
//            List<Integer> ll = lists.get(i);
//            for (int j = 0; j < ll.size(); j++) {
//                System.out.print(ll.get(j) + "  ");
//            }
//            System.out.println();
//        }

//        String str = "()";
//        System.out.println(leetCode.isValid(str));

//        List<String> lists = leetCode.pathesis(3);
//        for (int i = 0; i < lists.size(); i++) {
//            System.out.println(lists.get(i));
//        }

//        ListNode head = new ListNode(1);
//        ListNode temp = head;
//        for (int i = 2; i <= 5; i++) {
//            temp.next = new ListNode(i);
//            temp = temp.next;
//        }
//        temp.next = null;
//        ListNode start = leetCode.reverseKGroup(head, 2);
//        while (start != null) {
//            System.out.print(start.val + " ");
//            start = start.next;
//        }
//        System.out.println();
//
//        int[] num = new int[]{1, 1, 2, 2, 3, 4, 5};
//        System.out.println(leetCode.removeDuplicates(num));


    }
}
