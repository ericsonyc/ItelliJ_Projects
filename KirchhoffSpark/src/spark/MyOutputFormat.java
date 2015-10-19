package spark;

import org.apache.hadoop.conf.Configuration;
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
        Configuration conf = context.getConfiguration();
        Path output = FileOutputFormat.getOutputPath(context);
        System.out.println("path:" + output.toString());
        String taskId = context.getTaskAttemptID().toString();
        taskId=taskId.substring(taskId.lastIndexOf("_"));
        String outputPath = output.toString() + "/map/" +taskId;
        return new CustomRecordWriter(outputPath, conf);
    }

}
