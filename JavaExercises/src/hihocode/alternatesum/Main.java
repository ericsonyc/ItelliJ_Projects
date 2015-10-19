package hihocode.alternatesum;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/11 0011.
 */
public class Main {

    final int M = 28;
    final int N = 25;
    final long mod = 1000000007;

    class Node {
        public long n = -1;
        public long s = -1;
    }

    Node[][] dp = new Node[N][400];
    long[] base = new long[N];
    long[] bit = new long[N];
    long yy;

    void pre() {
        base[1] = 1;
        int i;
        for (i = 2; i <= 20; i++) {
            base[i] = (base[i - 1] * 10) % mod;
        }
    }

    Node dfs(long pos, long target, long limit) {
        Node t = new Node();
        t.n = t.s = 0;
        if (pos == 0) {
            if (target == 100) {
                t.n = 1;
            }
            return t;
        }
        if ((limit == 0) && dp[(int) pos][(int) target].n != -1) return dp[(int) pos][(int) target];
        long head, tail, sgn;
        tail = limit != 0 ? bit[(int) pos] : 9;
        if (pos == yy) {
            head = 1;
        } else {
            head = 0;
        }
        sgn = ((yy - pos) % 2) != 0 ? (-1) : (1);
        for (long i = head; i <= tail; i++) {
            Node nt;
            nt = dfs(pos - 1, target - i * sgn, ((limit == 1) && (i == tail)) ? 1 : 0);
            if (nt.n > 0) {
                t.n += nt.n;
                long q;
                q = (nt.n % mod * base[(int) pos]) % mod * i % mod;
                t.s = ((t.s + nt.s) % mod + q) % mod;
            }
        }
        if (limit == 0) dp[(int) pos][(int) target] = t;
        return t;
    }

    long solve(long x, long sum) {
        long ans = 0;
        if (x <= 0) return 0;
        long bt = 0;
        while (x > 0) {
            bt++;
            bit[(int) bt] = x % 10;
            x /= 10;
        }
        for (yy = 1; yy <= bt; yy++) {
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[0].length; j++) {
                    dp[i][j] = new Node();
                }
            }
            ans = (ans + dfs(yy, sum + 100, yy == bt ? 1 : 0).s) % mod;
        }
        return ans;
    }

    public static void main(String[] args) {
        Main mm = new Main();
        mm.pre();
        long l, r, k;
        long ans;
        Scanner sc = new Scanner(System.in);
        String line = null;
        while ((line = sc.nextLine()) != null) {
            String[] temp = line.split(" ");
            l = Long.parseLong(temp[0]);
            r = Long.parseLong(temp[1]);
            k = Long.parseLong(temp[2]);
            ans = (mm.solve(r, k) - mm.solve(l - 1, k) + mm.mod) % mm.mod;
            System.out.println(ans);
        }
    }

//    private final int N = 25;
//
//    class node {
//        long n, s;
//
//        public node(long n, long s) {
//            this.n = n;
//            this.s = s;
//        }
//    }
//
//    node[][] dp = new node[N][400];
//    int[] bit = new int[N];
//    int length;
//    long[] base = new long[N];
//
//    long mod = 1000000007;
//    long l, r;
//    int k;
//
//    node dfs(int pos, int target, boolean end_flag) {
//        node t = new node(0, 0);
//        if (pos == 0) {
//            if (target == 100)
//                t.n = 1;
//            return t;
//        }
//        if (!end_flag && dp[pos][target].n != -1) {
//            return dp[pos][target];
//        }
//        int end = end_flag ? bit[pos] : 9;
//        int sign = ((length - pos) % 2) == 0 ? 1 : -1;
//        int start = length == pos ? 1 : 0;
//        for (int i = start; i <= end; i++) {
//            node tmp = dfs(pos - 1, target - i * sign, end_flag && (i == end));
//            if (tmp.n > 0) {
//                t.n += tmp.n;
//                long q;
//                q = (tmp.n % mod * base[pos]) % mod * i % mod;
//                t.s = ((t.s + tmp.s) % mod + q) % mod;
//            }
//        }
//        if (!end_flag)
//            dp[pos][target] = t;
//        return t;
//    }
//
//    long solve(long right, int k) {
//        if (right <= 0) return 0;
//        int pos = 1;
//        while (right > 0) {
//            bit[pos] = (int) (right % 10);
//            right /= 10;
//            pos++;
//        }
//        long ans = 0;
//        node t = new node(-1, -1);
//        for (length = 1; length < pos; length++) {
//            for (int i = 0; i < N; i++) {
//                for (int j = 0; j < dp[0].length; j++)
//                    dp[i][j] = t;
//            }
//            ans = (ans + dfs(length, k + 100, length == pos - 1).s) % mod;
//        }
//        return ans;
//    }
//
//    public static void main(String[] args) {
//        Main mm = new Main();
//        Scanner sc = new Scanner(System.in);
//        String[] temp = sc.nextLine().split(" ");
//        mm.l = Long.parseLong(temp[0]);
//        mm.r = Long.parseLong(temp[1]);
//        mm.k = Integer.parseInt(temp[2]);
//        mm.base[1] = 1;
//        for (int i = 2; i < mm.N; i++) {
//            mm.base[i] = (mm.base[i - 1] * 10) % mm.mod;
//        }
//        long ans = 0;
//        ans = (mm.solve(mm.r, mm.k) - mm.solve(mm.l - 1, mm.k) + mm.mod) % mm.mod;
//        System.out.println(ans);
//    }
}
