package exercises;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2014/12/26 0026.
 */
public class SortTosTXT {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\tos.txt";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = null;
            List<int[]> lists = new ArrayList<int[]>();
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                lists.add(new int[]{Integer.parseInt(lines[0]), Integer.parseInt(lines[1])});
            }
            br.close();
            sortLists(lists);
            writeFile(lists,filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(List<int[]> lists,String filePath) {
        try {
            //String filePath = "D:\\traceheader.data";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (int i = 0; i < lists.size(); i++) {
                int[] value = lists.get(i);
                bw.write(value[0] + "," + value[1]);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            System.out.println("write over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sortLists(List<int[]> lists) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < lists.size(); i++) {
            for (int j = 0; j < lists.size() - i - 1; j++) {
                int[] value1 = lists.get(j);
                int[] value2 = lists.get(j + 1);
                if (value1[0] > value2[0]) {
                    lists.set(j, value2);
                    lists.set(j + 1, value1);
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("sortTime:" + (end - start) + "ms");
    }
}
