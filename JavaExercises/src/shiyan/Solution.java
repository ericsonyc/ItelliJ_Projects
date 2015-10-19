package shiyan;

/**
 * Created by ericson on 2015/9/7 0007.
 */
public class Solution {
    public String formatString(String sourceString) {
        sourceString = sourceString.trim();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sourceString.length(); ) {
            if (sourceString.charAt(i) != ' ') {
                builder.append(sourceString.charAt(i));
                i++;
            } else {
                builder.append(sourceString.charAt(i));
                while (i < sourceString.length() && sourceString.charAt(i) == ' ') {
                    i++;
                }
            }
        }
        return builder.toString();
    }
}
