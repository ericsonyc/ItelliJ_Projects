package shiyan;

/**
 * Created by ericson on 2015/9/8 0008.
 */
public class ArglyNumber {
    public static void main(String[] args) {
        ArglyNumber arglyNumber = new ArglyNumber();
//        System.out.println(arglyNumber.getUglyNumebr(6));
//        BinaryTreeNode node0 = arglyNumber.new BinaryTreeNode();
//        node0.m_nValue = 0;
//        BinaryTreeNode node1 = arglyNumber.new BinaryTreeNode();
//        node1.m_nValue = 1;
//        BinaryTreeNode node2 = arglyNumber.new BinaryTreeNode();
//        node2.m_nValue = 2;
//        BinaryTreeNode node3 = arglyNumber.new BinaryTreeNode();
//        node3.m_nValue = 3;
//        BinaryTreeNode node4 = arglyNumber.new BinaryTreeNode();
//        node4.m_nValue = 4;
//        node0.m_pLeft = node1;
//        node0.m_pRight = node2;
//        node1.m_pLeft = node3;
//        node1.m_pRight = node4;
//        BinaryTreeNode node5 = arglyNumber.new BinaryTreeNode();
//        node5.m_nValue = 5;
//        node4.m_pRight = node5;
//        int[] num = new int[1];
//        System.out.println(arglyNumber.isBalanced2(node0, num));

        System.out.println(arglyNumber.Add(2, 3));
    }

    private int Add(int num1, int num2) {
        int sum, carray;
        do {
            sum = num1 ^ num2;
            carray = (num1 & num2) << 1;
            num1 = sum;
            num2 = carray;
        } while (num2 != 0);
        return num1;
    }

