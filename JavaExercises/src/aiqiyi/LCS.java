package aiqiyi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ericson on 2015/10/17 0017.
 */
public class LCS {
    public static void main(String[] args) {
        LCS lcs = new LCS();
        Scanner scanner = new Scanner(System.in);
        String[] a = scanner.nextLine().split(",");
        int[] nums = new int[a.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(a[i]);
        }
        int[] result = lcs.getPermutation2(nums);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }

    private int[] getPermutation2(int[] nums) {
        int[] temp = new int[nums.length + 1];
        temp[0] = Integer.MIN_VALUE;
        int length = 0;
        List<int[]> list = new ArrayList<int[]>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > temp[length]) {
                temp[++length] = nums[i];
                int[] variable = new int[length];
                int[] tt = new int[0];
                for (int j = 0; j < list.size(); j++) {
                    int[] ttemp = list.get(j);
                    if (ttemp.length == length - 1 && ttemp[ttemp.length - 1] < nums[i]) {
                        tt = temp;
                        break;
                    }
                }
                for (int j = 0; j < tt.length; j++) {
                    variable[j] = tt[j];
                }
                variable[length - 1] = nums[i];
                list.add(variable);
            } else {
                int p = 0, r = length;
                while (p <= r) {
                    int middle = (p + r) / 2;
                    if (temp[middle] < nums[i]) {
                        p = middle + 1;
                    } else {
                        r = middle - 1;
                    }
                }
                temp[p] = nums[i];
                int[] varible = new int[p];
                int[] tt = p - 1 >= 0 ? list.get(p - 1) : new int[0];
                for (int j = 0; j < tt.length; j++) {
                    varible[j] = tt[j];
                }
                varible[p - 1] = nums[i];
                list.add(varible);
            }
        }
        int max = 0, index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (max < list.get(i).length) {
                max = list.get(i).length;
                index = i;
            }
        }
        return list.get(index);
//        temp = list.get(index);
//        int[] result = new int[temp.length - 1];
//        for (int i = 0; i < result.length; i++) {
//            result[i] = temp[i + 1];
//        }
//        return result;
    }

    private int[] getPermutation(int[] nums) {
        int[] temp = Arrays.copyOf(nums, nums.length);
        Arrays.sort(temp);
        int[][] dp = new int[nums.length][nums.length];
        dp[0][0] = nums[0] == temp[0] ? 1 : 0;
        for (int i = 1; i < nums.length; i++) {
            dp[0][i] = nums[0] == temp[i] ? dp[0][i - 1] + 1 : dp[0][i - 1];
        }
        for (int i = 1; i < nums.length; i++) {
            dp[i][0] = nums[i] == temp[0] ? 1 + dp[i - 1][0] : dp[i - 1][0];
        }
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; j < nums.length; j++) {
                if (nums[i] == temp[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int row = nums.length - 1;
        int col = nums.length - 1;
        int[] result = new int[dp[row][col]];
        int count = result.length;
        while (count > 0) {
            if (nums[row] == temp[col]) {
                result[--count] = nums[row];
                row--;
                col--;
            } else {
                if (row >= 1 && dp[row][col] == dp[row - 1][col]) {
                    row--;
                } else if (col >= 1 && dp[row][col] == dp[row][col - 1]) {
                    col--;
                }
            }
        }
        return result;
    }
}
