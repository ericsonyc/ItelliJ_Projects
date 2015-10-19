package exercises;

import java.io.RandomAccessFile;

/**
 * Created by ericson on 2015/1/3 0003.
 */
public class Try {
    public static void main(String[] args) {
        try {
            RandomAccessFile rand = new RandomAccessFile("D:\\test.data", "rw");
            rand.seek(200);
            rand.writeChars("Hello World");
            rand.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
