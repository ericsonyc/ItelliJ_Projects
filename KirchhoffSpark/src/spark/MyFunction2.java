package spark;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.io.Serializable;
import java.util.ArrayList;

public class MyFunction2 implements Function2<String, String, String>, Serializable {

    private SparkConf conf = null;
    private int nt, ont;
    private float ot, dt, oot, odt, oox, ooy, odx, ody;
    int maxtri;
    float trfact;
    boolean beAntiAliasing;

    public MyFunction2(SparkConf conf) {
        this.conf = conf;
    }

    @Override
    public String call(String text1, String text2) throws Exception {
        // TODO Auto-generated method stub
        String[] data = text1.split("#");
        int output = Integer.parseInt(data[0]);
//		String[] velocityStrs=data[1].split(",");
//		float[] velocity=new float[velocityStrs.length];
//		for(int i=0;i<velocityStrs.length;i++){
//			velocity[i]=Float.parseFloat(velocityStrs[i]);
//		}
        ArrayList<String> array = new ArrayList<String>();
        array.add(data[1]);
        String[] data1 = text2.split("#");
        array.add(data1[1]);

//		for(int i=0;i<input.length;i++){
//			array.add(Integer.parseInt(input[i]));
//		}
//		data=text2.split("#");
//		input=data[2].split(",");
//		for(int i=0;i<input.length;i++){
//			array.add(Integer.parseInt(input[i]));
//		}
        String value = this.kirchhoffMigrationKernel(output, array, conf);
        // connect=connect.substring(0, connect.lastIndexOf(","));
        // System.out.println("key,value:"+key+","+connect);
        System.out.println("Reduce");
        // context.write(key, new Text(connect));
        return String.valueOf(output) + "#" + value;
    }

    private String kirchhoffMigrationKernel(int outputShot,
                                            ArrayList<String> inputList, SparkConf conf) {
        nt = Integer.parseInt(conf.get("nt"));
        ont = Integer.parseInt(conf.get("ont"));
        ot = Float.parseFloat(conf.get("ot"));
        dt = Float.parseFloat(conf.get("dt"));
        oot = Float.parseFloat(conf.get("oot"));
        odt = Float.parseFloat(conf.get("odt"));
        int onx = Integer.parseInt(conf.get("onx"));
        int ony = Integer.parseInt(conf.get("ony"));
        maxtri = Integer.parseInt(conf.get("maxtri"));
        trfact = Float.parseFloat(conf.get("trfact"));
        boolean beDiff = Boolean.parseBoolean(conf.get("beDiff"));
        boolean beAntiAliasing = Boolean.parseBoolean(conf
                .get("beAntiAliasing"));
        int osize = ont * onx * ony;
        float[] image = new float[ont];

        beAntiAliasing = Boolean.parseBoolean(conf.get("beAntiAliasing"));
        float[] velocity = new float[ont];
        float[] trace = new float[nt];
        float[] sxsy = new float[2];
        float[] gxgy = new float[2];
        String tempImage = "";
        try {
            FileSystem fs = FileSystem.get(new JavaSparkContext(conf).hadoopConfiguration());
            FSDataInputStream fileIn = null;
            for (int i = 0; i < inputList.size(); i++) {
                String str = inputList.get(i);
                if (str.indexOf("&") != -1) {
                    String[] data = str.substring(0, str.lastIndexOf("&")).split(",");
                    for (int j = 0; j < data.length; j++) {
                        image[j] = Float.parseFloat(data[j]);
                    }
                    continue;
                } else {
                    for (int j = 0; j < ont; j++) {
                        image[j] = 0;
                    }
                }
                int index = Integer.parseInt(str);// get the input index
                // byte[] tempBytes=new byte[Float.SIZE/8];

                // read the velocity file
                this.readData("data/rmsv.data", ont, outputShot, velocity, fs);

                // read the trace file
                this.readData("data/shot.data", nt, index, trace, fs);

                // read the sxsy file and gxgy file
                this.readData("data/fsxy.data", 2, index, sxsy, fs);
                this.readData("data/fgxy.data", 2, index, gxgy, fs);

                // ktMigSbDiff---beDiff
                if (beDiff) {
                    this.ktMigSbDiff(trace, nt, dt);
                }

                if (beAntiAliasing) {
                    this.ktMigCint(trace, nt);
                    this.ktMigAcint(trace, nt);
                }

                float ox = oox + (outputShot % onx) * odx;
                float oy = ooy + (outputShot / onx) * ody;
                float sx = sxsy[0];
                float sy = sxsy[1];
                float gx = gxgy[0];
                float gy = gxgy[1];
                this.ktMigKernel(trace, velocity, image, ox, oy, sx, sy, gx,
                        gy, nt, ont, ot, dt, oot, odt, maxtri, trfact,
                        beAntiAliasing);
            }

            for (int j = 0; j < ont; j++) {
                tempImage += String.valueOf(image[j]) + ",";
            }
            tempImage = tempImage.substring(0, tempImage.lastIndexOf(","));
            tempImage += "&";
            //context.write(new Text(String.valueOf(outputShot)), new Text(tempImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
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

        for (int i = 0; i < nt; ++i) {
            val0 = trace[i];
            trace[i] = 0.5f * (3.0f * val0 - 4.0f * val1 + val2) / distance;
            val2 = val1;
            val1 = val0;
        }
    }

    private void readData(String path, int length, int start, float[] datas,
                          FileSystem fs) {
        try {
            FSDataInputStream fileIn = fs.open(new Path(path));
            fileIn.seek(length * start);
            byte[] temp = new byte[4];
            for (int i = 0; i < length; i++) {
                fileIn.read(temp);
                datas[i] = Float.intBitsToFloat(getInt(temp));
            }
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }

}
