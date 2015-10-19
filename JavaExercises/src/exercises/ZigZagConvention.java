package exercises;

/**
 * Created by ericson on 2015/3/15 0015.
 */
public class ZigZagConvention {
    public static void main(String[] args) {
        ZigZagConvention zig = new ZigZagConvention();
        String str = "abc";
        String result = zig.convention(str, 2);
        System.out.println(result);
    }

    private String conv2(String s, int nRows) {
        char[] c = s.toCharArray();
        int len = c.length;
        StringBuffer[] sb = new StringBuffer[nRows];
        for (int z = 0; z < sb.length; z++) {
            sb[z] = new StringBuffer();
        }
        int k = 0;
        while (k < len) {
            for (int zigZagIndex = 0; zigZagIndex < nRows && k < len; zigZagIndex++) {
                sb[zigZagIndex].append(c[k++]);
            }
            for (int zigZagIndex = nRows - 2; zigZagIndex >= 1 && k < len; zigZagIndex--)
                sb[zigZagIndex].append(c[k++]);
        }
        for (int index = 1; index < sb.length; index++)
            sb[0].append(sb[index]);
        return sb[0].toString();
    }

    private String conv(String s, int nRows) {
        if (nRows == 1)
            return s;
        StringBuilder strBuilder = new StringBuilder();
        int borderRowStep = 2 * nRows - 2;
        for (int i = 0; i < nRows; i++) {
            if (i == 0 || i == nRows - 1) {
                for (int j = i; j < s.length(); j = j + borderRowStep) {
                    strBuilder.append(s.charAt(j));
                }
            } else {
                int j = i;
                boolean flag = true;
                int insideRowLargeStep = 2 * (nRows - 1 - i);
                int insideRowSmallStep = borderRowStep - insideRowLargeStep;
                while (j < s.length()) {
                    strBuilder.append(s.charAt(j));
                    if (flag)
                        j = j + insideRowLargeStep;
                    else
                        j = j + insideRowSmallStep;
                    flag = !flag;
                }
            }
        }
        return strBuilder.toString();
    }

    private String convert(String s, int nRows) {
        char[] c = s.toCharArray();
        int len = c.length;
        StringBuffer[] sb = new StringBuffer[nRows];
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuffer();
        }

        int i = 0;
        while (i < len) {
            for (int idx = 0; idx < nRows && i < len; idx++) {
                sb[idx].append(c[i++]);
            }
            for (int idx = nRows - 2; idx >= 1 && i < len; idx--) {
                sb[idx].append(c[i++]);
            }
        }
        for (int idx = 1; idx < sb.length; idx++)
            sb[0].append(sb[idx]);
        return sb[0].toString();
    }

    private String convention(String str, int rows) {
        int length = str.length();
        //int cols = length / (rows - 1) == 0 ? length / (rows - 1) : length / (rows - 1) + 1;
        //int[][] chs = new int[rows][cols];
        StringBuilder[] strBuilder = new StringBuilder[rows];
        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                chs[i][j] = -1;
//            }
            strBuilder[i] = new StringBuilder();
        }
        int count = 0;
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (i < rows) {
                j = i;
            } else {
                count = (i - rows) / (rows - 1) + 1;
                j = count % 2 == 0 ? 1 + (i - rows) % (rows - 1) : (rows - (i - rows) % (rows - 1) - 2);

            }
            //chs[j][count] = i;
            strBuilder[j].append(str.charAt(i));
        }

        String result = "";
        for (int i = 1; i < rows; i++) {
//            for (int t = 0; t < cols; t++) {
//                if (chs[i][t] != -1) {
//                    result += str.charAt(chs[i][t]);
//                }
//            }
            strBuilder[0].append(strBuilder[i]);
        }
        result = strBuilder[0].toString();
        return result;
    }
}
