package hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ericson on 2015/1/25 0025.
 */
public class ThreadMap extends Mapper<IntWritable, Text, IntWritable, Text> {
    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println("threadmap map");
        context.write(key, value);
    }

}
