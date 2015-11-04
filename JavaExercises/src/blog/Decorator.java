package blog;

/**
 * Created by ericson on 2015/11/4 0004.
 */
public class Decorator extends Component {
    protected Component component;

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void Operation() {
        if(component!=null)
            component.Operation();
    }
}
