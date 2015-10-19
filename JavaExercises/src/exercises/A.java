package exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/4/2 0002.
 */
public class A {
    //    public static int length = 2;
    public static List<Integer> lists = null;

    static {
//        length = 3;
        lists = new ArrayList<Integer>();
        lists.add(1);
    }

    public void setLength(int len) {
        lists.add(len);
    }

    public void getLength() {
        for (int i = 0; i < lists.size(); i++) {
            System.out.print(lists.get(i) + "  ");
        }
        System.out.println();
    }
}
