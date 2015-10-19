package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by jtang on 2015/1/14 0014.
 */
public class JavaKMeans {
    private static HashMap<Integer, Iterable<Double>> kPoint = new HashMap<Integer, Iterable<Double>>();

    static class Closet implements Serializable {
        int i;
        Iterable<Double> iter;

        public Closet(Iterable<Double> dd, int ii) {
            this.i = ii;
            this.iter = dd;
        }

        public Closet add(Closet clt) {
            int ii = this.i + clt.i;
            Iterator<Double> titer = this.iter.iterator();
            Iterator<Double> oiter = clt.iter.iterator();
            Iterator<Double> tmp = this.iter.iterator();
            int count = 0;
            while (tmp.hasNext()) {
                tmp.next();
                count++;
            }
            Double[] dd = new Double[count];
            count = 0;
            while (titer.hasNext() && oiter.hasNext()) {
                Double me = titer.next();
                Double other = oiter.next();
                dd[count++] = me + other;
            }
            Iterable<Double> newiter = Arrays.asList(dd);
            return new Closet(newiter, ii);
        }

        public Iterable<Double> division(){
            Iterator<Double> tmp=iter.iterator();
            Iterator<Double> iterator=iter.iterator();
            int count=0;
            while(tmp.hasNext()){
                tmp.next();
                count++;
            }
            Double[] dd=new Double[count];
            count=0;
            while(iterator.hasNext()){
                dd[count++]=iterator.next()/this.i;
            }
            return Arrays.asList(dd);
        }
    }

    public static void main(String[] args){
        if(args.length<4){
            System.err.println("Usage:SparkLocalKMeans <master> <file> <k> <coverageDist>");
            System.exit(1);
        }
        int k=Integer.parseInt(args[1]);
        double converge=Double.parseDouble(args[2]);
        double tmpDist=1.0;
        SparkConf sparkConf=new SparkConf().setAppName("kmeans");
        JavaSparkContext sc=new JavaSparkContext(sparkConf);
        JavaRDD<String> lines=sc.textFile(args[0]);

    }

}
