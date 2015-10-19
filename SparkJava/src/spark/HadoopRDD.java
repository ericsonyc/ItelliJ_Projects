package spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.spark.Partition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtang on 2015/1/14 0014.
 */
public class HadoopRDD {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("hadooprdd");

        JavaSparkContext jsc = new JavaSparkContext(conf);

        Job job = null;
        String[] otherArgs = null;
        try {
            otherArgs = new GenericOptionsParser(jsc.hadoopConfiguration(), args).getRemainingArgs();
            job = Job.getInstance();
            FileInputFormat.setInputPaths(job, new Path(otherArgs[0]));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        Configuration hadoopConf = job.getConfiguration();
        hadoopConf.setLong("FileSplitLength", 1 * 8000L);//文件逻辑切分字节
        hadoopConf.setInt("SplitPerMap", 1);//一个map中多少个键值对
        long start = System.currentTimeMillis();
        JavaPairRDD<Integer, String> sourceData = jsc.newAPIHadoopRDD(hadoopConf, MyInputFormat.class, Integer.class, String.class);
        List<Partition> partions = sourceData.partitions();

        System.out.println("count:" + partions.size());
        long end = System.currentTimeMillis();
        System.out.println("rddTime:" + (end - start) + "ms");
        List<Integer> list = sourceData.keys().collect();
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i).toString());
//        }


        //*************************************************
        Configuration hadconf = jsc.hadoopConfiguration();
        hadconf.setLong("FileSplitLength", 125 * 8000L);//文件逻辑切分字节
        hadconf.setInt("SplitPerMap", 125);//一个map中多少个键值对
        long sstart = System.currentTimeMillis();


        //List<Partition> ppartions = pairRDD.partitions();
        //System.out.println("count:" + ppartions.size());
        long eend = System.currentTimeMillis();
        System.out.println("fileTime:" + (eend - sstart) + "ms");
//        List<Integer> keys = pairRDD.keys().collect();
//        for (int i = 0; i < keys.size(); i++) {
//            System.out.println("key:" + keys.get(i));
//        }

//        pairRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, String>>>() {
//            @Override
//            public void call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
//                int count = 0;
//                while (tuple2Iterator.hasNext()) {
//                    Tuple2<Integer, String> tuple = tuple2Iterator.next();
//                    count++;
//                    System.out.println("iterator:" + tuple._1());
//                }
//                System.out.println("count:" + count);
//            }
//        });


        final int number = 174;
        long count = 0;
        for (int i = 0; i < number; i++) {
            hadoopConf.setInt("INDEX", i);
            JavaPairRDD<Integer, String> pairRDD = jsc.newAPIHadoopFile(otherArgs[0], MyInputFormat.class, Integer.class, String.class, hadconf);
            JavaPairRDD<Integer, String> pairRDD1 = pairRDD.flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, String>, Integer, String>() {
                @Override
                public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                    List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
                    int key = integerStringTuple2._1();
                    String[] datas = integerStringTuple2._2().split(",");
                    for (int i = 0; i < datas.length / 2; i++) {
                        String data = datas[2 * i] + "," + datas[2 * i + 1];
                        Tuple2<Integer, String> tuple = new Tuple2<Integer, String>(key + i, data);
                        lists.add(i, tuple);
                    }
                    return lists;
                }
            }).repartition(200);
            //JavaPairRDD<Integer, String> filterRdd = pairRDD1.filter(new MyFunction(number, i));
//            filterRdd.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, String>>>() {
//                @Override
//                public void call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
//                    int count = 0;
//                    while (tuple2Iterator.hasNext()) {
//                        Tuple2<Integer, String> tuple = tuple2Iterator.next();
//                        //System.out.println("iterator:" + tuple._1());
//                        count++;
//                    }
//                    System.out.println("count:" + count);
//                }
//            });
            //pairRDD1=pairRDD1.subtractByKey(filterRdd);
            count += pairRDD1.count();

            //pairRDD1.count();
        }
        System.out.println("count:" + count);

//        pairRDD1.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, String>>>() {
//            @Override
//            public void call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
//                int count = 0;
//                while (tuple2Iterator.hasNext()) {
//                    Tuple2<Integer, String> tuple = tuple2Iterator.next();
//                    System.out.println("iterator:" + tuple._1());
//                    count++;
//                }
//                System.out.println("count:" + count);
//            }
//        });


        jsc.stop();
    }
}
