package shiyan;

/**
 * Created by ericson on 2015/8/29 0029.
 */
public class Tenxun {
    public static void main(String[] args) {
        Tenxun tenxun = new Tenxun();
        final Test test = tenxun.new Test(10);
        System.out.println(test.a);
        test.a = 12;
        System.out.println(test.a);
//        test=tenxun.new Test(3);//报错
    }

    class Test {
        int a = 5;

        public Test(int a) {
            this.a = a;
        }
    }
}
