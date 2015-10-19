package exercises;

import java.util.Arrays;

/**
 * Created by ericson on 2015/3/14 0014.
 */
public class Solution {
    public double findMedianSortedArrays(int A[], int B[]) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        int m = A.length;
        int n = B.length;
        int total = m + n;
        if ((total & 0x01) != 0) {
            return find_kth(A, m, B, n, total / 2 + 1);
        } else {
            return (find_kth(A, m, B, n, total / 2) + find_kth(A, m, B, n,
                    total / 2 + 1)) / 2.0;
        }
    }

    public double find_kth(int A[], int m, int B[], int n, int k) {
        if (m > n) {
            return find_kth(B, n, A, m, k);
        }
        if (m == 0) {
            return B[k - 1];
        }
        if (k == 1) {
            return Math.min(A[0], B[0]);
        }

        int pa = Math.min(k / 2, m);
        int pb = k - pa;
        if (A[pa - 1] < B[pb - 1]) {
            return find_kth(Arrays.copyOfRange(A, pa, A.length), m - pa, B, n,
                    k - pa);
        } else if (A[pa - 1] > B[pb - 1]) {
            return find_kth(A, m, Arrays.copyOfRange(B, pb, B.length), n - pb,
                    k - pb);
        } else {
            return A[pa - 1];
        }
    }

    public static void main(String[] args) {
        int[] A = {1, 3};
        int[] B = {2, 4};
        Solution slt = new Solution();
        double result = slt.findMedianSortedArrays(A, B);
        System.out.println(result);
    }
}
