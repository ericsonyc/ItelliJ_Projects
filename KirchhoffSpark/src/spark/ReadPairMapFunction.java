package spark;

import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;

import scala.Tuple2;

public class ReadPairMapFunction implements PairFlatMapFunction<Tuple2<String, String>, String, String>  {

	private String filename;
	private SparkConf conf;
	private static ArrayList<Tuple2<String, String>> arrays = new ArrayList<Tuple2<String, String>>();
	
	public ReadPairMapFunction(String filename,SparkConf conf){
		this.filename=filename;
		this.conf=conf;
	}
	
	@Override
	public Iterable<Tuple2<String, String>> call(Tuple2<String, String> tuple)
			throws Exception {
		// TODO Auto-generated method stub
		int output=Integer.parseInt(tuple._1());
//		int length=Integer.parseInt(conf.get("ont"));
//		String value=this.readData(length, output);
//		value+="#"+tuple._2();
		arrays.add(new Tuple2<String,String>(tuple._1(),tuple._1()+"#"+tuple._2()));
		return null;
	}
	
//	private String readData(int length, int start) {
//		String value="";
//		try {
//			JavaSparkContext jsc=new JavaSparkContext(conf);
//			Configuration hadoopConf=jsc.hadoopConfiguration();
//			FileSystem fs=FileSystem.get(hadoopConf);
//			jsc.stop();
//			//float[] dates=new float[length];
//			FSDataInputStream fileIn = fs.open(new Path(filename));
//			fileIn.seek(length * start);
//			byte[] temp = new byte[4];
//			float t=0;
//			for (int i = 0; i < length; i++) {
//				fileIn.read(temp);
//				t = Float.intBitsToFloat(getInt(temp));
//				value+=String.valueOf(t)+",";
//			}
//			fileIn.close();
//			value=value.substring(0, value.lastIndexOf(","));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return value;
//	}
//
//	public static int getInt(byte[] bytes) {
//		return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
//				| (0xff0000 & (bytes[2] << 16))
//				| (0xff000000 & (bytes[3] << 24));
//	}

}
