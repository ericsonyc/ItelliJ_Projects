package spark;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.List;

/**
 * Created by jtang on 2015/1/13 0013.
 */
public class BinaryRead {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("Binary");
        JavaSparkContext context = new JavaSparkContext(conf);
        String[] otherArgs = new GenericOptionsParser(context.hadoopConfiguration(), args).getRemainingArgs();
        if (otherArgs.length < 1) {
            System.err.println("error");
            System.exit(1);
        }
//        JavaPairRDD<String, PortableDataStream> rdd = context.binaryFiles(otherArgs[0], 4);
////        List<Tuple2<String, PortableDataStream>> list = rdd.collect();
////        for (int i = 0; i < list.size(); i++) {
////            Tuple2<String,PortableDataStream> tuple=list.get(i);
////            System.out.println("key:"+tuple._1());
////            System.out.println("value:"+tuple._2().getPath());
////        }
//        JavaRDD<String> objectrdd = rdd.flatMap(new FlatMapFunction<Tuple2<String, PortableDataStream>, String>() {
//            @Override
//            public Iterable<String> call(Tuple2<String, PortableDataStream> stringPortableDataStreamTuple2) throws Exception {
//                List<String> list = new ArrayList<String>();
//                String key = stringPortableDataStreamTuple2._1();
//                PortableDataStream stream = stringPortableDataStreamTuple2._2();
//                String result = key + "," + stream.getPath();
//                list.add(result);
//                return list;
//            }
//        });
//        String result = objectrdd.reduce(new Function2<String, String, String>() {
//            @Override
//            public String call(String o, String o2) throws Exception {
//                return o + "," + o2;
//            }
//        });
//        System.out.println(result);


        JavaPairRDD<FloatWritable, FloatWritable> rdd = context.sequenceFile(otherArgs[0], FloatWritable.class, FloatWritable.class, 10);

        List<Tuple2<FloatWritable, FloatWritable>> list = rdd.collect();
        for (int i = 0; i < list.size(); i++) {
            Tuple2<FloatWritable, FloatWritable> tuple = list.get(i);
            System.out.println("key:" + tuple._1().get());
            System.out.println("value" + tuple._2().get());
        }

        context.stop();
    }

}
