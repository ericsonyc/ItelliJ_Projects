package exercises;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ericson on 2014/12/26 0026.
 */
public class TwoFilesMatch {
    public static void main(String[] args){
        try{
            String filePath1="D:\\traceheader.data";
            String filePath2="D:\\tos.txt";
            BufferedReader br1=new BufferedReader(new FileReader(filePath1));
            BufferedReader br2=new BufferedReader(new FileReader(filePath2));
            String line1=null;
            String line2=null;
            boolean flag=false;
            int count=0;
            while((line1=br1.readLine())!=null){
                line2=br2.readLine();
                count++;
                if(line1!=line2){
                    flag=true;
                }
            }
            br1.close();
            br2.close();
            System.out.println(flag);
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
