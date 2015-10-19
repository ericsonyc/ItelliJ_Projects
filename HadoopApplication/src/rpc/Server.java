package rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

/**
 * Created by ericson on 2015/3/25 0025.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        org.apache.hadoop.ipc.RPC.Server server = new RPC.Builder(new Configuration()).setBindAddress(Conf.ADDR).setPort(Conf.PORT).setProtocol(OperatorAble.class).setInstance(new Operator()).build();
        server.start();
    }
}
