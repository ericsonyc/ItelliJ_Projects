package exercises;

/**
 * Created by ericson on 2015/3/3 0003.
 */
public class ReverseBits {
    public static void main(String[] args) {
        ReverseBits reverseBits = new ReverseBits();
        int x = 2;
        x = reverseBits.reverseXor(x);
        System.out.println("result is:" + x);
    }

    int reverseXor(int x) {
        int n = Integer.SIZE;
        for (int i = 0; i < n / 2; i++) {
            x = swapBits(x, i, n - i - 1);
        }
        return x;
    }

    int swapBits(int x, int i, int j) {
        int lo = ((x >> i) & 1);
        int hi = ((x >> j) & 1);
        if ((lo ^ hi) != 0) {
            x ^= ((1 << i) | (1 << j));
        }
        return x;
    }
}
