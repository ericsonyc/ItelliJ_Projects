package hadoop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class EchoServer extends Thread {

    private InetSocketAddress address = null;
    private String bindAddress = "127.0.0.1";
    private int port = 8787;
    private ServerSocketChannel acceptChannel = null;
    private int backlogLength = 10;
    private Selector selector = null;

    public Selector initSelector() throws IOException {
        address = new InetSocketAddress(bindAddress, port);
        acceptChannel = ServerSocketChannel.open();
        acceptChannel.configureBlocking(false);
        acceptChannel.socket().bind(address, backlogLength);
        Selector selector = Selector.open();
        acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("register selector");
        return selector;
    }

    public void setSelector(Selector s) {
        this.selector = s;
    }

    @Override
    public void run() {
        while (true) {
            SelectionKey key = null;
            try {
                System.out.println("begin wait");
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                System.out.println("handle event");
                while (iter.hasNext()) {
                    key = iter.next();
                    iter.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        doAccept(key);
                    } else if (key.isReadable()) {
                        receive(key);
                    } else if (key.isWritable()) {
                        send(key);
                    }
                    key = null;
                }
            } catch (Exception e) {

            }
        }
    }

    private void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        SelectionKey clientKey = socketChannel.register(selector, SelectionKey.OP_READ);
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        clientKey.attach(buffer);
    }

    private void receive(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = (ByteBuffer) key.attachment();
        int numRead = socketChannel.read(readBuffer);
        if (numRead > 0) {
            readBuffer.flip();
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void send(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer writeBuffer = (ByteBuffer) key.attachment();
        socketChannel.write(writeBuffer);
        if (writeBuffer.remaining() == 0) {
            writeBuffer.clear();
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}
