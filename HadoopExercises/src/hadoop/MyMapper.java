package hadoop;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ericson on 2015/1/24 0024.
 */
public class MyMapper extends Mapper<Text, Text, Text, Text> {
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        System.out.println("keyMyMapper:" + key);
        context.write(key, value);
    }
}
