package hihocode.travel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public class Main {
    class Person {
        private int data = 3;
    }

    public static void main(String... args) {
        Main mm=new Main();
        List<Person> lists = new LinkedList<Person>();
        while (true) {
            lists.add(mm.new Person());
        }
    }
}
