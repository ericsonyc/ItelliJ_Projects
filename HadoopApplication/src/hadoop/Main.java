package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Created by ericson on 2015/3/12 0012.
 */
public class Main {
    public void runTestJni(String[] args) throws Exception {
        Configuration conf = new Configuration();
        GenericOptionsParser goparser = new GenericOptionsParser(conf, args);
        String[] otherargs = goparser.getRemainingArgs();

        Job job = new Job(conf, "TestForJni");
        job.setJarByClass(Main.class);
        job.setMapperClass(MapTestJni.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(ReduceTestJni.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(job, new Path(otherargs[0]));
        FileSystem fs=FileSystem.get(conf);
        Path path=new Path(otherargs[1]);
        if(fs.exists(path)){
            fs.delete(path,true);
        }
        FileOutputFormat.setOutputPath(job, path);
        job.waitForCompletion(true);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("begin main");
        new Main().runTestJni(args);
    }
}
