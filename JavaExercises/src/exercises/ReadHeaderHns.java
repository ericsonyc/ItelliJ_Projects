package exercises;

import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * Created by ericson on 2014/12/27 0027.
 */
public class ReadHeaderHns {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\new_gabon_3d_csps_06992_12071_tran.su";
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            dis.skipBytes(3600);
            dis.skipBytes(114);
            //dis.skipBytes(12);
            byte[] temp = new byte[2];
            dis.read(temp);
            for (int i = 0; i < temp.length / 2; i++) {
                byte t = temp[i];
                temp[i] = temp[temp.length - i - 1];
                temp[temp.length - i - 1] = t;
            }
            int hns = getShort(temp);
            //dis.close();
            System.out.println(hns);
            dis.skipBytes(126);
            dis.skipBytes(hns + 114);

            dis.read(temp);
            for (int i = 0; i < temp.length / 2; i++) {
                byte t = temp[i];
                temp[i] = temp[temp.length - i - 1];
                temp[temp.length - i - 1] = t;
            }
            hns = getShort(temp);
            System.out.println(hns);
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
}
