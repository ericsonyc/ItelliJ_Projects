package HDU.balloonrise;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by ericson on 2015/8/20 0020.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> maps = new HashMap<String, Integer>();
        while (true) {
            int n = Integer.parseInt(scanner.nextLine());
            if (n == 0)
                break;
            while (n-- > 0) {
                String color = scanner.nextLine();
                if (maps.containsKey(color)) {
                    maps.put(color, maps.get(color) + 1);
                } else {
                    maps.put(color, 1);
                }
            }
            String color = null;
            int max = 0;
            for (Map.Entry<String, Integer> entry : maps.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    color = entry.getKey();
                }
            }
            System.out.println(color);
            maps.clear();
        }
    }
}
