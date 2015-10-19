package mianshi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by ericson on 2015/8/17 0017.
 */
public class CaptionHammer {
    public static void main(String[] args) {
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader("H:\\mianshi\\B-small-practice.in"));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("H:\\mianshi\\B-output.out"));
            int n = Integer.parseInt(bufferedreader.readLine());
            double g = 9.8;
            for (int i = 1; i <= n; i++) {
                String[] temp = bufferedreader.readLine().split(" ");
                double v = Double.parseDouble(temp[0]);
                double d = Double.parseDouble(temp[1]);
                double jiaodu = g * d / (v * v);
                if (Math.abs(jiaodu - 1) < 1.0e-6) {
                    jiaodu = 1;
                }
                double sin = 0.5 * Math.asin(jiaodu) * 180 / Math.PI;
                bufferedWriter.write("Case #" + i + ": " + sin);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedreader.close();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
