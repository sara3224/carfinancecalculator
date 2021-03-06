package edu.stthomas.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Helper {
    private static DecimalFormat df = new DecimalFormat("#.00");
    public static double roundUp(double dbl) {
        df.setRoundingMode(RoundingMode.UP);
        return Double.parseDouble(df.format(dbl));
    }

    public static double formatTwoDigit(double dbl) {
        return Double.parseDouble(df.format(dbl));
    }
}
