package mianshi.baodian;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public class ObserverPattern {
    public static void main(String[] args) {
        Product p = new Product("《Java核心技术》", 103.00);
        Observer o1 = new WebObserver();
        Observer o2 = new MailObserver();
        p.addObserver(o1);
        p.addObserver(o2);
        System.out.println("===第一次价格改动===");
        p.setPrice(80);
        o1.unreg(p);
        System.out.println("===第二次价格改动===");
        p.setPrice(100);
    }
}
