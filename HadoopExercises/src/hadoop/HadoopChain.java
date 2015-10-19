package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by ericson on 2015/1/23 0023.
 */
public class HadoopChain {
    private static class AMapper01 extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String text = value.toString();
            String texts[] = text.split(" ");
//            System.out.println("AMapper01里面的数据：" + text);
            if (texts[1] != null && texts[1].length() > 0) {
                int count = Integer.parseInt(texts[1]);
                if (count > 10000) {
//                    System.out.println("AMapper01过滤掉大于10000数据：" + value.toString());
                    return;
                } else {
                    context.write(new Text(texts[0]), new Text(texts[1]));
                }
            }
        }
    }

    private static class AMapper02 extends Mapper<Text, Text, Text, Text> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            int count = Integer.parseInt(value.toString());
            if (count >= 100 && count <= 10000) {
//                System.out.println("AMapper02过滤掉的小于10000大于100的数据：" + key + " " + value);
                return;
            } else {
                System.out.println("AMapper02:" + key.toString());
                context.write(key, value);
            }
        }
    }

    private static class AReducer03 extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            System.out.println("进到Reduce里");
            Iterator<Text> vvs = values.iterator();
            while (vvs.hasNext()) {
                Text t = vvs.next();
                sum += Integer.parseInt(t.toString());
            }
            context.write(key, new Text(sum + ""));
//            Iterator<Text> iterator = values.iterator();
//            String str = "";
//            while (iterator.hasNext()) {
//                str += iterator.next().toString() + ",";
//            }
//            str = str.substring(0, str.lastIndexOf(","));
//            System.out.println("values:" + str);
//            context.write(key, new Text(str));
        }
    }

    private static class AReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Iterator<Text> iterator = values.iterator();
            String str = "";
            while (iterator.hasNext()) {
                str += iterator.next().toString() + ",";
            }
            str = str.substring(0, str.lastIndexOf(","));
            System.out.println("values:" + str);
            context.write(key, new Text(str));
        }
    }

    private static class AMapper04 extends Mapper<Text, Text, Text, Text> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            int len = key.toString().trim().length();
            if (len >= 3) {
//                System.out.println("Reduce后的Mapper过滤掉长度大于3的商品名：" + key.toString() + " " + value.toString());
                return;
            } else {
                System.out.println("AMpper04:" + key.toString());
                context.write(key, value);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(HadoopChain.class);
        job.setJobName("HadoopChain");
//        MultiMap.setNumberOfThreads(job, 2);
//        MultiMap.setMapperClass(job, MyMapper.class);
        ChainMapper.addMapper(job, AMapper01.class, LongWritable.class, Text.class, Text.class, Text.class, conf);
//        ChainMapper.addMapper(job, AMapper02.class, Text.class, Text.class, Text.class, Text.class, conf);
//        MultiMap.setNumberOfThreads(job, 4);
//        MultiMap.setMapperClass(job, MyMapper.class);
        //job.setMapperClass(MultithreadedMapper.class);
//        ChainMapper.addMapper(job, ThreadMap.class, Text.class, Text.class, Text.class, Text.class, conf);
        job.setPartitionerClass(MyPartitioner.class);
        job.setNumReduceTasks(2);
        ChainReducer.setReducer(job, AReducer03.class, Text.class, Text.class, Text.class, Text.class, conf);
        ChainReducer.addMapper(job, AMapper04.class, Text.class, Text.class, Text.class, Text.class, conf);
        ChainReducer.addMapper(job, AMapper02.class, Text.class, Text.class, Text.class, Text.class, conf);

        //job.setMapperClass(ChainMapper.class);
        //job.setReducerClass(AReducer03.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileSystem fs = FileSystem.get(conf);
        Path op = new Path(args[1]);
        if (fs.exists(op)) {
            fs.delete(op, true);
        }
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, op);
        job.waitForCompletion(true);
    }
}
