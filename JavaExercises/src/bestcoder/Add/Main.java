package bestcoder.Add;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        int[] nums = {5, 10, 3, 11};
        System.out.println(mm.findMaxDifference(nums, 4));
    }

    private int distance(String s1, String s2, int len) {
        int result = 0;
        if (len == 1) {
            return (int) (s2.charAt(0) - s1.charAt(0)) * 26 + 1;
        }
        return result;
    }

    private int findMaxDifference(int[] arr, int len) {
        int left = 0;
        int right = len - 2;
        int[] temp = new int[len - 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int t = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = t;
                }
            }
        }
        for (int i = 0; i < len - 1; i++) {
            temp[i] = arr[i];
        }

        return temp[right] - temp[left];
    }
}
