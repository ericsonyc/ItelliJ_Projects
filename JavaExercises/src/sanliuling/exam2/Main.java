package sanliuling.exam2;

public class Main {
    public static void main(String[] args) {
        try {
            int T = Integer.parseInt(String.valueOf((char) System.in.read()));
            System.in.read();
            char[] chars = new char[100];
            int count = 0;
            while (T-- > 0) {
                char ch;
                while ((ch = (char) System.in.read()) != '\n') {
                    if (ch == '#') {
                        count--;
                        if (count < 0)
                            count = 0;
                    } else if (ch == '@') {
                        count = 0;
                    } else {
                        chars[count++] = ch;
                    }
                }
                for (int i = 0; i < count; i++) {
                    System.out.write(chars[count]);
                }
                System.out.write('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
