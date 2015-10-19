package rpc;

import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * Created by ericson on 2015/3/25 0025.
 */
public interface OperatorAble extends VersionedProtocol {

    public static final long versionID=1L;

    public String Talk(String name);
}
