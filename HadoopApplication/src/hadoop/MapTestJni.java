package hadoop;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;

/**
 * Created by ericson on 2015/3/12 0012.
 */
public class MapTestJni extends Mapper<Writable, Text, Text, Text> {
    protected String s;

    /**
     * Called once at the beginning of the task.
     *
     * @param context
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Random ran=new Random();
        int numElements=50000;
        float[] datas=new float[numElements];
        String result="";
        for(int i=0;i<numElements;i++){
            datas[i]=ran.nextInt(5);
            result+=datas[i];
        }
        result=result.substring(0,result.lastIndexOf(","));
        //s = FakeSegmentForJni.SegmentALine("jni-value");
        byte[] bytes = s.getBytes("utf-8");
        s = new String(bytes, "utf-8");
    }

    /**
     * Called once for each key/value pair in the input split. Most applications
     * should override this, but the default is the identity function.
     *
     * @param key
     * @param value
     * @param context
     */
    @Override
    protected void map(Writable key, Text value, Context context) throws IOException, InterruptedException {
        context.write(new Text("key"), new Text(s.toString()));
    }
}
