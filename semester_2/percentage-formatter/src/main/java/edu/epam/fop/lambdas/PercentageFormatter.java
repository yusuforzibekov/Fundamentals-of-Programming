package edu.epam.fop.lambdas;

import java.util.function.DoubleFunction;

public interface PercentageFormatter {

    DoubleFunction<String> INSTANCE = value -> {
        if (value > 1) {
            value *= 100;
            double fraction_part = value - (int) value;

            if (fraction_part < 0.5) {
                return (int) Math.floor(value) + " %";
            } else {
                return (int) Math.ceil(value) + " %";
            }
        } else {
            value *= 100;
            double fraction_part = value - (int) value;
            if (fraction_part == 0) {
                return (int) value + " %";
            } else {
                return String.format("%.1f", value).replace("," , ".") + " %";
            }
        }
    };
}