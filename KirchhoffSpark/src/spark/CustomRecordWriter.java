package spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class CustomRecordWriter extends RecordWriter<Integer, String> {

    private FSDataOutputStream fsOut = null;
    private Configuration conf = null;
    private String output = null;

    public CustomRecordWriter(String output, Configuration conf) {
        // TODO Auto-generated constructor stub
        this.conf = conf;
        this.output = output;
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException,
            InterruptedException {
        // TODO Auto-generated method stub
        if (fsOut != null)
            fsOut.close();
    }

    @Override
    public void write(Integer key, String value) throws IOException,
            InterruptedException {
        // TODO Auto-generated method stub
        try {
            if (fsOut == null) {
                FileSystem fs = FileSystem.get(conf);
                Path out = new Path(output + "_" + key);
                if (fs.exists(out)) {
                    fs.delete(out, true);
                }
                fsOut = fs.create(out, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("write key:" + key);
        if (fsOut != null) {
            String[] values = value.split(",");
            byte[] temp = new byte[values.length * Float.SIZE / 8];
            float[] datas = new float[values.length];
            for (int i = 0; i < values.length; i++) {
                datas[i] = Float.parseFloat(values[i]);
            }
            values = null;
            this.getBytes(datas, temp);
            fsOut.write(temp, 0, temp.length);
        }
    }

    public void getBytes(float[] data, byte[] bytes) {
        int offset = 0;
        int size = Float.SIZE / 8;
        for (int i = 0; i < data.length; i++) {
            offset = size * i;
            int d = Float.floatToIntBits(data[i]);
            bytes[offset] = (byte) (d & 0xff);
            bytes[1 + offset] = (byte) ((d & 0xff00) >> 8);
            bytes[2 + offset] = (byte) ((d & 0xff0000) >> 16);
            bytes[3 + offset] = (byte) ((d & 0xff000000) >> 24);
        }
    }

    public void getBytes(int data, byte[] bytes, int offset) {
        bytes[offset] = (byte) (data & 0xff);
        bytes[1 + offset] = (byte) ((data & 0xff00) >> 8);
        bytes[2 + offset] = (byte) ((data & 0xff0000) >> 16);
        bytes[3 + offset] = (byte) ((data & 0xff000000) >> 24);
    }

}
