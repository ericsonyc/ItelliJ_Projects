package exercises;

/**
 * Created by ericson on 2015/3/3 0003.
 */
public class InsertCyclicList {

    class Node {
        int data;
        Node next;

        public Node() {
            data = 0;
        }

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {

    }

    void insert(Node aNode, int x) {
        if (aNode == null) {
            aNode = new Node(x);
            aNode.next = aNode;
            return;
        }
        Node p = aNode;
        Node prev = null;
        do {
            prev = p;
            p = p.next;
            if (x <= p.data && x >= prev.data) break;
            if ((prev.data > p.data) && (x < p.data || x > prev.data)) break;
        } while (p != aNode);

        Node newNode = new Node(x);
        newNode.next = p;
        prev.next = newNode;
    }
}
