package spark;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.input.PortableDataStream;
import scala.Tuple2;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtang on 2015/1/15 0015.
 */
public class MyFlatMapFunction implements FlatMapFunction<Tuple2<String, PortableDataStream>, String> {

    private int splitLength;
    private int index;

    public MyFlatMapFunction(int splitLength,int index){
        this.splitLength=splitLength;
        this.index=index;
    }

    @Override
    public Iterable<String> call(Tuple2<String, PortableDataStream> stringPortableDataStreamTuple2) throws Exception {
        PortableDataStream portableDataStream=stringPortableDataStreamTuple2._2();
        DataInputStream dis=portableDataStream.open();
        List<String> resultLists=new ArrayList<String>();
        byte[] temp=new byte[splitLength];
        int count=dis.read(temp)/4;
        byte[] b=new byte[4];
        String result=(index*splitLength)/8+"#";
        for(int i=0;i<count;i++){
            for(int j=0;j<b.length;j++){
                b[j]=temp[4*i+j];
            }
            result=result+Float.intBitsToFloat(getInt(b))+",";
        }
        resultLists.add(result);
        return resultLists;
    }

    public int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }
}
