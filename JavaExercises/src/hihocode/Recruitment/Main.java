package hihocode.Recruitment;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Main mm=new Main();
        Scanner scanner=new Scanner(System.in);
        int N,X,Y,B;
        N=scanner.nextInt();
        X=scanner.nextInt();
        Y=scanner.nextInt();
        B=scanner.nextInt();
        scanner.nextLine();
        int[][] males=new int[N][2];
        int mcount=0;
        int[][] females=new int[N][2];
        int fcount=0;
        for(int i=0;i<N;i++){
            String gender=scanner.next();
            if(gender=="M"){
                int ability=scanner.nextInt();
                int spend=scanner.nextInt();
                males[mcount][0]=ability;
                males[mcount][1]=spend;
                mcount++;
            }else if(gender=="F"){
                int ablity=scanner.nextInt();
                int spend=scanner.nextInt();
                females[fcount][0]=ablity;
                females[fcount][1]=spend;
                fcount++;
            }
        }
    }

}
