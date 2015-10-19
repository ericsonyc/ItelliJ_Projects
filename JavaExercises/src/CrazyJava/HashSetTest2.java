package CrazyJava;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by ericson on 2015/10/17 0017.
 */
public class HashSetTest2 {
    public static void main(String[] args) {
        HashSet hs = new HashSet();
        hs.add(new R(5));
        hs.add(new R(-3));
        hs.add(new R(9));
        hs.add(new R(-2));
        System.out.println(hs);
        Iterator it = hs.iterator();
        R first = (R) it.next();
        first.count = -3;
        System.out.println(hs);
        hs.remove(new R(-3));
        System.out.println(hs);
        System.out.println("hs包含" + hs.contains(new R(-3)));
        System.out.println("hs" + hs.contains(new R(5)));
    }
}

class R {
    int count;

    public R(int count) {
        this.count = count;
    }

    public String toString() {
        return "R[count:" + count + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj != null && obj.getClass() == R.class) {
            R r = (R) obj;
            if (r.count == this.count)
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.count;
    }
}
