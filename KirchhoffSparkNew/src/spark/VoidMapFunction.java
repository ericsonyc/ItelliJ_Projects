package spark;

import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.*;

public class VoidMapFunction implements VoidFunction<Iterator<Tuple2<Integer, String>>> {

    private ProgramConf pconf = null;
    private static List<Tuple2<Integer, String>> arrays = new ArrayList<Tuple2<Integer, String>>();

    public VoidMapFunction(ProgramConf pconf) {
        this.pconf = pconf;
    }

    @Override
    public void call(Iterator<Tuple2<Integer, String>> ites) throws Exception {
        // TODO Auto-generated method stub

        HashMap<Integer, float[]> maps = new HashMap<Integer, float[]>();
        System.out.println("begin call");
        while (ites.hasNext()) {
            Tuple2<Integer, String> tuples = ites.next();
            String[] datas = tuples._2().split(",");
            int[] keys = new int[datas.length / 2];
            float[] fcxydatas = new float[datas.length];
            for (int i = 0; i < datas.length / 2; i++) {
                keys[i] = tuples._1().intValue() + i;
                fcxydatas[2 * i] = Float.parseFloat(datas[2 * i]);
                fcxydatas[2 * i + 1] = Float.parseFloat(datas[2 * i + 1]);
            }
            System.out.println("begin outputshot");
            OutputShotNoThread output = new OutputShotNoThread(keys, fcxydatas, pconf, maps);
            output.run();

        }
        System.out.println("systemTime:" + Calendar.getInstance().get(Calendar.SECOND));
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

    }

    public List<Tuple2<Integer, String>> getArrays() {
        return arrays;
    }

}
