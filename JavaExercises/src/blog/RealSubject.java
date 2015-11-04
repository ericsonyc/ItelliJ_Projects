package blog;

/**
 * Created by ericson on 2015/11/4 0004.
 */
public class RealSubject extends Subject {
    @Override
    public void Request() {
        System.out.println("真实的请求");
    }
}
