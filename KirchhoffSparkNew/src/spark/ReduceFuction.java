package spark;

import org.apache.spark.api.java.function.Function2;


public class ReduceFuction implements Function2<String, String, String> {

    @Override
    public String call(String text1, String text2) throws Exception {
        // TODO Auto-generated method stub
        String[] data1 = text1.split(",");
        String[] data2 = text2.split(",");
        float[] data = new float[data1.length];
        String result = "";
        for (int i = 0; i < data1.length; i++) {
            data[i] = Float.parseFloat(data1[i]) + Float.parseFloat(data2[i]);
            result += data[i] + ",";
        }
        result = result.substring(0, result.lastIndexOf(","));
        return result;
    }

}
