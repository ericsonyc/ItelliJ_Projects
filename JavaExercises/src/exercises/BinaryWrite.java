package exercises;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Created by ericson on 2014/12/26 0026.
 */
public class BinaryWrite {
    public static void main(String[] args){
        try{
            String filePath="D:\\traceheader.data";
            String fileTos="D:\\tos.data";
            BufferedReader br=new BufferedReader(new FileReader(filePath));
            DataOutputStream dos=new DataOutputStream(new FileOutputStream(fileTos));
            String line=null;
            while((line=br.readLine())!=null){
                String[] values=line.split(",");
                for(int i=0;i<values.length;i++){
                    dos.write(getBytes(Float.floatToIntBits(Float.parseFloat(values[i]))));
                    dos.flush();
                }
            }
            dos.flush();
            dos.close();
            System.out.println("Write over");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] getBytes(int data)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }
}
