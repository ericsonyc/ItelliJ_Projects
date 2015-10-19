package exercises;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by ericson on 2014/12/23 0023.
 */
public class WriteMetaFile {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\data.meta";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write("Data File Path = " + "D:\\data.data");
            bw.newLine();
            bw.write("bytes per sample = " + "float");
            bw.newLine();
            bw.flush();

            int MAXDIM = 10;
            int maxDim = 2;
            int[] n = new int[maxDim];
            float[] d = new float[maxDim];
            float[] o = new float[maxDim];
            String[] label = new String[MAXDIM];
            String[] unit = new String[MAXDIM];
            for (int i = 0; i < maxDim; i++) {
                n[i] = 0;
                d[i] = 1.0f;
                o[i] = 0.0f;
                label[i] = "";
                unit[i] = "";
            }
            for (int i = 0; i < maxDim; i++) {
                bw.write("n"+(i+1)+"="+n[i]+", d"+(i+1)+"="+d[i]+", o"+(i+1)+"="+o[i]+", label"+(i+1)+"="+label[i]+", unit"+(i+1)+"="+unit[i]+",\n");

            }


            bw.close();
            System.out.println("write finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
