package exercises;

import java.io.*;

/**
 * Created by ericson on 2014/12/29 0029.
 */
public class GetSmallData {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\input\\new_gabon_3d_csps_06992_12071_tran.su";
            String output = "D:\\ktmigration\\data\\input\\new_gabon_3d_csps_06992_12071_tran1.su";
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
            byte[] temp = new byte[1200 * 4 + 240];
            for (int i = 0; i < 2000000; i++) {
                dis.read(temp);
                dos.write(temp);
                dos.flush();
            }
            dos.close();
            dis.close();
            System.out.println("over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
