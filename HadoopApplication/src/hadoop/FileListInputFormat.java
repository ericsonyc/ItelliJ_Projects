package hadoop;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by jtang on 2015/1/8 0008.
 */
public class FileListInputFormat extends FileInputFormat<Text, Text> {
    private static final String MAPCOUNT = "map.reduce.map.count";
    private static final String INPUTPATH = "mapred.input.dir";

    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        Configuration conf = new Configuration();//获取新配置
        Path[] paths = getInputPaths(job);
        String[] hosts = getActiveServersList(job);
        Path p = new Path(StringUtils.unEscapeString(paths[0].toString()));
        System.out.println("path:" + p.toString());
        List<InputSplit> splits = new LinkedList<InputSplit>();
        FileSystem fs = p.getFileSystem(conf);
        int mappers = 0;
        try {
            mappers = Integer.parseInt(conf.get(MAPCOUNT, "2"));
            System.out.println("mapcount:" + mappers);
        } catch (Exception e) {
        }
        if (mappers == 0) {
            throw new IOException("Number of mappers is not specified");
        }
        FileStatus[] files = fs.globStatus(p);
        int nfiles = files.length;
        System.out.println("filelength:" + nfiles);
        if (nfiles < mappers)
            mappers = nfiles;
        for (int i = 0; i < mappers; i++) {
            splits.add(new MultiFIleSplit(0, hosts));
        }
        Iterator<InputSplit> siter = splits.iterator();
        for (FileStatus f : files) {
            if (!siter.hasNext()) {
                siter = splits.iterator();
            }
            ((MultiFIleSplit) (siter.next())).addFile(f.getPath().toUri().getPath());
        }
        return splits;
    }

    public static void setMapCount(Job job, int mappers) {
        Configuration conf = job.getConfiguration();
        conf.set(MAPCOUNT, new Integer(mappers).toString());
    }

    @Override
    public RecordReader<Text, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return null;
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
}
