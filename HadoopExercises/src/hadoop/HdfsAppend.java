package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Created by ericson on 2015/1/26 0026.
 */
public class HdfsAppend {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        //System.setProperty("HADOOP_HOME", "~/hadoop");
//        conf.set("mapreduce.job.jar", "HadoopExercises.jar");
//        conf.set("fs.defaultFS", "hdfs://114.212.189.19:8020");
//        conf.set("mapreduce.jobtracker.address", "114.212.189.19:9001");
//        conf.set("yarn.resourcemanager.address", "114.212.189.19:8032");
        FileSystem fs = FileSystem.get(conf);
        FSDataOutputStream fsout = fs.append(new Path(args[0]));
        long offset = fsout.getPos();
        System.out.println("offset:" + offset);
    }
}
