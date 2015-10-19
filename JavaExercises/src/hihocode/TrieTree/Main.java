package hihocode.TrieTree;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main mm = new Main();
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        TrieTree root = mm.new TrieTree(' ', 0);
        for (int i = 0; i < n; i++) {
            String words = sc.nextLine();
            root.setCount(root.getCount() + 1);
            TrieTree temp = root;
            for (int j = 0; j < words.length(); j++) {
                TrieTree[] lists = temp.getNext();
                if (lists[words.charAt(j) - 'a'] != null) {
                    temp = lists[words.charAt(j) - 'a'];
                    temp.setCount(temp.getCount() + 1);
                } else {
                    TrieTree tree = mm.new TrieTree(words.charAt(j), 1);
                    temp.getNext()[words.charAt(j) - 'a'] = tree;
                    temp = tree;
                }
            }
        }
        int m = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < m; i++) {
            String request = sc.nextLine();
            TrieTree temp = root;
            boolean flag = false;
            for (int j = 0; j < request.length(); j++) {
                TrieTree[] lists = temp.getNext();
                if (lists[request.charAt(j) - 'a'] != null) {
                    temp = lists[request.charAt(j) - 'a'];
                } else {
                    System.out.println(0);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println(temp.getCount());
            }
        }
    }

    class TrieTree {
        private char ch;
        TrieTree[] next;
        int count;

        public char getCh() {
            return ch;
        }

        public TrieTree[] getNext() {

            return next;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public TrieTree(char c, int cou) {
            ch = c;
            count = cou;
            next = new TrieTree[26];
        }
    }
}
