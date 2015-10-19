package exercises;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ericson on 2014/12/25 0025.
 */
public class MatchTos {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\tos.txt";
            int[] values = new int[]{499, 0};
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            boolean flag = false;
            boolean containShot = false;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                String[] splits = line.split(",");
                int i = -1;
                for (i = 0; i < splits.length; i++) {
                    if (Integer.parseInt(splits[0]) == values[i]) {
                        containShot = true;
                    }
                    if (Integer.parseInt(splits[i]) != values[i]) {
                        break;
                    }
                }
                if (i == splits.length) {
                    flag = true;
                    break;
                }
            }
            System.out.println("flag:" + flag);
            System.out.println("containShot:" + containShot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
