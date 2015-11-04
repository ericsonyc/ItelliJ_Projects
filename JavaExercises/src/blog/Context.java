package blog;

/**
 * Created by ericson on 2015/11/3 0003.
 */
public class Context {
    Strategy strategy;
    public Context(Strategy strategy){
        this.strategy=strategy;
    }
    public void ContextInterface(){
        strategy.AlgorithmInterface();
    }
    public static void main(String[] args){
        Context context;
        context=new Context(new ConcreteStrategyA());
        context.ContextInterface();
        context=new Context(new ConcreteStrategyB());
        context.ContextInterface();
        context=new Context(new ConcreteStrategyC());
        context.ContextInterface();
    }
}
