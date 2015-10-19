package exercises;

import java.util.Random;

/**
 * Created by ericson on 2015/3/4 0004.
 */
public class ArrayOperation {
    public static void main(String[] args) {
        ArrayOperation arrayOperation = new ArrayOperation();
        int[] array = new int[10];
        Random ran = new Random();
        for (int i = 0; i < array.length; i++) {
            if (ran.nextInt(10) % 2 == 0)
                array[i] = -1 * ran.nextInt(10);
            else
                array[i] = ran.nextInt(10);
        }
        arrayOperation.printArray(array, 0, array.length);
        int result = arrayOperation.MaxProduct(array, array.length);
        System.out.println("result:" + result);


        int[] data = new int[5];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        int[] re = new int[3];
        arrayOperation.combine_increase(data, 0, re, 0, 3, 5);
        for (int i = 0; i < re.length; i++) {
            System.out.print(i + "  ");
        }
        System.out.print("\n");

    }

    void printArray(int[] array, int start, int end) {
        for (int i = start; i < end; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println("");
    }


    int[] MaxandMin(int[] a, int l, int r) {
        int[] result = new int[2];
        if (l == r) {
            result[0] = a[l];
            result[1] = a[l];
            return result;
        }

        if (l + 1 == r) {
            if (a[l] >= a[r]) {
                result[0] = a[l];
                result[1] = a[l];
            } else {
                result[0] = a[r];
                result[1] = a[l];
            }
            return result;
        }

        int m = (l + r) / 2;
        int[] lmaxmin = MaxandMin(a, l, m);
        int[] rmaxmin = MaxandMin(a, m + 1, r);
        result[0] = Math.max(lmaxmin[0], rmaxmin[0]);
        result[1] = Math.min(lmaxmin[1], rmaxmin[1]);

        return result;
    }

    int[] MaxandMinII(int[] a, int left, int right) {
        int[] result = new int[2];
        if (left == right) {
            result[0] = a[left];
            result[1] = a[left];
            return result;
        }

        if (left + 1 == right) {
            result[0] = a[left] > a[right] ? a[left] : a[right];
            result[1] = a[left] < a[right] ? a[left] : a[right];
            return result;
        }

        int mid = left + (right - left) / 2;
        int[] leftMaxMin = MaxandMinII(a, left, mid);
        int[] rightMaxMin = MaxandMinII(a, mid + 1, right);

        if (leftMaxMin[0] > rightMaxMin[0]) {
            result[0] = leftMaxMin[0];
            result[1] = leftMaxMin[1] > rightMaxMin[0] ? leftMaxMin[1] : rightMaxMin[0];
        } else {
            result[0] = rightMaxMin[0];
            result[1] = rightMaxMin[1] > leftMaxMin[0] ? rightMaxMin[1] : leftMaxMin[0];
        }

        return result;
    }

    int Find(int[] a, int n) {
        int curValue = a[0];
        int count = 1;
        for (int i = 1; i < n; ++i) {
            if (a[i] == curValue)
                count++;
            else {
                count--;
                if (count < 0) {
                    curValue = a[i];
                    count = 1;
                }
            }
        }
        return curValue;
    }

    void FindCommon(int[] a, int[] b, int n) {
        int i = 0;
        int j = 0;
        while (i < n && j < n) {
            if (a[i] < b[j]) {
                i++;
            } else if (a[i] == b[j]) {
                i++;
                j++;
                System.out.println("Common Value:" + a[i]);
            } else {
                ++j;
            }
        }
    }

    void FindCommonElements(int[] a, int[] b, int[] c, int x, int y, int z) {
        for (int i = 0, j = 0, k = 0; i < x && j < y && k < z; ) {
            if (a[i] < b[j]) {
                i++;
            } else {
                if (b[j] < c[k]) {
                    j++;
                } else {
                    if (c[k] < a[i]) {
                        k++;
                    } else {
                        System.out.println("value:" + c[k]);
                        return;
                    }
                }
            }
        }
    }

    boolean BinarySearch(int[] a, int n, int k) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (a[mid] < k) {
                left = mid + 1;
            } else if (a[mid] == k) {
                return true;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    int FindElementWithOddCount(int[] a, int n) {
        int r = a[0];
        for (int i = 1; i < n; ++i) {
            r ^= a[i];
        }
        return r;
    }

    void FixedSum(int[] a, int[] b, int n, int d) {
        for (int i = 0, j = n - 1; i < n && j >= 0; ) {
            if (a[i] + b[j] < d) {
                ++i;
            } else if (a[i] + b[j] == d) {
                System.out.println("a[i]:" + a[i] + ",b[j]:" + b[j]);
                ++i;
                --j;
            } else {
                --j;
            }
        }
    }

    int Sum(int[] a, int n) {
        int curSum = 0;
        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            if (curSum + a[i] < 0) {
                curSum = 0;
            } else {
                curSum += a[i];
                maxSum = Math.max(maxSum, curSum);
            }
        }
        return maxSum;
    }

    int MaxProduct(int[] a, int n) {
        int maxProduct = 1;
        int minProduct = 1;
        int r = 1;
        for (int i = 0; i < n; i++) {
            if (a[i] > 0) {
                maxProduct *= a[i];
                minProduct = Math.min(minProduct * a[i], 1);

            } else if (a[i] == 0) {
                maxProduct = 1;
                minProduct = 1;
            } else {
                int temp = maxProduct;
                maxProduct = Math.max(minProduct * a[i], 1);
                minProduct = temp * a[i];
            }
            r = Math.max(r, maxProduct);
        }
        return r;
    }

    void Reverse(int[] buffer, int start, int end) {
        while (start < end) {
            int temp = buffer[start];
            buffer[start++] = buffer[end];
            buffer[end--] = temp;
        }
    }

    void Shift(int[] buffer, int n, int k) {
        k %= n;
        Reverse(buffer, 0, n - k - 1);
        Reverse(buffer, n - k, n - 1);
        Reverse(buffer, 0, n - 1);
    }

    void ReverseString(StringBuilder a, int n) {
        int left = 0;
        int right = n - 1;
        while (left < right) {
            char temp = a.charAt(left);
            String t = String.valueOf(a.charAt(right));
            a.replace(left, left + 1, t);
            a.replace(right, right + 1, String.valueOf(temp));
        }
    }

    void Select(int[] a, int t, int n, int m) {
        if (t == m)
            PrintArray(a, m);
        else {
            for (int i = 1; i <= n; i++) {
                a[t] = i;
                if (IsValid(a, t, i)) {
                    Select(a, t + 1, n, m);
                }
            }
        }
    }

    boolean IsValid(int[] a, int lastIndex, int value) {
        for (int i = 0; i < lastIndex; i++) {
            if (a[i] >= value)
                return false;
        }
        return true;
    }

    void PrintArray(int[] a, int n) {
        for (int i = 0; i < n; ++i) {
            System.out.print(a[i] + "  ");
        }
        System.out.println("\n");
    }

    void combine_increase(int[] arr, int start, int[] result, int count, final int NUM, final int arr_len) {
        int i = 0;
        for (i = start; i < arr_len + 1 - count; i++) {
            result[count - 1] = i;
            if (count - 1 == 0) {
                int j;
                for (j = NUM - 1; j >= 0; j--) {
                    System.out.print(arr[result[j]]);
                }
                System.out.println(" ");
            } else {
                combine_increase(arr, i + 1, result, count - 1, NUM, arr_len);
            }
        }
    }

    void Arrange(int[] a, int n) {
        int k = n - 1;
        for (int i = n - 1; i >= 0; --i) {
            if (a[i] != 0) {
                if (a[k] == 0) {
                    a[k] = a[i];
                    a[i] = 0;
                }
                --k;
            }
        }
    }

    boolean SameSign(int a, int b) {
        if (a * b > 0) {
            return true;
        } else {
            return false;
        }
    }

    int MinimumAbsoluteValue(int[] a, int n) {
        if (n == 1) {
            return a[0];
        }

        if (SameSign(a[0], a[n - 1])) {
            return a[0] > 0 ? a[0] : a[n - 1];
        }

        int l = 0;
        int r = n - 1;
        while (l < r) {
            if (l + 1 == r) {
                return Math.abs(a[l]) < Math.abs(a[r]) ? a[l] : a[r];
            }
            int m = (l + r) / 2;
            if (SameSign(a[m], a[r])) {
                r = m;
                continue;
            }
            if (SameSign(a[l], a[m])) {
                l = m;
                continue;
            }
        }
        return a[0];
    }


}
