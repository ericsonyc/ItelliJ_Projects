package exercises;

import java.io.*;

/**
 * Created by ericson on 2014/12/31 0031.
 */
public class SwapBinary {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\input\\tostxt.data";
            String output = "D:\\ktmigration\\data\\input\\tos1.data";
            BufferedReader dis = new BufferedReader(new FileReader(filePath));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
            String line = null;
            while ((line = dis.readLine()) != null) {
                String[] splits = line.split(",");
                for(int i=0;i<splits.length;i++){
                    dos.write(getBytes(Integer.parseInt(splits[i])));
                }
                dos.flush();
            }
            System.out.println("write over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }
}
