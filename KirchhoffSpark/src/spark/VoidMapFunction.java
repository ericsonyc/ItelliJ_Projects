package spark;

import java.util.Iterator;

import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class VoidMapFunction implements VoidFunction<Iterator<Tuple2<Integer, String>>> {

	private ProgramConf pconf=null;
	public VoidMapFunction(ProgramConf pconf){
		this.pconf=pconf;
	}
	
	@Override
	public void call(Iterator<Tuple2<Integer, String>> ites) throws Exception {
		// TODO Auto-generated method stub
		MyMapFunction map=new MyMapFunction(pconf);
		Iterable<Tuple2<Integer, String>> iterators=map.call(ites);
	}

}
