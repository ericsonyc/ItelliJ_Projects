package exercises;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Created by ericson on 2014/12/28 0028.
 */
public class TxtToBinary {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\input\\tostxt.data";
            String output = "D:\\ktmigration\\data\\input\\tos1.data";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                for (int i = 0; i < splits.length; i++) {
                    byte[] temp=getBytes(Integer.parseInt(splits[i]));
                    swapBytes(temp);
                    dos.write(temp);
                    dos.flush();
                }
            }
            dos.close();
            br.close();
            System.out.println("over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void swapBytes(byte[] temp) {
        for (int j = 0; j < temp.length / 2; j++) {
            byte replace = temp[j];
            temp[j] = temp[temp.length - j - 1];
            temp[temp.length - j - 1] = replace;
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
