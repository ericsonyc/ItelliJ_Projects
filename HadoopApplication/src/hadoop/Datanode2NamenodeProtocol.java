package hadoop;

import org.apache.hadoop.ipc.VersionedProtocol;


/**
 * Created by ericson on 2015/3/25 0025.
 */
public interface Datanode2NamenodeProtocol extends VersionedProtocol {

    public static final long versionID=19L;

    public String getString();
}
