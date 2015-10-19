package beauty;

import java.util.Random;

/**
 * Created by ericson on 2015/4/15 0015.
 */
public class AllSortAlgorithm {

    private void printDatas(int[] datas, int left, int right) {
        for (int i = left; i < right; i++) {
            System.out.print(datas[i] + "  ");
        }
        System.out.println();
    }

    //bubble sort
    private void bubbleSort(int[] datas, int left, int right) {
        boolean flag = true;
        for (int i = left; i < right; i++) {
            for (int j = 0; j < right - i - 1; j++) {
                if (datas[j] > datas[j + 1]) {
                    datas[j] ^= datas[j + 1];
                    datas[j + 1] ^= datas[j];
                    datas[j] ^= datas[j + 1];
                    flag = false;
                }
            }
            if (flag)
                break;
        }
    }

    //insert sort
    private void insertSort(int[] datas, int left, int right) {
        for (int i = left + 1; i < right; i++) {
            int temp = datas[i];
            int j = i;
            while (j > left && datas[j - 1] > temp) {
                datas[j] = datas[j - 1];
                j--;
            }
//            for (j = i; j > left; j--) {
//                if (datas[j - 1] > temp) {
//                    datas[j] = datas[j - 1];
//                } else {
//                    break;
//                }
//            }
            if (j < i)
                datas[j] = temp;
        }
    }

    //select sort
    private void selectSort(int[] datas, int left, int right) {
        for (int i = left; i < right; i++) {
            int index = i;
            int min = datas[i];
            for (int j = i + 1; j < right; j++) {
                if (datas[j] < min) {
                    min = datas[j];
                    index = j;
                }
            }
            if (index != i) {
                datas[i] ^= datas[index];
                datas[index] ^= datas[i];
                datas[i] ^= datas[index];
            }
        }
    }

    //shell sort
    private void shellSort(int[] datas, int left, int right) {
        int N = (right - left) / 2;
        for (; N > 0; N /= 2) {
            for (int i = 0; i < N; i++) {
                for (int j = i + left + N; j < right; j += N) {
                    int temp = datas[j];
                    int t = j;
                    while (t > left + i && datas[t - N] > temp) {
                        datas[t] = datas[t - N];
                        t = t - N;
                    }
                    datas[t] = temp;
                }
            }
        }
    }

    //improve shell sort
    private void shellSortImprove(int[] datas, int left, int right) {
        for (int i = (right - left) / 2; i > 0; i /= 2) {
            for (int j = i + left; j < right; j++) {
                int temp = datas[j];
                int t;
                for (t = j; t > j - i; t -= i) {
                    if (datas[t - i] > temp) {
                        datas[t] = datas[t - i];
                    } else {
                        break;
                    }
                }
                datas[t] = temp;
            }
        }
    }

    private void buildHeap(int[] datas, int left, int right) {
        for (int i = (right - 1) / 2; i >= left; i--) {
            int leftnode = 2 * i + 1;
            int rightnode = 2 * (i + 1);
            compareData(datas, leftnode, rightnode, right);


        }
    }

    private void compareData(int[] datas, int left, int right, int end) {
        if (left >= end) {
            return;
        }
        int i = (left - 1) / 2;
        if (right >= end) {
            if (datas[left] > datas[i]) {
                datas[left] ^= datas[i];
                datas[i] ^= datas[left];
                datas[left] ^= datas[i];
                compareData(datas, 2 * left + 1, 2 * (left + 1), end);
            }
        } else {
            if (datas[left] > datas[right] && datas[left] > datas[i]) {
                datas[left] ^= datas[i];
                datas[i] ^= datas[left];
                datas[left] ^= datas[i];
                compareData(datas, 2 * left + 1, 2 * (left + 1), end);
            } else if (datas[right] > datas[left] && datas[right] > datas[i]) {
                datas[right] ^= datas[i];
                datas[i] ^= datas[right];
                datas[right] ^= datas[i];
                compareData(datas, 2 * right + 1, 2 * (right + 1), end);
            }
        }

    }

    private void heapSortRecursion(int[] datas, int left, int right) {
        for (int i = right - 1; i >= left; i--) {
            buildHeap(datas, left, i + 1);
            datas[0] ^= datas[i];
            datas[i] ^= datas[0];
            datas[0] ^= datas[i];
        }
    }

