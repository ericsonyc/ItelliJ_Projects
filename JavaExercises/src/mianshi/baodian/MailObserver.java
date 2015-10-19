package mianshi.baodian;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public class MailObserver implements Observer {
    @Override
    public void update(Product p) {
        System.out.println("为所有会员发送价格变化信息：" + p.getName() + ":" + p.getPrice());
    }

    @Override
    public void unreg(Product p) {
        p.getObservers().remove(this);
    }
}
