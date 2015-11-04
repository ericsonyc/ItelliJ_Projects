package blog;

/**
 * Created by ericson on 2015/11/4 0004.
 */
public class ConcreteDecoratorA extends Decorator {
    private String addedState;

    @Override
    public void Operation() {
        super.Operation();
        addedState="New State";
        System.out.println("具体装饰对象A的操作");
    }
}

class ConcreteDecoratorB extends Decorator{
    @Override
    public void Operation() {
        super.Operation();
        AddedBehavior();
        System.out.println("具体装饰对象B的操作");
    }
    private void AddedBehavior(){

    }
}