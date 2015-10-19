package hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ericson on 2015/1/27 0027.
 */
public class WriteMap extends Mapper<IntWritable,Text,Text,Text> {
    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println("writemap map");

    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        System.out.println("writemap setup");
    }

    @Override
    public void run(Context context) throws IOException, InterruptedException {
        System.out.println("writemap run");
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        System.out.println("writemap cleanup");
    }
}
