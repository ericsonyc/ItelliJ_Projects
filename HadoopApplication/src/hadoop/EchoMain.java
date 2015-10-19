package hadoop;

import java.nio.channels.Selector;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class EchoMain {
    public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer();
        Selector s = server.initSelector();
        server.setSelector(s);
        server.start();
        System.out.println("begin client");
        EchoClient client = new EchoClient();
        client.send();
    }
}
