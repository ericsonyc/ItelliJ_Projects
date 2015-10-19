package mianshi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ericson on 2015/8/23 0023.
 */
public class GoogolString {
    public static void main(String[] args) {
        try {
            GoogolString google = new GoogolString();
            BufferedWriter bw = new BufferedWriter(new FileWriter("h:\\mianshi\\A-small.out"));
            BufferedReader br = new BufferedReader(new FileReader("h:\\mianshi\\A-small-attempt6.in"));
            int T = Integer.parseInt(br.readLine());
            int k;
            List<Byte> lists = new ArrayList<Byte>();
            lists.add((byte) 0);
            double value = 100000000;
            for (int i = 0; i < value; i++) {
                lists.add((byte) 0);
                for (int j = lists.size() - 2; j >= 0; j--) {
                    byte b = lists.get(j).byteValue();
                    b ^= 1;
                    lists.add(b);
                }
            }
            for (int i = 1; i <= T; i++) {
                k = Integer.parseInt(br.readLine());
                bw.write("Case #" + i + ": " + lists.get(k - 1));
                bw.newLine();
                bw.flush();
            }
            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class PerComparator implements Comparator<Per> {
        @Override
        public int compare(Per o1, Per o2) {

            return Integer.compare(o1.k, o2.k);
        }
    }

    class Per {
        public int index = 0;
        public int k = 0;

        public Per(int index, int k) {
            this.index = index;
            this.k = k;
        }
    }

    private void switchString(StringBuffer str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0') {
                str.setCharAt(i, '1');
            } else {
                str.setCharAt(i, '0');
            }
        }
    }

    private StringBuffer reserve(StringBuffer str) {
        return str.reverse();
    }
}
