package hadoop;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class Server implements CalculateProtocol {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }
}
