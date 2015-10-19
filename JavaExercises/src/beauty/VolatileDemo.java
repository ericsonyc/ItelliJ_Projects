package beauty;

/**
 * Created by ericson on 2015/4/8 0008.
 */
public class VolatileDemo {

    private volatile int number = 0;

    public int getNumber() {
        return this.number;
    }

    public void increase() {
        this.number++;
    }

    public static void main(String[] args) {
        final VolatileDemo volDemo = new VolatileDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    volDemo.increase();
                }
            }).start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println("number:" + volDemo.getNumber());
    }
}
