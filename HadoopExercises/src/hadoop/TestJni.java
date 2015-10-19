package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Created by ericson on 2015/2/6 0006.
 */
public class TestJni {
    public static void main(String[] args) throws Exception {
        TestJni jni = new TestJni();
        jni.runTestJni(args);
    }

    public void runTestJni(String[] args) throws Exception {
        Configuration conf = new Configuration();
        GenericOptionsParser goparser = new GenericOptionsParser(conf, args);
        String[] otherArgs = goparser.getRemainingArgs();
        Job job;
        job = Job.getInstance(conf, "JniHadoop");
//        job.setJarByClass(TestFakeSegmentForJni.class);
//
//        job.setMapperClass(MapTestJni.class);
//        job.setMapOutputKeyClass(Text.class);
//        job.setMapOutputValueClass(Text.class);
//        job.setReducerClass(ReduceTestJni.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(1);
        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        job.waitForCompletion(true);

    }
}
