package leetcode;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by ericson on 2015/8/14 0014.
 */
public class Server {
    public static void main(String[] args) {
        try {
//            ServerSocket server = new ServerSocket(9999);
//            Socket socket = server.accept();
//            OutputStream os = socket.getOutputStream();
//            PrintWriter pw = new PrintWriter(os);
//            pw.print("now time=" + new Date());
//            pw.flush();
//            pw.close();
//            socket.close();
//            server.close();


            DatagramSocket datagramSocket=new DatagramSocket(9999);
            byte[] buff=new byte[1024];
            DatagramPacket dp=new DatagramPacket(buff,1024);
            datagramSocket.receive(dp);
            String str=new String(dp.getData(),0,dp.getLength());
            System.out.println(str);
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
