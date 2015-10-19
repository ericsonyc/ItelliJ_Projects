package leetcode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by ericson on 2015/8/14 0014.
 */
public class Client {
    public static void main(String[] args) {
        try {
//            Socket s = new Socket("localhost", 9999);
//            InputStream is = s.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String str = br.readLine();
//            System.out.println(str);
//            br.close();
//            isr.close();
//            is.close();
//            s.close();

            DatagramSocket ds=new DatagramSocket(9998);
            String str="abc";
            DatagramPacket dp=new DatagramPacket(str.getBytes(),0,str.length(), InetAddress.getByName("localhost"),9999);
            ds.send(dp);
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
