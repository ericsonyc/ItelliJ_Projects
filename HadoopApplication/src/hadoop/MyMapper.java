package hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MyMapper extends Mapper<IntWritable, Text, Text, Text> {

	// private Logger LOG=Logger.getLogger("MAP");
	@Override
	protected void map(IntWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// LOG.info("key:"+key.toString());
		// LOG.info("value:"+value.toString());
		context.write(new Text(String.valueOf(key.get())), value);
	}
}
