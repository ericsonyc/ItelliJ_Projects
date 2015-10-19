package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.net.NetUtils;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by ericson on 2015/3/25 0025.
 */
public class Namenode implements Datanode2NamenodeProtocol {

    private Configuration conf;
    private Server rpcServer;

    public Namenode() throws IOException{
        conf=new Configuration();
        InetSocketAddress socAddr= NetUtils.createSocketAddr(conf.get("dfs.namenode.address",""));
        int handlerCount=conf.getInt("dfs.namenode.handler.count",5);

    }

    /**
     * Return protocol version corresponding to protocol interface.
     *
     * @param protocol      The classname of the protocol interface
     * @param clientVersion The version of the protocol that the client speaks
     * @return the version that the server will speak
     * @throws java.io.IOException if any IO error occurs
     */
    @Override
    public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
        return Datanode2NamenodeProtocol.versionID;
    }

    /**
     * Return protocol version corresponding to protocol interface.
     *
     * @param protocol          The classname of the protocol interface
     * @param clientVersion     The version of the protocol that the client speaks
     * @param clientMethodsHash the hashcode of client protocol methods
     * @return the server protocol signature containing its version and
     * a list of its supported methods
     * @see org.apache.hadoop.ipc.ProtocolSignature#getProtocolSignature(VersionedProtocol, String,
     * long, int) for a default implementation
     */
    @Override
    public ProtocolSignature getProtocolSignature(String protocol, long clientVersion, int clientMethodsHash) throws IOException {
        return null;
    }

    @Override
    public String getString() {
        return "This is a remote call";
    }
}
