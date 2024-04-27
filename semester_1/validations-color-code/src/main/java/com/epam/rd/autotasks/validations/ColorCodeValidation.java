package com.epam.rd.autotasks.validations;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ColorCodeValidation {
    public static boolean validateColorCode(String color) {
        if (color == null) {
            return false;
        }

        Pattern p1 = Pattern.compile("#\\d{6}");
        Pattern p2 = Pattern.compile("#\\d{3}");
        Pattern p3 = Pattern.compile("#(\\d[a-fA-F]){3}");
        Pattern p4 = Pattern.compile("#[a-fA-F]{6}");
        Pattern p5 = Pattern.compile("#[a-fA-F]{3}");

        return Stream.of(p1, p2, p3, p4, p5)
                .anyMatch(pattern -> pattern.matcher(color).matches());
    }
}




