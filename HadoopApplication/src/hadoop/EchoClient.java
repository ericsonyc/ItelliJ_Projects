package hadoop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class EchoClient {

    private SocketChannel socketChannel = null;
    private InetSocketAddress address = null;

    public EchoClient() throws IOException {
        socketChannel = SocketChannel.open();
        address = new InetSocketAddress("127.0.0.1", 8787);
    }

    public void send() throws Exception {
        socketChannel.connect(address);
        ByteBuffer buffer = ByteBuffer.allocate(100);
        int numRead = socketChannel.read(buffer);
        if (numRead > 0) {
            System.out.println("read Buffer:" + buffer.toString());
        }
        buffer.clear();
        buffer.putChar('g');
        socketChannel.write(buffer);
    }
}
