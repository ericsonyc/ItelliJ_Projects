package mianshi.baodian;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by ericson on 2015/8/17 0017.
 */
public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
//        mm.printPrime(100);
//        mm.printMultiple();
//        mm.printParlindrome(10000);
//        mm.printDate();
//        mm.deleteCircle(10);
//        mm.printDate(new Date());
        int[] nums = new int[10];
        Random ran = new Random(10);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = ran.nextInt(10);
        }
        mm.printData(nums);
//        mm.bubblesort(nums);
//        mm.insertsort(nums);
        mm.quicksort(nums, 0, nums.length - 1);
        mm.printData(nums);
        Person p = Person.getInstance();
    }

    static class Person {
        private Person() {

        }

        private static Person person = null;

        public synchronized static Person getInstance() {
            if (person == null)
                person = new Person();
            return person;
        }
    }

    private void printData(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    private void quicksort(int[] data, int left, int right) {
        if (left >= right)
            return;
        int privot = left;
        left++;
        int end = right;
        while (left <= right) {
            while (left < data.length && data[left] < data[privot]) {
                left++;
            }
            while (right > privot && data[right] >= data[privot]) {
                right--;
            }
            if (left > right)
                break;
            data[left] ^= data[right];
            data[right] ^= data[left];
            data[left] ^= data[right];
        }
        if (privot != right) {
            data[privot] ^= data[right];
            data[right] ^= data[privot];
            data[privot] ^= data[right];
        }
        quicksort(data, privot, right - 1);
        quicksort(data, right + 1, end);
    }

    private void insertsort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int j = i - 1;
            int temp = data[i];
            while (j >= 0 && temp < data[j]) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = temp;
        }
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
    }

    private void bubblesort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j + 1] < data[j]) {
                    data[j] ^= data[j + 1];
                    data[j + 1] ^= data[j];
                    data[j] ^= data[j + 1];
                }
            }
        }
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
    }

    private void printDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }

    private void deleteCircle(int n) {
        List<Integer> lists = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) {
            lists.add(i);
        }
        int count = -1;
        while (lists.size() > 1) {
            count = (count + 3) % lists.size();
            lists.remove(count--);
        }
        System.out.println(lists.get(0));
    }

    private void printDate() {
        Date now = new Date();
        long addTime = 1 * 24 * 60 * 60 * 1000;
        System.out.println(new Date(now.getTime() + addTime));
    }

    private void printParlindrome(int n) {
        for (int i = 10; i <= n; i++) {
            if (isPalindrome(i))
                System.out.println(i);
        }
    }

    private boolean isPalindrome(int num) {
        int newNum = 0;
        int temp = num;
        while (num > 0) {
            newNum = newNum * 10 + num % 10;
            num /= 10;
        }
        return temp == newNum;
    }

    private void printMultiple() {
        for (int i = 1, j = 1; j <= 9; i++) {
            System.out.print(j + "*" + i + "=" + j * i + " ");
            if (i == j) {
                i = 0;
                j++;
                System.out.println();
            }
        }
    }

    private void printPrime(int n) {
        for (int i = 3; i <= n; i += 2) {
            if (isPrime(i))
                System.out.println(i);
        }
    }

    private boolean isPrime(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
}
