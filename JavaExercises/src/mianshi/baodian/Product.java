package mianshi.baodian;

import java.util.HashSet;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public class Product {
    private double price;
    private String name;
    private HashSet<Observer> observers;
    public Product(String name,double price){
        this.price=price;
        this.name=name;
        observers=new HashSet<Observer>();
    }
    public void addObserver(Observer ob){
        observers.add(ob);
    }
    public void notifyObserver(){
        for(Observer ob:observers){
            ob.update(this);
        }
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
        notifyObserver();
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public HashSet<Observer> getObservers(){
        return observers;
    }
    public void setObservers(HashSet<Observer> observers){
        this.observers=observers;
    }
}
