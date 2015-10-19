package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class CPUKTMigration extends Mapper<IntWritable, Text, Text, Text> {

    private boolean beAntiAliasing;
    private boolean beDiff;
    private boolean beVerb;
    private String shotsFile;
    private String sourceCoordinatesFile;
    private String receiverCoordinatesFile;
    private String midpointCoordinatesFile;
    private String velocityFile;
    private String outputFilePath;
    private int apx;
    private int apy;
    private int maxtri;
    private float trfact;
    private int onx;
    private int ony;
    private int ont;
    private int nt;
    private float oot;
    private float odt;
    private float dt;
    private float oox;
    private float odx;
    private float ooy;
    private float ody;
    private float ot;

    // the variables that mapper used
    // int nt, nx, ny, nin, nix, ntr;
    // float dt, ot;
    // int ont, onx, ony, osize;
    // float odt, odx, ody, oot, oox, ooy;

    public CPUKTMigration() {
        beAntiAliasing = true;
        beDiff = true;
        beVerb = false;
        shotsFile = "";
        sourceCoordinatesFile = "";
        receiverCoordinatesFile = "";
        midpointCoordinatesFile = "";
        velocityFile = "";
        outputFilePath = "";
        apx = 0;
        apy = 0;
        maxtri = 0;
        trfact = 0;
    }

    public void setInputFile(String shots, String sxy, String gxy, String cxy,
                             String rmsv) {
        this.shotsFile = shots;
        this.sourceCoordinatesFile = sxy;
        this.receiverCoordinatesFile = gxy;
        this.midpointCoordinatesFile = cxy;
        this.velocityFile = rmsv;
    }

    public void setOutputFile(String imageFile) {
        this.outputFilePath = imageFile;
    }

    public void setVerb(boolean verb) {
        this.beVerb = verb;
    }

    public void setAntiAlising(boolean antialising) {
        this.beAntiAliasing = antialising;
    }

    public String getString(byte[] bytes) {
        return getString(bytes, "GBK");
    }

    public String getString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    public int histInt(String file, String tag, Configuration conf) {
        int value = 1;
        try {
//			FileSystem fs = FileSystem.get(conf);
//			FSDataInputStream fsIn = fs.open(new Path(file));
//			BufferedReader br = new BufferedReader(new InputStreamReader(fsIn));
//			String line = "";
//			while ((line = br.readLine()) != null) {
//				int tagPos = line.indexOf(tag + "=");
//				if (tagPos != -1) {
//					String subStr = line.substring(tagPos + tag.length());
//					int firstCommaIndex = subStr.indexOf(',');
//					int firstEqualIndex = subStr.indexOf('=');
//					value = Integer.parseInt(subStr.substring(
//							firstEqualIndex + 1, firstCommaIndex).trim());
//					break;
//				}
//			}
//			br.close();

            FileSystem fs = FileSystem.get(conf);
            Path filepath = new Path(file);
            FSDataInputStream fsIn = fs.open(filepath);
            byte[] temp = new byte[250];
            fsIn.read(temp);
            String buf = getString(temp);
            //System.out.println("buf:" + buf);
            //System.out.println("tag:" + tag);
            if (buf.indexOf(tag + "=") != -1) {
                buf = buf.substring(buf.indexOf(tag + "=") + tag.length() + 1).trim();
                //System.out.println("middlebuf:" + buf);
                buf = buf.substring(0, buf.indexOf(",")).trim();
                //System.out.println("endbuf:" + buf);
                value = Integer.parseInt(buf);
                //System.out.println("value:" + value);
                fsIn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public float histfloat(String file, String tag, Configuration conf) {
        float value = -1.0f;
        try {
//			FileSystem fs = FileSystem.get(conf);
//			FSDataInputStream fsIn = fs.open(new Path(file));
//			BufferedReader br = new BufferedReader(new InputStreamReader(fsIn));
//			String line = "";
//			while ((line = br.readLine()) != null) {
//				int tagPos = line.indexOf(tag + "=");
//				if (tagPos != -1) {
//					String subStr = line.substring(tagPos + tag.length());
//					int firstCommaIndex = subStr.indexOf(",");
//					int firstEqualIndex = subStr.indexOf("=");
//					value = Float.parseFloat(subStr.substring(
//							firstEqualIndex + 1, firstCommaIndex).trim());
//					break;
//				}
//			}
//			br.close();

            FileSystem fs = FileSystem.get(conf);
            Path filepath = new Path(file);
            FSDataInputStream fsIn = fs.open(filepath);
            byte[] temp = new byte[250];
            fsIn.read(temp);
            String buf = getString(temp);
            if (buf.indexOf(tag + "=") != -1) {
                buf = buf.substring(buf.indexOf(tag + "=") + tag.length() + 1).trim();
                buf = buf.substring(0, buf.indexOf(",")).trim();
                value = Float.parseFloat(buf);
                //System.out.println("value:" + value);
                fsIn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }

    public void read(float[] outputDataBuffer, KTFile ktFile) {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(ktFile.getFilePath())));
            for (int i = 0; i < outputDataBuffer.length; i++) {
                byte[] temp = new byte[4];
                dis.read(temp);
                outputDataBuffer[i] = Float.intBitsToFloat(this.getInt(temp));
            }
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ktMigration(int dbtr, Configuration conf) {
        KTFile dataFile = new KTFile(conf);
        KTFile sxyFile = new KTFile(conf);
        KTFile gxyFile = new KTFile(conf);
        KTFile cxyFile = new KTFile(conf);
        KTFile vrmsFile = new KTFile(conf);
        KTFile imageFile = new KTFile(conf);
        dataFile.setFilePath(shotsFile);
        sxyFile.setFilePath(sourceCoordinatesFile);
        gxyFile.setFilePath(receiverCoordinatesFile);
        cxyFile.setFilePath(midpointCoordinatesFile);
        vrmsFile.setFilePath(velocityFile);

        int nt, nx, ny, nin, nix, ntr;
        nt = this.histInt(shotsFile, "n1", conf);
        nx = this.histInt(shotsFile, "n2", conf);
        ny = this.histInt(shotsFile, "n3", conf);
        nin = this.histInt(shotsFile, "n4", conf);
        nix = this.histInt(shotsFile, "n5", conf);
        ntr = nx * ny * nin * nix;

        float dt, ot;
        float temp = this.histfloat(shotsFile, "d1", conf);
        dt = (temp == -1.0f) ? 0.0f : temp;
        temp = this.histfloat(shotsFile, "o1", conf);
        ot = (temp == -1.0f) ? 0.0f : temp;

        int ont, onx, ony, osize;
        ont = this.histInt(velocityFile, "n1", conf);
        onx = this.histInt(velocityFile, "n2", conf);
        ony = this.histInt(velocityFile, "n3", conf);
        osize = ont * onx * ony;

        float odt, odx, ody, oot, oox, ooy;
        temp = this.histfloat(velocityFile, "d1", conf);
        odt = (temp == -1.0f) ? 0.0f : temp;
        temp = this.histfloat(velocityFile, "d2", conf);
        odx = (temp == -1.0f) ? 0.0f : temp;
        temp = this.histfloat(velocityFile, "d3", conf);
        ody = (temp == -1.0f) ? 1.0f : temp;
        temp = this.histfloat(velocityFile, "o1", conf);
        oot = (temp == -1.0f) ? 0.0f : temp;
        temp = this.histfloat(velocityFile, "o2", conf);
        oox = (temp == -1.0f) ? 0.0f : temp;
        temp = this.histfloat(velocityFile, "o3", conf);
        ooy = (temp == -1.0f) ? 0.0f : temp;

        int n;
        n = this.histInt(sourceCoordinatesFile, "n2", conf);

        if (this.apx == 0) {
            apx = onx / 2;
        }
        if (this.apy == 0) {
            apy = (ony + 1) / 2;
        }
        if (this.maxtri == 0) {
            this.maxtri = 13;
        }
        if (trfact == 0) {
            this.trfact = 4.0f * (0.5f * (odx + ody) / dt);
        }

        // map parameters
        conf.setInt("apx", apx);
        conf.setInt("apy", apy);
        conf.setInt("onx", onx);
        conf.set("ony", String.valueOf(ony));
        conf.set("oox", String.valueOf(oox));
        conf.set("odx", String.valueOf(odx));
        conf.set("ooy", String.valueOf(ooy));
        conf.set("ody", String.valueOf(ody));
        // reduce parameters
        conf.set("nt", String.valueOf(nt));
        conf.set("ont", String.valueOf(ont));
        conf.set("ot", String.valueOf(ot));
        conf.set("dt", String.valueOf(dt));
        conf.set("oot", String.valueOf(oot));
        conf.set("odt", String.valueOf(odt));
        conf.set("maxtri", String.valueOf(maxtri));
        conf.set("trfact", String.valueOf(trfact));
        conf.set("beDiff", String.valueOf(beDiff));
        conf.set("beVerb", String.valueOf(beVerb));
        conf.set("beAntiAliasing", String.valueOf(beAntiAliasing));
        conf.set("oox", String.valueOf(oox));
        conf.set("ooy", String.valueOf(ooy));
        // System.out.println("cpuktmigration:apx:" + apx + " ,apy:" + apy);
        // imageFile.setFilePath(outputFilePath);
        //
        // float[] velocity = new float[osize];
        // float[] image = new float[osize];
        //
        // this.read(velocity, vrmsFile);
        //
        // int btr=dbtr;
        // float[] trace=new float[btr*nt];
        // float[] sxsy=new float[2*btr];
        // float[] gxgy=new float[2*btr];
        // float[] cxcy=new float[2*btr];

    }

    private void getOuputShot(int[] keys, float[] cxcy, Context context)
            throws Exception {
        // System.out.println("getOutputShot");
        long start = System.currentTimeMillis();
        Configuration conf = context.getConfiguration();
        apx = Integer.parseInt(conf.get("apx"));
        apy = Integer.parseInt(conf.get("apy"));
        beVerb = Boolean.parseBoolean(conf.get("beVerb"));
        onx = Integer.parseInt(conf.get("onx"));
        ony = Integer.parseInt(conf.get("ony"));
        ont = Integer.parseInt(conf.get("ont"));
        nt = Integer.parseInt(conf.get("nt"));
        oot = Float.parseFloat(conf.get("oot"));
        odt = Float.parseFloat(conf.get("odt"));
        dt = Float.parseFloat(conf.get("dt"));
        oox = Float.parseFloat(conf.get("oox"));
        odx = Float.parseFloat(conf.get("odx"));
        ooy = Float.parseFloat(conf.get("ooy"));
        ody = Float.parseFloat(conf.get("ody"));
        ot = Float.parseFloat(conf.get("ot"));
        beDiff = Boolean.parseBoolean(conf.get("beDiff"));
        beAntiAliasing = Boolean.parseBoolean(conf.get("beAntiAliasing"));
        maxtri = Integer.parseInt(conf.get("maxtri"));
        trfact = Float.parseFloat(conf.get("trfact"));
        long end = System.currentTimeMillis();
        System.out.println("ConfTime:" + (end - start) + "ms");
        int[] ap = new int[onx * ony];
        float[] velocity = new float[ont];
        float[] trace = new float[nt];
        float[] sxsy = new float[2];
        float[] gxgy = new float[2];
        // System.out.println("getOuputShot:apx:" + apx + " ,apy:" + apy);
        HashMap<Integer, float[]> maps = new HashMap<Integer, float[]>();
        int[] indexs = new int[onx * ony];
        for (int i = 0; i < keys.length; ++i) {
            long start1 = System.currentTimeMillis();
            int minix = onx - 1;
            int maxix = 0;
            int miniy = ony - 1;
            int maxiy = 0;
            int ix, iy;
            // 计算输入道在输出结果中的位置
            ix = (int) ((cxcy[2 * i] - oox) / odx + 0.5f);// 计算idxi
            iy = (int) ((cxcy[2 * i + 1] - ooy) / ody + 0.5f);// 计算idyi
            if (ix < minix) {
                minix = ix;
            }
            if (ix > maxix) {
                maxix = ix;
            }
            if (iy < miniy) {
                miniy = iy;
            }
            if (iy > maxiy) {
                maxiy = iy;
            }
            // System.out.println("test");
            // Aperture corners，求出aox,aoy,aex,aey
            // 计算孔径范围，el_cx1代表输出道
            int el_cx1 = minix;
            int el_cx2 = maxix;
            int el_cy1 = miniy;
            int el_cy2 = maxiy;

            minix -= apx;
            if (minix < 0) {
                minix = 0;
            }
            miniy -= apy;
            if (miniy < 0) {
                miniy = 0;
            }
            maxix += apx;
            if (maxix >= onx) {
                maxix = onx - 1;
            }
            maxiy += apy;
            if (maxiy >= ony) {
                maxiy = ony - 1;
            }

            // if (beVerb) {
            // // System.out.println("key:" + keys[i]);
            // System.out.println("Rectangular aperture: " + minix + "-"
            // + maxix + "," + miniy + "-" + maxiy);
            // // Logger.getLogger("beVerb").info(
            // // "Rectangular aperture:" + minix + "-" + maxix + ","
            // // + miniy + "-" + maxiy);
            // }

            int l = 0;
            // 计算输出道
            // String outputshot = "";
            int iiy, iix, oidx;
            for (iiy = miniy; iiy <= maxiy; ++iiy) {
                for (iix = minix; iix <= maxix; ++iix) {
                    oidx = iiy * onx + iix;
                    if ((iix >= el_cx1 && iix <= el_cx2)
                            && (iiy >= el_cy1 && iiy <= el_cy2)) {
                        ap[l] = oidx;
                        // outputshot += String.valueOf(ap[l]) + ",";
                        ++l;
                        continue;
                    }

                    float el_x = (iix < el_cx1) ? iix - el_cx1 : iix - el_cx2;
                    float el_y = (iiy < el_cy1) ? iiy - el_cy1 : iiy - el_cy2;
                    // Check if the point is within one of the ellipses
                    if ((el_x * el_x / (apx * apx) + (el_y * el_y)
                            / (apy * apy)) < 1.0f) {
                        ap[l] = oidx;
                        // outputshot += String.valueOf(ap[l]) + ",";
                        ++l;
                    }
                }// for ix
            }// for iy
            // System.out.println("l:"+l);
            // this.computOutput(conf, keys[i], ap, l, context);
            try {
                String PATH = conf.get("path");
                long start3 = System.currentTimeMillis();
                // FileSystem fs = FileSystem.get(conf);
                this.readData(PATH + "data/shot.data", nt, keys[i],
                        Float.SIZE / 8, trace, conf);
                this.readData(PATH + "data/fsxy.data", 2, keys[i],
                        Float.SIZE / 8, sxsy, conf);
                this.readData(PATH + "data/fgxy.data", 2, keys[i],
                        Float.SIZE / 8, gxgy, conf);
                long end3 = System.currentTimeMillis();
                System.out.println("readTime:" + (end3 - start3) + "ms");

                // fs.close();
                float sx = sxsy[0];
                float sy = sxsy[1];
                float gx = gxgy[0];
                float gy = gxgy[1];
                // ktMigSbDiff---beDiff
                if (beDiff) {
                    this.ktMigSbDiff(trace, nt, dt);
                }

                if (beAntiAliasing) {
                    this.ktMigCint(trace, nt);
                    this.ktMigAcint(trace, nt);
                }

                System.out.println("Inputkey:" + keys[i]);
                // System.out.println("trace:" + Str);
                // System.exit(0);

                for (int t = 0; t < l; t++) {
                    float[] image = new float[ont];
                    // fs = FileSystem.get(conf);
                    // read the velocity file
                    this.readData(PATH + "data/rmsv.data", ont, ap[t],
                            Float.SIZE / 8, velocity, conf);

                    float ox = oox + (ap[t] % onx) * odx;
                    float oy = ooy + (ap[t] / onx) * ody;

                    this.ktMigKernel(trace, velocity, image, ox, oy, sx, sy,
                            gx, gy, nt, ont, ot, dt, oot, odt, maxtri, trfact,
                            beAntiAliasing);

                    if (maps.containsKey(ap[t])) {
                        float[] temp = maps.get(ap[t]);
                        for (int index = 0; index < temp.length; index++) {
                            temp[index] += image[index];
                        }
                        maps.put(ap[t], temp);
                        temp = null;
                    } else {
                        maps.put(ap[t], image);
                    }
                    // context.write(new Text(String.valueOf(ap[t])), new Text(
                    // result));
                    image = null;
                }

                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long end1 = System.currentTimeMillis();
            System.out.println("keyTime:" + (end1 - start1) + "ms");
        }
        long start2 = System.currentTimeMillis();
        Iterator iter = maps.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            int key = (Integer) entry.getKey();
            float[] value = (float[]) entry.getValue();
            String temp = "";
            for (int i = 0; i < value.length; i++) {
                temp += value[i] + ",";
            }
            temp = temp.substring(0, temp.lastIndexOf(","));
            context.write(new Text(String.valueOf(key)), new Text(temp));
        }
        velocity = null;
        trace = null;
        sxsy = null;
        gxgy = null;
        long end2 = System.currentTimeMillis();
        System.out.println("mapTime:" + (end2 - start2) + "ms");
    }

    private void readData(String path, int length, int start, int SIZE,
                          float[] datas, Configuration conf) {
        try {
            FileSystem fs = FileSystem.get(conf);
            FSDataInputStream fileIn = fs.open(new Path(path));
            fileIn.seek(length * start * SIZE);
            byte[] temp = new byte[4];
            // String result = "";
            for (int i = 0; i < datas.length; i++) {
                fileIn.read(temp);
                datas[i] = Float.intBitsToFloat(getInt(temp));
                // result += datas[i] + ",";
            }
            // System.out.println(result);
            fileIn.close();
            // fs.close();
            fs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ktMigKernel(float[] trace, float[] vrms, float[] image,
                             float ox, float oy, float sx, float sy, float gx, float gy, int nt,
                             int ont, float ot, float dt, float oot, float odt, int trm,
                             float trf, boolean aa) {

        float v, inv;
        float inv2trf, nf;
        float j, scale, smp, so2, go2;
        float depth2, dx, dy, ts, tg;

        // Loop over tau indices
        for (int k = 0; k < ont; ++k) {
            // RMS velocity at image location
            v = vrms[k];
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
                    image[k] += INTSMP(trace, j);
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
                smp = 2.0f * INTSMP(trace, j) - INTSMP(trace, (j - nf - 1.0f))
                        - INTSMP(trace, (j + nf + 1.0f));
                // Contribute to the image point
                image[k] += scale * smp;
            }
        }
    }

    private float INTSMP(float[] t, float i) {
        float out;
        float out1;
        if ((int) i + 1 >= t.length) {
            out1 = 0;
        } else {
            out1 = t[(int) (i) + 1];
        }
        if ((int) i >= t.length) {
            out = 0;
        } else {
            out = t[(int) i];
        }
        float value = ((1.0f - i + (float) ((int) i)) * out + (i - (float) ((int) i))
                * out1);
        return value;
    }

    private void ktMigCint(float[] trace, int nt) {
        for (int i = 1; i < nt; ++i) {
            trace[i] += trace[i - 1];
        }
    }

    private void ktMigAcint(float[] trace, int nt) {
        for (int i = nt - 2; i >= 0; i--) {
            trace[i] += trace[i + 1];
        }
    }

    private void ktMigSbDiff(float[] trace, int length, float distance) {
        float val0, val1, val2;
        val1 = trace[0];
        val2 = trace[0];

        for (int i = 0; i < length; ++i) {
            val0 = trace[i];
            trace[i] = 0.5f * (3.0f * val0 - 4.0f * val1 + val2) / distance;
            val2 = val1;
            val1 = val0;
        }
    }

    @Override
    public void map(IntWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // System.out.println("key: " + key);
        // System.out.println("value: "+value);
        // System.out.println("length: " + value.toString().split(",").length);
        String[] datas = value.toString().split(",");
        int[] keys = new int[datas.length / 2];
        float[] fcxydatas = new float[datas.length];
        for (int i = 0; i < datas.length / 2; i++) {
            keys[i] = key.get() + i;
            fcxydatas[2 * i] = Float.parseFloat(datas[2 * i]);
            fcxydatas[2 * i + 1] = Float.parseFloat(datas[2 * i + 1]);
        }
        datas = null;
        try {
            System.out.println("begin map");
            long start = System.currentTimeMillis();
            // this.getOuputShot(keys, fcxydatas, context);

            //HashMap<Integer, float[]> maps = new HashMap<Integer, float[]>();

            List<Thread> threads = new ArrayList<Thread>();
            int place = 4;
            HashMap<Integer, float[]>[] maplists = new HashMap[place];
            for (int i = 0; i < place; i++) {
                maplists[i] = new HashMap<Integer, float[]>();
            }
            int count = keys.length / place;
            for (int i = 0; i < place; i++) {
                System.out.println("place:" + i);
                int[] keytemp = null;
                float[] fcxy = null;
                if (i == place - 1) {
                    keytemp = new int[keys.length - (place - 1) * count];
                    fcxy = new float[2 * keytemp.length];

                } else {
                    keytemp = new int[count];
                    fcxy = new float[2 * keytemp.length];

                }
                int con = i * count;
                System.out.println("con:" + con);
                for (int j = 0; j < keytemp.length; j++) {
                    keytemp[j] = keys[con + j];
                    fcxy[2 * j] = fcxydatas[2 * (con + j)];
                    fcxy[2 * j + 1] = fcxydatas[2 * (con + j) + 1];
                }
                // System.out.println("keytemp:"+keytemp[0]);
//                Thread thread = new Thread(new OutputShotThread(keytemp, fcxy, context, maplists));
//                threads.add(thread);
//                thread.start();
            }
            keys = null;
            fcxydatas = null;

            // for (int i = 0; i < threads.size(); i++) {
            // threads.get(i).start();
            // }

            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).join();
            }

//			while (true) {
//				if (!OutputShotThread.hasRunningThread()) {
//					threads = null;
//					// System.gc();
//					break;
//				} else {
//					Thread.currentThread().sleep(500);
//				}
//			}

            long end = System.currentTimeMillis();
            System.out.println("OutputTime:" + (end - start) + "ms");

//			Iterator iter = maps.entrySet().iterator();
//			while (iter.hasNext()) {
//				Entry entry = (Entry) iter.next();
//				int entrykey = (Integer) entry.getKey();
//				float[] entryvalue = (float[]) entry.getValue();
//				String result = "";
//				for (int j = 0; j < entryvalue.length; j++) {
//					result += entryvalue[j] + ",";
//				}
//				result = result.substring(0, result.lastIndexOf(","));
//				context.write(new Text(String.valueOf(entrykey)), new Text(
//						result));
//			}

            List<Thread> threadlists = new ArrayList<Thread>();
            for (int j = 0; j < place; j++) {
//                Thread thread = new ContextWrite(maplists[j], context);
//                threadlists.add(thread);
//                thread.start();
            }

            for (int j = 0; j < place; j++) {
                threadlists.get(j).join();
            }
            long end1 = System.currentTimeMillis();
            System.out.println("mapTime:" + (end1 - end) + "ms");
            threadlists = null;
            System.gc();
            // String keyStr="";
            // for(int i=0;i<keys.length;i++){
            // keyStr+=String.valueOf(keys[i])+",";
            // }
            // LOG.info("key: "+keyStr);
            // LOG.info("value: "+value.toString());
            // LOG.info("length: "+datas.length);
            // context.write(new Text(String.valueOf(key.get())), value);
            // context.write(new Text(String.valueOf(key)), value);
        } catch (Exception e) {
            System.out.println("Exception in the map fuction");
            e.printStackTrace();
        }
    }
}
