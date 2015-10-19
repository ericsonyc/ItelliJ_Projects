package CrazyJava;

import com.sun.prism.impl.ps.AATessRoundRectRep;

import java.util.TreeSet;

/**
 * Created by ericson on 2015/10/19 0019.
 */
public class TreeSetTest3 {
    public static void main(String[] args) {
        TreeSet ts = new TreeSet();
        ts.add(new RR(5));
        ts.add(new RR(-3));
        ts.add(new RR(9));
        ts.add(new RR(-2));
        System.out.println(ts);
        RR first = (RR) ts.first();
        first.count = 20;
        RR last = (RR) ts.last();
        last.count = -2;
        System.out.println(ts);
        System.out.println(ts.remove(new RR(-2)));
        System.out.println(ts);
        System.out.println(ts.remove(new RR(5)));
        System.out.println(ts);
    }
}

class RR implements Comparable {
    int count;

    public RR(int count) {
        this.count = count;
    }

    public String toString() {
        return "R[count:" + count + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == Z.class) {
            RR r = (RR) obj;
            if (r.count == this.count) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        RR r = (RR) o;
        return count > r.count ? 1 : count < r.count ? -1 : 0;
    }
}
