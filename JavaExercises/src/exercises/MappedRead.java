package exercises;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ericson on 2014/12/23 0023.
 */
public class MappedRead {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\sigsbee2a_migration_velocity.sgy";
            RandomAccessFile rand = new RandomAccessFile(filePath, "r");
            long start = System.currentTimeMillis();
            long size = rand.length();
            long end = System.currentTimeMillis();
            System.out.println("Time:" + (end - start) + "ms");
            System.out.println("File length:" + size + "B");

            FileChannel fc = rand.getChannel();
            MappedByteBuffer input = fc.map(FileChannel.MapMode.READ_ONLY, 0, size);
            String outputFilePath = filePath.substring(0, filePath.lastIndexOf(".")) + "data";
            long outputLength = 1201 * 2133 * 4;

            RandomAccessFile output = new RandomAccessFile(outputFilePath, "rw");
            MappedByteBuffer outputBuffer = output.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, outputLength);
            byte[] header = new byte[240];
            byte[] traceData = new byte[1201 * 4];
            input.get(new byte[3600]);
            start = System.currentTimeMillis();
            for (int i = 0; i < 2133; i++) {
                input.get(header);
                input.get(traceData);

                for (int j = 0; j < traceData.length / 4; j++) {
                    byte temp = traceData[4 * j];
                    traceData[4 * j] = traceData[4 * j + 3];
                    traceData[4 * j + 3] = temp;
                    temp = traceData[4 * j + 1];
                    traceData[4 * j + 1] = traceData[4 * j + 2];
                    traceData[4 * j + 2] = temp;
                }
                outputBuffer.put(traceData);
                if (!outputBuffer.isLoaded()) {
                    outputBuffer.load();
                }
            }
            end = System.currentTimeMillis();
            //outputBuffer.force();
            fc.close();
            input.clear();
            output.close();
            System.out.println("Elapsed Time:" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
