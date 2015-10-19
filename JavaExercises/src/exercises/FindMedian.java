package exercises;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by ericson on 2015/3/14 0014.
 */
public class FindMedian {
    public static void main(String[] args) {

    }

    public double findMedianSortedArrays(int[] A, int[] B) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((A.length + B.length) / 2 + 2, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2)
                    return 1;
                else if (o1 < o2)
                    return -1;
                else
                    return 0;
            }
        });
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((A.length + B.length) / 2 + 2, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2)
                    return -1;
                else if (o1 < o2)
                    return 1;
                else
                    return 0;
            }
        });

        int pA = 0;
        int pB = 0;
        if (A.length != 0 && B.length != 0) {
            minHeap.add(Math.max(A[pA], B[pB]));
            maxHeap.add(Math.min(A[pA], B[pB]));
        } else if (A.length == 0 && B.length != 0) {
            minHeap.add(B[pB]);
        } else {
            minHeap.add(A[pA]);
        }

        pA++;
        pB++;

        while (pA < A.length || pB < B.length) {
            if (pA < A.length) {
                if (maxHeap.isEmpty() || A[pA] <= maxHeap.peek()) {
                    maxHeap.add(A[pA]);
                } else {
                    minHeap.add(A[pA]);
                }
            }
            if (pB < B.length) {
                if (maxHeap.isEmpty() || B[pB] <= maxHeap.peek()) {
                    maxHeap.add(B[pB]);
                } else {
                    minHeap.add(B[pB]);
                }
            }

            if (minHeap.size() - maxHeap.size() > 1) {
                maxHeap.add(minHeap.poll());
            } else if (maxHeap.size() - minHeap.size() > 1)
                minHeap.add(maxHeap.poll());
            pA++;
            pB++;
        }
        if (minHeap.size() == maxHeap.size())
            return (double) (minHeap.peek() + maxHeap.peek()) / 2;
        else if (minHeap.size() > maxHeap.size())
            return minHeap.peek();
        else
            return maxHeap.peek();
    }

    private double findMedian(int[] a, int[] b) {
        double value = -1;
        int aPrev = 0, bPrev = 0;
        int aEnd = a.length, bEnd = b.length;
        int[] length = new int[a.length + b.length];
        int minKey = 0;
        int maxKey = length.length - 1;
        while (aPrev <= aEnd && bPrev <= bEnd) {
            if (a[aPrev] > b[bPrev]) {
                length[minKey] = b[bPrev];
                bPrev++;
            } else {
                length[minKey] = a[aPrev];
                aPrev++;
            }
            minKey++;
            if (a[aEnd] > b[bEnd]) {
                length[maxKey] = a[aEnd];
                aEnd--;
            } else {
                length[maxKey] = b[bEnd];
                bEnd--;
            }
            maxKey--;
        }
        while (aPrev < aEnd) {
            length[minKey++] = a[aPrev++];
        }
        while (bPrev < bEnd) {
            length[minKey++] = b[bPrev++];
        }
        if (length.length % 2 == 0)
            value = (double)(length[length.length / 2 - 1] + length[length.length / 2]) / 2.0;
        else {
            value = length[length.length / 2];
        }
        return value;
    }

}
