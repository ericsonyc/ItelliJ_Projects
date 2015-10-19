package spark;

import com.google.common.base.Optional;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/4/6 0006.
 */
public class TextJoin {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        JavaSparkContext jsc = new JavaSparkContext(conf);
        List<Tuple2<Integer, String>> tuples = new ArrayList<Tuple2<Integer, String>>();
        for (int i = 0; i < 50; i++) {
            Tuple2<Integer, String> tu = new Tuple2<Integer, String>(i, String.valueOf(i));
            tuples.add(tu);
        }
        Tuple2<Integer, String> tu = new Tuple2<Integer, String>(0, "hello");
        tuples.add(tu);
        JavaPairRDD<Integer, String> pair = jsc.parallelizePairs(tuples);

        List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
        for (int i = 0; i < 10; i++) {
            Tuple2<Integer, String> tuple = new Tuple2<Integer, String>(i, "h" + i);
            lists.add(tuple);
        }
        JavaPairRDD<Integer, String> rdd = jsc.parallelizePairs(lists);

        JavaPairRDD<Integer, String> ddd = rdd.join(pair).flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, Tuple2<String, String>>, Integer, String>() {
            @Override
            public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, Tuple2<String, String>> integerTuple2Tuple2) throws Exception {

                return null;
            }
        });
        List<Tuple2<Integer, String>> lll = ddd.collect();
        for (int i = 0; i < lll.size(); i++) {
            Tuple2<Integer, String> tuple = lll.get(i);
            System.out.println("key:" + tuple._1() + ",value:" + tuple._2());
        }
    }
}
