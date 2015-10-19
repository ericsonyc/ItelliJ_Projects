package mianshi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by ericson on 2015/8/16 0016.
 */
public class GoogleCode {

    private static int count = 0;

    public static void main(String[] args) {
        try {
            GoogleCode google = new GoogleCode();
            BufferedWriter bw = new BufferedWriter(new FileWriter("h:\\mianshi\\output2"));
            BufferedReader br = new BufferedReader(new FileReader("h:\\mianshi\\google2.in"));
            int n = Integer.parseInt(br.readLine());
            count++;
            int index;
            int[][] map = null;
            for (int i = 1; i <= n; i++) {
                Map<String, Integer> mapSet = new HashMap<String, Integer>();
                int m = Integer.parseInt(br.readLine());
                count++;
                index = 0;
                map = new int[2 * m][2 * m];
                for (int j = 0; j < m; j++) {
                    String[] temp = br.readLine().split(" ");
                    count++;
                    Integer a;
                    if ((a = mapSet.get(temp[0])) == null) {
                        a = index;
                        mapSet.put(temp[0], index);
                        index++;
                    }
                    Integer b;
                    if ((b = mapSet.get(temp[1])) == null) {
                        b = index;
                        mapSet.put(temp[1], index);
                        index++;
                    }
                    map[a][b] = 1;
                    map[b][a] = 1;
                }
                boolean isbit = google.isBipartite(map, index);
                String output = "Case #" + i + ": ";
                if (isbit)
                    output += "Yes\r\n";
                else
                    output += "No\r\n";
                bw.write(output);
                bw.newLine();
                bw.flush();
            }
            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isBipartite(int[][] map, int n) {
        int[] colorArr = new int[n];
        System.out.println(n + ":" + count);
        colorArr[0] = 1;
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int top = queue.poll();
            for (int i = 0; i < n; i++) {
                if (map[top][i] == 1 && colorArr[i] == 0) {
                    colorArr[i] = 3 - colorArr[top];
                    queue.add(i);
                } else if (map[top][i] == 1 && colorArr[i] == colorArr[top]) {
                    return false;
                }
            }
        }
        return true;
    }
}
