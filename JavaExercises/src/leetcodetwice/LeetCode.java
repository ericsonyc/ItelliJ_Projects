package leetcodetwice;

import java.util.*;

/**
 * Created by ericson on 2015/8/30 0030.
 */
public class LeetCode {

    private int[] twoSum(int[] nums, int target) {
        int[] index = new int[2];
        Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (maps.containsKey(nums[i])) {
                index[0] = maps.get(nums[i]) + 1;
                index[1] = i + 1;
                break;
            } else {
                maps.put(target - nums[i], i);
            }
        }
        return index;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        int plus = 0;
        ListNode cur1 = l1;
        ListNode cur2 = l2;
        while (cur1.next != null && cur2.next != null) {
            int temp = cur1.val + cur2.val + plus;
            cur1.val = temp % 10;
            plus = temp / 10;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        if (cur1.next == null) {
            int temp = cur1.val + cur2.val + plus;
            cur1.val = temp % 10;
            plus = temp / 10;
            cur1.next = cur2.next;
            cur2 = cur2.next;
            while (plus > 0 && cur2 != null) {
                temp = cur2.val + plus;
                cur2.val = temp % 10;
                plus = temp / 10;
                cur1 = cur2;
                cur2 = cur2.next;
            }
            if (plus > 0 && cur2 == null) {
                cur1.next = new ListNode(plus);
            }
        } else {
            int temp = cur1.val + cur2.val + plus;
            cur1.val = temp % 10;
            plus = temp / 10;
            cur1 = cur1.next;
            ListNode prev = cur1;
            while (plus > 0 && cur1 != null) {
                temp = cur1.val + plus;
                cur1.val = temp % 10;
                plus = temp / 10;
                prev = cur1;
                cur1 = cur1.next;
            }
            if (plus > 0 && cur1 == null) {
                prev.next = new ListNode(plus);
            }
        }
        return l1;
    }

