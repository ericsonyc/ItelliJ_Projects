package newCrime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n, c;
        long t;
        n = scanner.nextInt();
        t = scanner.nextLong();
        c = scanner.nextInt();
        scanner.nextLine();
        long[] nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextLong();
        }
        List<Integer> list = new ArrayList<Integer>();
        System.out.println(mm.getNum(nums, 0, n, c, t, list));
    }

    private int getNum(long[] num, int start, int end, int c, long t,List<Integer> list) {
        if(t<0)return 0;
        if(start>=end)return 0;
        if(c<0)return 0;
        if(c==0&&t>=0&&start<=end){
            return 1;
        }
        int result=getNum(num,start+1,end,c,t,list);
        if(t>num[start]){
            list.add(start);
            result+=getNum(num,start+1,end,c-1,t-num[start],list);
        }
        return result;
    }
}
