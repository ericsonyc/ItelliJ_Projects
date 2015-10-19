package hadoop;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.v2.app.job.TaskAttempt;

import java.util.HashMap;
import java.util.Map.Entry;

public class ContextWrite extends Thread {

    private HashMap<Integer, float[]> maps = null;
    private Context context = null;

    public ContextWrite(HashMap<Integer, float[]> maps, Context context) {
        this.maps = maps;
        this.context = context;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("write:");
        for (Entry<Integer, float[]> entry : maps.entrySet()) {
            int entryKey = entry.getKey();
            float[] value = entry.getValue();
            String result = "";
            for (int i = 0; i < value.length; i++) {
                result += value[i] + ",";
            }
            result = result.substring(0, result.lastIndexOf(","));
            try {

                context.write(new Text(String.valueOf(entryKey)), new Text(result));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
