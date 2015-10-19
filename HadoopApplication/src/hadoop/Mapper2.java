package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ericson on 2015/1/23 0023.
 */
public class Mapper2 extends Mapper<IntWritable, Text, Text, Text> {
    private int CONSTANT = 2000;
    private int LENGTH;
    private Context context = null;

    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println("keyMapper2:" + key.get());
        long startTime = System.currentTimeMillis();
        this.context = context;
        Configuration conf = context.getConfiguration();
        int apx = Integer.parseInt(conf.get("apx"));
        int apy = Integer.parseInt(conf.get("apy"));
        boolean beVerb = Boolean.parseBoolean(conf.get("beVerb"));
        int onx = Integer.parseInt(conf.get("onx"));
        int ony = Integer.parseInt(conf.get("ony"));
        int ont = Integer.parseInt(conf.get("ont"));
        int nt = Integer.parseInt(conf.get("nt"));
        float oot = Float.parseFloat(conf.get("oot"));
        float odt = Float.parseFloat(conf.get("odt"));
        float dt = Float.parseFloat(conf.get("dt"));
        float oox = Float.parseFloat(conf.get("oox"));
        float odx = Float.parseFloat(conf.get("odx"));
        float ooy = Float.parseFloat(conf.get("ooy"));
        float ody = Float.parseFloat(conf.get("ody"));
        float ot = Float.parseFloat(conf.get("ot"));
        boolean beDiff = Boolean.parseBoolean(conf.get("beDiff"));
        boolean beAntiAliasing = Boolean.parseBoolean(conf.get("beAntiAliasing"));
        int maxtri = Integer.parseInt(conf.get("maxtri"));
        float trfact = Float.parseFloat(conf.get("trfact"));
        String PATH = conf.get("path");

        FileSystem fs = FileSystem.get(conf);
        byte[] datafully = new byte[ont * 4];
        float[] velocity = new float[ont];
        float[] image = new float[ont];
        this.readData(PATH + "data/rmsv.data", datafully, ont, key.get(), ont, Float.SIZE / 8, velocity, fs, false);

