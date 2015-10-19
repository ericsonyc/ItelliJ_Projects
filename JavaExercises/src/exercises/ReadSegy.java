package exercises;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

/**
 * Created by ericson on 2014/12/27 0027.
 */
public class ReadSegy {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\sigsbee2a_migration_velocity.sgy";
            RandomAccessFile ran = new RandomAccessFile(filePath, "r");
            long size = ran.length();
            ran.close();
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            dis.skipBytes(3200);
            byte[] temp2 = new byte[2];
            byte[] temp4 = new byte[4];
            dis.skipBytes(20);
            dis.read(temp2);
            swapBytes(temp2);
            int data = getShort(temp2);
            System.out.println(data);


            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static short getShort(byte[] bytes) {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }

    private static void swapBytes(byte[] temp) {
        for (int j = 0; j < temp.length / 2; j++) {
            byte replace = temp[j];
            temp[j] = temp[temp.length - j - 1];
            temp[temp.length - j - 1] = replace;
        }
    }
}
