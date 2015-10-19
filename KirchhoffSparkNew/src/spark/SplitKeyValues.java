package spark;

import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/1/17 0017.
 */
public class SplitKeyValues implements PairFlatMapFunction<Tuple2<Integer, String>, Integer, String> {
    @Override
    public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
        List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
        int key = integerStringTuple2._1();
        String[] datas = integerStringTuple2._2().split(",");
        int count = 0;
        String data = "";
        int previous = 0;
        //System.out.println("datas.length:" + datas.length);
        for (int i = 0; i <= datas.length; i++) {
            //System.out.println("i:" + i);
            if (count == 2) {
                data = data.substring(0, data.lastIndexOf(","));
                //System.out.println("key:" + (key + previous) + " ,value:" + data);
                Tuple2<Integer, String> tuple = new Tuple2<Integer, String>(key + previous, data);
                lists.add(tuple);
                data = "";
                //System.out.println("previous:" + previous);
                previous += count / 2;
                count = 0;
                if (i == 20) {
                    break;
                } else {
                    i--;
                }
            } else {
                //System.out.println("count:" + count + ",i:" + i);
                data += datas[i] + ",";
                count++;
            }

        }
        return lists;
    }
}
