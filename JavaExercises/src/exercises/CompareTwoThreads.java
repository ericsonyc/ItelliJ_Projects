package exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtang on 2015/1/10 0010.
 */
public class CompareTwoThreads {
    public static void main(String[] args) {
        try {
            List<Thread> lists = new ArrayList<Thread>();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 4; i++) {
                Thread th = new MyThread();
                lists.add(th);
            }
            long end = System.currentTimeMillis();
            System.out.println("Time:" + (end - start) + "ms");

            start = System.currentTimeMillis();
            for (int i = 0; i < 3; i++) {

            }
            end = System.currentTimeMillis();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < 1000000; i++) {
                sum += i * i;
            }
            System.out.println(sum);
        }
    }

    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            long sum = 0;
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 100000; i++) {
                    sum += i;
                }
            }

            System.out.println(sum);
        }
    }

    public static class MyThread2 extends Thread {
        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < 25000; i++) {
                for (int j = 0; j < 2; j++) {
                    sum += i;
                }
            }
            System.out.println(sum);
        }
    }

}