        String[] values = value.toString().split(",");
        int[] inputKeys = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            inputKeys[i] = Integer.parseInt(values[i]);
        }
        values = null;
        quick_sort(inputKeys, 0, inputKeys.length - 1);

        if (inputKeys.length < CONSTANT) {
            LENGTH = inputKeys.length;
        } else {
            LENGTH = CONSTANT;
        }

        datafully = new byte[nt * CONSTANT * 4];
        float[] trace = new float[nt * CONSTANT];
        this.readData(PATH + "data/shot.data", datafully, nt, 0, nt * LENGTH, Float.SIZE / 8, trace, fs, false);
        float[] sxsy = new float[2 * CONSTANT];
        this.readData(PATH + "data/fsxy.data", datafully, 2, 0, 2 * LENGTH, Float.SIZE / 8, sxsy, fs, false);
        float[] gxgy = new float[2 * CONSTANT];
        this.readData(PATH + "data/fgxy.data", datafully, 2, 0, 2 * LENGTH, Float.SIZE / 8, gxgy, fs, false);
        int count = 0;
        for (int i = 0; i < inputKeys.length; i++) {
            if (inputKeys[i] - inputKeys[count] >= LENGTH) {
                if (inputKeys.length - i < CONSTANT) {
                    LENGTH = inputKeys.length - i;
                }
                readData(PATH + "data/shot.data", datafully, nt, inputKeys[i], nt * LENGTH, Float.SIZE / 8, trace, fs, false);
                readData(PATH + "data/fsxy.data", datafully, 2, inputKeys[i], 2 * LENGTH, Float.SIZE / 8, sxsy, fs, false);
                readData(PATH + "data/fgxy.data", datafully, 2, inputKeys[i], 2 * LENGTH, Float.SIZE / 8, gxgy, fs, false);
                count = i;
            }

            float sx = sxsy[2 * i];
            float sy = sxsy[2 * i + 1];
            float gx = gxgy[2 * i];
            float gy = gxgy[2 * i + 1];
            if (beDiff) {
                this.ktMigSbDiff(trace, nt, i - count, dt);
            }
            if (beAntiAliasing) {
                this.ktMigCint(trace, nt, i - count);
                this.ktMigAcint(trace, nt, i - count);
            }
            float ox = oox + (key.get() % onx) * odx;
            float oy = ooy + (key.get() / onx) * ody;
            this.ktMigKernel(trace, i - count, velocity, 0, image, ox, oy, sx, sy, gx, gy, nt, ont, ot, dt, oot, odt, maxtri, trfact, beAntiAliasing);

        }

        String result = "";
        for (int i = 0; i < image.length; i++) {
            result += image[i] + ",";
        }
        result = result.substring(0, result.lastIndexOf(","));
        context.write(new Text(String.valueOf(key.get())), new Text(result));
        long endTime = System.currentTimeMillis();
        System.out.println("Mapper2Time:" + (endTime - startTime) + "ms");
    }

    private void quick_sort(int[] s, int l, int r) {
        if (l < r) {
            Swap(s, l, l + (r - l + 1) / 2);
            int i = l, j = r, x = s[l];
            while (i < j) {
                while (i < j && s[j] >= x) {
                    j--;
                }
                if (i < j)
                    s[i++] = s[j];
                while (i < j && s[i] < x) {
                    i++;
                }
                if (i < j)
                    s[j--] = s[i];
            }
            s[i] = x;
            quick_sort(s, l, i - 1);
            quick_sort(s, i + 1, r);
        }
    }

    private void Swap(int[] s, int oldindex, int newindex) {
        int temp = s[oldindex];
        s[oldindex] = s[newindex];
        s[newindex] = temp;
    }

    private void readData(String path, byte[] datafully, int length, int start, int totalLength, int SIZE,
                          float[] datas, FileSystem fs, boolean flag) {
        try {
            // FileSystem fs = FileSystem.newInstance(conf);
            FSDataInputStream fileIn = fs.open(new Path(path));
            //fileIn.seek(length * start * SIZE);
            //byte[] temp = new byte[4];
            // String result = "";
            //System.out.println("datalength:" + datas.length);
            //byte[] datafully = new byte[totalLength * 4];
            fileIn.read(length * start * SIZE, datafully, 0, SIZE * totalLength);
            //fileIn.read(length * start * SIZE, datafully, 0, datafully.length);
            fileIn.close();
            fileIn = null;
            for (int i = 0; i < totalLength; i++) {
//                for (int j = 0; j < temp.length; j++) {
//                    temp[j] = datafully[4 * i + j];
//                }
                // fileIn.read(temp);
                datas[i] = Float.intBitsToFloat(getInt(datafully, i * 4, flag));
                // result += datas[i] + ",";
            }
            // System.out.println(result);
            // fs.close();
            datafully = null;
            System.gc();
            //temp = null;
            //fs = null;
        } catch (Exception e) {
            e.printStackTrace();
            String command = "hadoop job -kill " + context.getJobID();
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }

    public int getInt(byte[] bytes, int offset, boolean flag) {
        if (flag) {
            return (0xff & bytes[3 + offset]) | (0xff00 & (bytes[2 + offset] << 8))
                    | (0xff0000 & (bytes[1 + offset] << 16))
                    | (0xff000000 & (bytes[0 + offset] << 24));
        } else {
            return (0xff & bytes[0 + offset]) | (0xff00 & (bytes[1 + offset] << 8))
                    | (0xff0000 & (bytes[2 + offset] << 16))
                    | (0xff000000 & (bytes[3 + offset] << 24));
        }
    }

    private void ktMigCint(float[] trace, int nt, int index) {
        int count = nt * index;
        for (int i = 1; i < nt; ++i) {
            trace[count + i] += trace[count + i - 1];
        }
    }

    private void ktMigAcint(float[] trace, int nt, int index) {
        int count = nt * index;
        for (int i = nt - 2; i >= 0; i--) {
            trace[count + i] += trace[count + i + 1];
        }
    }

    private void ktMigSbDiff(float[] trace, int length, int index,
                             float distance) {
        float val0, val1, val2;
        int count = length * index;
        val1 = trace[count];
        val2 = trace[count];

        for (int i = 0; i < length; ++i) {
            val0 = trace[count + i];
            trace[count + i] = 0.5f * (3.0f * val0 - 4.0f * val1 + val2)
                    / distance;
            val2 = val1;
            val1 = val0;
        }
    }

    private void ktMigKernel(float[] trace, int traceindex, float[] vrms,
                             int vrmsindex, float[] image, float ox, float oy, float sx,
                             float sy, float gx, float gy, int nt, int ont, float ot, float dt,
                             float oot, float odt, int trm, float trf, boolean aa) {

        float v, inv;
        float inv2trf, nf;
        float j, scale, smp, so2, go2;
        float depth2, dx, dy, ts, tg;
        int traceposition = nt * traceindex;
        int vrmsposition = ont * vrmsindex;

        // Loop over tau indices
        for (int k = 0; k < ont; ++k) {
            // RMS velocity at image location
            v = vrms[vrmsposition + k];
            // Slowness at image location
            inv = 1.0f / v;
            inv2trf = trf * inv * inv;
            depth2 = (float) Math.pow(0.5 * v * (oot + k * odt), 2.0);
            // squared distance to source from the image point on the surface
            so2 = (sx - ox) * (sx - ox) + (sy - oy) * (sy - oy);
            // squared distance to receiver from the image point on the surface
            go2 = (gx - ox) * (gx - ox) + (gy - oy) * (gy - oy);
            // Time from source to image point in pseudodepth
            ts = (float) Math.sqrt(so2 + depth2) * inv;
            // Time from receiver to image point in pseudodepth
            tg = (float) Math.sqrt(go2 + depth2) * inv;
            // double root square time = time to source + time to receiver
            j = (ts + tg - ot) / dt;

            if (!aa) {
                if (j >= 0.f && j < nt - 1) {
                    image[k] += INTSMP(trace, j, nt, traceposition);
                }
                continue;
            }
            // (distance to source.x) / (time to source) + (distance to
            // receiver.x) / (time to receiver)
            dx = (sx - ox) / ts + (gx - ox) / tg;
            // (distance to source.y) / (time to source) + (distance to
            // receiver.y) / (time to receiver)
            dy = (sy - oy) / ts + (gy - oy) / tg;
            // Filter length
            nf = (float) (inv2trf * Math.sqrt(dx * dx + dy * dy));
            // Truncate filter
            if (nf > trm) {
                nf = (float) trm;
            }
            // Check ranges
            if ((j - nf - 1.0f) >= 0.0f && (j + nf + 1.0f) < nt) {
                // Scaling factor
                scale = 1.0f / (1.0f + nf);
                scale *= scale;
                // Collect samples
                smp = 2.0f * INTSMP(trace, j, nt, traceposition)
                        - INTSMP(trace, (j - nf - 1.0f), nt, traceposition)
                        - INTSMP(trace, (j + nf + 1.0f), nt, traceposition);
                // Contribute to the image point
                image[k] += scale * smp;
            }
        }
    }

    private float INTSMP(float[] t, float i, int length, int traceposition) {
        float out;
        float out1;
        if ((int) i + 1 >= length) {
            out1 = 0;
        } else {
            out1 = t[traceposition + (int) (i) + 1];
        }
        if ((int) i >= length) {
            out = 0;
        } else {
            out = t[traceposition + (int) i];
        }
        float value = ((1.0f - i + (float) ((int) i)) * out + (i - (float) ((int) i))
                * out1);
        return value;
    }
}
