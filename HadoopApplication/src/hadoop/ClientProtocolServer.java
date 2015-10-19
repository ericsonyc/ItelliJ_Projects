package hadoop;

import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class ClientProtocolServer {
    public static void main(String[] args) throws IOException {
        org.apache.hadoop.ipc.Server server = RPC.Server.get();
        server.start();
    }
}
