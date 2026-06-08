package com.example.Services;
//version5
import java.math.BigDecimal;

public class ResultCheck {

    public static boolean resultCheck(double xd, String yStr, String rStr) {
        BigDecimal x, y, r;
        boolean flag = false;

        x = new BigDecimal(xd);
        y = new BigDecimal(yStr);
        r = new BigDecimal(rStr);

        if (r == null || x == null || y == null) return false;

        if (r.compareTo(BigDecimal.ZERO) <= 0 || r.compareTo(BigDecimal.valueOf(5)) > 0) return false;
        if (x.compareTo(BigDecimal.valueOf(-5)) < 0 || x.compareTo(BigDecimal.valueOf(5)) > 0) return false;
        if (y.compareTo(BigDecimal.valueOf(-5)) < 0 || y.compareTo(BigDecimal.valueOf(3)) > 0) return false;

        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0
                && y.compareTo(r.subtract(x)) <= 0) {
            flag = true;
        }

        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0
                && x.compareTo(r.divide(BigDecimal.valueOf(-2))) >= 0
                && y.compareTo(r.negate()) >= 0) {
            flag = true;
        }

        BigDecimal x2y2 = x.multiply(x).add(y.multiply(y));
        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0
                && x2y2.compareTo(r.multiply(r)) <= 0) {
            flag = true;
        }

        return flag;
    }
}
