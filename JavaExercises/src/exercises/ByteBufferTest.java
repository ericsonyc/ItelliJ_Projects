package exercises;

import java.nio.ByteBuffer;

/**
 * Created by ericson on 2016/1/2 0002.
 */
public class ByteBufferTest {
    public static void main(String[] args){
        System.out.println("----------Test allocate----------");
        System.out.println("before allocate:"+Runtime.getRuntime().freeMemory());
        ByteBuffer buffer=ByteBuffer.allocate(102400);
        System.out.println("buffer="+buffer);
        System.out.println("after allocate:"+Runtime.getRuntime().freeMemory());
        ByteBuffer directBuffer=ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer="+directBuffer);
        System.out.println("after direct allocate"+Runtime.getRuntime().freeMemory());
        System.out.println("---------Test wrap-------");
        byte[] bytes=new byte[32];
        buffer=ByteBuffer.wrap(bytes);
        System.out.println(buffer);
        buffer=ByteBuffer.wrap(bytes,10,10);
        System.out.println(buffer);
    }
}
