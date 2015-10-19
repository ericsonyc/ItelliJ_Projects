package beauty;

/**
 * Created by ericson on 2015/4/2 0002.
 */
public class CpuUsage {
    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 9600000; i++)
                System.out.print("");
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
