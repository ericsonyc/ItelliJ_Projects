package exercises;

import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * Created by ericson on 2015/1/1 0001.
 */
public class ReadCompare {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\sigsbee2a_nfs.sgy";
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            long start = System.currentTimeMillis();
            byte[] temp = new byte[4];
            for (int i = 0; i < 50000; i++) {
                dis.read(temp);
                getInt(temp);
            }
            dis.close();
            long end = System.currentTimeMillis();
            System.out.println("Time:" + (end - start) + "ms");
            dis = new DataInputStream(new FileInputStream(filePath));
            start=System.currentTimeMillis();
            byte[] temp1=new byte[4*50000];
            dis.read(temp1);
            byte[] four=new byte[4];
            for(int i=0;i<temp1.length/4;i++){
                four[0]=temp1[4*i];
                four[1]=temp1[4*i+1];
                four[2]=temp1[4*i+2];
                four[3]=temp1[4*i+3];
                getInt(four);
            }
            end=System.currentTimeMillis();
            System.out.println("Time:"+(end-start)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }
}