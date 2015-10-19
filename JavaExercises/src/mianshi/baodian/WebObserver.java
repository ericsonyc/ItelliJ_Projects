package mianshi.baodian;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public class WebObserver implements Observer {
    @Override
    public void update(Product p) {
        System.out.println("更新页面价格：" + p.getName() + ":" + p.getPrice());
    }

    @Override
    public void unreg(Product p) {
        p.getObservers().remove(this);
    }
}
