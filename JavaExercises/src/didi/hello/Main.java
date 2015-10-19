package didi.hello;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        String[] temp = scanner.nextLine().split(";");
        String[] strs = temp[0].trim().split(" ");
        int[][] nums = new int[temp.length][strs.length];
        for (int i = 0; i < nums.length; i++) {
            strs = temp[i].trim().split(" ");
            for (int j = 0; j < strs.length; j++) {
                nums[i][j] = Integer.parseInt(strs[j]);
            }
        }
        System.out.println(mm.getMaxNumber(nums));
    }

    private int getMaxNumber(int[][] nums) {
//        int[][] dp = new int[nums.length][nums[0].length];
        int result = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; j < nums[0].length; j++) {
                result = Math.max(nums[i][j] + nums[i - 1][j - 1] + nums[i - 1][j] + nums[i][j - 1], result);
            }
        }
//        int max = 0;
//        for (int i = 1; i < dp.length; i++) {
//            for (int j = 1; j < dp[0].length; j++) {
//                max = Math.max(dp[i][j], max);
//            }
//        }
        return result;
    }

}
