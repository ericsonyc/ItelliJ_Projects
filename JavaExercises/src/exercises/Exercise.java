package exercises;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by ericson on 2014/12/23 0023.
 */
public class Exercise {
    public static void main(String[] args) {
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(ArrayList.class.isAssignableFrom(List.class));
//        System.out.println(Object.class.isAssignableFrom(String.class));
//
//        Exercise exercise = new Exercise();
//        int[] sort = new int[10];
//        Random random = new Random(200);
//        for (int i = 0; i < sort.length; i++) {
//            sort[i] = random.nextInt(200);
//        }
//        exercise.printArray(sort, 0, sort.length);
//        exercise.quick_sort(sort, 0, sort.length - 1);
//        exercise.printArray(sort, 0, sort.length);
//        exercise.quick_sort(sort, 0, sort.length - 1);
//        exercise.printArray(sort, 0, sort.length);
//
//
//        float[] image = new float[10];
//        for (int i = 0; i < image.length; i++) {
//            image[i] = 0;
//        }
//
//        String str = "1234567890";
//        System.out.println("length:" + str.getBytes().length);
//
//        str = "";
//        System.out.println(str.substring(str.indexOf(",") + 1));
//
//        List<Integer> lists = new ArrayList<Integer>();
//        //lists.add(2, 45);
//        //System.out.println(lists.size());
//        //System.out.println(lists.get(2));
//
//        String filename = "adsgasdg,gdfh,dsghf";
//        String[] ff = filename.split("$");
//        for (int i = 0; i < ff.length; i++) {
//            System.out.println(ff[i]);
//        }
//
//        String s = "aa";
//        String p = " aa".trim();
//        System.out.println(s == p);
//
//        char ch1 = 'a';
//        char ch2 = 'a';
//        System.out.println(ch1 == ch2);
//
//        System.out.println(1 ^ 1);
//
//        System.currentTimeMillis();


//        Scanner scanner=new Scanner(System.in);
//        String str=scanner.nextLine();
//        System.out.println(str);

//        String str1 = "Hello";
//        String str2 = "Hello";
//        System.out.println(str1.equals(str2));

        Map<String, Integer> maps = new ConcurrentSkipListMap<String, Integer>();
        for (int i = 0; i < 10; i++) {
            maps.put(String.valueOf(i), i);
        }
        for (Map.Entry<String, Integer> entry : maps.entrySet()) {
            System.out.println(entry.getKey());
            entry.setValue(entry.getValue()+1);
            maps.put(entry.getKey(), entry.getValue());
        }
        System.out.println("---------------");
        for(Map.Entry<String, Integer>entry:maps.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        System.out.println("-----------------");
    }

    private void printArray(int[] s, int start, int end) {
        for (int i = start; i < end; i++) {
            System.out.print(s[i] + " ");
        }
        System.out.println("**********************************");
    }

    private void demo() {
        Runner runner = new Runner();
        Throwable thro = runner.throwable;
    }

    private void quick_sort(int[] s, int l, int r) {
        if (l < r) {
            Swap(s, l, l + (r - l + 1) / 2);
            int i = l, j = r, x = s[l];
            while (i < j) {
                while (i < j && s[j] >= x) {
                    j--;
                }
                if (i < j)
                    s[i++] = s[j];
                while (i < j && s[i] < x) {
                    i++;
                }
                if (i < j)
                    s[j--] = s[i];
            }
            s[i] = x;
            quick_sort(s, l, i - 1);
            quick_sort(s, i + 1, r);
        }
    }

    private void Swap(int[] s, int oldindex, int newindex) {
        int temp = s[oldindex];
        s[oldindex] = s[newindex];
        s[newindex] = temp;
    }

    private class Runner extends Thread {
        private Throwable throwable;

        @Override
        public void run() {
            super.run();
        }
    }
}
