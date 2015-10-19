package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShotReduce extends Reducer<Text, Text, Text, Text> {

	private int nt, ont;
	private float ot, dt, oot, odt, oox, ooy, odx, ody;
	int maxtri;
	float trfact;
	boolean beAntiAliasing;

	// private static float[] image = null;

	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Iterator<Text> ites = values.iterator();
		// String connect="";
		ArrayList<Integer> datas = new ArrayList<Integer>();
		while (ites.hasNext()) {
			// connect+=ites.next().toString()+",";
			String[] temps = ites.next().toString().split(",");
			for (int i = 0; i < temps.length; i++) {
				datas.add(Integer.parseInt(temps[i]));
			}
		}
		Configuration conf = context.getConfiguration();
		this.kirchhoffMigrationKernel(Integer.parseInt(key.toString()), datas,
				conf, context);
		// connect=connect.substring(0, connect.lastIndexOf(","));
		// System.out.println("key,value:"+key+","+connect);
		// System.out.println("Reduce");
		// context.write(key, new Text(connect));
	}

	private void kirchhoffMigrationKernel(int outputShot,
			ArrayList<Integer> inputList, Configuration conf, Context context) {
		Logger.getLogger("kernel").log(Level.INFO, "kernel");
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
		for (int i = 0; i < ont; i++) {
			image[i] = 0;
		}
		beAntiAliasing = Boolean.parseBoolean(conf.get("beAntiAliasing"));
		float[] velocity = new float[ont];
		float[] trace = new float[nt];
		float[] sxsy = new float[2];
		float[] gxgy = new float[2];
		try {
			FileSystem fs = FileSystem.get(conf);
			FSDataInputStream fileIn = null;
			for (int i = 0; i < inputList.size(); i++) {
				int index = inputList.get(i);// get the input index
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
			String tempImage = "";
			for (int j = 0; j < ont; j++) {
				tempImage += String.valueOf(image[j]) + ",";
			}
			tempImage = tempImage.substring(0, tempImage.lastIndexOf(","));
			context.write(new Text(String.valueOf(outputShot)), new Text(
					tempImage));
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
		if ((int) i + 1 > t.length) {
			out1 = 0;
		} else {
			out1 = t[(int) (i) + 1];
		}
		if ((int) i > t.length) {
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
