package hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.map.MultithreadedMapper;

import java.io.IOException;

/**
 * Created by jtang on 2015/1/12 0012.
 */
public class Multithreadmap extends MultithreadedMapper<IntWritable, Text, Text, Text> {

    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        context.write(new Text(String.valueOf(key.get())), new Text("1"));
    }
}
