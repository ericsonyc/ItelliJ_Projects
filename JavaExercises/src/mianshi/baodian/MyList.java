package mianshi.baodian;

/**
 * Created by ericson on 2015/8/18 0018.
 */
public class MyList {
    private static class Node {
        Object data;
        Node next;

        public Node(Object data) {
            super();
            this.data = data;
            this.next = null;
        }
    }

    Node head;

    public MyList() {
        head = null;
    }

    public void clear() {
        head = null;
    }

    public void travel() {
        Node p = head;
        while (p != null) {
            System.out.println(p.data);
            p = p.next;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        Node p = head;
        int sum = 0;
        while (p != null) {
            sum++;
            p = p.next;
        }
        return sum;
    }

    public void insert(Object d, int pos) {
        if (pos < 0 || pos > size()) {
            throw new RuntimeException("下标出错");
        }
        Node newNode = new Node(d);
        if (pos == 0) {
            newNode.next = head;
            head = newNode;
        } else if (pos >= size() - 1) {
            get(size() - 1).next = newNode;
        } else {
            newNode.next = get(pos);
            get(pos - 1).next = newNode;
        }
    }

    public Node get(int pos) {
        if (pos < 0 || pos > size())
            throw new RuntimeException("下标出错");
        if (pos == 0)
            return head;
        Node p = head;
        for (int i = 0; i < pos; i++) {
            p = p.next;
        }
        return p;
    }

    public static void main(String[] args){
        MyList list=new MyList();
        list.insert(10,0);
        list.insert(20,1);
        list.insert(30,0);
        list.insert(40,1);
        list.travel();
    }
}
