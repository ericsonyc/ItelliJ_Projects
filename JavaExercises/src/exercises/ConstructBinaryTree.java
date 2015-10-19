package exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/3/8 0008.
 */
public class ConstructBinaryTree {
    private static int Max = 256;
    private int[] mapIndex = new int[Max];

    public static void main(String[] args) {
        ConstructBinaryTree binary = new ConstructBinaryTree();
        int[] preorder = new int[]{7, 10, 4, 3, 1, 2, 8, 11};
        int[] inorder = new int[]{4, 10, 3, 1, 7, 11, 8, 2};
        binary.mapToIndices(inorder, inorder.length);
        Node root = binary.buildInorderPerorder(inorder, 0, preorder, 0, inorder.length, 0);
        System.gc();
        binary.print(root);
    }

    private void print(Node root) {
        List<Node> lists = new ArrayList<Node>();
        lists.add(root);
        while (!lists.isEmpty()) {
            Node node = lists.get(0);
            lists.remove(0);
            lists.add(node.left);
            lists.add(node.right);
            System.out.println(node.value);
        }
    }

    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    void mapToIndices(int[] inorder, int n) {
        for (int i = 0; i < n; i++) {
            assert (0 <= inorder[i] && inorder[i] <= Max - 1);
            mapIndex[inorder[i]] = i;
        }
    }

    Node buildInorderPerorder(int[] in, int h, int[] pre, int len, int n, int offset) {
        assert (n >= 0);
        if (n == 0) return null;
        int rootVal = pre[0 + len];
        int i = mapIndex[rootVal] - offset;
        Node root = new Node(rootVal);
        root.left = buildInorderPerorder(in, 0, pre, 1, i, offset);
        root.right = buildInorderPerorder(in, i + 1, pre, i + 1, n - i - 1, offset + i + 1);
        return root;
    }

}
