package rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.net.InetSocketAddress;

/**
 * Created by ericson on 2015/3/25 0025.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        OperatorAble proxy = (OperatorAble) RPC.waitForProxy(OperatorAble.class, OperatorAble.versionID, new InetSocketAddress(Conf.ADDR, Conf.PORT), new Configuration());
        for (int i = 0; i < 100; i++) {
            String talk = proxy.Talk("yc" + i);
            System.out.println(talk);
        }
        RPC.stopProxy(proxy);
    }
}
