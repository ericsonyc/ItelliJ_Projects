package spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KirchhoffSpark {

    private String outputDir = "/";
    private String shotsFile = outputDir + "shot.meta";
    private String cpuSxyFile = outputDir + "fsxy.meta";
    private String cpuGxyFile = outputDir + "fgxy.meta";
    private String cpuCxyFile = outputDir + "fcxy.meta";
    private String rmsvFile = outputDir + "rmsv.meta";
    private String imageFile = outputDir + "cpuktmig500.meta";

    public static void main(String[] args) throws Exception {
        // get the spark configure
        System.out.println("main");
        SparkConf conf = new SparkConf().setAppName("KirchhoffSpark");
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

        long start = System.currentTimeMillis();
        KirchhoffRead kirchhoff = new KirchhoffRead(pconf);
        System.out.println("begin KirchhoffRead");
        kirchhoff.setInputFile(spark.shotsFile, spark.cpuSxyFile,
                spark.cpuGxyFile, spark.cpuCxyFile, spark.rmsvFile);
        kirchhoff.setOutputFile(spark.imageFile);
        kirchhoff.setVerb(true);
        kirchhoff.setAntiAlising(true);
        kirchhoff.readData(jsc);
        long end = System.currentTimeMillis();
        System.out.println("KirchhoffRead Time:" + (end - start) + "ms");

        pconf.setPath(otherArgs[0]);

        hadoopConf.setLong("FileSplitLength", 1 * 8000L);//文件逻辑切分字节
        hadoopConf.setInt("SplitPerMap", 1);//一个map中多少个键值对

		/*
         * read the hadoop file key:the offset of the bytes in file value:the
		 * data in the file
		 */
        System.out.println("begin RDD");
        JavaPairRDD<Integer, String> pairRdd = jsc.newAPIHadoopFile(otherArgs[0]
                        + "/fcxy.data", MyInputFormat.class, Integer.class,
                String.class, hadoopConf);
//        List<Tuple2<Integer,String>> tp= pairRdd.collect();
//        for(int i=0;i<tp.size();i++){
//            Tuple2<Integer,String> tuple=tp.get(i);
//            System.out.println("tuple:"+tuple._1());
//        }
        //int numPartition = 2000;

        //JavaPairRDD<Integer, String> pairRdd1 = pairRdd.repartition(numPartition).cache();
        // JavaPairRDD<Integer, String> pair = pairRdd.distinct(10);
        // pair.persist(StorageLevel.MEMORY_AND_DISK_SER());
        // List<Tuple2<Integer, String>> pairRddList = pairRdd1.collect();
        // for (int i = 0; i < pairRddList.size(); i++) {
        // Tuple2<Integer, String> tuple = pairRddList.get(i);
        // System.out.println("key: " + tuple._1());
        // // System.out.println("value: "+tuple._2());
        // System.out.println("length: " + tuple._2().split(",").length);
        // }

        // pairRdd1.foreachPartition(new VoidMapFunction(pconf));

        // pairRdd.saveAsTextFile(args[0]+"sparkkir");

		/*
         * this is the map process: key:the output value:output+"#"+input
		 */
        System.out.println("begin mappair");
        JavaPairRDD<Integer, String> output = pairRdd
                .flatMapToPair(new MyPairMapFunction(pconf));

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

        System.out.println("begin reduce");
        JavaPairRDD<Integer, String> rdd = output.reduceByKey(new ReduceFuction(), 30);
        rdd.count();
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

            rdd.saveAsNewAPIHadoopFile(path.toString(), Integer.class,
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
            //fs.delete(path, true);
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
