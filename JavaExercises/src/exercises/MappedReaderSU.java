package exercises;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ericson on 2014/12/27 0027.
 */
public class MappedReaderSU {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\new_gabon_3d_csps_06992_12071_tran.su";
            String outputPath = "D:\\new_gabon_3d_csps_06992_12071_tran.data";
            RandomAccessFile ran = new RandomAccessFile(filePath, "r");
            RandomAccessFile ran2 = new RandomAccessFile(outputPath, "rw");
            long size = ran.length();
            int header = 240;
            int tracelength = 1200 * 4;
            float traces = ((float) size) / (header + tracelength);
            System.out.println(traces);
            ran.seek(0);
            FileChannel channel = ran.getChannel();
            int length = 5024 * 1024*100;
            int rep = size % length == 0 ? (int) (size / length) : (int) (size / length) + 1;
            for(int index=0;index<rep;index++){
                if(index==rep-1){
                    length=(int)(size-(index-1)*length);
                }
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, index*length, length);

                byte[] readBytes = new byte[240];
                for (int i = 0; i < 102400; i++) {
                    buffer.get(readBytes);
                    byte[] temp = new byte[4];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = readBytes[24 + j];
                    }

                    int xkey = getInt(temp) - 1;
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = readBytes[8 + j];
                    }

                    int ykey = getInt(temp);
                    ran2.writeChars(xkey + "," + ykey + "\n");
                    System.out.println(traces - i);
                }
                buffer.clear();
            }


            ran.close();
            ran2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }
}
