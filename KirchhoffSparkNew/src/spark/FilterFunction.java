package spark;

import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

/**
 * Created by jtang on 2015/1/14 0014.
 */
public class FilterFunction implements Function<Tuple2<Integer, String>, Boolean> {
    private int splits = -1;
    private int index = -1;

    public FilterFunction(int splits, int index) {
        this.splits = splits;
        this.index = index;
    }

    @Override
    public Boolean call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
        int key = integerStringTuple2._1();
        if (key % splits == index) {
            return true;
        }
        return false;
    }
}
