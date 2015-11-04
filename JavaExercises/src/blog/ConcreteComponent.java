package blog;

/**
 * Created by ericson on 2015/11/4 0004.
 */
public class ConcreteComponent extends Component {
    @Override
    public void Operation() {
        System.out.println("具体对象的操作");
    }
}
