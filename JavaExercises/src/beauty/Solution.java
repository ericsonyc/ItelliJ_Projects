package beauty;

/**
 * Created by ericson on 2015/4/13 0013.
 */
public class Solution {
    public int solution(int[] S) {
        int max_sum = 0;
        int current_sum = 0;
        boolean positive = false;
        int n = S.length;
        for (int i = 0; i < n; ++i) {
            int item = S[i];
            if (item < 0) {
                if (max_sum < current_sum) {
                    max_sum = current_sum;
                    current_sum = 0;
                }
            } else {
                positive = true;
                current_sum += item;
            }
        }
        if (current_sum > max_sum) {
            max_sum = current_sum;
        }
        if (positive) {
            return max_sum;
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] ss = {3, 5, 6, 3, 3, 5};
        System.out.println(solution.solution1(ss));
        System.out.println(Integer.MAX_VALUE);
    }

    public int solution(int[][] A) {
        int N = A.length;
        int M = A[0].length;
        boolean rowFlag = false;
        boolean colFlag = false;
        int rowLength = 0;
        int colLength = 0;
        int data = 0;
        int total = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                total += A[i][j];
            }
        }
        int rowTotal = total;
        for (int i = 0; i < N; i++) {
            int length = getSum(A, i, true);
            rowTotal -= length;
            if (rowTotal == data) {
                rowLength++;
                rowFlag = true;
            }
            data += length;
        }
        data = 0;
        for (int i = 0; i < M; i++) {
            int length = getSum(A, i, false);
            total -= length;
            if (total == data) {
                colLength++;
                colFlag = true;
            }
            data += length;
        }
        if (rowFlag && colFlag)
            return rowLength * colLength;
        return 0;
    }

    private int getSum(int[][] A, int index, boolean flag) {
        int value = 0;
        if (flag) {
            for (int i = 0; i < A[index].length; i++) {
                value += A[index][i];
            }
        } else {
            for (int i = 0; i < A.length; i++) {
                value += A[i][index];
            }
        }
        return value;
    }

    private int solution1(int[] A) {
        if (A == null) return 0;
        if (A.length == 0) return 0;
        this.quick_sort(A, 0, A.length - 1);
        int item = A[0];
        int count = 0;
        int sum = 0;
        for (int i = 0; i < A.length; ) {
            while (i < A.length && A[i] == item) {
                i++;
                count++;
            }
            if (i < A.length)
                item = A[i];
            if (count > 1) {
                sum += this.sum(count);
                count = 0;
            }
        }
        return sum;
    }

    private int sum(int index) {
        int value = 0;
        for (int i = 1; i < index; i++) {
            value += i;
        }
        return value;
    }

    private void quick_sort(int[] s, int l, int r) {
        if (l < r) {
            Swap(s, l, l + (r - l + 1) / 2);
            int i = l, j = r, x = s[l];
            while (i < j) {
                while (i < j && s[j] >= x) {
                    j--;
                }
                if (i < j)
                    s[i++] = s[j];
                while (i < j && s[i] < x) {
                    i++;
                }
                if (i < j)
                    s[j--] = s[i];
            }
            s[i] = x;
            quick_sort(s, l, i - 1);
            quick_sort(s, i + 1, r);
        }
    }

    private void Swap(int[] s, int oldindex, int newindex) {
        int temp = s[oldindex];
        s[oldindex] = s[newindex];
        s[newindex] = temp;
    }
}
