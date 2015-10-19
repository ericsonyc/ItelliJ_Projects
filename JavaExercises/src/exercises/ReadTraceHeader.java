package exercises;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2014/12/25 0025.
 */
public class ReadTraceHeader {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\sigsbee2a_nfs.sgy";
            RandomAccessFile ranfile = new RandomAccessFile(filePath, "r");
            long SIZE = ranfile.length();
            ranfile.close();
            DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
            dis.skipBytes(3600);
            int traceLength = 1500 * 4 + 240;
            int traces = (int) (SIZE - 3600) / traceLength;
            byte[] readBytes = new byte[240];
            List<int[]> lists = new ArrayList<int[]>();
            for (int i = 0; i < traces; i++) {
                dis.read(readBytes);
                byte[] temp = new byte[4];
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = readBytes[24 + j];
                }
                swapBytes(temp);

                int xkey = getInt(temp) - 1;
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = readBytes[8 + j];
                }
                swapBytes(temp);
                int ykey = getInt(temp);

                lists.add(new int[]{xkey, ykey});

                dis.skipBytes(6000);
            }
            dis.close();
            //sortLists(lists);
            writeFile(lists);
            //readWriteFile(lists);
            printLists(lists);
            //printMinMaxLists(lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void swapBytes(byte[] temp) {
        for (int j = 0; j < temp.length / 2; j++) {
            byte replace = temp[j];
            temp[j] = temp[temp.length - j - 1];
            temp[temp.length - j - 1] = replace;
        }
    }

    private static void readWriteFile(List<int[]> lists) {
        try {
            String filePath = "D:\\traceheader.data";
            String fileTos = "D:\\tos.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            BufferedReader br = new BufferedReader(new FileReader(fileTos));
            for (int i = 0; i < lists.size(); i++) {
                int[] value = lists.get(i);
                String tos = br.readLine();
                bw.write(value[0] + "," + value[1] + "   tos:" + tos);
                bw.newLine();
                bw.flush();
            }
            br.close();
            bw.close();
            System.out.println("write over");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(List<int[]> lists) {
        try {
            String filePath = "D:\\traceheader.data";
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

    private static void printMinMaxLists(List<int[]> lists) {
        int[] min = lists.get(0);
        int[] max = lists.get(lists.size() - 1);
        System.out.println("Min value:" + min[0] + "," + min[1]);
        System.out.println("Max value:" + max[0] + "," + max[1]);
    }

    private static void printLists(List<int[]> lists) {
        for (int i = 0; i < lists.size(); i++) {
            int[] temp = lists.get(i);
            String value = temp[0] + "," + temp[1];
            System.out.println(value);
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

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }
}
