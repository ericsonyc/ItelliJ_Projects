package exercises;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ericson on 2014/12/27 0027.
 */
public class ReadTosTXT {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\input\\tostxt.data";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = null;
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                String[] splits = line.split(",");
                int xkey = Integer.parseInt(splits[0]);
                int ykey = Integer.parseInt(splits[1]);
                if (xkey < minX) {
                    minX = xkey;
                }
                if (xkey > maxX) {
                    maxX = xkey;
                }
                if (ykey < minY) {
                    minY = ykey;
                }
                if (ykey > maxY) {
                    maxY = ykey;
                }
            }
            br.close();
            System.out.println(minX);
            System.out.println(maxX);
            System.out.println(minY);
            System.out.println(maxY);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
