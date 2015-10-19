package hadoopcuda;

/**
 * Created by ericson on 2015/3/11 0011.
 */
public class FeatureExtractSIFT_CPU {
    static {
        try {
            System.loadLibrary("SiftGPU");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Cannot load SIFT library\n" + e.toString());
        }
    }

    public FeatureExtractSIFT_CPU() {

    }

    public static void main(String[] args) {

    }

}
