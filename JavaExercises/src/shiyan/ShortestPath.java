package shiyan;

import java.util.*;

/**
 * Created by ericson on 2015/9/24 0024.
 */
public class ShortestPath {
    private static final int minDis = 0;
    private static final int maxDis = Integer.MAX_VALUE;
    int[][] matrix;
    int startIndex;
    HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
    Set<Integer> findedSet = new HashSet<Integer>();

    public ShortestPath(int[][] matrix, int start) {
        this.matrix = matrix;
        this.startIndex = start;
    }

    public void find() {
        for (int i = 0; i < matrix.length; i++) {
            distanceMap.put(i, matrix[startIndex][i]);
        }
        while (findedSet.size() != matrix.length) {
            int currentMinIndex = currentMinIndex();
            for (int i = 0; i < matrix.length; i++) {
                if (!findedSet.contains(i) && matrix[currentMinIndex][i] != maxDis && matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex) < distanceMap.get(i))
                    distanceMap.put(i, matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex));
            }
            findedSet.add(currentMinIndex);
        }
    }

    private int currentMinIndex() {
        Iterator<Map.Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            if (!findedSet.contains(entry.getKey()) && entry.getValue() < min) {
                min = entry.getValue();
                minIndex = entry.getKey();
            }
        }
        return minIndex;
    }

    public static void main(String[] args) {
        int[][] inputMatrix = new int[][]{
                {minDis, 2, maxDis, 1, maxDis, maxDis, maxDis},
                {maxDis, minDis, maxDis, 3, 10, maxDis, maxDis},
                {4, maxDis, minDis, maxDis, maxDis, 5, maxDis},
                {maxDis, maxDis, 2, minDis, 2, 8, 4},
                {maxDis, maxDis, maxDis, maxDis, minDis, maxDis, 6},
                {maxDis, maxDis, maxDis, maxDis, maxDis, minDis, maxDis},
                {maxDis, maxDis, maxDis, maxDis, maxDis, 1, minDis}};
        ShortestPath path = new ShortestPath(inputMatrix, 0);
        path.find();
        path.printDistance();
    }

    public void printDistance() {
        Iterator<Map.Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
        int min = Integer.MIN_VALUE;
        int minIndex = -1;
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            System.out.println(startIndex + "---->" + entry.getKey() + ":" + entry.getValue());
        }
    }
}
