package CrazyJava;

import java.util.TreeSet;

public class TreeSetTest2 {
    public static void main(String[] args) {
        TreeSet set = new TreeSet();
        Z z1=new Z(6);
        set.add(z1);
        System.out.println(set.add(z1));
        System.out.println(set);
        ((Z)set.first()).age=9;
        System.out.println(((Z)set.last()).age);
    }
}

class Z implements Comparable {
    int age;

    public Z(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return 1;
    }
}
