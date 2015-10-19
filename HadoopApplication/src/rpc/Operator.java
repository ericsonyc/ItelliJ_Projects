package rpc;

import org.apache.hadoop.ipc.ProtocolSignature;

import java.io.IOException;

/**
 * Created by ericson on 2015/3/25 0025.
 */
public class Operator implements OperatorAble {


    @Override
    public String Talk(String name) {
        System.out.println("Operator is call ...");
        return "hello:" + name;
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
        return versionID;
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
}
