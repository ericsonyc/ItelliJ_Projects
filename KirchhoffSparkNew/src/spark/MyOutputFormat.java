package spark;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MyOutputFormat extends FileOutputFormat<Integer, String> {

    @Override
    public RecordWriter<Integer, String> getRecordWriter(TaskAttemptContext context)
            throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        Path output = FileOutputFormat.getOutputPath(context);
        System.out.println("path:" + output.toString());
        String file = context.getTaskAttemptID().getTaskID().toString();
        Path path = new Path(output + "/r"
                + file.substring(file.lastIndexOf("_") + 1, file.length()));
        FSDataOutputStream fsIn = path
                .getFileSystem(context.getConfiguration()).create(path);
        return new CustomRecordWriter(fsIn);
    }

}
