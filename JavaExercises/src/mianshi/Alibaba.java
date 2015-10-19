package mianshi;

/**
 * Created by ericson on 2015/8/21 0021.
 */
public class Alibaba {
    String str=new String("hello");
    char[] ch={'a','b'};
    public static void main(String[] args) {
        Alibaba alibaba=new Alibaba();
        alibaba.change(alibaba.str,alibaba.ch);
        System.out.print(alibaba.str+" and ");
        System.out.print(alibaba.ch);
    }
    private void change(String str,char ch[]){
        str="test ok";
        ch[0]='c';
    }
}
