package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/4/3 0003.
 */
public class SparkMain {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        JavaSparkContext jsc = new JavaSparkContext(conf);
        List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
        for (int i = 0; i < 100; i++) {
            Tuple2<Integer, String> tuple = new Tuple2<Integer, String>(i % 10, String.valueOf(i));
            lists.add(tuple);
        }
        JavaPairRDD<Integer, String> rdd = jsc.parallelizePairs(lists, 10);
        JavaPairRDD<Integer, String> pair = rdd.combineByKey(new Function<String, String>() {
            @Override
            public String call(String s) throws Exception {
                System.out.println("one:" + s);
                return s;
            }
        }, new Function2<String, String, String>() {
            @Override
            public String call(String s, String s2) throws Exception {
                System.out.println("two:" + s + "," + s2);
                return "world";
            }
        }, new Function2<String, String, String>() {
            @Override
            public String call(String s, String s2) throws Exception {
                System.out.println("three:" + s + "," + s2);
                return s + "," + s2;
            }
        });
        List<Tuple2<Integer, String>> result = pair.collect();
        for (int i = 0; i < result.size(); i++) {
            Tuple2<Integer, String> tuple = result.get(i);
            System.out.println("key:" + tuple._1() + ",value:" + tuple._2());
        }
    }
}
