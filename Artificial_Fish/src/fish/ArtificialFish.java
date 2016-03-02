package fish;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by ericson on 2016/2/2 0002.
 */
public class ArtificialFish {
    private int visual = 6; //visual scope
    private double seta = 0.618;
    private int N = 20; //number of fishes
    private int n = 15; //number of items
    private int T = 15; //iteration times
    private int attempt = 50; //attempt to move fish
    private int m = 3; //knapsacks
    private int[][] fishes; //preserve all fishes
    private int[] knapsacks; //preserve knapsack capacity
    private int[] tasks; //preserve item capacity
    private int[] values; //preserve item value;
    private int[] remaining; //preserve remaining knapsack capacity
    private double bulletin = 0;// preserve the max bulletin
    private int bulletinFish = 0;
    private ArrayList<Integer>[] arrays;//preserve the tasks in knapsacks
    private int step = 3;
    private int noknapsack = -1;

    public ArtificialFish() {
        fishes = new int[N][n];
        knapsacks = new int[m];
        remaining = new int[m];
        tasks = new int[n];
        values = new int[n];
        arrays = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            arrays[i] = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        System.out.println("begin Artificial fish swarm algorithm");
        ArtificialFish artificialFish = new ArtificialFish();
        artificialFish.initKnapsacks(110, 150, 190);
        artificialFish.initRemaining();

        int[] tmpTasks = {41, 34, 69, 78, 62, 5, 81, 61, 95, 27, 91, 2, 92, 21, 18};
        artificialFish.initTasks(tmpTasks);
        int[] tmpValues = {467, 500, 724, 358, 464, 145, 827, 491, 942, 436, 604, 153, 382, 716, 895};
        artificialFish.initValues(tmpValues);
        artificialFish.initfishes();
        artificialFish.checkAllFishes();

        artificialFish.function();
        artificialFish.checkAllFishes();
        artificialFish.printBulletin();
        System.out.println("*************************");
//        artificialFish.printFishes();
    }

    public void printBulletin() {
        System.out.println(bulletin);
        for (int i = 0; i < n; i++) {
            System.out.print(fishes[bulletinFish][i] + " ");
        }
        System.out.println();
        initRemaining();
        for (int i = 0; i < n; i++) {
            if (fishes[bulletinFish][i] != -1) {
                remaining[fishes[bulletinFish][i]] -= tasks[i];
            }
        }
        System.out.println(Arrays.toString(remaining));
    }

    public int[] randomSwim(ArrayList<int[]> list, int X) {//随机游走
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(100) % list.size();
        initRemainingCapacity(list.get(index));
        int[] ran = list.get(index);
        int count = 0;
        while (distance(list.get(index), fishes[X]) < step && count < 10) {
            int good = random.nextInt(100) % n;
            int pack = random.nextInt(100) % m;
            putInto(good, pack, ran);
            count++;
        }
        return ran;
    }

    public int[] foraging(ArrayList<int[]> list, int X) {
        for (int i = 0; i < attempt; i++) {
            Random random = new Random(System.currentTimeMillis());
            int index = random.nextInt(100) % list.size();
            int[] tmpFishes = list.get(index);
            if (valuefunction(tmpFishes) > bulletin) {
                return tmpFishes;
            }
        }
        return fishes[X];
    }

