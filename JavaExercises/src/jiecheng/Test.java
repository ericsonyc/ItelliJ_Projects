package jiecheng;

/**
 * Created by ericson on 2015/9/17 0017.
 */
public class Test {
    public static void main(String[] args) {
        SingleInstance si = new SingleInstance();
        Thread a = new Thread(new PrintThread(si, 'a'));
        Thread b = new Thread(new PrintThread(si, 'b'));
        Thread c = new Thread(new PrintThread(si, 'c'));
        a.start();
        b.start();
        c.start();
    }
}

class PrintThread implements Runnable {
    SingleInstance si;
    char str;

    public PrintThread(SingleInstance si, char str) {
        this.si = si;
        this.str = str;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (si) {
                while (str != si.getPrintStr()) {
                    try {
                        si.wait();
                    } catch (Exception e) {

                    }
                }
                System.out.println(str);
                si.setPrintStr();
                si.notifyAll();
            }
        }
    }
}

class SingleInstance {
    static SingleInstance singleInstance = null;
    private char printStr = 'a';

    public static SingleInstance newInstance() {
        if (singleInstance == null)
            singleInstance = new SingleInstance();
        return singleInstance;
    }

    public char getPrintStr() {
        return this.printStr;
    }

    public char setPrintStr() {
        printStr = (char) (printStr + 1);
        if (printStr == 'd') {
            printStr = 'a';
        }
        return printStr;
    }
}