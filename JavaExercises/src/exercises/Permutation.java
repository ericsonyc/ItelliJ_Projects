package exercises;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by ericson on 2015/3/5 0005.
 */
public class Permutation {
    public static void main(String[] args) {
        Permutation permutation = new Permutation();
        System.out.println("Please input n:");
        Scanner scan1 = new Scanner(System.in);
        int n = scan1.nextInt();

        System.out.println("Please input r:");
        Scanner scan2 = new Scanner(System.in);
        int r = scan2.nextInt();
        int[] list = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            list[i] = i;
        }

        permutation.combination(list, r, 0, n);
    }

    void combination(int[] list, int r, int low, int n) {
        if (low < r) {
            for (int j = low; j < n; j++) {
                if ((low > 0 && list[j] < list[low - 1]) || low == 0) {
                    int temp = list[low];
                    list[low] = list[j];
                    list[j] = temp;
                    combination(list, r, low + 1, n);
                    temp = list[low];
                    list[low] = list[j];
                    list[j] = temp;
                }
            }
        }

        if (low == r) {
            for (int i = 0; i < r; i++) {
                System.out.print(list[i] + " ");
            }
            System.out.println(" ");
        }
    }
}
