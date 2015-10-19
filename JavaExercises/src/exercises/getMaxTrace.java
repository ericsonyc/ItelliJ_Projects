package exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2014/12/27 0027.
 */
public class getMaxTrace {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\tos.data";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = null;
            List<Integer> lists = new ArrayList<Integer>();
            int current = 0;
            int previous = 0;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                current = Integer.parseInt(lines[1]);
                if (previous != current) {
                    lists.add(count + 1);
                    count = 0;
                } else {
                    count++;
                }
                previous = current;
            }
            lists.remove(0);
            int max = 0;
            for (int i = 0; i < lists.size(); i++) {
                if (max < lists.get(i)) {
                    max = lists.get(i);
                    System.out.println(lists.get(i));
                }
                //System.out.println(lists.get(i));
            }
            System.out.println("size:" + lists.size());
            System.out.println("max:" + max);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
