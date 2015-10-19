package exercises;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ericson on 2014/12/28 0028.
 */
public class BinaryConvert {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\input\\new_gabon_3d_csps_06992_12071_tran.su";
            String output = "D:\\ktmigration\\data\\input\\Gabon_velocity_tran_cutslice1.dat";
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            //DataOutputStream dos=new DataOutputStream(new FileOutputStream(output));
            dis.skipBytes(114);
            byte[] temp2 = new byte[2];
            byte[] temp4 = new byte[4];
            for (int j = 0; j < temp2.length; j++) {
                int temp = (int)temp2[j];
                System.out.println(temp);
            }
            dis.read(temp2);
            int data = getShort(temp2);
            System.out.println(data);

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
