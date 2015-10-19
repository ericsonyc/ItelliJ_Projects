package hadoop;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ericson on 2015/3/12 0012.
 */
public class ReduceTestJni extends Reducer<Text, Text, Text, Text> {
    /**
     * This method is called once for each key. Most applications will define
     * their reduce class by overriding this method. The default implementation
     * is an identity function.
     *
     * @param key
     * @param values
     * @param context
     */
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String outString = "";
        for (Text value : values) {
            outString = value.toString();
        }
        context.write(key, new Text(outString));
    }
}
