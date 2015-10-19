package mogujie;

import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        Test mm=new Test();
        Scanner scanner=new Scanner(System.in);
        String str;
        while(scanner.hasNext()){
            str=scanner.nextLine();
            System.out.println(mm.getString(str.substring(1))||mm.getString(str.substring(0,str.length()-1)));
        }
    }

    private boolean getString(String str){
        int left=0;int right=str.length()-1;
        while(left<right){
            if(str.charAt(left)!=str.charAt(right))
                return false;
        }
        return true;
    }
}
