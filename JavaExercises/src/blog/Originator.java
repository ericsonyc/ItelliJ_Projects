package blog;

/**
 * Created by ericson on 2015/11/9 0009.
 */
public class Originator {
    private String state;

    public Memento createMemento() {
        return new Memento(state);
    }

    public void setMemento(Memento memento) {
        state = memento.getState();
    }

    public void show() {
        System.out.println("State=" + state);
    }

    public static void main(String[] args) {
        Originator o = new Originator();
        o.state = "On";
        o.show();
        Caretaker c = new Caretaker();
        c.setMemento(o.createMemento());
        o.state = "OFF";
        o.show();
        o.setMemento(c.getMemento());
        o.show();
    }
}

class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

class Caretaker {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}