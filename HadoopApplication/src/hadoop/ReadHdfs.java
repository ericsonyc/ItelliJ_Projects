package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/1/19 0019.
 */
public class ReadHdfs {
    public static void main(String[] args) throws Exception {
        ReadHdfs hdfs = new ReadHdfs();
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path outputPath = new Path(args[0]);
        Path output = new Path(args[1]);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }
        FileStatus[] status = fs.listStatus(outputPath);
        List<Integer> keys = new ArrayList<Integer>();
        int dataCount = 0;
        //ArrayList<String> values = new ArrayList<String>();
        System.out.println("begin key");
        for (FileStatus ss : status) {//sort the key
            FSDataInputStream fsIn = fs.open(ss.getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    fsIn));
            String str = null;
            System.out.println(ss.getPath().toString());
            int errorOffset = 0;
            try {
                while ((str = br.readLine()) != null) {
                    //String[] datas = str.split("#");
                    keys.add(Integer.parseInt(str.substring(0, str.indexOf("#")).trim()));
                    //values.add(str.substring(str.indexOf("#") + 1).trim());
                    errorOffset += str.getBytes().length;
                    if (dataCount == 0) {
                        dataCount = str.substring(str.indexOf("#")).trim().split(",").length;
                    }
                }
            } catch (Exception e) {
                System.out.println("errorOffset:" + errorOffset);
                System.out.println(str);
            }

            br.close();
            fsIn.close();
        }

        System.out.println("sort key");
        hdfs.quick_sort(keys, 0, keys.size() - 1);
//        for (int i = 0; i < keys.size(); i++) {
//            for (int j = 0; j < keys.size() - i - 1; j++) {
//                if (keys.get(j) > keys.get(j + 1)) {
//                    int temp = keys.get(j);
//                    keys.set(j, keys.get(j + 1));
//                    keys.set(j + 1, temp);
////                    String t = values.get(j);
////                    values.set(j, values.get(j + 1));
////                    values.set(j + 1, t);
//                }
//            }
//        }
        System.out.println("begin offset");
        long[] offset = new long[keys.size()];
        String[] filenames = new String[keys.size()];
        int log = 0;
        for (FileStatus ss : status) {
            FSDataInputStream fsIn = fs.open(ss.getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(fsIn));
            String str = "";
            long previous = 0;
            long count = 0;
            System.out.println("log:" + log++);
            while (str != null) {
                //System.out.println(ss.getPath().toString());
                str = br.readLine();
                if (str == null) {
                    break;
                }
                //System.out.println("previous:" + previous);
                count += str.getBytes().length;
                int key = Integer.parseInt(str.substring(0, str.indexOf("#")).trim());
                int index = keys.indexOf(key);
                offset[index] = previous;
                filenames[index] = ss.getPath().toString();
                previous = count;
            }
            br.close();
            fsIn.close();
        }

        System.out.println("begin write");
        // fs.delete(outputPath, true);
        hdfs.writeImage(conf, outputPath, offset, filenames, args[1], dataCount);
    }

    private boolean writeImage(Configuration conf, Path rote, long[] offset, String[] filenames, String outputPath, int dataCount) {
        if (rote == null) {
            return false;
        }
        try {
            FileSystem fs = FileSystem.get(conf);
            Path output = new Path(outputPath);
            if (fs.exists(output)) {
                fs.delete(output, true);
            }
            FSDataOutputStream fsOut = fs.create(output, true);
            float[] datas = new float[dataCount];
            byte[] temp = new byte[dataCount * 4];
            for (int i = 0; i < offset.length; i++) {
                int count = 0;
                String file = filenames[i];
                FSDataInputStream fsIn = fs.open(new Path(file));
                BufferedReader br = new BufferedReader(new InputStreamReader(fsIn));
                br.skip(offset[i]);
                System.out.println("offset.length:" + offset[i]);
                String line = br.readLine();
                br.close();
                System.out.println("line:" + line);
                System.out.println("filename:" + file);

                //Console console = System.console();
                //console.readLine();
                line = line.substring(line.indexOf("#") + 1).trim();
                while (line.length() != 0) {
                    int index = line.indexOf(",");
                    if (index == -1) {
                        System.out.println("-1:" + line);
                        datas[count++] = Float.parseFloat(line.substring(0));
                        break;
                    } else {
                        System.out.println("!=-1:" + line);
                        //console.readLine();
                        datas[count++] = Float.parseFloat(line.substring(0, index));
                    }
                    //System.out.println(line);
                    line = line.substring(index + 1).trim();
                    //console.readLine();
                }
                System.out.println("index:" + i);
                fsIn.close();
                for (int j = 0; j < count; j++) {
                    this.getBytes(Float.floatToIntBits(datas[j]), temp, 4 * j);
                }
                fsOut.write(temp, 0, count * 4);
            }
            fsOut.close();

            // System.out.println("the length of the count is:"+count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void getBytes(int data, byte[] bytes, int start) {
        //byte[] bytes = new byte[4];
        bytes[start] = (byte) (data & 0xff);
        bytes[1 + start] = (byte) ((data & 0xff00) >> 8);
        bytes[2 + start] = (byte) ((data & 0xff0000) >> 16);
        bytes[3 + start] = (byte) ((data & 0xff000000) >> 24);
    }

    private void quick_sort(List<Integer> s, int l, int r) {
        if (l < r) {
            Swap(s, l, l + (r - l + 1) / 2);
            int i = l, j = r, x = s.get(l);
            while (i < j) {
                while (i < j && s.get(j) >= x) {
                    j--;
                }
                if (i < j)
                    s.set(i++, s.get(j));
                while (i < j && s.get(i) < x) {
                    i++;
                }
                if (i < j)
                    s.set(j--, s.get(i));
            }
            s.set(i, x);
            quick_sort(s, l, i - 1);
            quick_sort(s, i + 1, r);
        }
    }

    private void Swap(List<Integer> s, int oldindex, int newindex) {
        int temp = s.get(oldindex);
        s.set(oldindex, s.get(newindex));
        s.set(newindex, temp);
    }
}
