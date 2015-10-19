package hadoop;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by ericson on 2015/1/26 0026.
 */
public class MyPartitioner2 extends Partitioner<Text, Text> implements Configurable {

    private Configuration conf = null;

    /**
     * Set the configuration to be used by this object.
     *
     * @param conf
     */
    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    /**
     * Return the configuration used by this object.
     */
    @Override
    public Configuration getConf() {
        return conf;
    }

    public MyPartitioner2() {
        super();
    }

    @Override
    public int getPartition(Text text, Text text2, int numPartitions) {
        int value = Integer.parseInt(text2.toString());
        return 0;
    }
}
