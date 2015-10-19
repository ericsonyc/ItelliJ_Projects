package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.map.MultithreadedMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by jtang on 2015/1/10 0010.
 */
public class xml_test {
    public static int getFileInt(String filename) {
        int temp = 0;
        Configuration config = new Configuration();
        FSDataInputStream dis = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileSystem hdfs = FileSystem.get(config);
            dis = hdfs.open(new Path(filename));
            IOUtils.copyBytes(dis, baos, 4096, false);
            String str = baos.toString();
            str = str.substring(0, str.length() - 1);
            temp = Integer.parseInt(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(dis);
        }
        return temp;
    }

    public static class wordcountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        int temp2 = getFileInt("hdfs://Master/user/jtang/int");
        private Text word = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            int temp1 = 0;
            Configuration mapconf = context.getConfiguration();
            temp1 = mapconf.getInt("count", 0);
            IntWritable one = new IntWritable(temp1 + temp2);
            String line = value.toString();
            StringTokenizer itr = new StringTokenizer(line);
            while (itr.hasMoreElements()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
    }

    public static class wordcountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable str : values) {
                sum += str.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInt("count", 2);
        Job job = new Job(conf, "xml_test");
        job.setJarByClass(xml_test.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(wordcountMapper.class);
        job.setReducerClass(wordcountReduce.class);
        job.setCombinerClass(wordcountReduce.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
    }
}
