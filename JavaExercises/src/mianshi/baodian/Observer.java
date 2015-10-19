package mianshi.baodian;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public interface Observer {
    public void update(Product p);

    public void unreg(Product p);
}
