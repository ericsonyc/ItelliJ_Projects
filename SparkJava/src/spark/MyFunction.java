package spark;

import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

/**
 * Created by jtang on 2015/1/14 0014.
 */
public class MyFunction implements Function<Tuple2<Integer, String>, Boolean> {

    private int splits = -1;
    private int index = -1;

    public MyFunction(int splits, int index) {
        this.splits = splits;
        this.index = index;
    }

    @Override
    public Boolean call(Tuple2<Integer, String> v1) throws Exception {
        if (v1._1() % splits == index) {
            return true;
        } else {
            return false;
        }
    }
}
