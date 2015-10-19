package spark;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.util.HashMap;

class GetOutputShot {
    private boolean beAntiAliasing;
    private boolean beDiff;
    private boolean beVerb;
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

    private int[] keys = null;
    private float[] fcxy = null;
    private ProgramConf pconf = null;
    private HashMap<Integer, String> maps = null;

    public GetOutputShot(int[] keys, float[] fcxy, ProgramConf pconf,
                         HashMap<Integer, String> maps) {
        this.keys = keys;
        this.fcxy = fcxy;
        this.pconf = pconf;
        this.maps = maps;
    }

    public void run() {
        try {
            if (keys.length > 0)
                getOuputShot(keys, fcxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getOuputShot(int[] keys, float[] cxcy) throws Exception {
        apx = pconf.getApx();
        apy = pconf.getApy();
        System.out.println("apx:" + apx + ",apy:" + apy);
        beVerb = pconf.isBeVerb();
        onx = pconf.getOnx();
        ony = pconf.getOny();
        ont = pconf.getOnt();
        nt = pconf.getNt();
        oot = pconf.getOot();
        odt = pconf.getOdt();
        dt = pconf.getDt();
        oox = pconf.getOox();
        odx = pconf.getOdx();
        ooy = pconf.getOoy();
        ody = pconf.getOdy();
        ot = pconf.getOt();
        beDiff = pconf.isBeDiff();
        beAntiAliasing = pconf.isBeAntiAliasing();
        maxtri = pconf.getMaxtri();
        trfact = pconf.getTrfact();
        String PATH = pconf.getPath();

        int[] ap = new int[onx * ony];

        for (int i = 0; i < keys.length; ++i) {
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
            int l = 0;
            // 计算输出道
            int iiy, iix, oidx;
            for (iiy = miniy; iiy <= maxiy; ++iiy) {
                for (iix = minix; iix <= maxix; ++iix) {
                    oidx = iiy * onx + iix;
                    if ((iix >= el_cx1 && iix <= el_cx2)
                            && (iiy >= el_cy1 && iiy <= el_cy2)) {
                        ap[l] = oidx;
                        ++l;
                        continue;
                    }

                    float el_x = (iix < el_cx1) ? iix - el_cx1 : iix - el_cx2;
                    float el_y = (iiy < el_cy1) ? iiy - el_cy1 : iiy - el_cy2;
                    // Check if the point is within one of the ellipses
                    if ((el_x * el_x / (apx * apx) + (el_y * el_y)
                            / (apy * apy)) < 1.0f) {
                        ap[l] = oidx;
                        ++l;
                    }
                }// for ix
            }// for iy

            this.quick_sort(ap, 0, l - 1);

            for (int j = 0; j < l; j++) {
                synchronized (maps) {
//                    HashMap<Integer, String> map = maps[ap[j]
//                            % maps.length];
                    if (!maps.containsKey(ap[j])) {
                        maps.put(ap[j], String.valueOf(keys[i]));
                    } else {
                        String tt = maps.get(ap[j]);
                        maps.put(ap[j], tt + "," + keys[i]);
                    }
//                    maps[ap[j] % maps.length] = map;
                }
            }

//            System.out.println("l:" + l);
//            System.out.println("ap[l]:" + ap[0]);

        }
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

    private void readPerVelocityData(float[] velocity, int[] ap, int ont, int l, String filename, FileSystem fs, int SIZE) {
        try {
            FSDataInputStream fileIn = fs.open(new Path(filename));
            byte[] temp = new byte[ont * SIZE];
            //byte[] tt = new byte[SIZE];
            for (int i = 0; i < l; i++) {
                fileIn.read(ap[i] * ont * SIZE, temp, 0, temp.length);
                for (int j = 0; j < ont; j++) {

//                    for (int t = 0; t < SIZE; t++) {
//                        tt[t] = temp[SIZE * j + t];
//                    }
                    velocity[i * ont + j] = Float.intBitsToFloat(getInt(temp, j * SIZE, false));
                }
            }
            fileIn.close();
            temp = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//            String command = "hadoop job -kill " + context.getJobID();
//            try {
//                Runtime.getRuntime().exec(command);
//            } catch (IOException ee) {
//                ee.printStackTrace();
//            }
        }
    }

    private void readData(String path, int length, int start, int totalLength, int SIZE,
                          float[] datas, FileSystem fs, boolean flag) {
        try {
            // FileSystem fs = FileSystem.newInstance(conf);
            FSDataInputStream fileIn = fs.open(new Path(path));
            //fileIn.seek(length * start * SIZE);
            //byte[] temp = new byte[4];
            // String result = "";
            //System.out.println("datalength:" + datas.length);
            byte[] datafully = new byte[totalLength * 4];
            fileIn.read(length * start * SIZE, datafully, 0, datafully.length);
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
//            String command = "hadoop job -kill " + context.getJobID();
//            try {
//                Runtime.getRuntime().exec(command);
//            } catch (IOException ee) {
//                ee.printStackTrace();
//            }
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

}
