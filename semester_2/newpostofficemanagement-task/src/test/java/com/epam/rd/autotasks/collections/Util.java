package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Util {

    static List<Box> getBoxes(int size) {
        Random r = new Random(15);
        return IntStream.range(0, size).mapToObj(i -> new Box("sender_" + r.nextInt(5),
                        "recipient_" + r.nextInt(5),
                        round(r.nextDouble(1., 25.), 10),
                        round(r.nextDouble(.5, 15.), 100),
                        BigDecimal.valueOf(r.nextDouble(1., 250.)).setScale(2, RoundingMode.HALF_DOWN),
                        "city_" + i,
                        r.nextInt(1, 25)))
                .toList();
    }

    static double round(double d, int precision) {
        return Math.round(d * precision) / (double) precision;
    }

}
