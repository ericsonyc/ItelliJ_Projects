package hadoop;

import org.apache.hadoop.ipc.ProtocolSignature;

import java.io.IOException;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class ClientProtocolImpl implements ClientProtocol {
    /**
     * Return protocol version corresponding to protocol interface.
     *
     * @param s
     * @param l
     * @return the version that the server will speak
     * @throws java.io.IOException if any IO error occurs
     */
    @Override
    public long getProtocolVersion(String s, long l) throws IOException {
        return ClientProtocol.versionID;
    }

    /**
     * Return protocol version corresponding to protocol interface.
     *
     * @param s
     * @param l
     * @param i @return the server protocol signature containing its version and
     *          a list of its supported methods
     * @see org.apache.hadoop.ipc.ProtocolSignature#getProtocolSignature(VersionedProtocol, String,
     * long, int) for a default implementation
     */
    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return null;
    }

    @Override
    public String echo(String value) throws IOException {
        return value;
    }

    @Override
    public int add(int v1, int v2) throws IOException {
        return v1 + v2;
    }
}
