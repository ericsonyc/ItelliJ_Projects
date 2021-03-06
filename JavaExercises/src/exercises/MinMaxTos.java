package exercises;

import java.io.*;

/**
 * Created by ericson on 2014/12/25 0025.
 */
public class MinMaxTos {
    public static void main(String[] args){
        try{
            String filePath="D:\\tos.data";
            String outputPath="D:\\tos.txt";
            DataInputStream dis=new DataInputStream(new FileInputStream(filePath));
            BufferedWriter bw=new BufferedWriter(new FileWriter(outputPath));
            byte[] temp=new byte[4];
            int value=-1;
            int count=0;
            int min=Integer.MAX_VALUE;
            int max=Integer.MIN_VALUE;
            int[] lines=new int[2];
            while(dis.read(temp)!=-1){
//                value=getInt(temp);
//                lines[count]=value;
//                count++;
//                if(count==2){
//                    String str="";
//                    for(int i=0;i<lines.length;i++){
//                        str+=lines[i]+",";
//                    }
//                    str=str.substring(0,str.lastIndexOf(","));
//                    bw.write(str);
//                    bw.newLine();
//                    count=0;
//                }
//                if(value<min){
//                    min=value;
//                }
//                if(value>max){
//                    max=value;
//                }
                value=getInt(temp);
                if(count%2==0){
                    if(value<min){
                        min=value;
                    }
                    if(value>max){
                        max=value;
                    }
                }
                count++;
            }
            System.out.println("MIN:"+min);
            System.out.println("MAX:"+max);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getInt(byte[] bytes)
    {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }
}
