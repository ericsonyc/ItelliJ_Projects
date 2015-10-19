package mianshi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by ericson on 2015/8/17 0017.
 */
public class Moist {
    public static void main(String[] args) {
        try {
            Moist google = new Moist();
            String filename = "C-small-practice-2.in";
            BufferedReader br = new BufferedReader(new FileReader("H:\\mianshi\\" + filename));
            BufferedWriter bw = new BufferedWriter(new FileWriter("H:\\mianshi\\" + filename.replace(".in", ".out")));
            int T = Integer.parseInt(br.readLine());
            for (int i = 1; i <= T; i++) {
                int n = Integer.parseInt(br.readLine());
                String[] data = new String[n];
                for (int j = 0; j < n; j++) {
                    data[j] = br.readLine();
                }
                int count = google.sortNames(data);
                bw.write("Case #" + i + ": " + count);
                bw.newLine();
                bw.flush();
            }
            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int sortNames(String[] names) {
        int count = 0;
        for (int i = 1; i < names.length; i++) {
            int j = i - 1;
            boolean flag = false;
            String temp=names[i];
            while (j >= 0 && temp.compareTo(names[j]) < 0) {
                flag = true;
                names[j + 1] = names[j];
                j--;
            }
            names[j + 1] = names[i];
            count = flag ? count + 1 : count;
        }
        return count;
    }
}