    private int lengthOfLongestString(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int maxLength = 0;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            int index = buffer.indexOf(String.valueOf(s.charAt(i)));
            if (index == -1) {
                buffer.append(s.charAt(i));
            } else {
                maxLength = Math.max(maxLength, buffer.length());
                buffer.delete(0, index + 1);
                buffer.append(s.charAt(i));
            }
        }
        maxLength = Math.max(maxLength, buffer.length());
        return maxLength;
    }

    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0)
            return nums2.length % 2 == 0 ? (nums2[nums2.length / 2 - 1] + nums2[nums2.length / 2]) / 2.0 : nums2[nums2.length / 2];
        if (nums2 == null || nums2.length == 0)
            return nums1.length % 2 == 0 ? (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0 : nums1[nums1.length / 2];
        int m = nums1.length;
        int n = nums2.length;
        if (m > n)
            return findMedianSortedArrays(nums2, nums1);
        if ((m + n) % 2 == 1) {
            return findKthNumber(nums1, 0, nums2, 0, (m + n) / 2 + 1);
        } else {
            return (findKthNumber(nums1, 0, nums2, 0, (m + n) / 2) + findKthNumber(nums1, 0, nums2, 0, (m + n) / 2 + 1)) / 2.0;
        }
    }

    private int findKthNumber(int[] nums1, int start1, int[] nums2, int start2, int k) {
        if (start1 == nums1.length)
            return nums2[start2 + k - 1];
        if (k == 1) {
            if (start1 == nums1.length)
                return nums2[start2];
            if (start2 == nums2.length)
                return nums1[start1];
            return Math.min(nums1[start1], nums2[start2]);
        }
        int temp = Math.min(k / 2, nums1.length);
        int b = k - temp;
        if (nums1[start1 + temp - 1] < nums2[start2 + b - 1]) {
            start1 += temp;
            k -= temp;
            return findKthNumber(nums1, start1, nums2, start2, k);
        } else if (nums1[start1 + temp - 1] > nums2[start2 + b - 1]) {
            start2 += b;
            k -= b;
            return findKthNumber(nums1, start1, nums2, start2, k);
        } else
            return nums1[start1 + temp - 1];
    }

    private String longestPalindrome(String s) {
        if (s == null || s.length() == 0)
            return s;
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        String result = String.valueOf(s.charAt(0));
        int max = 1;
        int count = 1;
        int i = 0, j = 1;
        while (count < n) {
            if (s.charAt(i) == s.charAt(j)) {
                if (i == 0) {
                    dp[i][j] = dp[i][j - 1] == 0 ? 1 : dp[i][j - 1] + 1;
                } else {
                    dp[i][j] = dp[i + 1][j - 1] == 0 ? 1 : dp[i + 1][j - 1] + 2;
                }
                if (max < dp[i][j]) {
                    max = dp[i][j];
                    result = s.substring(i, j + 1);
                }
            }
            i = i + 1;
            j = i + count;
            if (j >= n) {
                i = 0;
                count++;
                j = i + count;
            }
        }
        return result;
    }

    private String longestPalindrome2(String s) {
        int len = s.length(), max = 1, ss = 0;
        boolean[][] flag = new boolean[len][len];
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len; j++)
                if (i >= j)
                    flag[i][j] = true;
                else flag[i][j] = false;
        for (int j = 1; j < len; j++)
            for (int i = 0; i < j; i++) {
                if (s.charAt(i) == s.charAt(j)) {
                    flag[i][j] = flag[i + 1][j - 1];
                    if (flag[i][j] == true && j - i + 1 > max) {
                        max = j - i + 1;
                        ss = i;

                    }
                } else flag[i][j] = false;

            }
        return s.substring(ss, max + ss);
    }

    private String convert(String s, int numRows) {
        if (s == null || s.length() == 0 || numRows <= 0)
            return s;
        StringBuffer[] buffer = new StringBuffer[numRows];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = new StringBuffer();
        }
        int length = 0;
        while (length < s.length()) {
            for (int i = 0; i < numRows && length < s.length(); i++) {
                buffer[i].append(s.charAt(length++));
            }
            for (int i = numRows - 2; i > 0 && length < s.length(); i--) {
                buffer[i].append(s.charAt(length++));
            }
        }
        for (int i = 1; i < numRows; i++) {
            buffer[0].append(buffer[i]);
        }
        return buffer[0].toString();
    }

    private int reverse(int x) {
        boolean flag = true;
        if (x < 0)
            flag = false;
        long t = Math.abs((long) x);
        long result = 0;
        while (t > 0) {
            long temp = t % 10;
            result = 10 * result + temp;
            t = t / 10;
        }
        int rr = (int) result;
        if (rr != result)
            return 0;
        return flag ? rr : -rr;
    }

    private int myAtoi(String str) {
        if (str == null || str.length() < 1)
            return 0;
        str = str.trim();
        int count = 0;
        boolean flag = true;
        char ch = str.charAt(0);
        if (ch == '+') {
            count++;
        } else if (ch == '-') {
            flag = false;
            count++;
        }
        long result = 0;
        while (count < str.length()) {
            if (Character.isDigit(str.charAt(count))) {
                result = result * 10 + Integer.parseInt(String.valueOf(str.charAt(count++)));
            } else
                break;
            if (flag && result > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            else if (!flag && -result < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;
        }
        return flag ? (int) result : -(int) result;
    }

    private boolean isPalindrome(int x) {
        int count = 1;
        if (x < 0)
            return false;
        while ((int) (x / (Math.pow(10, count))) != 0) {
            count++;
        }
        int i = 1;
        while (i <= count / 2) {
            if (x % (int) Math.pow(10, i) / (int) Math.pow(10, i - 1) != x / ((int) Math.pow(10, count - i)) % 10) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isMatch(String s, String p) {
        return isSubMatch(s, 0, p, 0);
    }

    private boolean isSubMatch(String s, int i, String p, int j) {
        if (i == s.length() || j == p.length()) {
            if (i == s.length() && j == p.length())
                return true;
            if (i == s.length()) {
                if (j + 1 < p.length() && p.charAt(j + 1) == '*')
                    return isSubMatch(s, i, p, j + 2);
            }
            return false;
        }
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            if (p.charAt(j) != s.charAt(i) && p.charAt(j) != '.') {
                return isSubMatch(s, i, p, j + 2);
            } else {
                boolean flag = false;
                while (i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.')) {
                    flag = isSubMatch(s, i, p, j + 2);
                    if (flag) {
                        break;
                    } else {
                        i++;
                    }
                }
                return flag ? flag : isSubMatch(s, i, p, j + 2);
            }
        }
        if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') {
            return isSubMatch(s, i + 1, p, j + 1);
        }
        return false;
    }

    private int maxArea(int[] height) {
        if (height == null || height.length == 0)
            return 0;
        int global = 0;
        int i = 0, j = height.length - 1;
        while (i < j) {
            global = Math.max(global, Math.min(height[i], height[j]) * (j - i));
            if (height[i] <= height[j])
                i++;
            else
                j--;
        }
        return global;
    }

    void setDigit(StringBuffer res, int n, char a, char b, char c) {
        if (n < 4) {
            for (int i = 0; i < n; ++i) {
                res.append(a);
            }
        }
        if (n == 4) {
            res.append(a);
            res.append(b);
        }
        if (n == 5) res.append(b);
        if (n > 5 && n < 9) {
            res.append(b);
            for (int i = 5; i < n; ++i) {
                res.append(a);
            }
        }
        if (n == 9) {
            res.append(a);
            res.append(c);
        }
    }

    String intToRoman(int num) {
        int t = (num / 1000) % 10, h = (num / 100) % 10, d = (num / 10) % 10, n = num % 10;
        StringBuffer res = new StringBuffer();
        setDigit(res, t, 'M', '?', '?');
        setDigit(res, h, 'C', 'D', 'M');
        setDigit(res, d, 'X', 'L', 'C');
        setDigit(res, n, 'I', 'V', 'X');
        return res.toString();
    }

    private int romanToInt(String s) {
        int i = 0;
        int result = 0;
        while (i < s.length()) {
            if (s.charAt(i) == 'M') {
                result += 1000;
                i++;
            }
            if (i < s.length() && s.charAt(i) == 'D') {
                result += 500;
                i++;
            }
            if (i < s.length() && s.charAt(i) == 'C') {
                i++;
                if (i < s.length() && s.charAt(i) == 'D') {
                    result += 400;
                    i++;
                } else if (i < s.length() && s.charAt(i) == 'M') {
                    result += 900;
                    i++;
                } else {
                    result += 100;
                }
            }
            if (i < s.length() && s.charAt(i) == 'L') {
                i++;
                result += 50;
            }
            if (i < s.length() && s.charAt(i) == 'X') {
                i++;
                if (i < s.length() && s.charAt(i) == 'L') {
                    result += 40;
                    i++;
                } else if (i < s.length() && s.charAt(i) == 'C') {
                    result += 90;
                    i++;
                } else {
                    result += 10;
                }
            }
            if (i < s.length() && s.charAt(i) == 'V') {
                i++;
                result += 5;
            }
            if (i < s.length() && s.charAt(i) == 'I') {
                i++;
                if (i < s.length() && s.charAt(i) == 'X') {
                    result += 9;
                    i++;
                } else if (i < s.length() && s.charAt(i) == 'V') {
                    result += 4;
                    i++;
                } else
                    result += 1;
            }
        }
        return result;
    }

    private String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        int i = 1;
        String str = strs[0];
        while (i < strs.length) {
            str = getPrefix(str, strs[i++]);
        }
        return str;
    }

    private String getPrefix(String str1, String str2) {
        int i = 0, j = 0;
        StringBuffer result = new StringBuffer();
        while (i < str1.length() && j < str2.length()) {
            if (str1.charAt(i) == str2.charAt(j)) {
                result.append(str1.charAt(i));
                i++;
                j++;
            } else
                break;
        }
        return result.toString();
    }

    private List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int low = i + 1;
                int high = nums.length - 1;
                int sum = 0 - nums[i];
                while (low < high) {
                    if (nums[low] + nums[high] < sum) {
                        low++;
                    } else if (nums[low] + nums[high] > sum) {
                        high--;
                    } else {
                        list.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        while (low < high && nums[low] == nums[low + 1]) low++;
                        while (low < high && nums[high] == nums[high - 1]) high--;
                        low++;
                        high--;
                    }
                }
            }
        }
        return list;
    }

    private int threeSumCloset(int[] nums, int target) {
        int sum = Integer.MAX_VALUE;
        Arrays.sort(nums);
        int result = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int low = i + 1;
                int high = nums.length - 1;
                int closet = target - nums[i];
                while (low < high) {
                    if (Math.abs(nums[low] + nums[high] - closet) < sum) {
                        result = nums[low] + nums[high] + nums[i];
                        sum = Math.abs(nums[low] + nums[high] - closet);
                    }
                    if (nums[low] + nums[high] < closet) {
                        low++;
                    } else {
                        high--;
                    }
                }
            }
        }
        return result;
    }

    private List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<String>();
        if (digits == null || digits.length() == 0)
            return result;
        Map<Integer, String> maps = new HashMap<Integer, String>();
        maps.put(2, "abc");
        maps.put(3, "def");
        maps.put(4, "ghi");
        maps.put(5, "jkl");
        maps.put(6, "mno");
        maps.put(7, "pqrs");
        maps.put(8, "tuv");
        maps.put(9, "wxyz");
        getCombination(digits, 0, result, "", maps);
        return result;
    }

    private void getCombination(String digits, int start, List<String> ll, String combination, Map<Integer, String> maps) {
        if (start == digits.length()) {
            ll.add(combination);
            return;
        }
        int number = Integer.parseInt(String.valueOf(digits.charAt(start)));
        String temp = maps.get(number);
        for (int i = 0; i < temp.length(); i++) {
            getCombination(digits, start + 1, ll, combination + temp.charAt(i), maps);
        }
    }

    private List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i == 0 || nums[i] != nums[i - 1])
                for (int j = i + 1; j < nums.length - 2; j++) {
                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        int sum = target - nums[i] - nums[j];
                        int k = j + 1, t = nums.length - 1;
                        while (k < t) {
                            if (nums[k] + nums[t] < sum)
                                k++;
                            else if (nums[k] + nums[t] > sum)
                                t--;
                            else {
                                list.add(Arrays.asList(nums[i], nums[j], nums[k], nums[t]));
                                k++;
                                t--;
                                while (k < t && nums[k] == nums[k - 1]) k++;
                                while (k < t && nums[t] == nums[t + 1]) t--;
                            }
                        }
                    }
                }
        }
        return list;
    }

    private ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode cur = head;
        ListNode h = pre;
        while (cur != null && n > 0) {
            cur = cur.next;
            n--;
        }
        while (cur != null) {
            pre = pre.next;
            cur = cur.next;
        }
        if (pre.next != null)
            pre.next = pre.next.next;
        return h.next;
    }

    private boolean isVaild(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            } else if (s.charAt(i) == ')' || s.charAt(i) == '}' || s.charAt(i) == ']') {
                if (!stack.isEmpty()) {
                    char ch = stack.pop();
                    if ((ch == '(' && s.charAt(i) == ')') || (ch == '{' && s.charAt(i) == '}') || (ch == '[' && s.charAt(i) == ']')) {
                        continue;
                    } else
                        return false;
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            if (l1 == null)
                return l2;
            if (l2 == null)
                return l1;
        }
        ListNode head = new ListNode(-1);
        ListNode cur = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
                cur = cur.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
                cur = cur.next;
            }
        }
        if (l1 != null) {
            cur.next = l1;
        } else if (l2 != null) {
            cur.next = l2;
        }
        return head.next;
    }

    private List<String> generateParenthese(int n) {
        List<String> list = new ArrayList<String>();
        if (n <= 0)
            return list;
        String[] str = {"(", ")"};
        getParenthese(list, str, "", 2 * n);
        return list;
    }

    private void getParenthese(List<String> list, String[] strs, String kuohao, int height) {
        if (height == 0) {
            if (judgeString(kuohao, height)) {
                list.add(kuohao);
            }
            return;
        }

        for (int i = 0; i < strs.length; i++) {
            if (judgeString(kuohao, height)) {
                String temp = kuohao + strs[i];
                getParenthese(list, strs, temp, height - 1);
            }
        }
    }

    private boolean judgeString(String kuohao, int n) {
        int countleft = 0;
        int countright = 0;
        for (int i = 0; i < kuohao.length(); i++) {
            if (kuohao.charAt(i) == '(')
                countleft++;
            else if (kuohao.charAt(i) == ')')
                countright++;
            if (countleft < countright || countleft > (n + kuohao.length()) / 2)
                return false;
        }
        return true;
    }

    private List<String> generateParenthese2(int n) {
        List<String> list = new ArrayList<String>();
        addingpar(list, "", n, 0);
        return list;
    }

    private void addingpar(List<String> list, String str, int n, int m) {
        if (n == 0 && m == 0) {
            list.add(str);
            return;
        }
        if (m > 0) {
            addingpar(list, str + ")", n, m - 1);
        }
        if (n > 0) {
            addingpar(list, str + "(", n - 1, m + 1);
        }
    }

    private ListNode mergeKList(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        ListNode head = new ListNode(-1);
        ListNode[] points = new ListNode[lists.length];
        for (int i = 0; i < lists.length; i++) {
            points[i] = lists[i];
        }
        ListNode cur = head;
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] != null)
                numbers.add(i);
        }
        while (numbers.size() > 0) {
            int index = 0;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < numbers.size(); i++) {
                if (points[numbers.get(i)] != null && points[numbers.get(i)].val < min) {
                    min = points[numbers.get(i)].val;
                    index = numbers.get(i);
                }
            }
            cur.next = points[index];
            if (points[index] != null)
                points[index] = points[index].next;
            cur = cur.next;
            for (int i = 0; i < numbers.size(); i++) {
                if (points[numbers.get(i)] == null) {
                    numbers.remove(i);
                }
            }
        }
        return head.next;
    }

    private ListNode mergeKLists2(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(20, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                ListNode temp = lists[i];
                while (temp != null) {
                    queue.offer(temp);
                    temp = temp.next;
                }
            }
        }
        ListNode head = new ListNode(-1);
        ListNode cur = head;
        while (!queue.isEmpty()) {
            cur.next = queue.poll();
            cur = cur.next;
        }
        return head.next;
    }

    private ListNode mergeKLists3(ListNode[] lists) {
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < lists.length; i++) {
            if (lists[0] != null) {
                numbers.add(i);
            }
        }
        if (numbers.size() == 0)
            return null;
        ListNode temp = lists[numbers.get(0)];
        numbers.remove(0);
        while (numbers.size() > 0) {
            int second = numbers.get(0);
            numbers.remove(0);
            temp = mergeTwoLists(temp, lists[second]);
        }
        return temp;
    }

    private ListNode mergeKLists4(ListNode[] lists) {
        Queue<ListNode> heap = new PriorityQueue<ListNode>(21, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        ListNode head = new ListNode(0), tail = head;
        for (ListNode node : lists)
            if (node != null)
                heap.offer(node);
        while (!heap.isEmpty()) {
            tail.next = heap.poll();
            tail = tail.next;
            if (tail.next != null) heap.offer(tail.next);
        }
        return head.next;
    }

    private ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode start = new ListNode(0);
        start.next = head;
        ListNode pre = start;
        ListNode cur = head.next;
        while (cur != null) {
            pre.next.next = cur.next;
            cur.next = pre.next;
            pre.next = cur;
            pre = cur.next;
            if (pre != null && pre.next != null)
                cur = pre.next.next;
            else
                cur = null;
        }
        return start.next;
    }

    private ListNode reverseKGroup(ListNode head, int k) {
        if (head == null)
            return head;
        ListNode root = new ListNode(0);
        root.next = head;
        ListNode prev = root;
        ListNode cur = prev.next;
        int t = k - 1;
        while (cur != null) {
            if (cur != prev.next) {
                while (cur != null && t > 0) {
                    head.next = cur.next;
                    cur.next = prev.next;
                    prev.next = cur;
                    cur = head.next;
                    t--;
                }
                if (t > 0) {
                    cur = prev.next.next;
                    head = prev.next;
                    while (cur != null) {
                        head.next = cur.next;
                        cur.next = prev.next;
                        prev.next = cur;
                        cur = head.next;
                    }
                    break;
                } else {
                    prev = head;
                    cur = prev.next;
                    head = cur;
                    t = k - 1;
                }
            } else {
                cur = cur.next;
            }
        }
        return root.next;
    }

    private int removeDuplicate(int[] nums) {
        int length = nums.length;
        if (length == 0) return 0;
        int start = nums[0];
        int count = 0;
        for (int i = 1; i < nums.length; ) {
            while (i < nums.length && nums[i] == start) {
                i++;
                length--;
            }
            if (i < nums.length) {
                start = nums[i];
                nums[++count] = start;
                i++;
            }
        }
        return length;
    }

    private int removeElement(int[] nums, int val) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int length = 0;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            while (left < nums.length && nums[left] != val) {
                left++;
                length++;
            }
            while (right >= 0 && nums[right] == val)
                right--;
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
        return length;
    }

    private int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0)
            return 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[count++] = nums[i];
            }
        }
        return count;
    }

    private int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.length() == 0)
            return 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                int count = i + 1;
                while (count < haystack.length() && count - i < needle.length() && haystack.charAt(count) == needle.charAt(count - i)) {
                    count++;
                }
                if (count == i + needle.length()) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int strStr2(String haystack, String needle) {
        if (haystack == null || needle == null || needle.length() == 0)
            return 0;
        int[] next = new int[needle.length()];
        for (int i = 1; i < needle.length(); i++) {
            if (needle.charAt(i) == needle.charAt(next[i - 1])) {
                next[i] = next[i - 1] + 1;
            } else {
                next[i] = 0;
            }
        }
        for (int i = needle.length() - 1; i > 0; i--) {
            next[i] = next[i - 1];
        }
        next[0] = -1;
        int i = 0;
        int j = 0;
        while (i <= haystack.length() - needle.length()) {
            while (j < needle.length() && i < haystack.length() && haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            if (j == needle.length())
                return i - needle.length();
            j = next[j];
            if (j == -1) {
                i++;
                j = 0;
            }
        }
        return -1;
    }

    private int divide(int dividend, int divisor) {
        long left = dividend;
        long right = divisor;
        int sign = 1;
        if (left < 0 && right < 0) {
            left = -left;
            right = -right;
        } else if (left < 0) {
            sign = -1;
            left = -left;
        } else if (right < 0) {
            sign = -1;
            right = -right;
        }
        int count = 0;
        while (left > right) {
            left -= right;
            count++;
        }
        return sign > 0 ? count : -count;
    }

    private int divide2(int dividend, int divisor) {
        long res = 0;
        int flag = 1;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0))
            flag = -1;
        long divid = Math.abs((long) dividend);
        long divi = Math.abs((long) divisor);
        long temp = 0;
        while (divi <= divid) {
            long cnt = 1;
            temp = divi;
            while ((temp <<= 1) <= divid) {
                cnt <<= 1;
            }
            res += cnt;
            divid -= (temp >> 1);
        }
        res = res * flag;
        if (res > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else if (res < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int) res;
    }

    private List<Integer> findSubstring(String s, String[] words) {
        List<Integer> list = new ArrayList<Integer>();
        if (s == null || words == null || words.length == 0) {
            return list;
        }
        List<String> maps = new LinkedList<String>();
        int length = words[0].length();
        for (int i = 0; i < words.length; i++) {
            maps.add(words[i]);
        }
        Collections.sort(maps);
        List<String> temp = new LinkedList<String>();
        int total = length * words.length;
        for (int i = 0; i <= s.length() - total; i++) {
            for (int j = 0; j < words.length; j++) {
                temp.add(s.substring(i + j * length, i + j * length + length));
            }
            Collections.sort(temp);
            if (maps.equals(temp)) {
                list.add(i);
            }
            temp.clear();
        }
        return list;
    }

    private boolean isEqual(PriorityQueue<String> queue1, PriorityQueue<String> queue2) {
        Iterator<String> iter1 = queue1.iterator();
        Iterator<String> iter2 = queue2.iterator();
        while (iter1.hasNext()) {
            String str1 = iter1.next();
            String str2 = iter2.next();
            if (!str1.equals(str2)) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> result = new ArrayList<Integer>();
        int size = words[0].length();
        if (words.length == 0 || words[0].isEmpty() || words[0].length() > s.length())
            return result;
        Map<String, Integer> hist = new HashMap<String, Integer>();
        for (String w : words) {
            hist.put(w, !hist.containsKey(w) ? 1 : hist.get(w) + 1);
        }
        for (int i = 0; i + size * words.length <= s.length(); i++) {
            if (hist.containsKey(s.substring(i, i + size))) {
                Map<String, Integer> currHist = new HashMap<String, Integer>();
                for (int j = 0; j < words.length; j++) {
                    String word = s.substring(i + j * size, i + (j + 1) * size);
                    currHist.put(word, !currHist.containsKey(word) ?
                            1 : currHist.get(word) + 1);
                }
                if (currHist.equals(hist)) result.add(i);
            }
        }
        return result;
    }

    private <T> void print(List<T> list) {
        for (T ll : list) {
            System.out.println(ll);
        }
    }

    private void nextPermutation(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return;
        }
        int index = n - 1;
        while (index > 0) {
            if (nums[index - 1] < nums[index])
                break;
            index--;
        }
        if (index == 0) {
            reverseSort(nums, 0, n - 1);
            return;
        } else {
            int val = nums[index - 1];
            int j = n - 1;
            while (j >= index) {
                if (nums[j] > val)
                    break;
                j--;
            }
            swap(nums, j, index - 1);
            reverseSort(nums, index, n - 1);
            return;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = 0;
        temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverseSort(int[] nums, int start, int end) {
        if (start > end)
            return;
        for (int i = start; i <= (end + start) / 2; i++) {
            swap(nums, i, start + end - i);
        }
    }

    private int longestValidParentheses(String s) {
        if (s == null || s.length() == 0)
            return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int max = 0;
        int left = -1;
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == '(') stack.push(j);
            else {
                if (stack.isEmpty()) left = j;
                else {
                    stack.pop();
                    if (stack.isEmpty()) max = Math.max(max, j - left);
                    else
                        max = Math.max(max, j - stack.peek());
                }
            }
        }
        return max;
    }

    private int longestValidParentheses2(String s) {
        if (s.length() <= 1) return 0;
        int curMax = 0;
        int[] longest = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    longest[i] = i - 2 > 0 ? longest[i - 2] + 2 : 2;
                    curMax = Math.max(longest[i], curMax);
                } else {
                    if (i - longest[i - 1] - 1 >= 0 && s.charAt(i - longest[i - 1] - 1) == '(') {
                        longest[i] = longest[i - 1] + 2 + ((i - longest[i - 1] - 2 >= 0) ? longest[i - longest[i - 1] - 2] : 0);
                        curMax = Math.max(longest[i], curMax);
                    }
                }
            }
        }
        return curMax;
    }

    private int longestValidParenthese3(String s) {
        if (s.length() <= 1) return 0;
        int curMax = 0;
        int[] longest = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')' && i - longest[i - 1] - 1 >= 0 && s.charAt(i - longest[i - 1] - 1) == '(') {
                longest[i] = longest[i - 1] + 2 + ((i - longest[i - 1] - 2 >= 0) ? longest[i - longest[i - 1] - 2] : 0);
                curMax = Math.max(curMax, longest[i]);
            }
        }
        return curMax;
    }

    private int search(int[] nums, int target) {
        if (nums == null || nums.length <= 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] >= nums[left]) {
                if (nums[middle] == target) {
                    return middle;
                } else if (nums[middle] < target) {
                    left = middle + 1;
                } else {
                    if (target >= nums[left])
                        right = middle - 1;
                    else
                        left = middle + 1;
                }
            } else {
                if (nums[middle] == target)
                    return middle;
                else if (nums[middle] > target) {
                    right = middle - 1;
                } else {
                    if (target <= nums[right])
                        left = middle + 1;
                    else
                        right = middle - 1;
                }
            }
        }
        return -1;
    }

    private int search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] > nums[right])
                left = middle + 1;
            else
                right = middle;
        }
        int ro = left;
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int middle = (left + (right - left) / 2 + ro) % nums.length;
            if (nums[middle] == target)
                return middle;
            else if (nums[middle] < target)
                left = middle + 1;
            else
                right = middle - 1;
        }
        return -1;
    }

    private int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0)
            return result;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target) {
                result[0] = middle;
                right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target) {
                result[1] = middle;
                left = middle + 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return result;
    }

    private int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (nums[middle] == target) {
                return middle;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

    private void solveSudoku(char[][] board) {
        if (board == null || board.length == 0)
            return;
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValidStr(board, i, j, c)) {
                            board[i][j] = c;
                            if (solve(board))
                                return true;
                            else
                                board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidStr(char[][] board, int i, int j, char c) {
        for (int row = 0; row < 9; row++) {
            if (board[row][j] == c) {
                return false;
            }
        }

        for (int col = 0; col < 9; col++) {
            if (board[i][col] == c)
                return false;
        }
        for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {
            for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
                if (board[row][col] == c)
                    return false;
            }
        }
        return true;
    }

    private boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (!isParticallyValid(board, i, 0, i, 8)) return false;
            if (!isParticallyValid(board, 0, i, 8, i)) return false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!isParticallyValid(board, i * 3, j * 3, i * 3 + 2, j * 3 + 2))
                    return false;
            }
        }
        return true;
    }

    private boolean isParticallyValid(char[][] board, int x1, int y1, int x2, int y2) {
        Set singleSet = new HashSet();
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (board[i][j] != '.')
                    if (!singleSet.add(board[i][j]))
                        return false;
            }
        }
        return true;
    }

    private String countAndSay(int n) {
        StringBuilder builder = new StringBuilder("1");
        while (--n > 0) {
            int size = builder.length();
            int left = 0;
            while (left < size) {
                int count = 1;
                char ch = builder.charAt(left++);
                while (left < size && ch == builder.charAt(left)) {
                    count++;
                    left++;
                }
                builder.append(count + String.valueOf(ch));
            }
            builder.delete(0, size);
        }
        return builder.toString();
    }

    private List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ret = new LinkedList<List<Integer>>();
        Arrays.sort(candidates);
        recurse(new ArrayList<Integer>(), target, candidates, 0, ret);
        return ret;
    }

    private void recurse(List<Integer> list, int target, int[] candidates, int index, List<List<Integer>> ret) {
        if (target == 0) {
            ret.add(list);
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            int newTarget = target - candidates[i];
            if (newTarget >= 0) {
                List<Integer> copy = new ArrayList<Integer>(list);
                copy.add(candidates[i]);
                recurse(copy, newTarget, candidates, i, ret);
            } else
                break;
        }
    }

    private List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        combination(candidates, 0, target, new ArrayList<Integer>(), result);
        return result;
    }

    private void combination(int[] candidates, int index, int target, List<Integer> list, List<List<Integer>> result) {
        if (target == 0) {
            result.add(list);
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1])
                continue;
            int newTarget = target - candidates[i];
            if (newTarget >= 0) {
                List<Integer> temp = new ArrayList<Integer>(list);
                temp.add(candidates[i]);
                combination(candidates, i + 1, newTarget, temp, result);
            } else
                break;
        }
    }

    private int firstMissingPositive(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            if (nums[i] == i + 1 || nums[i] <= 0 || nums[i] > nums.length) i++;
            else if (nums[nums[i] - 1] != nums[i]) swapNums(nums, i, nums[i] - 1);
            else i++;
        }
        i = 0;
        while (i < nums.length && nums[i] == i + 1) i++;
        return i + 1;
    }

    private void swapNums(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    private int trap(int[] height) {
        int i = 0, j = height.length - 1, result = 0, plank = 0;
        while (i <= j) {
            plank = plank < Math.min(height[i], height[j]) ? Math.min(height[i], height[j]) : plank;
            result = height[i] >= height[j] ? result + (plank - height[j--]) : result + (plank - height[i++]);
        }
        return result;
    }

    private String multiply(String num1, String num2) {
        StringBuilder builder = new StringBuilder("");
        for (int i = num1.length() - 1; i >= 0; i--) {
            int plus = 0;
            int count = num1.length() - 1 - i;
            for (int j = num2.length() - 1; j >= 0; j--) {
                int temp = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                if (builder.length() > count++) {
                    temp += plus;
                    int index = num1.length() - i - 1 + num2.length() - j;
                    temp += builder.charAt(builder.length() - index) - '0';
                    builder.replace(builder.length() - index, builder.length() - index + 1, String.valueOf(temp % 10));
                    plus = temp / 10;
                } else {
                    temp += plus;
                    builder.insert(0, temp % 10);
                    plus = temp / 10;
                }
            }
            if (plus > 0) {
                builder.insert(0, plus);
            }
        }
        int left = 0;
        while (builder.length() > 1) {
            if (builder.charAt(left) == '0') {
                builder.delete(left, left + 1);
            } else
                break;
        }
        return builder.toString();
    }

    private String[] buildSubsets(String s) {
        int length = s.length();
        String[] strs = new String[(int) Math.pow(2, length) - 1];
        int count = 0;
        for (int i = 1; i <= length; i++) {
            List<String> list = new ArrayList<String>();
            recurse(list, s, i, 0, "");
            for (String temp : list) {
                strs[count++] = temp;
            }
        }
        Arrays.sort(strs);
        return strs;
    }

    private void recurse(List<String> list, String s, int length, int index, String temp) {
        if (length == 0) {
            list.add(temp);
            return;
        }
        for (int i = index; i < s.length(); i++) {
            String tt = temp + s.charAt(i);
            recurse(list, s, length - 1, i + 1, tt);
        }
    }

    private boolean isMatch2(String s, String p) {
        if (p.replace("*", "").length() > s.length())
            return false;
        boolean[] d = new boolean[s.length() + 1];
        d[0] = true;
        for (int i = 1; i < s.length(); i++) {
            d[i] = false;
        }
        for (int i = 1; i <= p.length(); i++) {
            char pchar = p.charAt(i - 1);
            if (pchar == '*') {
                for (int j = 1; j <= s.length(); j++)
                    d[j] = d[j - 1] || d[j];
            } else {
                for (int j = s.length(); j >= 1; j--) {
                    d[j] = d[j - 1] && (pchar == '?' || pchar == s.charAt(j - 1));
                }
            }
            d[0] = d[0] && pchar == '*';
        }
        return d[s.length()];
    }

    private int jump(int[] nums) {
        if (nums.length < 2) return 0;
        int level = 0, currentMax = 0, i = 0, nextMax = 0;

        while (currentMax - i + 1 > 0) {
            level++;
            for (; i <= currentMax; i++) {
                nextMax = Math.max(nextMax, nums[i] + i);
                if (nextMax >= nums.length - 1) return level;
            }
            currentMax = nextMax;
        }
        return 0;
    }

    private List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        getPermutation(nums, 0, list);
        return list;
    }

    private void getPermutation(int[] nums, int left, List<List<Integer>> result) {
        if (left >= nums.length) {
            List<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < nums.length; i++) {
                ll.add(nums[i]);
            }
            result.add(ll);
            return;
        }
        for (int i = left; i < nums.length; i++) {
            int t;
            t = nums[left];
            nums[left] = nums[i];
            nums[i] = t;
            getPermutation(nums, left + 1, result);
            t = nums[left];
            nums[left] = nums[i];
            nums[i] = t;
        }
    }

    public List<List<Integer>> permuteUnique2(int[] num) {
        LinkedList<List<Integer>> res = new LinkedList<List<Integer>>();
        res.add(new ArrayList<Integer>());
        for (int i = 0; i < num.length; i++) {
            Set<String> cache = new HashSet<String>();
            while (res.peekFirst().size() == i) {
                List<Integer> l = res.removeFirst();
                for (int j = 0; j <= l.size(); j++) {
                    List<Integer> newL = new ArrayList<Integer>(l.subList(0, j));
                    newL.add(num[i]);
                    newL.addAll(l.subList(j, l.size()));
                    if (cache.add(newL.toString())) res.add(newL);
                }
            }
        }
        return res;
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        Arrays.sort(nums);
        List<Integer> ll = new LinkedList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            ll.add(nums[i]);
        }
        getUniquePermutation(ll, 0, nums.length, list);
        return list;
    }

    public void getUniquePermutation(List<Integer> nums, int i, int j, List<List<Integer>> result) {
        if (i == j - 1 && !result.contains(nums)) {
            List<Integer> temp = new LinkedList<Integer>();
            temp.addAll(nums);
            result.add(temp);
            return;
        }
        for (int k = i; k < j; k++) {
            if (i != k && nums.get(i) == nums.get(k)) continue;
            Collections.swap(nums, i, k);
            getUniquePermutation(nums, i + 1, j, result);
        }
    }

    public void rotate(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row / 2; i++) {
            for (int j = 0; j < col; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[row - i - 1][j];
                matrix[row - i - 1][j] = temp;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = i + 1; j < col; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new LinkedList<List<String>>();
        HashMap<String, List<String>> maps = new HashMap<String, List<String>>();
        for (int i = 0; i < strs.length; i++) {
            char[] ch = strs[i].toCharArray();
            Arrays.sort(ch);
            String temp = String.valueOf(ch);
            if (maps.containsKey(temp)) {
                maps.get(temp).add(strs[i]);
            } else {
                List<String> list = new LinkedList<String>();
                list.add(strs[i]);
                maps.put(temp, list);
            }
        }
        ListComparator comparator = new ListComparator();
        for (Map.Entry<String, List<String>> entry : maps.entrySet()) {
            List<String> list = entry.getValue();
            Collections.sort(list, comparator);
            result.add(list);
        }
        return result;
    }

    class ListComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    public double myPow(double x, int n) {
        if (n == 0)
            return 1;
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }
        return n % 2 == 0 ? myPow(x * x, n / 2) : myPow(x * x, n / 2) * x;
    }

    public List<List<String>> solveNQueens(int n) {
        int[] nums = new int[n];
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < n; i++) {
            nums[i] = i;
            sb.append(".");
        }
        List<List<String>> result = new LinkedList<List<String>>();
        getPermutation(nums, result, 0, sb.toString());
        return result;
    }

    public void getPermutation(int[] nums, List<List<String>> result, int start, String queen) {
        if (start >= nums.length) {
            if (!isAvailable(nums)) {
                return;
            }
            List<String> list = new LinkedList<String>();
            for (int i = 0; i < nums.length; i++) {
                String temp = queen.substring(0, nums[i]) + "Q" + queen.substring(nums[i] + 1);
                list.add(temp);
            }
            result.add(list);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            int temp = nums[start];
            nums[start] = nums[i];
            nums[i] = temp;
            getPermutation(nums, result, start + 1, queen);
            temp = nums[start];
            nums[start] = nums[i];
            nums[i] = temp;
        }
    }

    public boolean isAvailable(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (Math.abs(nums[j] - nums[i]) == Math.abs(j - i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int totalNQueen(int n) {
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        int[] count = new int[1];
        getQueens(nums, count, 0);
        return count[0];
    }

    public void getQueens(int[] nums, int[] count, int start) {
        if (start >= nums.length) {
            if (!isAvailable(nums)) {
                return;
            }
            count[0]++;
            return;
        }
        for (int i = start; i < nums.length; i++) {
            int temp = nums[start];
            nums[start] = nums[i];
            nums[i] = temp;
            getQueens(nums, count, start + 1);
            temp = nums[start];
            nums[start] = nums[i];
            nums[i] = temp;
        }
    }

    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            max = Math.max(dp[i], max);
        }
        return max;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new LinkedList<Integer>();
        int n = matrix.length;
        if (n == 0)
            return list;
        int m = matrix[0].length;
        if (m == 0)
            return list;
        int h = Math.min((n + 1) / 2, (m + 1) / 2);
        for (int i = 0; i < h; i++) {
            for (int j = i; j < m - i; j++) {
                list.add(matrix[i][j]);
            }
            for (int j = i + 1; j < n - i - 1; j++) {
                list.add(matrix[j][m - i - 1]);
            }
            for (int j = m - i - 1; j >= i && i != n - i - 1; j--) {
                list.add(matrix[n - i - 1][j]);
            }
            for (int j = n - i - 2; j > i && i != m - i - 1; j--) {
                list.add(matrix[j][i]);
            }
        }
        return list;
    }

    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; ) {
            if (nums[i] + i > max) {
                max = nums[i] + i;
            }
            if (max > i) {
                i++;
            } else {
                if (max == nums.length - 1) return true;
                return false;
            }
        }
        return true;
    }

    public class Interval {
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

    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new LinkedList<Interval>();
        if (intervals.size() == 0)
            return result;
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return Integer.compare(o1.start, o2.start);
            }
        });
        Interval temp = intervals.get(0);
        for (int i = 1; i < intervals.size(); i++) {
            Interval curr = intervals.get(i);
            if (temp.end < curr.start) {
                result.add(temp);
                temp = curr;
            } else {
                if (temp.end <= curr.end) {
                    temp.end = curr.end;
                }
            }
        }
        result.add(temp);
        return result;
    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (intervals.size() == 0) {
            intervals.add(newInterval);
            return intervals;
        }
        int i = 0;
        while (i < intervals.size()) {
            Interval inter = intervals.get(i);
            if (newInterval.end < inter.start) {
                intervals.add(i, newInterval);
                break;
            } else if (newInterval.start > inter.end) {
                i++;
                continue;
            } else {
                newInterval.start = Math.min(inter.start, newInterval.start);
                newInterval.end = Math.max(inter.end, newInterval.end);
                intervals.remove(i);
            }
        }
        if (i == intervals.size()) {
            intervals.add(newInterval);
        }
        return intervals;
    }

    public int lengthOfLastWord(String s) {
        int count = 0;
        if (s == null || s.length() == 0) {
            return count;
        }
        s = s.trim();
        int i = s.length() - 1;
        for (; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                break;
            }
        }
        count += s.length() - 1 - i;
        return count;
    }

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        if (n == 0)
            return matrix;
        int h = (n + 1) / 2;
        int number = 1;
        for (int i = 0; i < h; i++) {
            for (int j = i; j < n - i; j++) {
                matrix[i][j] = number++;
            }
            for (int j = i + 1; j < n - i - 1; j++) {
                matrix[j][n - i - 1] = number++;
            }
            for (int j = n - i - 1; j >= i && i != n - i - 1; j--) {
                matrix[n - i - 1][j] = number++;
            }
            for (int j = n - i - 2; j > i && i != n - i - 1; j--) {
                matrix[j][i] = number++;
            }
        }
        return matrix;
    }

    public String getPermutation(int n, int k) {
        int[] array = new int[n];
        int total = 1;
        String ss = "";
        for (int i = 1; i <= n; i++) {
            array[i - 1] = i;
            total *= i;
            ss = i + ss;
        }
        if (k > total) {
            return ss;
        }
        int count = 0;
        int accumulate = 1;
        while (k > 1) {
            count = 0;
            accumulate = 1;
            while (k > accumulate) {
                accumulate *= ++count;
            }
            accumulate /= count;
            int length = (k - 1) / accumulate + n - count;
            int temp = array[n - count];
            array[n - count] = array[length];
            for (int i = length; i > n - count + 1; i--) {
                array[i] = array[i - 1];
            }
            array[n - count + 1] = temp;
            k -= accumulate * (length - n + count);
        }
        String result = "";
        for (int i = 0; i < n; i++) {
            result += array[i] + "";
        }
        return result;
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0)
            return head;
        ListNode curr = head;
        ListNode tail = head;
        int count = 0;
        while (curr != null) {
            tail = curr;
            curr = curr.next;
            count++;
        }
        k = k % count;
        if (k == 0) return head;
        count -= k;
        curr = head;
        ListNode prev = null;
        while (count > 0) {
            prev = curr;
            curr = curr.next;
            count--;
        }
        tail.next = head;
        prev.next = null;
        return curr;
    }

    public int uniquePaths(int m,int n){
        
    }

    public static void main(String[] args) {
        LeetCode leetCode = new LeetCode();

        ListNode head = leetCode.new ListNode(1);
        leetCode.rotateRight(head, 0);

//        System.out.println(leetCode.getPermutation(8, 8590));

//        leetCode.lengthOfLastWord("a");

//        int[] nums = {0};
//        leetCode.canJump(nums);

//        int[][] nums = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}
//        };
//        leetCode.spiralOrder(nums);

//        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        System.out.println(leetCode.maxSubArray(nums));

//        leetCode.totalNQueen(2);

//        leetCode.solveNQueens(4);

//        leetCode.myPow(0.00001, 2147483647);

//        int[][] nums = {
//                {1, 2},
//                {3, 4}
//        };
//        leetCode.rotate(nums);

//        int[] nums = {3, 3, 0, 0, 2};
//        List<List<Integer>> list = leetCode.permuteUnique(nums);
//        for (List<Integer> ll : list) {
//            for (Integer in : ll) {
//                System.out.print(in + " ");
//            }
//            System.out.println();
//        }

//        int[] nums = {1, 2, 3};
//        List<List<Integer>> list = leetCode.permute(nums);
//        for (List<Integer> ll : list) {
//            for (Integer tt : ll) {
//                System.out.print(tt + " ");
//            }
//            System.out.println();
//        }


//        int[] nums = {1, 2, 3};
//        System.out.println(leetCode.jump(nums));
//
//        BigDecimal big = new BigDecimal(1);
//        for (int i = 1; i <= 50; i++) {
//            big = big.multiply(new BigDecimal(i));
//        }
//        System.out.println(big);

//        System.out.println(leetCode.isMatch2("abcdef", "a?de*"));

//        System.out.println(leetCode.multiply("123", "456"));

//        String[] tt = leetCode.buildSubsets("ab");
//        for (int i = 0; i < tt.length; i++) {
//            System.out.println(tt[i]);
//        }

//        int[] nums = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
//        System.out.println(leetCode.trap(nums));

//        int[] nums = {2, 2, 2};
//        List<List<Integer>> list = leetCode.combinationSum2(nums, 4);
//        for (List<Integer> ll : list) {
//            for (Integer ii : ll) {
//                System.out.print(ii + " ");
//            }
//            System.out.println();
//        }

//        int[] nums = {1, 3};
//        System.out.println(leetCode.searchInsert(nums, 2));

//        int[] nums = {1};
//        System.out.println(leetCode.searchRange(nums, 1));

//        int[] nums = {4, 5, 6, 7, 8, 1, 2, 3};
//        System.out.println(leetCode.search2(nums, 8));

//        System.out.println(leetCode.longestValidParenthese3("()()()(())"));

//        int[] nums = {1, 3, 2};
//        leetCode.nextPermutation(nums);

//        String s = "barfoofoobarthefoobarman";
//        String[] words = {"bar", "foo", "the"};
//        List<Integer> result = leetCode.findSubstring(s, words);
//        leetCode.print(result);

//        System.out.println(leetCode.divide2(-2147483648, -1));

//        System.out.println(leetCode.strStr2("a", "a"));

//        int[] nums = {3, 3, 2, 4, 5};
//        System.out.println(leetCode.removeElement(nums, 3));

//        int[] nums = new int[0];
//        System.out.println(leetCode.removeDuplicate(nums));

//        ListNode head = leetCode.new ListNode(1);
//        leetCode.reverseKGroup(head, 2);

//        ListNode[] list = new ListNode[4];
//        list[0] = null;
//        ListNode temp = leetCode.new ListNode(-1);
//        ListNode hh = temp;
//        temp.next = leetCode.new ListNode(5);
//        temp = temp.next;
//        temp.next = leetCode.new ListNode(11);
//        list[1] = hh;
//        list[2] = null;
//        temp = leetCode.new ListNode(6);
//        hh = temp;
//        temp.next = leetCode.new ListNode(10);
//        list[3] = hh;
//        leetCode.mergeKList(list);

//        List<String> list = leetCode.generateParenthese(1);
//        for (String temp : list) {
//            System.out.println(temp);
//        }

//        System.out.println(leetCode.isVaild("()"));

//        int[] nums = {1, 0, -1, 0, -2, 2};
//        leetCode.fourSum(nums, 0);

//        leetCode.letterCombinations("23");

//        int[] nums = {0, 0, 0};
//        System.out.println(leetCode.threeSumCloset(nums, 1));

//        int[] nums = {-2, 0, 1, 1, 2};
//        leetCode.threeSum(nums);

//        String[] strs = {"c", "c"};
//        System.out.println(leetCode.longestCommonPrefix(strs));

//        System.out.println(leetCode.romanToInt("MMCDXXV"));

//        System.out.println(leetCode.intToRoman(1));

//        System.out.println(leetCode.isMatch("aabc", "a*c"));

//        System.out.println(leetCode.isPalindrome(1001));

//        System.out.println(leetCode.myAtoi("9223372036854775809"));

//        System.out.println(leetCode.reverse(1534236469));

//        String str = "PAYPALISHIRING";
//        System.out.println(leetCode.convert(str, 3));

//        String str = "bananas";
//        System.out.println(leetCode.longestPalindrome2(str));

//        int[] num1 = {1, 2};
//        int[] num2 = {1, 2, 3};
//        System.out.println(leetCode.findMedianSortedArrays(num1, num2));

//        String str = "abcabcdd";
//        leetCode.lengthOfLongestString(str);

//        int[] nums = {2, 7, 11, 15};
//        leetCode.twoSum(nums, 9);

    }
}
