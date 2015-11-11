package blog;

/**
 * Created by ericson on 2015/11/5 0005.
 */
public class Facade {
    SubSystemOne one;
    SubSystemTwo two;
    SubSystemThree three;
    SubSystemFour four;

    public Facade() {
        one = new SubSystemOne();
        two = new SubSystemTwo();
        three = new SubSystemThree();
        four = new SubSystemFour();
    }

    public void MethodA() {
        System.out.println("method a");
        one.methodOne();
        two.methodTwo();
        four.methodFor();
    }

    public void MethodB() {
        two.methodTwo();
        three.methodThree();
    }
}

class SubSystemOne {
    public void methodOne() {
        System.out.println("method one");
    }
}

class SubSystemTwo {
    public void methodTwo() {
        System.out.println("method two");
    }
}

class SubSystemThree {
    public void methodThree() {
        System.out.println("method three");
    }
}

class SubSystemFour {
    public void methodFor() {
        System.out.println("method four");
    }
}