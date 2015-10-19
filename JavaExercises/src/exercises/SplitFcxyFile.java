package exercises;

import java.io.RandomAccessFile;

/**
 * Created by ericson on 2015/1/4 0004.
 */
public class SplitFcxyFile {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\output\\fcxy.data";
            byte[] temp = new byte[8000];
            int num = 125;
            RandomAccessFile rand = new RandomAccessFile(filePath, "r");
            long SIZE = rand.length();
            long count = SIZE % (num * temp.length) == 0 ? (SIZE / (num * temp.length)) : (SIZE / (num * temp.length) + 1);
            System.out.println(count);
            for (int i = 0; i < count; i++) {
                String path = "D:\\output\\fcxy" + i + ".data";
                RandomAccessFile access = new RandomAccessFile(path, "rw");
                if (i == count - 1) {
                    long length = SIZE - i * num * temp.length;
                    int n = -1;
                    while ((n = rand.read(temp)) != -1) {
                        access.write(temp, 0, n);
                    }
                    access.close();
                } else {
                    for (int j = 0; j < num; j++) {
                        rand.read(temp);
                        access.write(temp);
                    }
                    access.close();
                }
            }
            System.out.println("write over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
