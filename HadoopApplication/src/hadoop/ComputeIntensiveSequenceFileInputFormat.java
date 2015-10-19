package hadoop;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class ComputeIntensiveSequenceFileInputFormat<K, V>
        extends SequenceFileInputFormat<K, V> {

    private static final double SPLIT_SLOP = 1.1;   // 10% slop
    static final String NUM_INPUT_FILES = "mapreduce.input.num.files";

    /**
     * Generate the list of files and make them into FileSplits.
     */
    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {

        long minSize = Math.max(getFormatMinSplitSize(), getMinSplitSize(job));
        System.out.println("minSize:" + minSize);
        long maxSize = getMaxSplitSize(job);
        System.out.println("maxSize:" + maxSize);

        // generate splits
        List<InputSplit> splits = new ArrayList<InputSplit>();
        List<FileStatus> files = listStatus(job);
        System.out.println("files.length:" + files.size());
        String[] servers = getActiveServersList(job);
        if (servers == null)
            return null;
        int currentServer = 0;
        for (FileStatus file : files) {
            Path path = file.getPath();
            System.out.println("path:" + path.toString());
//			FileSystem fs = path.getFileSystem(job.getConfiguration());
            long length = file.getLen();
            System.out.println("length:" + length);
//			BlockLocation[] blkLocations = fs.getFileBlockLocations(file, 0, length);
            if ((length != 0) && isSplitable(job, path)) {
                long blockSize = file.getBlockSize();
                System.out.println("blockSize:" + blockSize);
                long splitSize = computeSplitSize(blockSize, minSize, maxSize);
                System.out.println("splitSize:" + splitSize);

                long bytesRemaining = length;
                while (((double) bytesRemaining) / splitSize > SPLIT_SLOP) {
//					int blkIndex = getBlockIndex(blkLocations, length-bytesRemaining);
//					splits.add(new FileSplit(path, length-bytesRemaining, splitSize, 
//					blkLocations[blkIndex].getHosts()));
                    splits.add(new FileSplit(path, length - bytesRemaining, splitSize,
                            new String[]{servers[currentServer]}));
                    currentServer = getNextServer(currentServer, servers.length);
                    bytesRemaining -= splitSize;
                }

                if (bytesRemaining != 0) {
                    splits.add(new FileSplit(path, length - bytesRemaining, bytesRemaining,
                            new String[]{servers[currentServer]}));
                    currentServer = getNextServer(currentServer, servers.length);
                }
            } else if (length != 0) {
                System.out.println("isSplit:False");
                splits.add(new FileSplit(path, 0, length,
                        new String[]{servers[currentServer]}));
                currentServer = getNextServer(currentServer, servers.length);
            } else {
                System.out.println("length:0");
                //Create empty hosts array for zero length files
                splits.add(new FileSplit(path, 0, length, new String[0]));
            }
        }

        // Save the number of input files in the job-conf
        job.getConfiguration().setLong(NUM_INPUT_FILES, files.size());

        return splits;
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