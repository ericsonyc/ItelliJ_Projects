package exercises;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2014/12/27 0027.
 */
public class ReadSU {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\input\\new_gabon_3d_csps_06992_12071_tran.su";
            String outputPath = "D:\\new_gabon_3d_csps_06992_12071_tran.data";
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
            int traceheader = 240;
            int traceLength = 1200 * 4;
            RandomAccessFile ran = new RandomAccessFile(filePath, "r");
            long size = ran.length();
            System.out.println(size);
            ran.close();
            float traces = size / (traceheader + traceLength);
            System.out.println(traces);
            //System.exit(0);
            //dis.skipBytes(HEADER);
            byte[] readBytes = new byte[240];
            //List<int[]> lists = new ArrayList<int[]>();
            for (int i = 0; i < traces; i++) {
                dis.read(readBytes);
                byte[] temp = new byte[4];
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = readBytes[24 + j];
                }
                //swapBytes(temp);

                int xkey = getInt(temp) - 1;
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = readBytes[8 + j];
                }
                //swapBytes(temp);
                int ykey = getInt(temp);
                bw.write(xkey + "," + ykey);
                bw.newLine();
                bw.flush();
                dis.skipBytes(traceLength);
                System.out.println(traces - i);
            }
            dis.close();
            bw.close();
            System.out.println("write over");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
