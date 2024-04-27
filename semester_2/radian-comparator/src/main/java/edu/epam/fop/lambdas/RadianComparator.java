package edu.epam.fop.lambdas;

import java.util.Comparator;

public final class RadianComparator {
    private static final double EPSILON = 0.001;


    /*
     * Compares 2 angles (passed in radians). The angle counts from 0 up to 2π, the period must
     * be ignored, i.e. if angle is greater than 2π, then it must be converted to the range [0; 2π).
     * Precision must be 0.001 (delta), so if |a - b| < 0.001, then a == b.
     * 0 == 2π
     */
    public static Comparator<Double> get() {

        return (a, b) -> {

            if (a == null && b == null) {
                return 0;
            } else if (a == null) {
                return - 1;
            } else if (b == null) {
                return 1;
            }

            double normalizedA = normalize(a);
            double normalizedB = normalize(b);


            if (Math.abs(normalizedA - normalizedB) < EPSILON) {
                return 0;
            } else if (normalizedA < normalizedB) {
                return - 1;
            } else {
                return 1;
            }
        };
    }

    private static double normalize(double radian) {
        while (radian < 0) {
            radian += 2 * Math.PI;
        }
        while (radian >= 2 * Math.PI) {
            radian -= 2 * Math.PI;
        }
        return radian;
    }
}