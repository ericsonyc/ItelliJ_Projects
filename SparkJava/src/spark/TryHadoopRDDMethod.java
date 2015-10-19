package spark;

import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;

/**
 * Created by jtang on 2015/1/15 0015.
 */
public class TryHadoopRDDMethod {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("hadoopRdd");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        try {
            String[] otherArgs = new GenericOptionsParser(jsc.hadoopConfiguration(), args).getRemainingArgs();
            long start = System.currentTimeMillis();
            JavaPairRDD<String, PortableDataStream> pairRDD = jsc.binaryFiles(otherArgs[0] + "data/fcxy.data", 1);
            int number = 174;
            long count = 0L;
            for (int i = 0; i < number; i++) {
                JavaPairRDD<Integer, String> rdd = pairRDD.flatMapToPair(new MyPairFlatFunction(8000, i));
                rdd.partitionBy(new ScalaPartitioner(18));
                count = count + rdd.count();
                System.out.println("count:" + count + " , i:" + i);
            }

            long end = System.currentTimeMillis();
            System.out.println("Time:" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
