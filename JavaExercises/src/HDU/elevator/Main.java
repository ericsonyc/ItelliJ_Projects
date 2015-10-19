package HDU.elevator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int[] numbers = new int[101];
        int count = 1;
        while (scanner.hasNext()) {
            numbers[0] = 0;
            count = 1;
            int n = scanner.nextInt();
            if (n == 0)
                break;
            for (int i = 0; i < n; i++) {
                numbers[count++] = scanner.nextInt();
            }
            scanner.nextLine();
            System.out.println(mm.getTime(numbers, count));
        }
    }

    private int getTime(int[] numbers, int count) {
        int up = 6;
        int down = 4;
        int time = 0;
        for (int i = 1; i < count; i++) {
            if (numbers[i] > numbers[i - 1]) {
                time += up * (numbers[i] - numbers[i - 1]);
            } else if (numbers[i] < numbers[i - 1]) {
                time += down * (numbers[i - 1] - numbers[i]);
            }
        }
        time+=5*(count-1);
        return time;
    }
}
