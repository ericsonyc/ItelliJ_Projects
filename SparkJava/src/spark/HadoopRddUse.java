package spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.List;

/**
 * Created by jtang on 2015/1/15 0015.
 */
public class HadoopRddUse {
    public static void main(String[] args) {
        try {
            SparkConf conf = new SparkConf().setAppName("hadoopConf");
            JavaSparkContext jsc = new JavaSparkContext(conf);
            long start = System.currentTimeMillis();
            Job job = Job.getInstance();
            Configuration hadoopConf = job.getConfiguration();
            hadoopConf.setLong("FileSplitLength", 1 * 8000L);//文件逻辑切分字节
            hadoopConf.setInt("SplitPerMap", 1);//一个map中多少个键值对
            FileInputFormat.addInputPath(job, new Path(args[0]));
            JavaPairRDD<Integer, String> pairRDD = jsc.newAPIHadoopRDD(hadoopConf, MyInputFormat.class, Integer.class, String.class);
            List<Tuple2<Integer, String>> lists = pairRDD.collect();
            long end = System.currentTimeMillis();
            System.out.println("lists.length:" + lists.size());
            for (int i = 0; i < lists.size(); i++) {
                Tuple2<Integer, String> tuple = lists.get(i);
                System.out.println("key:" + tuple._1());
            }
            System.out.println("Time:" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
