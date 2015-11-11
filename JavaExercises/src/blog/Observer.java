package blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/11/8 0008.
 */
public abstract class Observer {
    public abstract void Update();
}

abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public void Attach(Observer observer) {
        observers.add(observer);
    }

    public void Detach(Observer observer) {
        observers.remove(observer);
    }

    public void Notify() {
        for (Observer o : observers) {
            o.Update();
        }
    }
}

class ConcreteSubject extends Subject {
    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }
}

class ConcreteObserver extends Observer {
    private String name;
    private String observerState;
    private ConcreteSubject subject;

    public ConcreteObserver(ConcreteSubject subject, String name) {
        this.subject = subject;
        this.name = name;
    }

    @Override
    public void Update() {
        observerState = subject.getSubjectState();
    }

    public ConcreteSubject getSubject() {
        return subject;
    }

    public void setSubject(ConcreteSubject subject1) {
        this.subject = subject1;
    }
}