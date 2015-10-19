package spark;

import org.apache.spark.api.java.function.Function2;


public class ReduceFuction implements Function2<String, String, String> {

    @Override
    public String call(String text1, String text2) throws Exception {
        // TODO Auto-generated method stub
        return text1 + "," + text2;
    }

}
