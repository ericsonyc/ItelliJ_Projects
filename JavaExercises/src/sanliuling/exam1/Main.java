package sanliuling.exam1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int N, M;
        while (scanner.hasNext()) {
            N = scanner.nextInt();
            M = scanner.nextInt();
            if (N == 0 && M == 0)
                break;
            scanner.nextLine();
            List<List<Integer>> people = new LinkedList<List<Integer>>();
            for (int i = 0; i < N; i++) {
                List<Integer> temp = new LinkedList<Integer>();
                people.add(temp);
            }
            for (int i = 0; i < M; i++) {
                int l = scanner.nextInt();
                int r = scanner.nextInt();
                people.get(l - 1).add(r - 1);
                people.get(r - 1).add(l - 1);
                scanner.nextLine();
            }
            System.out.println(mm.getCount(people));
        }
    }

    private int getCount(List<List<Integer>> people) {
        Set<Integer> sets = new HashSet<Integer>();
        sets.add(0);
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int peo = queue.poll();
            for (Integer i : people.get(peo)) {
                if (!sets.contains(i)) {
                    sets.add(i);
                    queue.offer(i);
                }
            }
        }
        return sets.size() - 1;
    }
}
