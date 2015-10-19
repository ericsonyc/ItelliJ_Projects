package spark;

import com.google.common.base.Optional;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KirchhoffSpark {

    private String outputDir = "/";
    private String shotsFile = outputDir + "shot.meta";
    private String cpuSxyFile = outputDir + "fsxy.meta";
    private String cpuGxyFile = outputDir + "fgxy.meta";
    private String cpuCxyFile = outputDir + "fcxy.meta";
    private String rmsvFile = outputDir + "rmsv.meta";
    private String imageFile = outputDir = "cpuktmig500.meta";

    public static void main(String[] args) throws Exception {
        // get the spark configure
        System.out.println("main");
        SparkConf conf = new SparkConf().setAppName("KirchhoffSpark");
        conf.set("spark.dynamicAllocation.enabled", "true");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        Configuration hadoopConf = jsc.hadoopConfiguration();
        String[] otherArgs = new GenericOptionsParser(hadoopConf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.out.println("enter path");
            System.exit(0);
        }
        //System.setProperty("spark.scheduler.mode", "FAIR");//公平分配资源


        KirchhoffSpark spark = new KirchhoffSpark();
        spark.shotsFile = otherArgs[0] + spark.shotsFile;
        spark.cpuSxyFile = otherArgs[0] + spark.cpuSxyFile;
        spark.cpuGxyFile = otherArgs[0] + spark.cpuGxyFile;
        spark.cpuCxyFile = otherArgs[0] + spark.cpuCxyFile;
        spark.rmsvFile = otherArgs[0] + spark.rmsvFile;
        spark.imageFile = otherArgs[0] + spark.imageFile;

        ProgramConf pconf = new ProgramConf(conf);
        pconf.setPath(otherArgs[0]);
        long start = System.currentTimeMillis();
        KirchhoffRead kirchhoff = new KirchhoffRead(pconf);
        System.out.println("begin KirchhoffRead");
        kirchhoff.setInputFile(spark.shotsFile, spark.cpuSxyFile,
                spark.cpuGxyFile, spark.cpuCxyFile, spark.rmsvFile);
        kirchhoff.setOutputFile(spark.imageFile);
        kirchhoff.setVerb(true);
        kirchhoff.setAntiAlising(true);
        kirchhoff.readData(jsc, conf);
        long end = System.currentTimeMillis();
        System.out.println("KirchhoffRead Time:" + (end - start) + "ms");


        /***
         * binary cycle read
         */
        List<Tuple2<Integer, String>> tempLists = new ArrayList<Tuple2<Integer, String>>();
        JavaPairRDD<Integer, String> resultRdd = jsc.parallelizePairs(tempLists);
        long startTime = System.currentTimeMillis();
        long numberBytes = 1 * 80L;
        hadoopConf.setLong("FileSplitLength", numberBytes);//文件逻辑切分字节
        hadoopConf.setInt("SplitPerMap", 1);//一个map中多少个键值对
        //JavaPairRDD<String, PortableDataStream> pairRdd = jsc.binaryFiles(otherArgs[0] + "data/fcxy.data", 18);

        int number = 1;
        long count = 0L;
        for (int i = 0; i < number; i++) {
//            JavaPairRDD<Integer, String> rdd = pairRdd.flatMapToPair(new MyPairFlatFunction(80, i)).repartition(10);
            hadoopConf.setInt("INDEX", i);
            JavaPairRDD<Integer, String> pairRdd = jsc.newAPIHadoopFile(otherArgs[0]
                            + "/fcxy.data", MyInputFormat.class, Integer.class,
                    String.class, hadoopConf).flatMapToPair(new SplitKeyValues());
            pairRdd = pairRdd.partitionBy(new ScalaPartitioner(18, pairRdd.count()));
            //pairRdd.count();
            //System.out.println("ccc:" + pairRdd.count());
            //count = count + pairRdd.count();
            //System.out.println("count:" + count + " , i:" + i);
            //pairRdd = pairRdd.partitionBy(new ScalaPartitioner(36, pairRdd.count()));
//            List<Partition> lists = pairRdd.partitions();
//            for (int t = 0; t < lists.size(); t++) {
//                Partition partition = lists.get(t);
//                System.out.println("index:" + partition.index());
//            }
//            System.out.println("partitioner:" + lists.size());
            //VoidMapFunction voidmap = new VoidMapFunction(pconf);
            JavaPairRDD<Integer, String> pair = pairRdd.mapPartitionsToPair(new MyMapPartition(pconf));
            pair.count();

            //pairRdd.foreachPartition(voidmap);
            //Thread.currentThread().join();
            System.out.println("systemTimeMain:" + Calendar.getInstance().get(Calendar.SECOND));
            //List<Tuple2<Integer, String>> output = voidmap.getArrays();
            //System.out.println("output.size:" + output.size());
            //JavaPairRDD<Integer, String> pair = jsc.parallelizePairs(output, 18);
//            JavaPairRDD<Integer, String> output = pairRdd.flatMapToPair(new MyPairMapFunction(pconf)).reduceByKey(new ReduceFuction());
//            output.first();
////            System.out.println("output count finish");
            resultRdd = resultRdd.fullOuterJoin(pair).flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>>, Integer, String>() {
                @Override
                public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>> integerTuple2Tuple2) throws Exception {
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
            resultRdd.count();
//            long s = System.nanoTime();
//            resultRdd.first();
//            long e = System.nanoTime();
//            System.out.println("Time:" + (e - s) + "ns");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time:" + (endTime - startTime) + "ms");


//        int numberSplits = 1;
//        for (int i = 0; i < numberSplits; i++) {
//            System.out.println("begin RDD");
//            hadoopConf.setInt("INDEX", i);
//            JavaPairRDD<Integer, String> pairRdd = jsc.newAPIHadoopFile(otherArgs[0]
//                            + "data/fcxy.data", MyInputFormat.class, Integer.class,
//                    String.class, hadoopConf);
//            JavaPairRDD<Integer, String> pairRDD1 = pairRdd.flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, String>, Integer, String>() {
//                @Override
//                public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
//                    List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
//                    int key = integerStringTuple2._1();
//                    String[] datas = integerStringTuple2._2().split(",");
//                    for (int i = 0; i < datas.length / 2; i++) {
//                        String data = datas[2 * i] + "," + datas[2 * i + 1];
//                        Tuple2<Integer, String> tuple = new Tuple2<Integer, String>(key + i, data);
//                        lists.add(i, tuple);
//                    }
//                    return lists;
//                }
//            }).repartition(90);
//            System.out.println("splitlength" + pairRDD1.count());
//
//            resultRdd = resultRdd.fullOuterJoin(pairRDD1.flatMapToPair(new MyPairMapFunction(pconf)).reduceByKey(new ReduceFuction())).flatMapToPair(new PairFlatMapFunction<Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>>, Integer, String>() {
//                @Override
//                public Iterable<Tuple2<Integer, String>> call(Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>> integerTuple2Tuple2) throws Exception {
//                    List<Tuple2<Integer, String>> lists = new ArrayList<Tuple2<Integer, String>>();
//                    int key = integerTuple2Tuple2._1();
//                    Tuple2<Optional<String>, Optional<String>> tuple = integerTuple2Tuple2._2();
//                    Optional<String> optional1 = tuple._1();
//                    Optional<String> optional2 = tuple._2();
//                    String result = "";
//                    float[] data1 = null;
//                    float[] data2 = null;
//                    if (optional1.isPresent()) {
//                        String[] stemp = optional1.get().split(",");
//                        data1 = new float[stemp.length];
//                        for (int i = 0; i < stemp.length; i++) {
//                            data1[i] = Float.parseFloat(stemp[i]);
//                        }
//                        stemp = null;
//                    }
//                    if (optional2.isPresent()) {
//                        String[] stemp = optional2.get().split(",");
//                        data2 = new float[stemp.length];
//                        for (int i = 0; i < stemp.length; i++) {
//                            data2[i] = Float.parseFloat(stemp[i]);
//                        }
//                        stemp = null;
//                    }
//                    if (data1 == null) {
//                        for (int i = 0; i < data2.length; i++) {
//                            result += data2[i] + ",";
//                        }
//                    } else if (data2 == null) {
//                        for (int i = 0; i < data1.length; i++) {
//                            result += data1[i] + ",";
//                        }
//                    } else {
//                        for (int i = 0; i < data1.length; i++) {
//                            result += (data1[i] + data2[i]) + ",";
//                        }
//                    }
//                    result = result.substring(0, result.lastIndexOf(","));
//                    lists.add(new Tuple2<Integer, String>(key, result));
//                    return lists;
//                }
//            }).persist(StorageLevel.MEMORY_AND_DISK());
//            resultRdd.count();
//        }


//        List<Partition> partitions = pairRDD1.partitions();
//        int numberFilter = partitions.size();
//
//        for (int i = 0; i < numberFilter; i++) {
//
//            //filter rdd
//            //JavaPairRDD<Integer, String> filterRdd = pairRDD1.filter(new FilterFunction(numberFilter, i));
//            //System.out.println("count:" + filterRdd.count());
//
//            //map function rdd
//            //JavaPairRDD<Integer, String> middleReduce = filterRdd.flatMapToPair(new MyPairMapFunction(pconf));
//
//            //reduce function and join result
//
//        }

		/*
         * this is the map process: key:the output value:output+"#"+input
		 */
//        System.out.println("begin mappair");
//        JavaPairRDD<Integer, String> output = pairRdd
//                .flatMapToPair(new MyPairMapFunction(pconf));

        // //pair.unpersist(false);
        // output.persist(StorageLevel.MEMORY_AND_DISK_SER());
        // System.out.println("output");
        // List<Tuple2<Integer, String>> pairRddList1 = output.collect();
        // System.out.println("pairRDD length:"+pairRddList1.size());
        // for (int i = 0; i < pairRddList1.size(); i++) {
        // Tuple2<Integer, String> tuple = pairRddList1.get(i);
        // System.out.println("key: " + tuple._1());
        // System.out.println("value: " + tuple._2().split(",").length);
        // }

//        System.out.println("begin reduce");
//        JavaPairRDD<Integer, String> rdd = output.reduceByKey(new ReduceFuction(), 10);

        // Path path=new Path("cpuktmig500.data");
        // try{
        // FileSystem fs=FileSystem.get(hadoopConf);
        // if(fs.exists(path)){
        // fs.delete(path, true);
        // }
        // }catch(Exception e){
        // e.printStackTrace();
        // }
        //

        try {
            Path path = new Path(otherArgs[0] + otherArgs[1]);
            FileSystem fs = FileSystem.get(hadoopConf);
            if (fs.exists(path)) {
                fs.delete(path, true);
            }
            System.out.println("begin collect and write");

            resultRdd.saveAsNewAPIHadoopFile(path.toString(), Integer.class,
                    String.class, MyOutputFormat.class, hadoopConf);

            FileStatus[] status = fs.listStatus(path);
            ArrayList<Integer> keys = new ArrayList<Integer>();
            ArrayList<String> values = new ArrayList<String>();
            for (FileStatus ss : status) {
                FSDataInputStream fsIn = fs.open(ss.getPath());
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        fsIn));
                String str = null;
                while ((str = br.readLine()) != null) {
                    String[] datas = str.split("#");
                    keys.add(Integer.parseInt(datas[0]));
                    values.add(datas[1]);
                }
                br.close();
                fsIn.close();
            }

            for (int i = 0; i < keys.size(); i++) {
                for (int j = 0; j < keys.size() - i - 1; j++) {
                    if (keys.get(j) > keys.get(j + 1)) {
                        int temp = keys.get(j);
                        keys.set(j, keys.get(j + 1));
                        keys.set(j + 1, temp);
                        String t = values.get(j);
                        values.set(j, values.get(j + 1));
                        values.set(j + 1, t);
                    }
                }
            }
            fs.delete(path, true);
            writeImage(hadoopConf, path, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // output.unpersist();
        jsc.stop();
    }

    private static boolean writeImage(Configuration conf, Path rote,
                                      ArrayList<String> values) {
        if (rote == null) {
            return false;
        }
        try {
            FileSystem fs = FileSystem.get(conf);
            if (fs.exists(rote)) {
                fs.delete(rote, true);
            }
            Path result = new Path(rote.toString() + "/cpuktmig500.data");
            FSDataOutputStream fsout = fs.create(result);
            for (int i = 0; i < values.size(); i++) {
                String[] temp = values.get(i).split(",");
                for (int j = 0; j < temp.length; j++) {
                    int x = Float.floatToIntBits(Float.parseFloat(temp[j]));
                    fsout.write(getBytes(x));
                }
                fsout.flush();
            }
            fsout.close();

            // System.out.println("the length of the count is:"+count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }
}
