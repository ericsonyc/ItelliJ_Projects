package blog;

/**
 * Created by ericson on 2015/11/4 0004.
 */
public class Proxy extends Subject {
    RealSubject realSubject;

    @Override
    public void Request() {
        if(realSubject==null){
            realSubject=new RealSubject();
        }
        realSubject.Request();
    }
}
