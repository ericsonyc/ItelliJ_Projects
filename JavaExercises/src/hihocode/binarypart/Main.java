package hihocode.binarypart;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        scanner.nextLine();
        while (T-- > 0) {
            int N, M;
            N = scanner.nextInt();
            M = scanner.nextInt();
            boolean[] visit = new boolean[N];
            boolean[] flag = new boolean[N];
            int v1, v2;
            boolean valid = true;
            if (M > 0) {
                v1 = scanner.nextInt() - 1;
                v2 = scanner.nextInt() - 1;
                visit[v1] = visit[v2] = true;
                flag[v1] = true;
                flag[v2] = false;
                for (int i = 1; i < M; i++) {
                    v1 = scanner.nextInt() - 1;
                    v2 = scanner.nextInt() - 1;
                    if (!visit[v1] || !visit[v2]) {
                        if (visit[v1]) {
                            flag[v2] = !flag[v1];
                            visit[v2] = true;
                        }
                        if (visit[v2]) {
                            flag[v1] = !flag[v2];
                            visit[v1] = true;
                        }
                    } else if (flag[v1] == flag[v2])
                        valid = false;
                }
            }
            if (valid) {
                System.out.println("Correct");
            } else {
                System.out.println("Wrong");
            }
        }
    }
}
