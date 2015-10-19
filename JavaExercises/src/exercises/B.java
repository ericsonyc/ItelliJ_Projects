package exercises;

/**
 * Created by ericson on 2015/4/2 0002.
 */
public class B {
    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new A();
        a1.getLength();
        a2.getLength();
        a1.setLength(5);
        a1.getLength();
        a2.getLength();
        A a3 = new A();
        a3.getLength();
    }
}
