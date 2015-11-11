package blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/11/8 0008.
 */
public class Product {
    List<String> parts = new ArrayList<String>();

    public void Add(String part) {
        parts.add(part);
    }

    public void Show() {
        System.out.println("\n产品");
        for (String part : parts) {
            System.out.println(part);
        }
    }
}

abstract class Builder {
    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract Product getResult();
}

class ConcreteBuilder extends Builder {
    private Product product = new Product();

    @Override
    public void buildPartA() {
        product.Add("部件A");
    }

    @Override
    public void buildPartB() {
        product.Add("部件B");
    }

    @Override
    public Product getResult() {
        return product;
    }
}

class ConcreteBuilder2 extends Builder {
    private Product product = new Product();

    @Override
    public void buildPartA() {
        product.Add("部件X");
    }

    @Override
    public void buildPartB() {
        product.Add("部件Y");
    }

    @Override
    public Product getResult() {
        return product;
    }
}
class Director{
    public void Construct(Builder builder){
        builder.buildPartA();
        builder.buildPartB();
    }
}