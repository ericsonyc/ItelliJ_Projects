package exercises;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by ericson on 2015/7/16 0016.
 */
public class JavaExercise {
    public static void main(String[] args) {
        Map<Value, Integer> maps = new ConcurrentSkipListMap<Value, Integer>(new ValueComparator());
        for (int i = 0; i < 10; i++) {
            maps.put(new Value(i), i);
        }
        for (Map.Entry<Value, Integer> entry : maps.entrySet()) {
            System.out.println(entry.getKey());
            Value value = entry.getKey();
            for(int i=0;i<entry.getValue();){
                System.out.println("get");
                value.setNumber(value.getNumber() - 1);
                maps.put(value, entry.getValue() - 1);
                if(entry.getValue()+1>8){
                    maps.remove(value);
                }
            }

        }
        System.out.println("---------------");
        for (Map.Entry<Value, Integer> entry : maps.entrySet()) {
            System.out.println(entry.getKey().toString() + ":" + entry.getValue());
        }
        System.out.println("-----------------");
    }

    static class ValueComparator implements Comparator<Value> {

        @Override
        public int compare(Value o1, Value o2) {
            return Integer.compare(o1.getNumber(), o2.getNumber());
        }
    }

    static class Value {
        public int number;

        public Value(int number) {
            this.number = number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "number=" + number +
                    '}';
        }
    }
}
