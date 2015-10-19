package CrazyJava;

import java.util.TreeSet;

/**
 * Created by ericson on 2015/10/17 0017.
 */
public class TreeSetTest {
    public static void main(String[] args) {
        TreeSet nums = new TreeSet();
        nums.add(5);
        nums.add(2);
        nums.add(10);
        nums.add(-9);
        System.out.println(nums);
        System.out.println(nums.first());
        System.out.println(nums.last());
        System.out.println(nums.headSet(4));
        System.out.println(nums.tailSet(5));
        System.out.println(nums.subSet(-3, 4));
    }
}
