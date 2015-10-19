package spark;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.io.Serializable;

public class MyInputFormat2 extends FileInputFormat<Integer, String> {

    @Override
    public RecordReader<Integer, String> createRecordReader(InputSplit split,
                                                            TaskAttemptContext context) throws IOException,
            InterruptedException {
        System.out.println("createRecordReader");
        return new ZRecordReader();
    }

    // 自定义的数据类型
    public static class ZRecordReader extends RecordReader<Integer, String> implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        // data
        // private LineReader in; // 输入流
        private boolean more = true;// 提示后续还有没有数据

        private Integer key = null;
        private String value = null;

        // 这三个保存当前读取到位置（即文件中的位置）
        private long start;
        private long end;
        private long pos;
        private FSDataInputStream fileIn = null;
        private int shotNum = 0;
        private static long LENGTH = 8;

        public ZRecordReader() {

        }

        //private Log LOG = LogFactory.getLog(ZRecordReader.class);// 日志写入系统，可加可不加

        @Override
        public void initialize(InputSplit split, TaskAttemptContext context)
                throws IOException, InterruptedException {
            // 初始化函数
            //System.out.println("initialize");
            FileSplit inputsplit = (FileSplit) split;
            start = inputsplit.getStart(); // 得到此分片开始位置
            end = start + inputsplit.getLength();// 结束此分片位置
            // System.out.println("start:" + start + " ,end:" + end);
            final Path file = inputsplit.getPath();
            // System.out.println(file.toString());
            // 打开文件
            FileSystem fs = file.getFileSystem(context.getConfiguration());
            fileIn = fs.open(inputsplit.getPath());

            // 关键位置2
            // 将文件指针移动到当前分片，因为每次默认打开文件时，`其指针指向开头
            fileIn.seek(start);

            // in = new LineReader(fileIn, context.getConfiguration());

            if (start != 0) {
                System.out.println("not the first split");
                // 关键解决位置1
                // 如果这不是第一个分片，那么假设第一个分片是0——4，那么，第4个位置已经被读取，则需要跳过4，否则会产生读入错误，因为你回头又去读之前读过的地方
                start += (end - pos + 1);
            }
            pos = start;
        }

        // private int maxBytesToConsume(long pos) {
        // return (int) Math.min(Integer.MAX_VALUE, end - pos);
        // }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            // 下一组值
            // tips:以后在这种函数中最好不要有输出，费时
            // LOG.info("正在读取下一个，嘿嘿");
            if (pos >= end) {
                more = false;
                // System.out.println("pos>=end");
                return false;
            }
            //Logger.getLogger("Spark").info("nextKeyValue");
            System.out.println("nextKeyValue");
            if (null == key) {
                key = new Integer(1);
            }
            if (null == value) {
                value = "";
            }
            // Text nowline = new Text();// 保存当前行的内容
            // int readsize = in.readLine(nowline);
            byte[] temp = new byte[Float.SIZE / 8];
            long length = (end - start) > LENGTH ? LENGTH : (end - start);
            float[] data = new float[(int) length * 8 / Float.SIZE];
            int readsize = 0;
            int count = 0;
            // System.out.println("datasize:"+data.length);
            for (int i = 0; i < data.length; i++) {
                readsize = fileIn.read(temp);
                // System.out.println("readsize:"+readsize);
                // if(readsize==-1){
                // break;
                // }
                data[i] = Float.intBitsToFloat(getInt(temp));
                count++;
                // System.out.println("i:"+i);
            }
            // System.out.println(count);
            // System.out.println("end-start:"+(end-start));

            // 关键位置3
            // 如果pos的值大于等于end，说明此分片已经读取完毕

            // if (-1 == readsize) {
            // key = null;
            // value = null;
            // more = false;// 说明此时已经读取到文件末尾，则more为false
            // System.out.println("-1==readsize");
            // return false;
            // }
            // String[] keyandvalue = nowline.toString().split(",");

            // 排除第一行
            // if (keyandvalue[0].endsWith("\"CITING\"")) {
            // readsize = in.readLine(nowline);
            // // 更新当前读取到位置
            // pos += readsize;
            // if (0 == readsize) {
            // more = false;// 说明此时已经读取到文件末尾，则more为false
            // return false;
            // }
            // // 重新划分
            // keyandvalue = nowline.toString().split(",");
            // }

            // 得到key和value
            // LOG.info("key is :" + key +"value is" + value);
            key = shotNum / 2;
            shotNum += data.length;
            String temp_value = "";
            for (int i = 0; i < count; i++) {
                temp_value += String.valueOf(data[i]) + ",";
            }
            temp_value = temp_value.substring(0, temp_value.lastIndexOf(","));
            // temp_value = String.valueOf(count);
            value = temp_value;
            // 更新当前读取到位置
            pos += data.length * temp.length;
            return true;
        }

        public static int getInt(byte[] bytes) {
            return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                    | (0xff0000 & (bytes[2] << 16))
                    | (0xff000000 & (bytes[3] << 24));
        }

        @Override
        public Integer getCurrentKey() throws IOException,
                InterruptedException {
            // 得到当前的Key
            return key;
        }

        @Override
        public String getCurrentValue() throws IOException, InterruptedException {
            // 得到当前的value
            return value;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            // 计算对于当前片的处理进度
            if (false == more || end == start) {
                return 0f;
            } else {
                return Math.min(1.0f, (pos - start) / (end - start));
            }
        }

        @Override
        public void close() throws IOException {
            // 关闭此输入流
            if (null != fileIn) {
                fileIn.close();
            }
        }

    }

}
