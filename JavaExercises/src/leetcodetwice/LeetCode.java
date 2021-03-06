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

    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        for (int i = 1; i < obstacleGrid[0].length; i++) {
            if (obstacleGrid[0][i] == 1) {
                dp[0][i] = 0;
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }
        for (int i = 1; i < obstacleGrid.length; i++) {
            if (obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
            } else {
                dp[i][0] = dp[i - 1][0];
            }
        }
        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 1; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == 1)
                    dp[i][j] = 0;
                else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    public int minPathSum(int[][] grid) {
        for (int i = 1; i < grid[0].length; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        for (int i = 1; i < grid.length; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    public boolean isNumber(String s) {
        s = s.trim();
        if (s.length() == 0)
            return false;
        int pointer = 0;
        if (s.charAt(pointer) == '+' || s.charAt(pointer) == '-') {
            s = s.substring(1);
        }
        int counte = 0;
        int countp = 0;
        int countn = 0;
        while (pointer < s.length()) {
            if (Character.isDigit(s.charAt(pointer))) {
                pointer++;
                countn++;
            } else {
                if (s.charAt(pointer) == '.') {
                    if ((pointer == 0 || Character.isDigit(s.charAt(pointer - 1))) && countp == 0) {
                        s = s.substring(0, pointer) + s.substring(pointer + 1);
                        pointer = 0;
                    } else {
                        return false;
                    }
                    countp++;
                } else if (s.charAt(pointer) == 'e') {
                    if ((pointer > 0 && Character.isDigit(s.charAt(pointer - 1))) && counte == 0) {
                        pointer++;
                    } else {
                        return false;
                    }
                    counte++;
                    if (pointer == s.length())
                        return false;
                } else if (s.charAt(pointer) == '+' || s.charAt(pointer) == '-') {
                    if (pointer > 0 && s.charAt(pointer - 1) == 'e') {
                        pointer++;
                    } else
                        return false;
                    if (pointer == s.length())
                        return false;
                } else
                    return false;
            }
        }
        return countn > 0;
    }

    public int[] plusOne(int[] digits) {
        int plus = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] += plus;
            plus = digits[i] / 10;
            digits[i] %= 10;
        }
        if (plus > 0) {
            int[] result = new int[digits.length + 1];
            result[0] = plus;
            for (int i = 1; i < result.length; i++) {
                result[i] = digits[i - 1];
            }
            return result;
        }
        return digits;
    }

    public String addBinary(String a, String b) {
        if (a.length() < b.length())
            return addBinary(b, a);
        StringBuffer sb = new StringBuffer("");
        int length = b.length() - 1;
        int count = a.length() - b.length();
        byte plus = 0;
        while (length >= 0) {
            byte left = Byte.parseByte(String.valueOf(a.charAt(length + count)));
            byte right = Byte.parseByte(String.valueOf(b.charAt(length)));
            byte temp = (byte) (left ^ right ^ plus);
            sb.insert(0, temp);
            if (left + right + plus >= 2)
                plus = 1;
            else
                plus = 0;
            length--;
        }
        length = a.length() - b.length() - 1;
        while (length >= 0) {
            byte left = Byte.parseByte(String.valueOf(a.charAt(length)));
            sb.insert(0, left ^ plus);
            plus = (byte) (left & plus);
            length--;
        }
        if ((plus ^ 0) == 1) {
            sb.insert(0, plus);
        }
        return sb.toString();
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<String>();
        int index = 0;
        while (index < words.length) {
            int count = words[index].length();
            int last = index + 1;
            while (last < words.length) {
                if (words[last].length() + count + 1 > maxWidth) break;
                count += words[last].length() + 1;
                last++;
            }
            StringBuilder builder = new StringBuilder();
            int diff = last - index - 1;
            if (last == words.length || diff == 0) {
                for (int i = index; i < last; i++) {
                    builder.append(words[i] + " ");
                }
                builder.deleteCharAt(builder.length() - 1);
                for (int i = builder.length(); i < maxWidth; i++) {
                    builder.append(" ");
                }
            } else {
                int spaces = (maxWidth - count) / diff;
                int r = (maxWidth - count) % diff;
                for (int i = index; i < last; i++) {
                    builder.append(words[i]);
                    if (i < last - 1) {
                        for (int j = 0; j <= (spaces + ((i - index) < r ? 1 : 0)); j++)
                            builder.append(" ");
                    }
                }
            }
            lines.add(builder.toString());
            index = last;
        }
        return lines;
    }

    public int mySqrt(int x) {
        if (x == 0 || x == 1) return x ^ 0;
        long left = 0;
        long right = x / 2;
        while (left <= right) {
            long middle = left + (right - left) / 2;
            if (middle * middle == x)
                return (int) middle;
            else if (middle * middle < x) {
                left = middle + 1;
            } else if (middle * middle > x) {
                right = middle - 1;
            }
        }
        return (int) right;
    }

    public int climbStairs(int n) {
        if (n == 1) return 1;
        int left = 1;
        int right = 1;
        for (int i = 2; i <= n; i++) {
            int temp = left + right;
            left = right;
            right = temp;
        }
        return right;
    }

    public String simplifyPath(String path) {
        String[] strs = path.split("/");
        Stack<String> stack = new Stack<String>();
        int count = 0;
        while (count < strs.length) {
            if (strs[count].equals(".") || strs[count].length() == 0) {
                count++;
            } else if (strs[count].equals("..")) {
                if (!stack.isEmpty()) stack.pop();
                count++;
            } else {
                stack.push(strs[count]);
                count++;
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            String temp = stack.pop();
            sb.insert(0, "/" + temp);
        }
        if (sb.length() == 0)
            sb.append("/");
        return sb.toString();
    }

    public int minDistance(String word1, String word2) {
        if (word1.length() == 0 || word2.length() == 0) {
            return Math.max(word1.length(), word2.length());
        }
        int[][] dp = new int[word1.length()][word2.length()];
        dp[0][0] = word1.charAt(0) == word2.charAt(0) ? 0 : 1;
        for (int i = 1; i < word1.length(); i++) {
            dp[i][0] = word1.charAt(i) == word2.charAt(0) ? i : dp[i - 1][0] + 1;
        }
        for (int i = 1; i < word2.length(); i++) {
            dp[0][i] = word1.charAt(0) == word2.charAt(i) ? i : dp[0][i - 1] + 1;
        }
        for (int i = 1; i < word1.length(); i++) {
            for (int j = 1; j < word2.length(); j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                }
            }
        }
        return dp[word1.length() - 1][word2.length() - 1];
    }

    public void setZeros(int[][] matrix) {
        int col0 = 1;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) col0 = 0;
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j > 0; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (col0 == 0)
                matrix[i][0] = 0;
        }
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int left = 0;
        int right = matrix[0].length * matrix.length - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            int row = middle / matrix[0].length;
            int col = middle - (matrix[0].length * row);
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return false;
    }

    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int i = 0;
        while (i <= right) {
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[left];
                nums[left] = temp;
                left++;
                i++;
            } else if (nums[i] == 2) {
                int temp = nums[i];
                nums[i] = nums[right];
                nums[right] = temp;
                right--;
            } else {
                i++;
            }
        }
    }

    public void sortColors2(int[] nums) {
        int i = -1, j = -1, k = -1;
        for (int p = 0; p < nums.length; p++) {
            if (nums[p] == 0) {
                nums[++k] = 2;
                nums[++j] = 1;
                nums[++i] = 0;
            } else if (nums[p] == 1) {
                nums[++k] = 2;
                nums[++j] = 1;
            } else {
                nums[++k] = 2;
            }
        }
    }

    public String minWindow(String s, String t) {
        int min = Integer.MAX_VALUE;
        int dex = -1;
        List<Integer> indexes = new LinkedList<Integer>();
        List<Character> list = new LinkedList<Character>();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (t.indexOf(s.charAt(i)) != -1) {
                char ch = s.charAt(i);
                if (!indexes.contains(ch)) {
                    indexes.add(i);
                    list.add(ch);
                    count++;
                } else {
                    int index = list.indexOf(ch);
                    while (index-- >= 0) {
                        indexes.remove(0);
                        list.remove(0);
                    }
                    list.add(ch);
                    count = list.size();
                    indexes.add(i);
                }
                if (list.size() == t.length()) {
                    if (min > indexes.get(indexes.size() - 1) - indexes.get(0)) {
                        min = indexes.get(indexes.size() - 1) - indexes.get(0);
                        dex = i;
                    }
                }
            }
        }
        return min == Integer.MAX_VALUE ? "" : s.substring(dex - min, dex + 1);
    }

    public String minWindow2(String s, String t) {
        if (s.isEmpty() || t.isEmpty())
            return "";
        int count = t.length();
        int[] require = new int[128];
        boolean[] chSet = new boolean[128];
        for (int i = 0; i < count; ++i) {
            require[t.charAt(i)]++;
            chSet[t.charAt(i)] = true;
        }
        int i = -1;
        int j = 0;
        int minLen = Integer.MAX_VALUE;
        int midIdx = 0;
        while (i < s.length() && j < s.length()) {
            if (count > 0) {
                i++;
                if (i >= s.length()) break;
                require[s.charAt(i)]--;
                if (chSet[s.charAt(i)] && require[s.charAt(i)] >= 0) {
                    count--;
                }
            } else {
                if (minLen > i - j + 1) {
                    minLen = i - j + 1;
                    midIdx = j;
                }
                require[s.charAt(j)]++;
                if (chSet[s.charAt(j)] && require[s.charAt(j)] > 0) {
                    count++;
                }
                j++;
            }
        }
        if (minLen == Integer.MAX_VALUE) {
            return "";
        }
        return s.substring(midIdx, midIdx + minLen);
    }

    public String minWindow3(String s, String t) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            map.put(t.charAt(i), map.get(t.charAt(i)) == null ? 1 : map.get(t.charAt(i)) + 1);
        }
        int left = 0;
        int minLeftIndex = 0;
        int minLen = s.length() + 1;
        int missing = t.length();
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) - 1);
                if (map.get(ch) >= 0) {
                    missing--;
                }
                while (missing == 0) {
                    if (right - left + 1 < minLen) {
                        minLen = right - left + 1;
                        minLeftIndex = left;
                    }
                    char lc = s.charAt(left);
                    if (map.containsKey(lc)) {
                        map.put(lc, map.get(lc) + 1);
                        if (map.get(lc) > 0) {
                            missing++;
                        }
                    }
                    left++;
                }
            }
        }
        if (minLen > s.length())
            return "";
        return s.substring(minLeftIndex, minLeftIndex + minLen);
    }

    public List<List<Integer>> combine(int n, int k) {
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        combination(list, nums, 0, k);
        return list;
    }

    public void combination(List<List<Integer>> list, int[] nums, int start, int k) {
        if (start >= k) {
            List<Integer> ll = new LinkedList<Integer>();
            for (int i = 0; i < k; i++) {
                ll.add(nums[i]);
            }
            list.add(ll);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            int temp = nums[i];
            nums[i] = nums[start];
            nums[start] = temp;
            combination(list, nums, start + 1, k);
            temp = nums[i];
            nums[i] = nums[start];
            nums[start] = temp;
        }
    }

    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        if (k > n) return list;
        List<Integer> temp = new LinkedList<Integer>();
        CombinationAll(list, temp, 0, 0, n, k);
        return list;
    }

    public void CombinationAll(List<List<Integer>> list, List<Integer> temp, int start, int num, int n, int k) {
        if (num == k) {
            List<Integer> result = new LinkedList<Integer>();
            for (int i = 0; i < temp.size(); i++) {
                result.add(temp.get(i));
            }
            list.add(result);
            return;
        }
        for (int i = start; i < n; i++) {
            temp.add(i + 1);
            CombinationAll(list, temp, i + 1, num + 1, n, k);
            temp.remove(temp.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        int elem_num = nums.length;
        int subset_num = (int) Math.pow(2, elem_num);
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        for (int i = 0; i < subset_num; i++) {
            List<Integer> temp = new LinkedList<Integer>();
            for (int j = 0; j <= elem_num; j++) {
                int t = 1 << j;
                if ((i & (1 << j)) > 0) {
                    temp.add(nums[j]);
                }
            }
            list.add(temp);
        }
        return list;
    }

    public boolean exist(char[][] board, String word) {
        boolean flag = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    board[i][j] = '#';
                    flag |= getBoard(board, word, 1, i, j);
                    board[i][j] = word.charAt(0);
                    if (flag)
                        break;
                }
            }
        }
        return flag;
    }

    public boolean getBoard(char[][] board, String word, int start, int row, int col) {
        int top = row == 0 ? 0 : row - 1;
        int bottom = row == board.length - 1 ? row : row + 1;
        int left = col == 0 ? 0 : col - 1;
        int right = col == board[0].length - 1 ? col : col + 1;
        if (start >= word.length())
            return true;
        boolean flag = false;
        if (!flag && board[top][col] == word.charAt(start)) {
            board[top][col] = '#';
            flag |= getBoard(board, word, start + 1, top, col);
            board[top][col] = word.charAt(start);
        }
        if (!flag && board[row][left] == word.charAt(start)) {
            board[row][left] = '#';
            flag |= getBoard(board, word, start + 1, row, left);
            board[row][left] = word.charAt(start);
        }
        if (!flag && board[bottom][col] == word.charAt(start)) {
            board[bottom][col] = '#';
            flag |= getBoard(board, word, start + 1, bottom, col);
            board[bottom][col] = word.charAt(start);
        }
        if (!flag && board[row][right] == word.charAt(start)) {
            board[row][right] = '#';
            flag |= getBoard(board, word, start + 1, row, right);
            board[row][right] = word.charAt(start);
        }
        return flag;
    }

    public boolean exist1(char[][] board, String word) {
        char[] w = word.toCharArray();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (exist(board, y, x, w, 0)) return true;
            }
        }
        return false;
    }

    public boolean exist(char[][] board, int y, int x, char[] word, int i) {
        if (i == word.length) return true;
        if (y < 0 || x < 0 || y == board.length || x == board[y].length) return false;
        if (board[y][x] != word[i]) return false;
        board[y][x] ^= 256;
        boolean exist = exist(board, y, x + 1, word, i + 1) || exist(board, y, x - 1, word, i + 1) || exist(board, y + 1, x, word, i + 1) || exist(board, y - 1, x, word, i + 1);
        board[y][x] ^= 256;
        return exist;
    }

    public int removeDuplicate1(int[] nums) {
        if (nums == null) return 0;
        int left = 1;
        int count = 1;
        int length = 0;
        while (left < nums.length - length) {
            count = 1;
            while (left < nums.length - length && nums[left] == nums[left - 1]) {
                left++;
                count++;
            }
            if (count > 2) {
                int temp = left - (count - 2);
                int t = left;
                left = temp;
                while (t < nums.length - length) {
                    nums[temp++] = nums[t++];
                }
                length += count - 2;
            }
            left++;
        }
        return nums.length - length;
    }

    public int removeDuplicates2(int[] nums) {
        if (nums.length <= 1) return nums.length;
        int left = 0;
        int right = 1;
        int count = 1;
        while (right < nums.length) {
            if (nums[right] == nums[left]) {
                if (right - left < 2) {
                    nums[count] = nums[left];
                    count++;
                }
                right++;
            } else {
                left = right;
                right++;
                nums[count] = nums[left];
                count++;
            }
        }
        return count;
    }

    public boolean search1(int[] nums, int target) {
        int start = 0, end = nums.length - 1, mid = -1;
        while (start <= end) {
            mid = (start + end) / 2;
            if (nums[mid] == target)
                return true;
            if (nums[mid] < nums[end] || nums[mid] < nums[start]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            } else if (nums[mid] > nums[start] || nums[mid] > nums[end]) {
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                end--;
            }
        }
        return false;
    }

    public boolean search3(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        int mid = 0;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (nums[mid] == target) return true;
            if (nums[mid] > nums[hi]) {
                if (nums[mid] > target && nums[lo] <= target) hi = mid - 1;
                else lo = mid + 1;
            } else if (nums[mid] < nums[hi]) {
                if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
                else hi = mid - 1;
            } else
                hi--;
        }
        return false;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode root = new ListNode(-1);
        root.next = head;
        ListNode prev = root;
        ListNode curr = head;
        ListNode temp = curr.next;
        while (temp != null) {
            if (temp.val == curr.val) {
                prev.next = temp.next;
                temp = temp.next;
            } else {
                curr = temp;
                temp = temp.next;
                if (prev.next != curr) {
                    prev = prev.next;
                }
            }
        }
        return root.next;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) return null;
        ListNode root = head;
        ListNode curr = head.next;
        while (curr != null) {
            if (curr.val == head.val) {
                head.next = curr.next;
                curr = curr.next;
            } else {
                head = curr;
                curr = curr.next;
            }
        }
        return root;
    }

    public int largestRectangleArea(int[] height) {
        Stack<Integer> stack = new Stack<Integer>();
        int n = height.length, area = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.empty() && height[stack.peek()] > height[i]) {
                int h = height[stack.peek()];
                stack.pop();
                int l = stack.empty() ? -1 : stack.peek();
                area = Math.max(area, h * (i - l - 1));
            }
            stack.push(i);
        }
        while (!stack.empty() && height[stack.peek()] > 0) {
            int h = height[stack.peek()];
            stack.pop();
            int l = stack.empty() ? -1 : stack.peek();
            area = Math.max(area, h * (height.length - 1 - l));
        }
        return area;
    }

    public int maximalRectangle1(char[][] matrix) {
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                max = Math.max(max, maxRectangle1(matrix, i, j));
            }
        }
        return max;
    }

    public int maxRectangle1(char[][] matrix, int row, int col) {
        int minWidth = Integer.MAX_VALUE;
        int maxArea = 0;
        for (int i = row; i < matrix.length && matrix[i][col] == '1'; i++) {
            int width = 0;
            while (col + width < matrix[row].length && matrix[i][col + width] == '1') {
                width++;
            }
            if (width < minWidth) {
                minWidth = width;
            }
            int area = minWidth * (i - row + 1);
            if (area > maxArea)
                maxArea = area;
        }
        return maxArea;
    }

    public int maximalRectangle2(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;
        int[] dp = new int[matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dp[j] += matrix[i][j] - '0';
                } else {
                    dp[j] = 0;
                }
            }
            max = Math.max(maxRectangle2(dp), max);
        }
        return max;
    }

    public int maxRectangle2(int[] dp) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        int n = dp.length, area = 0;
        for (int i = 0; i < n; i++) {
            while (stack.size() > 0 && dp[stack.peek()] > dp[i]) {
                int h = dp[stack.peek()];
                stack.pop();
                int l = stack.size() == 0 ? -1 : stack.peek();
                area = Math.max(area, h * (i - l - 1));
            }
            stack.push(i);
        }
        while (stack.size() > 0 && dp[stack.peek()] > 0) {
            int h = dp[stack.peek()];
            stack.pop();
            int l = stack.size() == 0 ? -1 : stack.peek();
            area = Math.max(area, h * (dp.length - 1 - l));
        }
        return area;
    }

    public ListNode partition(ListNode head, int x) {
        ListNode little = new ListNode(-2);
        ListNode large = new ListNode(-1);
        ListNode p1 = little;
        ListNode p2 = large;
        while (head != null) {
            if (head.val < x) {
                p1.next = head;
                p1 = p1.next;
            } else {
                p2.next = head;
                p2 = p2.next;
            }
            head = head.next;
        }
        p2.next = null;
        p1.next = large.next;
        return little.next;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] >= nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        if (j >= 0) {
            while (j >= 0) {
                nums1[k--] = nums2[j--];
            }
        }
    }

    public static void main(String[] args) {
        LeetCode leetCode = new LeetCode();

        int[] nums1 = new int[1];
        int[] nums2 = new int[1];
        nums2[0] = 1;
        leetCode.merge(nums1, 0, nums2, 1);

//        char[][] matrix = {{'1', '0', '1', '0'}, {'1', '0', '1', '1'}, {'1', '0', '1', '1'}, {'1', '1', '1', '1'}};
//        leetCode.maximalRectangle2(matrix);

//        int[] nums = {1};
//        System.out.println(leetCode.largestRectangleArea(nums));

//        int[] nums = {3, 1, 1};
//        System.out.println(leetCode.search1(nums, 3));

//        int[] nums = {1, 1, 1, 1, 3, 3};
//        System.out.println(leetCode.removeDuplicate1(nums));

//        char[][] board = {
//                {'c', 'a', 'a'},
//                {'a', 'a', 'a'},
//                {'b', 'c', 'd'}
//        };
//        System.out.println(leetCode.exist1(board, "aab"));

//        int[] nums = {1, 2, 0};
//        leetCode.sortColors(nums);

//        int[][] nums = {{1}, {3}};
//        leetCode.searchMatrix(nums, 2);

//        int[][] nums = {{-4, -2147483648, 6, -7, 0}, {-8, 6, -8, -6, 0}, {2147483647, 2, -9, -6, -10}};
//        leetCode.setZeros(nums);

//        leetCode.minDistance("mart", "karma");

//        leetCode.simplifyPath("/abc/...");

//        leetCode.mySqrt(2147395599);

//        String[] words = {"a", "b", "c", "d", "e"};
//        leetCode.fullJustify(words, 3);

//        leetCode.addBinary("11", "1");

//        System.out.println(leetCode.isNumber("4e+"));

//        int[][] nums = {{0, 0}, {0, 0}};
//        leetCode.uniquePathsWithObstacles(nums);

//        ListNode head = leetCode.new ListNode(1);
//        leetCode.rotateRight(head, 0);

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