    public int[] tailgating(ArrayList<int[]> list, int X) {
        double tt = bulletin;
        double maxY = 0;
        int[] max = null;
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i);
            if (valuefunction(temp) > tt) {
                tt = valuefunction(temp);
                maxY = tt;
                max = temp;
            }
        }
        if (max != null) {
            if (valuefunction(max) / list.size() > seta * tt) {
                for (int i = 0; i < n; i++) {
                    Random random = new Random(System.currentTimeMillis());
                    int temp = max[i];
                    if (temp == -1) {
                        temp = random.nextInt(100) % m;
                    }
                    putInto(i, temp, max);
                }
                return max;
            }
        }
        return fishes[X];
    }

    public int[] swarm(ArrayList<int[]> list, int X) {
        int[] xc = new int[n];
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i);
            addVector(xc, temp);
        }
        average(xc, list.size());
        checkVector(xc);
        if (valuefunction(xc) / list.size() > seta * bulletin) {
            for (int i = 0; i < n; i++) {
                Random random = new Random(System.currentTimeMillis());
                int temp = xc[i];
                if (temp == -1) {
                    temp = random.nextInt(100) % m;
                }
                putInto(i, temp, xc);
            }
        } else {
            xc = foraging(list, X);
        }
        return xc;
    }

    public void function() {
        String filename = System.getProperty("user.dir") + "/src/fish/file10";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            ArrayList<Double> arrays = new ArrayList<Double>();
            int k = 0;
            while (k < T) {
                for (int i = 0; i < N; i++) {
                    int index = i;
                    int nf = 0;
                    ArrayList<int[]> lists = new ArrayList<>();
                    for (int j = 0; j < N; j++) {
                        if (distance(fishes[index], fishes[j]) < visual) {
                            nf++;
                            lists.add(fishes[j]);
                        }
                    }

                    int[] x1 = randomSwim(lists, index);
                    checkVector(x1);
                    int[] x2 = foraging(lists, index);
                    checkVector(x2);
                    int[] x3 = tailgating(lists, index);
                    checkVector(x3);
                    int[] x4 = swarm(lists, index);
                    checkVector(x4);

                    if (valuefunction(x1) > valuefunction(x2)) {
                        if (valuefunction(x1) > valuefunction(x3)) {
                            if (valuefunction(x1) > valuefunction(x4)) {
                                bulletin = valuefunction(x1);
                                fishes[index] = x1;
                            } else {
                                bulletin = valuefunction(x4);
                                fishes[index] = x4;
                            }
                        } else {
                            if (valuefunction(x3) > valuefunction(x4)) {
                                bulletin = valuefunction(x3);
                                fishes[index] = x3;
                            } else {
                                bulletin = valuefunction(x4);
                                fishes[index] = x4;
                            }
                        }
                    } else {
                        if (valuefunction(x2) > valuefunction(x3)) {
                            if (valuefunction(x2) > valuefunction(x4)) {
                                bulletin = valuefunction(x2);
                                fishes[index] = x2;
                            } else {
                                bulletin = valuefunction(x4);
                                fishes[index] = x4;
                            }
                        } else {
                            if (valuefunction(x3) > valuefunction(x4)) {
                                bulletin = valuefunction(x3);
                                fishes[index] = x3;
                            } else {
                                bulletin = valuefunction(x4);
                                fishes[index] = x4;
                            }
                        }
                    }

//                average(xc, nf);
////                checkVector(xc);
////                boolean flag = initRemainingCapacity(xc);
//                initRemaining();
//                adjustoperator(xc);
//                checkVector(xc);
////                flag = initRemainingCapacity(xc);
////                System.out.println("initRemainingCapacity2:" + flag);
//                if (valuefunction(xc) > valuefunction(fishes[index])) {
//                    getNextFish(fishes[index], xc);
//                } else {
//                    prey(lists, index);
//                }
//
//                //追尾行为
//                int tempfish = 0;
//                for (int j = 0; j < lists.size(); j++) {
//                    if (valuefunction(fishes[lists.get(j)]) > bulletin) {
//                        bulletin = valuefunction(fishes[lists.get(j)]);
//                        bulletinFish = lists.get(j);
//                        tempfish = lists.get(j);
//                    }
//                }
//                if (bulletin > valuefunction(fishes[index])) {
//                    getNextFish(fishes[index], fishes[tempfish]);
//                } else {
//                    prey(lists, index);
//                }

                }

                for (int i = 0; i < N; i++) {
                    if (valuefunction(fishes[i]) > bulletin) {
                        bulletin = valuefunction(fishes[i]);
                    }
                }
                k++;
                arrays.add(bulletin);
            }
            arrays.add(6151.0);
            arrays.sort(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return Double.compare(o1, o2);
                }
            });
            for(Double dd:arrays){
                bw.write(dd+"");
                bw.newLine();
                bw.flush();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean initRemainingCapacity(int[] x) {
        initRemaining();
        for (int i = 0; i < m; i++) {
            arrays[i].clear();
        }
        for (int i = 0; i < x.length; i++) {
            if (x[i] != noknapsack) {
                arrays[x[i]].add(i);
                remaining[x[i]] -= tasks[i];
            }
        }
        boolean flag = true;
        for (int i = 0; i < m; i++) {
            if (remaining[i] < 0)
                flag = false;
        }
        return flag;
    }

    public void adjustoperator(int[] xc) {
        for (int i = 0; i < xc.length; i++) {
            if (xc[i] == noknapsack) {
                Random random = new Random(System.currentTimeMillis());
                int kk = random.nextInt(100) % m;
                putInto(i, kk, xc);
            }
        }
    }

    public void prey(ArrayList<Integer> v, int index) {
        int i = 0;
        for (i = 0; i < attempt; i++) {
            Random random = new Random(System.currentTimeMillis());
            int next = random.nextInt(v.size());
            if (valuefunction(fishes[index]) < valuefunction(fishes[v.get(next)])) {
                getNextFish(fishes[index], fishes[v.get(next)]);
                break;
            }
        }
        if (i >= attempt) {
            Random random = new Random(System.currentTimeMillis());
            getNextFish(fishes[index], fishes[random.nextInt(N)]);
        }
    }

    public void getNextFish(int[] x, int[] next) {
        for (int i = 0; i < x.length; i++) {
            x[i] = next[i];
        }
    }

    public int valuefunction(int[] x) {
        int maxvalue = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] != noknapsack) {
                maxvalue += values[i];
            }
        }
        return maxvalue;
    }

    public void checkVector(int[] xc) {
        initRemaining();
        for (int i = 0; i < xc.length; i++) {
            if (xc[i] != noknapsack) {
                if (remaining[xc[i]] - tasks[i] < 0) {
                    xc[i] = noknapsack;
                } else
                    remaining[xc[i]] -= tasks[i];
            }
        }
        for (int i = 0; i < xc.length; i++) {
            if (xc[i] == noknapsack) {
                for (int j = 0; j < m; j++) {
                    if (remaining[j] >= tasks[i]) {
                        xc[i] = j;
                        remaining[j] -= tasks[i];
                        break;
                    }
                }
            }
        }
    }

    public void average(int[] xc, int nf) {
        for (int i = 0; i < xc.length; i++) {
            xc[i] /= nf;
        }
    }

    public void addVector(int[] left, int[] right) {
        for (int i = 0; i < left.length; i++) {
            left[i] += right[i];
        }
    }

    public void printFishes() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(fishes[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void initKnapsacks(int... packs) {
        for (int i = 0; i < m; i++) {
            knapsacks[i] = packs[i];
        }
    }

    public void initTasks(int... ts) {
        for (int i = 0; i < n; i++) {
            tasks[i] = ts[i];
        }
    }

    public void initValues(int... vs) {
        for (int i = 0; i < n; i++) {
            values[i] = vs[i];
        }
    }

    public void initRemaining() {
        for (int i = 0; i < m; i++) {
            remaining[i] = knapsacks[i];
        }
    }

    public void checkAllFishes() {
        for (int i = 0; i < N; i++) {
            initRemaining();
            for (int j = 0; j < n; j++) {
                if (fishes[i][j] != -1)
                    remaining[fishes[i][j]] -= tasks[j];
            }
            boolean flag = true;
            for (int j = 0; j < m; j++) {
                if (remaining[j] < 0) {
                    flag = false;
                    break;
                }
            }
            System.out.println(flag);
        }
        System.out.println("----------------");
    }

    private void initfishes() {
        for (int i = 0; i < N; i++) {
            initRemaining();
            Random random = new Random(System.currentTimeMillis());
            for (int j = 0; j < n; j++) {
                int index = random.nextInt(100) % m;
                int attempt = 0;
                while (remaining[index] < tasks[j] && attempt < m) {
                    index = random.nextInt(100) % m;
                    attempt++;
                }
                if (attempt >= m) {
                    fishes[i][j] = noknapsack;
                } else {
                    fishes[i][j] = index;
                    remaining[index] -= tasks[j];
                }
            }
//            printFish(fishes[i]);
        }
    }

    public void printFish(int[] x) {
        for (int i = 0; i < x.length; i++)
            System.out.print(x[i] + " ");
        System.out.println();
    }

    public void takeOut(int task, int knapsack, int[] fish) {
        ArrayList<Integer> ts = arrays[knapsack];
        if (ts == null)
            ts = new ArrayList<>();
        if (ts.contains(task)) {
            ts.remove(ts.indexOf(task));
            fish[task] = -1;
            remaining[knapsack] += tasks[task];
        }
    }

    public void putInto(int task, int knapsack, int[] fish) {
        if (knapsacks[knapsack] < tasks[task]) {
            return;
        } else {
            if (remaining[knapsack] >= tasks[task]) {
                if (fish[task] == noknapsack) {
                    fish[task] = knapsack;
                } else {
                    takeOut(task, fish[task], fish);
                    fish[task] = knapsack;
                    remaining[knapsack] -= tasks[task];
                }
                arrays[knapsack].add(task);
            } else {
                Random random = new Random(System.currentTimeMillis());
                while (remaining[knapsack] < tasks[task] && arrays[knapsack].size() > 0) {
                    int index = random.nextInt(100) % arrays[knapsack].size();
                    takeOut(arrays[knapsack].get(index), knapsack, fish);
                }
                if (fish[task] == noknapsack) {
                    fish[task] = knapsack;
                    remaining[knapsack] -= tasks[task];
                    arrays[knapsack].add(task);
                } else {
                    takeOut(task, fish[task], fish);
                    fish[task] = knapsack;
                    remaining[knapsack] -= tasks[task];
                    arrays[knapsack].add(task);
                }
            }
        }
    }

    public int distance(int[] left, int[] right) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (left[i] != right[i])
                result++;
        }
        return result;
    }


}