    private void MergeDatas(int[] datas, int left, int right, int[] temp) {
        int middle = (left + right) / 2;
        int k = left;
        int t = middle + 1;
        int i = left;
        while (i <= middle && t <= right) {
            if (datas[i] < datas[t]) {
                temp[k++] = datas[i++];
            } else {
                temp[k++] = datas[t++];
            }
        }

        while (i <= middle) {
            temp[k++] = datas[i++];
        }

        while (t <= right) {
            temp[k++] = datas[t++];
        }
    }

    private void splitDatas(int[] datas, int left, int right, int[] temp) {
        if (left < right) {
            int middle = (left + right) / 2;
            splitDatas(datas, left, middle, temp);
            splitDatas(datas, middle + 1, right, temp);
            MergeDatas(datas, left, right, temp);
            for (int i = 0; i < datas.length; i++) {
                datas[i] = temp[i];
            }
        }
    }

    private void mergeSort(int[] datas, int left, int right) {
        int[] tempDatas = new int[datas.length];
        for (int i = 0; i < datas.length; i++) {
            tempDatas[i] = datas[i];
        }
        splitDatas(datas, left, right - 1, tempDatas);
        tempDatas = null;
    }

    private void mergeSortNoRecursion(int[] datas, int left, int right) {
        int[] temp = new int[datas.length];
        int start;
        int end;
        for (int N = 2; N < right * 2; N *= 2) {
            for (int i = left; i < right; i += N) {
                start = i;
                end = (i + N - 1) >= right ? right - 1 : i + N - 1;
                int k = start;
                int middle = (start + N / 2 - 1) >= right ? right - 1 : (start + N / 2 - 1);
                int t = middle + 1;
                while (start <= middle && t <= end) {
                    if (datas[start] < datas[t]) {
                        temp[k++] = datas[start++];
                    } else {
                        temp[k++] = datas[t++];
                    }
                }
                while (start <= middle) {
                    temp[k++] = datas[start++];
                }
                while (t <= end) {
                    temp[k++] = datas[t++];
                }
            }
            for (int i = 0; i < datas.length; i++) {
                datas[i] = temp[i];
            }
        }
    }

    //quick sort
    private void quickSort(int[] datas, int left, int right) {
        int temp = datas[left];
        int i = left + 1;
        int j = right - 1;
        if (left >= right - 1)
            return;
        while (i <= j) {
            while (i < right && datas[i] < temp) {
                i++;
            }

            while (j >= left && datas[j] > temp) {
                j--;
            }

            if (i < j) {
                datas[i] ^= datas[j];
                datas[j] ^= datas[i];
                datas[i] ^= datas[j];
            }
        }
        if (left < j) {
            datas[j] ^= datas[left];
            datas[left] ^= datas[j];
            datas[j] ^= datas[left];
        }

        quickSort(datas, left, j);
        quickSort(datas, i, right);

    }

    public static void main(String[] args) {
        AllSortAlgorithm allSortAlgorithm = new AllSortAlgorithm();
        int[] datas = new int[5];
        Random rand = new Random();
        for (int i = 0; i < datas.length; i++) {
            datas[i] = rand.nextInt(200);
        }
        datas = new int[]{4, 162, 159, 197, 132};

        //original datas
        allSortAlgorithm.printDatas(datas, 0, datas.length);

        //bubble sort
        //allSortAlgorithm.bubbleSort(datas, 0, datas.length);

        //insert sort
        //allSortAlgorithm.insertSort(datas, 0, datas.length);

        //select sort
        //allSortAlgorithm.selectSort(datas, 0, datas.length);

        //shell sort
        //allSortAlgorithm.shellSort(datas, 0, datas.length);
        //allSortAlgorithm.shellSortImprove(datas, 0, datas.length);

        //heap sort
//        allSortAlgorithm.heapSortRecursion(datas, 0, datas.length);

        //merge sort
//        allSortAlgorithm.mergeSort(datas, 0, datas.length);
//        allSortAlgorithm.mergeSortNoRecursion(datas, 0, datas.length);

        //quick sort
        allSortAlgorithm.quickSort(datas, 0, datas.length);

        //print datas
        allSortAlgorithm.printDatas(datas, 0, datas.length);
    }
}
