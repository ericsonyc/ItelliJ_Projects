package blog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * Created by ericson on 2015/10/29 0029.
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
//        test.iterator();
        test.weak();
    }

    public void iterator() {
        Collection books = new ArrayList();
        books.add("a");
        books.add("b");
        books.add("c");
        Iterator it = books.iterator();
        while (it.hasNext()) {
            String book = (String) it.next();
            book = "d";
        }
        System.out.println(books.toString());
    }

    public void weak() {
        WeakHashMap whm = new WeakHashMap();
        whm.put(new String("a"), new String("a"));
        whm.put(new String("b"), new String("b"));
        whm.put(new String("c"), new String("c"));
        whm.put("java", new String("love"));
        System.out.println(whm);
        System.gc();//garbage collection
        System.runFinalization();//run finalize method
        System.out.println(whm);
    }

    class Apple<E> {
        private E info;

        public Apple(E info) {
            this.info = info;
        }

        public void setInfo(E info) {
            this.info = info;
        }

        public E getInfo() {
            return this.info;
        }
    }
}
