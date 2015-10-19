package exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/3/3 0003.
 */
public class LCA {
    public static void main(String[] args) {
        LCA lca = new LCA();

    }

    class Node {
        int data;
        Node left;
        Node right;
        Node parent;
    }

    /**
     * Binary Search Tree
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    Node LCA(Node root, Node p, Node q) {
        if (root == null || p == null || q == null) return null;
        if (Math.max(p.data, q.data) < root.data)
            return LCA(root.left, p, q);
        else if (Math.min(p.data, q.data) > root.data) {
            return LCA(root.right, p, q);
        } else
            return root;
    }

    Node LCAIter(Node root, Node p, Node q) {
        if (findNode(root, p) && findNode(root, q)) {
            return lowestCommonAncestor(root, p, q);
        }
        return null;
    }

    boolean findNode(Node root, Node pNode) {
        boolean flag = false;
        Node pTemp = root;
        if (root != null && pNode != null) {
            while (pTemp != null && pTemp.data != pNode.data) {
                if (pTemp.data > pNode.data) {
                    pTemp = pTemp.left;
                } else {
                    pTemp = pTemp.right;
                }
            }
            if (pTemp != null && pTemp.data == pNode.data) {
                flag = true;
            }
        }
        return flag;
    }

    Node lowestCommonAncestor(Node root, Node p, Node q) {
        Node lca = null;
        if (root != null && p != null && q != null) {
            if (root.data > Math.max(p.data, q.data)) {
                lca = lowestCommonAncestor(root.left, p, q);
            } else if (root.data < Math.min(p.data, q.data)) {
                lca = lowestCommonAncestor(root.right, p, q);
            } else {
                lca = root;
            }
        }
        return lca;
    }

    /**
     * Binary Tree
     */
    int countMatchesPQ(Node root, Node p, Node q) {
        if (root != null) return 0;
        int matches = countMatchesPQ(root.left, p, q) + countMatchesPQ(root.right, p, q);
        if (root == p || root == q)
            return 1 + matches;
        else
            return matches;
    }

    Node LCABinary(Node root, Node p, Node q) {
        if (root == null || p == null || q == null) return null;
        if (root == p || root == q) return root;
        int totalMatches = countMatchesPQ(root.left, p, q);
        if (totalMatches == 1)
            return root;
        else if (totalMatches == 2)
            return LCABinary(root.left, p, q);
        else
            return LCABinary(root.right, p, q);
    }

    Node LCABinaryBottomTop(Node root, Node p, Node q) {
        if (root == null) return null;
        if (root == p || root == q) return root;
        Node L = LCABinaryBottomTop(root.left, p, q);
        Node R = LCABinaryBottomTop(root.right, p, q);
        if (L != null && R != null) return root;
        return L != null ? L : R;
    }

    /**
     * Binary Tree with parent
     */
    Node LCAParent(Node root, Node p, Node q) {
        List<Node> lists = new ArrayList<Node>();
        while (p != null || q != null) {
            if (p != null) {
                if (lists.contains(p)) {
                    return p;
                } else {
                    lists.add(p);
                }
                p = p.parent;
            }
            if (q != null) {
                if (lists.contains(q)) {
                    return q;
                } else {
                    q = q.parent;
                }
            }
        }
        return null;
    }

    int getHeight(Node p) {
        int height = 0;
        while (p != null) {
            height++;
            p = p.parent;
        }
        return height;
    }

    //p must be depper than q
    Node LCAParentNew(Node p, Node q) {
        int h1 = getHeight(p);
        int h2 = getHeight(q);
        int dh = h2 - h1;
        for (int h = 0; h < dh; h++) {
            q = q.parent;
        }
        while (p != null && q != null) {
            if (p == q) return p;
            p = p.parent;
            q = q.parent;
        }
        return null;
    }
}
