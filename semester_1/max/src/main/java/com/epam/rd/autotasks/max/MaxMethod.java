package com.epam.rd.autotasks.max;

public class MaxMethod {
    public static int max(int[] values) {

        int katta;
        katta = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > katta) {
                katta = values[i];
            }
        }
        return katta;
    }
}