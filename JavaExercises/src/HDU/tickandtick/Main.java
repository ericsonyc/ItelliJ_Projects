package HDU.tickandtick;

import java.util.Scanner;

/**
 * Created by ericson on 2015/8/20 0020.
 */
public class Main {
    final double s_h = 719.0 / 120, s_m = 59.0 / 10, m_h = 11.0 / 120;
    final double tsh = 43200.0 / 719, tsm = 3600.0 / 59, tmh = 43200.0 / 11;

    double MAX(double a, double b, double c) {
        double max = a;
        if (b > max)
            max = b;
        if (c > max)
            max = c;
        return max;
    }

    double MIN(double a, double b, double c) {
        double min = a;
        if (b < min)
            min = b;
        if (c < min)
            min = c;
        return min;
    }

    public static void main(String[] args) {
        Main mm = new Main();
        double D;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            D = scanner.nextDouble();
            double bsm, bsh, bmh, esm, esh, emh, begin, end, total = 0;
            bsm = D / mm.s_m;
            bsh = D / mm.s_h;
            bmh = D / mm.m_h;
            esm = (360 - D) / mm.s_m;
            esh = (360 - D) / mm.s_h;
            emh = (360 - D) / mm.m_h;
            for (double bt3 = bsh, et3 = esh; et3 <= 43200.000001; bt3 += mm.tsh, et3 += mm.tsh) {
                for (double bt2 = bmh, et2 = emh; et2 <= 43200.000001; bt2 += mm.tmh, et2 += mm.tmh) {
                    if (et2 < bt3)
                        continue;
                    if (bt2 > et3)
                        break;
                    for (double bt1 = bsm, et1 = esm; et1 <= 43200.000001; bt1 += mm.tsm, et1 += mm.tsm) {
                        if (et1 < bt2 || et1 < bt3)
                            continue;
                        if (bt1 > et2 || bt1 > et3)
                            break;
                        begin = mm.MAX(bt1, bt2, bt3);
                        end = mm.MIN(et1, et2, et3);
                        total += (end - begin);
                    }
                }
            }
            System.out.println(total / 432);
        }
    }
}
