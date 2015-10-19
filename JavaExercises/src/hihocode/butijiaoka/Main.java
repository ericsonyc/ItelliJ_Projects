package hihocode.butijiaoka;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        scanner.nextLine();
        int N, M;
        int[] nums;
        for (int i = 0; i < T; i++) {
            N = scanner.nextInt();
            M = scanner.nextInt();
            scanner.nextLine();
            nums = new int[N];
            for (int j = 0; j < N; j++) {
                nums[j] = scanner.nextInt();
            }
            scanner.nextLine();
            System.out.println(mm.getDays(nums, M));
        }
    }

    private int getDays(int[] nums, int M) {
        if (M >= nums.length)
            return 100;
        int[] days = new int[nums.length + 1];
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            days[i] = nums[i] - 1 - temp;
            temp = nums[i];
        }
        days[nums.length] = 100 - temp;
        int[] tt = new int[days.length - M];
        int result = 0;
        for (int i = 0; i <= M; i++) {
            result += days[i];
        }
        tt[0] = result + M;
        int count = tt[0];
        for (int i = 1; i < tt.length; i++) {
            tt[i] = tt[i - 1] + days[i + M] - days[i - 1];
            count = Math.max(count, tt[i]);
        }
        return count;
    }

}
