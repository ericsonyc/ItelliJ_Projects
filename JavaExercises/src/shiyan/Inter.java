package shiyan;

/**
 * Created by ericson on 2015/9/5 0005.
 */
public class Inter {
    interface A{
        public void handle();
    }

    interface B extends A{
        @Override
        void handle();
    }

    interface C extends A{
        @Override
        void handle();
    }

    class D implements B,C{
        @Override
        public void handle() {
            System.out.println("fasdf");
        }
    }

    public static void main(String[] args){
        System.out.println("A"+"B");
        System.out.println('A'+'B');
    }
}
