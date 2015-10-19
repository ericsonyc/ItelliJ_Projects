package hihocode.magicbox;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int x, y, z;
        x = scanner.nextInt();
        y = scanner.nextInt();
        z = scanner.nextInt();
        scanner.nextLine();
        String sequence = scanner.nextLine();
        System.out.println(mm.getCount(sequence, x, y, z));
    }

    private int getCount(String sequence, int x, int y, int z) {
        int cr = 0;
        int cy = 0;
        int cb = 0;
        int count = 0;
        int kmax = 0;
        int length=sequence.length();
        int i=0;
        while (i < length) {
            switch (sequence.charAt(i)) {
                case 'R':
                    cr++;
                    break;
                case 'Y':
                    cy++;
                    break;
                case 'B':
                    cb++;
                    break;
            }
            if (valid(cr, cy, cb, x, y, z)) {
                count++;
                if(count>kmax) kmax=count;
                count = 0;
                cr=cy=cb=0;
            } else {
                count++;
            }
            i++;
        }
        return Math.max(count, kmax);
    }

    private boolean valid(int cr, int cy, int cb, int x, int y, int z) {
        int[] nums1 = {cr, cy, cb};
        int[] nums2 = {x, y, z};
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        if (nums1[2] - nums1[0] == nums2[2]) {
            int temp1 = nums1[1] - nums1[0];
            int temp2 = nums1[2] - nums1[1];
            if ((temp1 == nums2[0] && temp2 == nums2[1]) || (temp1 == nums2[1] && temp2 == nums2[0]))
                return true;
            else
                return false;
        } else
            return false;
    }
}
