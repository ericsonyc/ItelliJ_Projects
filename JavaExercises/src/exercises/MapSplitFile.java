package exercises;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ericson on 2015/1/2 0002.
 */
public class MapSplitFile {
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\ktmigration\\data\\output\\shots.data";
        String output = "D:\\splits\\";
        RandomAccessFile rand = new RandomAccessFile(filePath, "r");
        long SIZE = rand.length();
        //rand.close();
        long length = 40000000L;
        long splits = SIZE % length == 0 ? SIZE / length : SIZE / length + 1;
        System.out.println("splits total:" + splits);
        FileChannel channel = rand.getChannel();
        long start=System.currentTimeMillis();
        for (int i = 0; i < splits; i++) {
            String out = i + ".data";
            //System.out.println("i:"+i);
            RandomAccessFile randout = new RandomAccessFile(output + out, "rw");
            if (i == splits - 1) {
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, i * length, SIZE - i * length);
                MappedByteBuffer bufout = randout.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, SIZE - i * length);
                byte[] temp = new byte[4];
                for (int j = 0; j < (SIZE - i * length) / temp.length; j++) {
                    buffer.get(temp);
                    bufout.put(temp);
                    //bufout.force();
                    //System.out.println("j:"+j);
                }
                bufout.force();
                bufout.clear();
                buffer.clear();
            } else {
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, i * length, length);
                MappedByteBuffer bufout = randout.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
                byte[] temp = new byte[1000];
                for (int j = 0; j < length / temp.length; j++) {
                    buffer.get(temp);
                    bufout.put(temp);
                    //bufout.force();
                    //System.out.println("j:"+j);
                }
                bufout.force();
                bufout.clear();
                buffer.clear();
            }
            System.out.println("splits:" + i);
        }
        long end=System.currentTimeMillis();
        System.out.println("Time:"+(end-start)+"ms");
    }
}
