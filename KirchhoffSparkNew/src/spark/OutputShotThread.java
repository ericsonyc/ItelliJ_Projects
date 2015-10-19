package spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.util.HashMap;

class OutputShotThread implements Runnable {
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
	private ProgramConf pconf = null;

	private int[] keys = null;
	private float[] fcxy = null;
	private HashMap<Integer,float[]> maps = null;

	public OutputShotThread(int[] keys, float[] fcxy,
			HashMap<Integer,float[]> maps, ProgramConf pconf) {
		this.keys = keys;
		this.fcxy = fcxy;
		this.maps = maps;
		this.pconf = pconf;
	}

	// private void register(Runnable run){
	// synchronized (list) {
	// list.add(run);
	// }
	// }
	//
	// private void unregister(Runnable run){
	// synchronized (list) {
	// list.remove(run);
	// }
	// }

	// public static boolean hasRunningThread(){
	// synchronized (list) {
	// return (list.size()>0);
	// }
	// }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// this.register(this);
			getOuputShot(keys, fcxy);
			// this.unregister(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getOuputShot(int[] keys, float[] cxcy) throws Exception {
		// System.out.println("getOutputShot");
		// long start = System.currentTimeMillis();
		apx = pconf.getApx();
		apy = pconf.getApy();
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

		FileSystem fs = FileSystem.get(new Configuration());
		long startTime = System.currentTimeMillis();
		int[] ap = new int[onx * ony];
		float[] velocity = new float[ont * onx * ony];
		this.readData(PATH + "data/rmsv.data", ont, 0, Float.SIZE / 8,
				velocity, fs);
		float[] trace = new float[nt * keys.length];
		float[] sxsy = new float[2 * keys.length];
		float[] gxgy = new float[2 * keys.length];

		this.readData(PATH + "data/shot.data", nt, keys[0], Float.SIZE / 8,
				trace, fs);
		this.readData(PATH + "data/fsxy.data", 2, keys[0], Float.SIZE / 8,
				sxsy, fs);
		this.readData(PATH + "data/fgxy.data", 2, keys[0], Float.SIZE / 8,
				gxgy, fs);
		long endTime = System.currentTimeMillis();
		//System.out.println("readTime:" + (endTime - startTime) + "ms");
		//System.out.println("trace:" + trace[nt]);
		//System.out.println("keys:"+keys[0]+",length:"+keys.length);

		//HashMap<Integer, float[]> hashs = new HashMap<Integer, float[]>();

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

				// fs.close();
				float sx = sxsy[2 * i];
				float sy = sxsy[2 * i + 1];
				float gx = gxgy[2 * i];
				float gy = gxgy[2 * i + 1];
				// ktMigSbDiff---beDiff
				if (beDiff) {
					this.ktMigSbDiff(trace, nt, i, dt);
				}

				if (beAntiAliasing) {
					this.ktMigCint(trace, nt, i);
					this.ktMigAcint(trace, nt, i);
				}
				if(i%100==0){
					System.out.println("Inputkey:" + keys[i]);
				}
				
				// System.out.println("trace:" + Str);
				// System.exit(0);

				for (int t = 0; t < l; t++) {
					float[] image = new float[ont];
					// fs = FileSystem.get(conf);
					// read the velocity file

					float ox = oox + (ap[t] % onx) * odx;
					float oy = ooy + (ap[t] / onx) * ody;
					this.ktMigKernel(trace, i, velocity, ap[t], image, ox, oy,
							sx, sy, gx, gy, nt, ont, ot, dt, oot, odt, maxtri,
							trfact, beAntiAliasing);

					synchronized (maps) {
						if (maps.containsKey(ap[t])) {
							float[] data = maps.get(ap[t]);
							for (int j = 0; j < ont; j++) {
								data[j] += image[j];
							}
							maps.put(ap[t], data);
						} else {
							maps.put(ap[t], image);
						}
					}
					// context.write(new Text(String.valueOf(ap[t])), new Text(
					// result));
					image = null;
				}
				// fs.close();
				// System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long end1 = System.currentTimeMillis();
			//System.out.println("keyTime:" + (end1 - start1) + "ms");
		}
		// int index=0;
		// for (Entry entry : hashs.entrySet()) {
		// int key = (Integer) entry.getKey();
		// float[] value = (float[]) entry.getValue();
		// maps.add(new Tuple2<Integer, float[]>(key, value));
		// index++;
		// // if(index%100==0){
		// // System.out.println("key:"+key+",value length:"+value.length);
		// // }
		// }

		velocity = null;
		trace = null;
		sxsy = null;
		gxgy = null;
		// long end2 = System.currentTimeMillis();
		// System.out.println("mapTime:" + (end2 - start2) + "ms");
		// System.gc();
	}

	private void readData(String path, int length, int start, int SIZE,
			float[] datas, FileSystem fs) {
		try {
			// FileSystem fs = FileSystem.newInstance(conf);
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

	public int getInt(byte[] bytes) {
		return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
				| (0xff0000 & (bytes[2] << 16))
				| (0xff000000 & (bytes[3] << 24));
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
