package cudaHadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ericson on 2015/3/15 0015.
 */
public class CudaMapper extends Mapper<IntWritable, Text, Text, Text> {
    /**
     * Called once for each key/value pair in the input split. Most applications
     * should override this, but the default is the identity function.
     *
     * @param key
     * @param value
     * @param context
     */
    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {

    }
}
