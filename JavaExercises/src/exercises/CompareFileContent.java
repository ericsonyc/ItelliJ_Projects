package exercises;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

/**
 * Created by ericson on 2014/12/23 0023.
 */
public class CompareFileContent {
    public static void main(String[] args) {
        try {
            String inputFile1 = "D:\\data.data";
            String inputFile2 = "D:\\sigsbee2a_migration_velocity.sgy";
            RandomAccessFile rand1 = new RandomAccessFile(inputFile1, "r");
            RandomAccessFile rand2 = new RandomAccessFile(inputFile2, "r");
            assert (rand1.length() == rand2.length());
            long size = rand1.length();
            int count = 0;
            byte[] temp = new byte[4];
            for (int i = 0; i < size / 4; i++) {
                rand1.read(temp);
                float value1=Float.intBitsToFloat(getInt(temp));
                rand2.read(temp);
                float value2=Float.intBitsToFloat(getInt(temp));
                System.out.println("value1:"+value1+" value2:"+value2);
                if(value1!=value2){
                    break;
                }
                count++;
            }
            System.out.println("Equal size:" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }
}
