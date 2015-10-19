package spark;

import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.input.PortableDataStream;
import scala.Tuple2;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtang on 2015/1/15 0015.
 */
public class MyPairFlatFunction implements PairFlatMapFunction<Tuple2<String, PortableDataStream>, Integer, String> {

    private int splitlength;
    private int index;

    public MyPairFlatFunction(int splitlength, int index) {
        this.splitlength = splitlength;
        this.index = index;
    }

    @Override
    public Iterable<Tuple2<Integer, String>> call(Tuple2<String, PortableDataStream> stringPortableDataStreamTuple2) throws Exception {
//        PortableDataStream portableDataStream = stringPortableDataStreamTuple2._2();
//        DataInputStream dis = portableDataStream.open();
//        List<Tuple2<Integer, String>> resultLists = new ArrayList<Tuple2<Integer, String>>();
//        byte[] temp = new byte[splitlength];
//        int count = dis.read(temp) / 4;
//        int key = index * splitlength / 8;
//        byte[] b = new byte[4];
//        int l = 0;
//        float[] values = new float[2];
//        for (int i = 0; i < count; i++) {
//            for (int j = 0; j < b.length; j++) {
//                b[j] = temp[4 * i + j];
//            }
//            l++;
//            values[l - 1] = Float.intBitsToFloat(getInt(b));
//            if (l % 2 == 0) {
//                String result = values[0] + "," + values[1];
//                resultLists.add(new Tuple2<Integer, String>(key, result));
//                key++;
//                l = 0;
//            }
//        }
//        System.out.println("resultLists.length:" + resultLists.size());


        PortableDataStream portableDataStream = stringPortableDataStreamTuple2._2();
        List<Tuple2<Integer, String>> resultLists = new ArrayList<Tuple2<Integer, String>>();
        Thread[] threads = new Thread[4];
        int key = index * splitlength / 8;
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(key + i * splitlength / (8 * threads.length), splitlength / threads.length, resultLists, portableDataStream);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        return resultLists;
    }

    class MyThread extends Thread {

        private int key;
        private int length;
        private List<Tuple2<Integer, String>> lists;
        private PortableDataStream portableDataStream;

        public MyThread(int key, int length, List<Tuple2<Integer, String>> lists, PortableDataStream portableDataStream) {
            this.key = key;
            this.length = length;
            this.lists = lists;
            this.portableDataStream = portableDataStream;
        }

        @Override
        public void run() {
            getOutputShot();
        }

        public void getOutputShot() {
            try {
                DataInputStream dis = portableDataStream.open();
                byte[] temp = new byte[length];
                int count = dis.read(temp) / 4;
                byte[] b = new byte[4];
                int l = 0;
                float[] values = new float[2];
                for (int i = 0; i < count; i++) {
                    for (int j = 0; j < b.length; j++) {
                        b[j] = temp[4 * i + j];
                    }
                    l++;
                    values[l - 1] = Float.intBitsToFloat(getInt(b));
                    if (l % 2 == 0) {
                        String result = values[0] + "," + values[1];
                        synchronized (lists) {
                            lists.add(new Tuple2<Integer, String>(key, result));
                        }

                        key++;
                        l = 0;
                    }
                }
                System.out.println("resultLists.length:" + lists.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }
}
