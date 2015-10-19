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
        PortableDataStream portableDataStream = stringPortableDataStreamTuple2._2();
        DataInputStream dis = portableDataStream.open();
        List<Tuple2<Integer, String>> resultLists = new ArrayList<Tuple2<Integer, String>>();
        System.out.println("splitlength:" + splitlength + " ,index:" + index);
        byte[] temp = new byte[splitlength];
        dis.skipBytes(index * splitlength);
        int count = dis.read(temp) / 4;
        dis.close();
        int key = index * splitlength / 8;
        System.out.println("count:" + count + " ,key:" + key);
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
                //System.out.println("result:" + result);
                resultLists.add(new Tuple2<Integer, String>(key, result));
                key++;
                l = 0;
            }
        }
        //List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
        //lists.add(resultLists.get(0));
        return resultLists;
    }

    public int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }
}
