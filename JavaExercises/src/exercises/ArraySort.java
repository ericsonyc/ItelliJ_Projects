package exercises;

import java.util.Random;

/**
 * Created by jtang on 2015/1/12 0012.
 */
public class ArraySort {
    public static void main(String[] args) {
        int[] array = getRandomArray(2000);
        int[] array1 = array.clone();
        ArraySort sort = new ArraySort();
        sort.printArray(array, 0, array.length);
        long start = System.currentTimeMillis();
        sort.quick_sort(array, 0, array.length - 1);
        long end = System.currentTimeMillis();
        sort.printArray(array, 0, array.length);
        System.out.println("quick_sortTime:" + (end - start) + "ms");


        sort.printArray(array1, 0, array1.length);
        start = System.currentTimeMillis();
        sort.bubble_sort(array1, 0, array.length);
        end = System.currentTimeMillis();
        sort.printArray(array1, 0, array1.length);
        System.out.println("bubble_sortTime:" + (end - start) + "ms");
    }

    private void bubble_sort(int[] s, int start, int end) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < end - i - 1; j++) {
                if (s[j] > s[j + 1]) {
                    Swap(s, j, j + 1);
                }
            }
        }
    }

    private void printArray(int[] s, int start, int end) {
        for (int i = start; i < end; i++) {
            System.out.print(s[i] + " ");
        }
        System.out.println("");
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

    private static int[] getRandomArray(int length) {
        int[] array = new int[length];
        Random ran = new Random(200);
        for (int i = 0; i < length; i++) {
            array[i] = ran.nextInt(2000);
        }
        return array;
    }
}
