package hadoop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by ericson on 2015/3/24 0024.
 */
public class DynamicProxyExample {
    public static void main(String[] args) {
        CalculateProtocol server = new Server();
        InvocationHandler handler = new CalculateHandler(server);
        CalculateProtocol client = (CalculateProtocol) Proxy.newProxyInstance(server.getClass().getClassLoader(), server.getClass().getInterfaces(), handler);
        int r = client.add(5, 3);
        System.out.println("5+3= " + r);
        r = client.subtract(10, 2);
        System.out.println("10-2= " + r);
    }
}
