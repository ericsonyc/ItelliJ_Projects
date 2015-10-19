package yamaxun;

// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// TNode CLASS IS DEFINED BY DEFAULT
// CLASS BEGINS, THIS CLASS IS REQUIRED
public class BstMinSum {
    class TNode {
        public int value;
        public TNode left;
        public TNode right;
    }

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public static int minTreePath(TNode t) {
        // INSERT YOUR CODE HERE
        if (t.left == null && t.right == null)
            return t.value;
        if (t.left == null) {
            return t.value + minTreePath(t.right);
        }
        if (t.right == null) {
            return minTreePath(t.left) + t.value;
        }
        int left = minTreePath(t.left);
        int right = minTreePath(t.right);
        return Math.min(left, right) + t.value;
    }
    // METHOD SIGNATURE ENDS
}
