package spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by ericson on 2015/1/19 0019.
 */
public class SparkMapfunction {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("sparkMap");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        Configuration conf=jsc.hadoopConfiguration();

        jsc.stop();
    }
}
