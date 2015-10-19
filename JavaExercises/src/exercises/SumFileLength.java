package exercises;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/1/29 0029.
 */
public class SumFileLength {
    public static void main(String[] args) {
        try {
            File file = new File("H:\\sparkkir");
            File[] listFiles = file.listFiles();
            int count = 0;
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < listFiles.length; i++) {
                RandomAccessFile ran = new RandomAccessFile(listFiles[i].getPath(), "r");
                count += ran.length();
                String line=null;
                while((line=ran.readLine())!=null){
                    String[] values = line.split("\t");
                    list.add(Integer.parseInt(values[0]));
                }
                ran.close();
            }
            System.out.println("size:" + list.size());
            System.out.println("count:" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
