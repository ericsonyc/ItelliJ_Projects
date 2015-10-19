package spark;

import com.google.common.base.Optional;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jtang on 2015/1/14 0014.
 */
public class RddJoin {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("joinRdd");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
        for (int i = 0; i < 10; i++) {
            lists.add(i, new Tuple2<Integer, String>(i, "1,2,3"));
        }
        JavaPairRDD<Integer, String> rdd1 = jsc.parallelizePairs(lists, 5);
        List<Tuple2<Integer, String>> lists2 = new ArrayList<Tuple2<Integer, String>>();
        for (int i = 0; i < 5; i++) {
            lists2.add(i, new Tuple2<Integer, String>(i, "1,4,9"));
        }
        JavaPairRDD<Integer, String> rdd2 = jsc.parallelizePairs(lists2, 1);
//        JavaPairRDD<Integer, String> rdd = rdd1.join(rdd2, 5).flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, Tuple2<String, String>>, Integer, String>() {
//            @Override
//            public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, Tuple2<String, String>> integerTuple2Tuple2) throws Exception {
//                List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
//                int key = integerTuple2Tuple2._1();
//                Tuple2<String, String> tuple = integerTuple2Tuple2._2();
//                String result = tuple._1() + "," + tuple._2();
//                lists.add(new Tuple2<Integer, String>(key, result));
//                return lists;
//            }
//        });

//        JavaPairRDD<Integer, String> rdd = rdd1.rightOuterJoin(rdd2, 5).flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, Tuple2<Optional<String>, String>>, Integer, String>() {
//            @Override
//            public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, Tuple2<Optional<String>, String>> integerTuple2Tuple2) throws Exception {
//                List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
//                int key = integerTuple2Tuple2._1();
//                Tuple2<Optional<String>, String> optional = integerTuple2Tuple2._2();
//                Optional<String> op = optional._1();
//                String rightValue = optional._2();
//                String result = "";
//                if (op.isPresent()) {
//                    result = op.get();
//                }
//                result += "," + rightValue;
//                lists.add(new Tuple2<Integer, String>(key, result));
//                return lists;
//            }
//        });

        JavaPairRDD<Integer, String> rdd = rdd2.fullOuterJoin(rdd1, 5).flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>>, Integer, String>() {
            @Override
            public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>> integerTuple2Tuple2) throws Exception {
//                List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
//                int key = integerTuple2Tuple2._1();
//                Tuple2<Optional<String>, Optional<String>> tuple = integerTuple2Tuple2._2();
//                Optional<String> optional1 = tuple._1();
//                Optional<String> optional2 = tuple._2();
//                String result = "";
//                if (optional1.isPresent()) {
//                    result += optional1.get() + ",";
//                }
//                if (optional2.isPresent()) {
//                    result += optional2.get();
//                } else {
//                    result = result.substring(0, result.lastIndexOf(","));
//                }
//                lists.add(new Tuple2<Integer, String>(key, result));
//                return lists;

                List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
                int key = integerTuple2Tuple2._1();
                Tuple2<Optional<String>, Optional<String>> tuple = integerTuple2Tuple2._2();
                Optional<String> optional1 = tuple._1();
                Optional<String> optional2 = tuple._2();
                String result = "";
                float[] data1 = null;
                float[] data2 = null;
                if (optional1.isPresent()) {
                    String[] stemp = optional1.get().split(",");
                    data1 = new float[stemp.length];
                    for (int i = 0; i < stemp.length; i++) {
                        data1[i] = Float.parseFloat(stemp[i]);
                    }
                    stemp = null;
                }
                if (optional2.isPresent()) {
                    String[] stemp = optional2.get().split(",");
                    data2 = new float[stemp.length];
                    for (int i = 0; i < stemp.length; i++) {
                        data2[i] = Float.parseFloat(stemp[i]);
                    }
                    stemp = null;
                }
                if (data1 == null) {
                    for (int i = 0; i < data2.length; i++) {
                        result += data2[i] + ",";
                    }
                } else if (data2 == null) {
                    for (int i = 0; i < data1.length; i++) {
                        result += data1[i] + ",";
                    }
                } else {
                    for (int i = 0; i < data1.length; i++) {
                        result += (data1[i] + data2[i]) + ",";
                    }
                }
                result = result.substring(0, result.lastIndexOf(","));
                lists.add(new Tuple2<Integer, String>(key, result));
                return lists;
            }
        });

//        rdd.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, String>>>() {
//            @Override
//            public void call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
//                while (tuple2Iterator.hasNext()) {
//                    Tuple2<Integer, String> tuple = tuple2Iterator.next();
//                    System.out.println("key:" + tuple._1());
//                    System.out.println("value:" + tuple._2());
//                }
//            }
//        });
        rdd.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Tuple2<Integer, String>>, Integer, String>() {
            @Override
            public Iterable<Tuple2<Integer, String>> call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
                List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
                while (tuple2Iterator.hasNext()) {
                    Tuple2<Integer, String> tuple = tuple2Iterator.next();
                    lists.add(tuple);
                    int key = tuple._1();
                    String value = tuple._2();
                    System.out.println("key:" + key);
                    System.out.println("value:" + value);
                }
                return lists;
            }
        }).count();
        jsc.stop();
    }
}
