package exercises;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by ericson on 2015/1/3 0003.
 */
public class MergeFiles {
    public static void main(String[] args) {
        try {
            RandomAccessFile out = new RandomAccessFile(args[0] + "shot.data", "rw");
            for (int i = 0; i < 150; i++) {
                String file = i + ".data";
                RandomAccessFile rand = new RandomAccessFile(args[0] + file, "r");
                byte[] temp = new byte[4000];
                int count = 0;
                while ((count = rand.read(temp)) != -1) {
                    out.write(temp, 0, count);

                }
                rand.close();
                System.out.println("i:" + i);
                File f=new File(args[0]+file);
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
