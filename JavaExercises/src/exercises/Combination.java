package exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/3/7 0007.
 */
public class Combination {
    private List<int[]> combination(int[] a, int m) {
        Combination c = new Combination();
        List<int[]> list = new ArrayList<int[]>();
        int n = a.length;
        boolean end = false;
        int[] tempNum = new int[n];
        for (int i = 0; i < n; i++) {
            if (i < m) {
                tempNum[i] = 1;
            } else {
                tempNum[i] = 0;
            }
        }
        printVir(tempNum);
        list.add(c.createResult(a, tempNum, m));
        int k = 0;
        while (!end) {
            boolean findFirst = false;
            boolean swap = false;
            for (int i = 0; i < n; i++) {
                int l = 0;
                if (!findFirst && tempNum[i] == 1) {
                    k = i;
                    findFirst = true;
                }
                if (tempNum[i] == 1 && tempNum[i + 1] == 0) {
                    tempNum[i] = 0;
                    tempNum[i + 1] = 1;
                    swap = true;
                    for (l = 0; l < i - k; l++) {
                        tempNum[l] = tempNum[k + l];
                    }
                    for (l = i - k; l < i; l++) {
                        tempNum[l] = 0;
                    }
                    if (k == i && i + 1 == n - m) {
                        end = true;
                    }
                }
                if (swap) {
                    break;
                }
            }
            printVir(tempNum);
            list.add(c.createResult(a, tempNum, m));
        }
        return list;
    }

    public int[] createResult(int[] a, int[] temp, int m) {
        int[] result = new int[m];
        int j = 0;
        for (int i = 0; i < a.length; i++) {
            if (temp[i] == 1) {
                result[j] = a[i];
                System.out.println("result[" + j + "]:" + result[j]);
                j++;
            }
        }
        return result;
    }

    public void print(List<int[]> list) {
        System.out.println("result:");
        for (int i = 0; i < list.size(); i++) {
            int[] temp = (int[]) list.get(i);
            for (int j = 0; j < temp.length; j++) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("00");
                System.out.print(df.format(temp[j]) + " ");
            }
            System.out.println();
        }
    }

    public void printVir(int[] a) {
        System.out.println("array:");
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]);
        }
        System.out.println();
    }

    public static void main(String[] args){
        int[] a={1,2,3,4,5,6,7,8,9};
        int m=5;
        Combination c=new Combination();
        List<int[]> list=c.combination(a,m);
        c.print(list);
    }
}
