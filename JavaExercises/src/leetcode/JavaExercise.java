package leetcode;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.ServerSocket;

/**
 * Created by ericson on 2015/7/24 0024.
 */
public class JavaExercise {
    public static void main(String... args) throws IOException, ClassNotFoundException,Exception {
        JavaExercise exercise = new JavaExercise();
//        Map<Double, Integer> maps = new ConcurrentSkipListMap<Double, Integer>(exercise.new MapComparator());
//        String str1 = "hello";
//        String str2 = "world";
//        maps.put(0.0, 1);
//        maps.put(1.0, 2);
//        maps.put(2.0, 3);
//        for (Map.Entry<Double, Integer> entry : maps.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//
//        System.out.println("over");

//        PriorityQueue<Double> queue = new PriorityQueue<Double>(20, new QueueComparator());
//        queue.offer(new Double(10));
//        queue.offer(new Double(20));
//        while (!queue.isEmpty()) {
//            double value = queue.poll();
//            System.out.println(value);
//        }

//        BigDecimal b1=new BigDecimal(0.5);
//        BigDecimal b2=new BigDecimal(0.1);
//        System.out.println(b1.add(b2).doubleValue());
//        System.out.println(Character.SIZE/8);
//        char c='中';
//        int i=c;
//        System.out.println(i);

//        File file1 = new File("E:\\data\\text");
//        if (!file1.exists()) {
//            try {
//                file1.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        File dir = new File("E:\\data");
//        if (dir.isDirectory()) {
//            String[] files = dir.list();
//            for (String fileName : files) {
//                File f = new File(dir.getPath() + File.separator + fileName);
//                if (f.isFile()) {
//                    System.out.println("file:" + f.getName());
//                } else if (f.isDirectory()) {
//                    System.out.println("dir:" + f.getName());
//                }
//            }
//        }

//        FileInputStream fin = new FileInputStream("d:\\text");
//        FileOutputStream fout = new FileOutputStream("d:\\textcopy");
//        byte[] buff = new byte[256];
//        int len = 0;
//        while ((len = fin.read(buff)) > 0) {
//            fout.write(buff, 0, len);
//        }
//        fin.close();
//        fout.close();

//        RandomAccessFile file = new RandomAccessFile("d:\\text", "rw");
//        for (int i = 0; i < file.length(); i++) {
//            byte b = (byte) file.read();
//            char c = (char) b;
//            if (c == 'a') {
//                file.seek(i);
//                file.write('c');
//            }
//        }
//        file.close();

//        InputStream fin = new FileInputStream("d:\\text");
//        OutputStream fout = new FileOutputStream("d:\\textcopy");
//        byte[] buff = new byte[256];
//        int len = 0;
//        while ((len = fin.read(buff)) > 0) {
//            fout.write(buff, 0, len);
//        }
//        fin.close();
//        fout.close();

//        InputStream in = new FileInputStream("d:\\text.txt");
//        InputStreamReader isr = new InputStreamReader(in, "GBK");
//        BufferedReader br = new BufferedReader(isr);
//        StringBuffer sb = new StringBuffer();
//        String str = null;
//        while ((str = br.readLine()) != null) {
//            sb.append(str);
//        }
//        br.close();
//        System.out.println("text content:" + sb);
//        PrintWriter pw = new PrintWriter(new FileOutputStream("d:\\textcopy.txt"));
//        pw.println("a");
//        pw.println("b");
//        pw.println("c");
//        pw.close();

//        Student stu = exercise.new Student();
//        stu.setAge(20);
//        stu.setName("yangchen");
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d:\\text"));
//        oos.writeObject(stu);
//        oos.flush();
//        oos.close();
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d:\\text"));
//        Object obj = ois.readObject();
//        Student stuBak = (Student) obj;
//        System.out.println("stu name is " + stuBak.getName());
//        System.out.println("stu age is " + stuBak.getAge());

//        new MyThread().start();
//        new MyThread().start();
//        new MyThread().start();


        Person p1=new Person(10,"name1");
        Person p2=new Person(20,"name2");

        Field field=Person.class.getDeclaredField("age");
        field.setAccessible(true);
        int age1=(Integer)field.get(p1);
        System.out.println(age1);

        ServerSocket serverSocket=new ServerSocket(8788);
        serverSocket.accept();
    }

    static class Person {
        private int age;
        private String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    static class MyThread extends Thread {
        private static int index = 0;

        @Override
        public void run() {
            for (int i = 0; i < 100; i++)
                System.out.println(getName() + ",index:" + index++);
        }
    }

    static class Student implements Serializable {

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }


//    static class QueueComparator implements Comparator<Double> {
//
//        @Override
//        public int compare(Double o1, Double o2) {
//            return Double.compare(o2, o1);
//        }
//    }
//
//    class MapComparator implements Comparator<Double> {
//        @Override
//        public int compare(Double o1, Double o2) {
//            return -1;
//        }
//    }
}
