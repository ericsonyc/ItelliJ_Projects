package yamaxun;

import javax.xml.stream.events.StartDocument;

/**
 * Created by ericson on 2015/10/9 0009.
 */
public class A {
    public static void main(String[] args){
        new Son();
    }

    static class Son extends  Father{
        private static String i="son static";
        static{
            System.out.println(i);
        }
        private String j=func();

        private String func(){
            System.out.println("son non static");
            return "";
        }

        public Son(){
            System.out.println("son const");
        }
    }

    static class Father{
        private static String i="fa static";
        static{
            System.out.println(i);
        }
        private String j=func();

        private String func(){
            System.out.println("fa non static");
            return "";
        }

        public Father(){
            System.out.println("fa const");
        }
    }

}
