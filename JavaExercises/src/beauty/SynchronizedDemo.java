package beauty;

/**
 * Created by ericson on 2015/4/8 0008.
 */
public class SynchronizedDemo {
    private boolean ready = false;
    private int result = 0;
    private int number = 1;

    public void write() {
        ready = true;
        number = 2;
    }

    public void read() {
        if (ready) {
            result = number * 3;
        }
        System.out.println("result's value is: " + result);
    }

    private class ReadWriteThread extends Thread {
        private boolean flag;

        public ReadWriteThread(boolean flag) {
            this.flag = flag;
        }

        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p/>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
            if (flag) {
                write();
            } else {
                read();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        SynchronizedDemo synDemo = new SynchronizedDemo();
        synDemo.new ReadWriteThread(true).start();
        Thread.sleep(1000);
        synDemo.new ReadWriteThread(false).start();
    }
}
