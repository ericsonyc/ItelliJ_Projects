package beauty;

/**
 * Created by ericson on 2015/4/9 0009.
 */
public class TryCatch {
    public static void main(String[] args) {
        TryCatch tct = new TryCatch();
        int result = tct.test();
        System.out.println("test method finished");

        result=tct.test2();
        System.out.println("test2");
    }

    public int test() {
        int divider = 10;
        int result = 100;
        try {
            while (divider > -1) {
                divider--;
                result = result + 100 / divider;
            }
            return result;
        } catch (Exception e) {
            System.out.println("exception");
            return -1;
        }
    }

    public int test2() {
        int divider = 10;
        int result = 100;
        try {
            while (divider > -1) {
                divider--;
                result = result + 100 / divider;
            }
            return result;
        } catch (Exception e) {
            System.out.println("exception");
            return result = 999;
        } finally {
            System.out.println("finally");
            System.out.println("result:" + result);
        }
    }
}
