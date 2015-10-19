package hadoop;


import com.google.common.base.Stopwatch;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class ComputeIntensiveLocalizedSequenceFileInputFormat extends FileInputFormat<IntWritable, Text> {

    private static final double SPLIT_SLOP = 1.1;   // 10% slop
    static final String NUM_INPUT_FILES = "mapreduce.input.num.files";

    @Override
    public RecordReader<IntWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        System.out.println("createRecordReader");
        FileSplit filesplit = (FileSplit) split;
        System.out.println(filesplit.getPath().toString());
        return null;
    }

    /**
     * Generate the list of files and make them into FileSplits.
     * 该类兼顾了本地性和均匀分布
     */
    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        System.out.println("getSplits");
        FileInputFormat.setMaxInputSplitSize((Job) job, 1000000L);
        List<InputSplit> originalSplits = super.getSplits(job);
        System.out.println("originSplit:" + originalSplits.size());
        String[] servers = getActiveServersList(job);
        for (int i = 0; i < servers.length; i++) {
            System.out.println("server:" + servers[i]);
        }
        if (servers == null)
            return null;
        List<InputSplit> splits = new ArrayList<InputSplit>(originalSplits.size());
        int numSplits = originalSplits.size();
        //System.out.println("numSplits:" + numSplits);
        int currentServer = 0;
        for (int i = 0; i < numSplits; i++, currentServer = getNextServer(currentServer, servers.length)) {
            //System.out.println("numSplits:" + numSplits);
            String server = servers[currentServer];
            //System.out.println("server:" + server);
            boolean replaced = false;
            for (InputSplit split : originalSplits) {
                FileSplit fs = (FileSplit) split;
                //System.out.println("path:" + fs.getPath().toString());
                String[] locations = fs.getLocations();
                //System.out.println("locations.length:" + locations.length);
                for (String l : locations) {
                    //System.out.println("location:" + l);
                    if (l.equals(server)) {
                        //System.out.println("l equals server");
                        splits.add(new FileSplit(fs.getPath(), fs.getStart(), fs.getLength(), new String[]{server}));
                        originalSplits.remove(split);
                        replaced = true;
                        break;
                    }
                }
                if (replaced) {
                    //System.out.println("replaced");
                    break;
                }
            }
            if (!replaced) {
                FileSplit fs = (FileSplit) originalSplits.get(0);
                splits.add(new FileSplit(fs.getPath(), fs.getStart(), fs.getLength(), new String[]{server}));
                originalSplits.remove(0);
            }
        }
        return splits;
    }

    @Override
    protected List<FileStatus> listStatus(JobContext job) throws IOException {
        return super.listStatus(job);
    }

    private String[] getActiveServersList(JobContext context) {

        String[] servers = null;
        try {
            JobClient jc = new JobClient((JobConf) context.getConfiguration());
            ClusterStatus status = jc.getClusterStatus(true);
            Collection<String> atc = status.getActiveTrackerNames();
            servers = new String[atc.size()];
            int s = 0;
            for (String serverInfo : atc) {
                System.out.println("serverInfo:" + serverInfo);
                StringTokenizer st = new StringTokenizer(serverInfo, ":");
                String trackerName = st.nextToken();
                System.out.println("trackerName:" + trackerName);
                StringTokenizer st1 = new StringTokenizer(trackerName, "_");
                st1.nextToken();
                servers[s++] = st1.nextToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return servers;
    }

    private static int getNextServer(int current, int max) {
        System.out.println("current:" + current);
        current++;
        if (current >= max)
            current = 0;
        return current;
    }
}