    private int LastRemaining(int n, int m) {
        if (n < 1 || m < 1)
            return -1;
        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }
        return last;
    }

    private void PrintProbability(int number) {
        if (number < 1)
            return;
        int g_maxValue = 6;
        int[][] pProbabilities = new int[2][g_maxValue * number + 1];
        for (int i = 0; i < g_maxValue * number + 1; i++) {
            pProbabilities[0][i] = 0;
            pProbabilities[1][i] = 0;
        }
        int flag = 0;
        for (int i = 1; i < g_maxValue; i++) {
            pProbabilities[flag][i] = 1;
        }
        for (int k = 2; k <= number; ++k) {
            for (int i = 0; i < k; i++) {
                pProbabilities[1 - flag][i] = 0;
            }
            for (int i = k; i <= g_maxValue * k; i++) {
                pProbabilities[1 - flag][i] = 0;
                for (int j = 1; j <= i && j <= g_maxValue; j++) {
                    pProbabilities[1 - flag][i] += pProbabilities[flag][i - j];
                }
                flag = 1 - flag;
            }
            double total = Math.pow((double) g_maxValue, number);
            for (int i = number; i <= g_maxValue * number; ++i) {
                double ratio = (double) pProbabilities[flag][i] / total;
                System.out.printf("%d: %e\n", i, ratio);
            }
        }
    }

    private int getUglyNumebr(int index) {
        if (index <= 0)
            return 0;
        int[] pUglyNumbers = new int[index + 1];
        pUglyNumbers[0] = 1;
        int nextUglyIndex = 1;
        int pMultiply2 = 0;
        int pMultiply3 = 0;
        int pMultiply5 = 0;
        while (nextUglyIndex <= index) {
            int min = Math.min(pUglyNumbers[pMultiply2] * 2, Math.min(pUglyNumbers[pMultiply3] * 3, pUglyNumbers[pMultiply5] * 5));
            pUglyNumbers[nextUglyIndex] = min;
            while (pUglyNumbers[pMultiply2] * 2 <= pUglyNumbers[nextUglyIndex])
                ++pMultiply2;
            while (pUglyNumbers[pMultiply3] * 3 <= pUglyNumbers[nextUglyIndex])
                ++pMultiply3;
            while (pUglyNumbers[pMultiply5] * 5 <= pUglyNumbers[nextUglyIndex])
                ++pMultiply5;
            ++nextUglyIndex;
        }
        int ugly = pUglyNumbers[nextUglyIndex - 1];
        return ugly;
    }

    private int getFirstK(int[] data, int length, int k, int start, int end) {
        if (start > end)
            return -1;
        int middleIndex = (start + end) / 2;
        int middleData = data[middleIndex];
        if (middleData == k) {
            if ((middleIndex > 0 && data[middleIndex - 1] != k) || middleIndex == 0)
                return middleIndex;
            else
                end = middleIndex - 1;
        } else if (middleIndex > k)
            end = middleIndex - 1;
        else
            start = middleIndex + 1;
        return getFirstK(data, length, k, start, end);
    }

    private int GetLastK(int[] data, int length, int k, int start, int end) {
        if (start > end)
            return -1;
        int middleIndex = (start + end) / 2;
        int middleData = data[middleIndex];
        if (middleData == k) {
            if ((middleIndex < length - 1 && data[middleIndex + 1] != k) || middleIndex == length - 1)
                return middleIndex;
            else
                start = middleIndex + 1;
        } else if (middleData < k) {
            start = middleIndex + 1;
        } else {
            end = middleIndex - 1;
        }
        return GetLastK(data, length, k, start, end);
    }

    private int GetNumberOfK(int[] data, int length, int k) {
        int number = 0;
        if (data != null && length > 0) {
            int first = getFirstK(data, length, k, 0, length - 1);
            int last = GetLastK(data, length, k, 0, length - 1);
            if (first > -1 && last > -1)
                number = last - first + 1;
        }
        return number;
    }

    class BinaryTreeNode {
        int m_nValue;
        BinaryTreeNode m_pLeft;
        BinaryTreeNode m_pRight;
    }

    private int TreeDepth(BinaryTreeNode pRoot) {
        if (pRoot == null)
            return 0;
        int nLeft = TreeDepth(pRoot.m_pLeft);
        int nRight = TreeDepth(pRoot.m_pRight);
        return Math.max(nLeft, nRight) + 1;
    }

    private boolean isBalanced(BinaryTreeNode pRoot) {
        if (pRoot == null)
            return true;
        int left = TreeDepth(pRoot.m_pLeft);
        int right = TreeDepth(pRoot.m_pRight);
        int diff = left - right;
        if (diff > 1 || diff < -1)
            return false;
        return isBalanced(pRoot.m_pLeft) && isBalanced(pRoot.m_pRight);
    }

    private boolean isBalanced2(BinaryTreeNode pRoot, int[] pDepth) {
        if (pRoot == null) {
            pDepth[0] = 0;
            return true;
        }
        int[] left = new int[1], right = new int[1];
        if (isBalanced2(pRoot.m_pLeft, left) && isBalanced2(pRoot.m_pRight, right)) {
            int diff = left[0] - right[0];
            if (diff <= 1 && diff >= -1) {
                pDepth[0] = 1 + (left[0] > right[0] ? left[0] : right[0]);
                return true;
            }
        }
        return false;
    }

    private void FindNumsAppearOnce(int[] data, int length, int[] result) {
        if (data == null || length < 2)
            return;
        int resultExclusiveOR = 0;
        for (int i = 0; i < length; ++i) {
            resultExclusiveOR ^= data[i];
        }
        int index = FindFirstBitIs1(resultExclusiveOR);
        result[0] = result[1] = 0;
        for (int j = 0; j < length; ++j) {
            if (isBit1(data[j], index)) {
                result[0] ^= data[j];
            } else
                result[1] ^= data[j];
        }
    }

    private int FindFirstBitIs1(int num) {
        int indexBit = 0;
        indexBit = num & (~(num - 1));
        return indexBit;
    }

    private boolean isBit1(int num, int index) {
        num = num >> index;
        return (num & 1) == 1;
    }
}
