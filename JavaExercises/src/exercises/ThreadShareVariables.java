package exercises;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jtang on 2015/1/11 0011.
 */
public class ThreadShareVariables {

    private HashMap<Integer, float[]> maps = new HashMap<Integer, float[]>();

    public static void main(String[] args) {
        ThreadShareVariables thread = new ThreadShareVariables();
        //thread.compute();
        for (int i = 0; i < 10; i++) {
            float[] value = new float[4];
            for (int j = 0; j < value.length; j++) {
                value[j] = j;
            }
            thread.maps.put(i, value);
        }
        thread.printMaps();
        thread.compute();
        thread.printMaps();

    }

    private void printMaps() {
        System.out.println("maps:***************");
        for (Map.Entry<Integer, float[]> entry : maps.entrySet()) {
            int key = entry.getKey();
            float[] value = entry.getValue();
            String result = String.valueOf(key);
            for (int i = 0; i < value.length; i++) {
                result += "," + value[i];
            }

            System.out.println(result);
        }
    }

    private void compute() {
        try {
            List<Thread> lists = new ArrayList<Thread>();
            for (int i = 0; i < 4; i++) {
                Thread th = new MyThread();
                lists.add(th);
                th.start();
            }
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            synchronized (maps) {
                for (Map.Entry<Integer, float[]> entry : maps.entrySet()) {
                    int key = entry.getKey();
                    float[] value = entry.getValue();
                    for (int i = 0; i < value.length; i++) {
                        value[i] *= 2;
                    }
                    maps.put(key, value);
                }
            }
        }
    }
}
