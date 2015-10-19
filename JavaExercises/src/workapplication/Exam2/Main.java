package workapplication.Exam2;

import java.util.*;

public class Main {
    private int[][] vector;
    Set<Integer> set = new HashSet<Integer>();
    Map<Integer, Integer> distance = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        Main mm = new Main();
        Scanner scanner = new Scanner(System.in);
        int n, m;
        while (scanner.hasNext()) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            mm.vector = new int[n][m];
            mm.initVector();
            int startcity, endcity;
            for (int i = 0; i < n - 1; i++) {
                startcity = scanner.nextInt();
                endcity = scanner.nextInt();
                mm.vector[startcity - 1][endcity - 1] = 1;
                mm.vector[endcity - 1][startcity - 1] = 1;
            }
            mm.set.add(0);
            for (int i = 0; i < n; i++) {
                mm.distance.put(i, mm.vector[0][i]);
            }
            for (int i = 0; i < m; i++) {
                int indicate = scanner.nextInt();
                if (indicate == 1) {
                    int temp = scanner.nextInt() - 1;
                    mm.set.add(temp);
                    mm.updatedistance(temp);
                } else if (indicate == 2) {
                    System.out.println(mm.getShortestPath(scanner.nextInt() - 1));
                }
            }
        }
    }

    private void updatedistance(int index) {
        for (int i = 0; i < vector.length; i++) {
            if (index != i) {
                if (vector[index][i] == Integer.MAX_VALUE && distance.get(i) == Integer.MAX_VALUE) continue;
                int min = binarySelect(index, i);
                if (min < distance.get(i))
                    distance.put(i, min);
            } else
                distance.put(index, 0);
        }
    }

    private int getShortestPath(int dist) {
        int result = 0;
        if (distance.get(dist) != Integer.MAX_VALUE)
            result = distance.get(dist);
        else {
            int min = Integer.MAX_VALUE;
            for (Integer node : set) {
                min = Math.min(binarySelect(node, dist), min);
            }
            distance.put(dist, min);
            result = min;
        }
        return result;
    }

    private int binarySelect(int node, int dist) {
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> count = new Stack<Integer>();
        Set<Integer> ready = new HashSet<Integer>();
        stack.push(node);
        count.push(1);
        while (!stack.isEmpty()) {
            int root = stack.pop();
            int height = count.pop();
            ready.add(root);
            if (vector[root][dist] < Integer.MAX_VALUE) {
                return height;
            }
            for (int i = 0; i < vector.length; i++) {
                if (vector[root][i] == 1 && !ready.contains(i)) {
                    stack.push(i);
                    count.push(height + 1);
                }
            }
        }
        return count.peek();
    }

    private void initVector() {
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector[0].length; j++) {
                if (i != j) {
                    vector[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }
}
