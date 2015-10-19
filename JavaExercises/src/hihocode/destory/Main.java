package hihocode.destory;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/11 0011.
 */
public class Main {

    final int N = 100010 << 2;
    final int M = 100000;
    int[] sum = new int[N];
    int[] m = new int[M];
    int[] ri = new int[M];
    int[] t = new int[N];
    final long mod = 1000000007;

    Scanner sc = new Scanner(System.in);

    private void pushup(int root) {
        sum[root] = sum[root << 1] + sum[root << 1 | 1];
    }

    private void build(int l, int r, int root) {
        if (r == l) {
            String[] temp = sc.nextLine().split(" ");
            sum[root] = Integer.parseInt(temp[0]);
            m[l] = Integer.parseInt(temp[1]);
            ri[l] = Integer.parseInt(temp[2]);
            t[l] = 0;
            return;
        }
        int mid = (l + r) >> 1;
        build(l, mid, root << 1);
        build(mid + 1, r, root << 1 | 1);
        pushup(root);
    }

    private void recover(int time, int L, int R, int l, int r, int root) {
        if (l == r && (l >= L && l <= R)) {
            int temp = (time - t[l]) * ri[l];
            if (sum[root] + temp > m[l])
                sum[root] = m[l];
            else
                sum[root] += temp;
            return;
        }
        int mid = (l + r) >> 1;
        if (L <= mid)
            recover(time, L, R, l, mid, root << 1);
        if (R > mid)
            recover(time, L, R, mid + 1, r, root << 1 | 1);
        pushup(root);
    }

    private long query(int L, int R, int l, int r, int root) {
        if (L <= l && R >= r)
            return sum[root];
        int mid = (l + r) >> 1;
        long ans = 0;
        if (L <= mid)
            ans = query(L, R, l, mid, root << 1);
        if (R > mid)
            ans += query(L, R, mid + 1, r, root << 1 | 1);
        return ans;
    }

    private void clea(int time, int L, int R, int l, int r, int root) {
        if (l == r && (l >= L && l <= R)) {
            t[l] = time;
            sum[root] = 0;
            return;
        }
        int mid = (l + r) >> 1;
        if (L <= mid)
            clea(time, L, R, l, mid, root << 1);
        if (R > mid)
            clea(time, L, R, mid + 1, r, root << 1 | 1);
        pushup(root);
    }

    public static void main(String[] args) {
        Main mm = new Main();
        int n;
        for (int i = 0; i < mm.t.length; i++) {
            mm.t[i] = 0;
        }
        n = Integer.parseInt(mm.sc.nextLine());
        mm.build(1, n, 1);
        int tm;
        tm = Integer.parseInt(mm.sc.nextLine());
        long ans = 0;
        for (int i = 1; i <= tm; i++) {
            String[] temp = mm.sc.nextLine().split(" ");
            int time, l, r;
            time = Integer.parseInt(temp[0]);
            l = Integer.parseInt(temp[1]);
            r = Integer.parseInt(temp[2]);
            mm.recover(time, l, r, 1, n, 1);
            ans = (ans + mm.query(l, r, 1, n, 1)) % mm.mod;
            mm.clea(time, l, r, 1, n, 1);
        }
        System.out.println(ans);
    }
}
