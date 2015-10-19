package exercises;

import java.io.*;

/**
 * Created by ericson on 2015/1/2 0002.
 */
public class SplitFile {
    public static void main(String[] args) {
        try {
            String filePath = "D:\\ktmigration\\data\\output\\shots.data";
            String output = "D:\\splits\\";
            RandomAccessFile rand = new RandomAccessFile(filePath, "r");
            long SIZE = rand.length();
            rand.close();
            long length = 400000000L;
            long splits = SIZE % length == 0 ? SIZE / length : SIZE / length + 1;
            System.out.println("splits total:"+splits);
            int count = 0;
            byte[] temp = new byte[(int) length / 1000];
            DataInputStream dis=new DataInputStream(new FileInputStream(filePath));
            long start=System.currentTimeMillis();
            for (int i = 0; i < splits; i++) {
                String out=i+".data";
                //System.out.println("i:"+i);
                if(i==splits-1){
                    DataOutputStream dos=new DataOutputStream(new FileOutputStream(output+out));
                    byte[] t=new byte[4];
                    for(int j=0;j<(SIZE-i*length)/4;j++){
                        dis.read(t);
                        dos.write(t);
                        dos.flush();
                        //System.out.println("j:"+j);
                    }
                    dos.close();
                }else{
                    DataOutputStream dos=new DataOutputStream(new FileOutputStream(output+out));
                    for(int j=0;j<length/temp.length;j++){
                        dis.read(temp);
                        dos.write(temp);
                        dos.flush();
                        //System.out.println("j:"+j);
                    }
                    dos.close();
                }
                System.out.println("splits:"+i);
            }
            dis.close();
            long end=System.currentTimeMillis();
            System.out.println("Time:"+(end-start)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
