package exercises;

import java.io.RandomAccessFile;

/**
 * Created by ericson on 2015/1/21 0021.
 */
public class ReadShotHeader {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("H:\\data.sgy", "r");
            RandomAccessFile output = new RandomAccessFile("H:\\output", "rw");
            int traces = 152684;
            int length = 6240;
            byte[] temp = new byte[16];
            float[] values = new float[temp.length / 4];
            for (int i = 0; i < traces; i++) {
                randomAccessFile.seek(3600 + i * length);
//                randomAccessFile.skipBytes(72);
//                randomAccessFile.read(temp, 0, temp.length);
//                String result = "";
//                for (int j = 0; j < values.length; j++) {
//                    values[j] = Float.intBitsToFloat(getInt(temp, 4 * j));
//                    result += values[j] + ",";
//                }
//                output.writeChars(result.substring(0, result.lastIndexOf(",")) + "\n");
                byte[] ss = new byte[4];
                randomAccessFile.skipBytes(36);
                float data = Float.intBitsToFloat(getInt(ss,0));
                System.out.println(data);
            }
            randomAccessFile.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static short getShort(byte[] bytes) {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static int getInt(byte[] bytes, int offset) {
        return (0xff & bytes[0 + offset]) | (0xff00 & (bytes[1 + offset] << 8))
                | (0xff0000 & (bytes[2 + offset] << 16))
                | (0xff000000 & (bytes[3 + offset] << 24));
    }
}
