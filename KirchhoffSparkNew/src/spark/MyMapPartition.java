package spark;

import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.*;

/**
 * Created by ericson on 2015/1/17 0017.
 */
public class MyMapPartition implements PairFlatMapFunction<Iterator<Tuple2<Integer, String>>, Integer, String> {

    private ProgramConf pconf;

    public MyMapPartition(ProgramConf pconf) {
        this.pconf = pconf;
    }

    @Override
    public Iterable<Tuple2<Integer, String>> call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
        //System.out.println("compute pairflatmapfunction");
        List<Tuple2<Integer, String>> arrays = new ArrayList<Tuple2<Integer, String>>();
        HashMap<Integer, float[]> maps = new HashMap<Integer, float[]>();
        int count = 0;
        while (tuple2Iterator.hasNext()) {
            Tuple2<Integer, String> tuples = tuple2Iterator.next();
            String[] datas = tuples._2().split(",");
            int[] keys = new int[datas.length / 2];
            float[] fcxydatas = new float[datas.length];
            for (int i = 0; i < datas.length / 2; i++) {
                keys[i] = tuples._1().intValue() + i;
                fcxydatas[2 * i] = Float.parseFloat(datas[2 * i]);
                fcxydatas[2 * i + 1] = Float.parseFloat(datas[2 * i + 1]);
            }
            //System.out.println("begin map");
            count++;
            long start = System.currentTimeMillis();
            //OutputShotNoThread output = new OutputShotNoThread(keys, fcxydatas, pconf, maps);
            //output.run();
            long end = System.currentTimeMillis();
            System.out.println("whileTime:" + (end - start) + "ms");
        }
        System.out.println("count:" + count);
        System.out.println("systemTime:" + Calendar.getInstance().get(Calendar.MINUTE));
        for (Map.Entry entry : maps.entrySet()) {
            // index++;
            int key = (Integer) entry.getKey();
            //System.out.println("key:" + key);
            float[] value = (float[]) entry.getValue();
            String temp = "";
            for (int i = 0; i < value.length; i++) {
                temp += value[i] + ",";
            }
            temp = temp.substring(0, temp.lastIndexOf(","));
            // System.out.println("key:"+key+", value:"+value.length);
            synchronized (arrays) {
                arrays.add(new Tuple2<Integer, String>(key, temp));
            }

        }
        return arrays;
    }
}
