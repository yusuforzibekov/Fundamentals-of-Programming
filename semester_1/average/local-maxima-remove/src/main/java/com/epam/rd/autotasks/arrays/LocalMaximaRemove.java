package com.epam.rd.autotasks.arrays;

import java.util.Arrays;

public class LocalMaximaRemove {

    public static void main(String[] args) {
        int[] array = new int[] { 18, 1, 3, 6, 7, -5 };

        System.out.println(Arrays.toString(removeLocalMaxima(array)));
    }

    public static int[] removeLocalMaxima(int[] array) {
        int[] a = new int[array.length];
        int plus = -1;

        for (int i = 0; i <= array.length - 1; i++) {
            if (i == 0) {
                if (array[0] <= array[1]) {
                    a[0] = array[0];
                    plus++;
                }
            } else if ((i == array.length - 1 && array[i] <= array[i - 1]) ||
                    ((i < array.length - 1) && (array[i] <= array[i - 1] || array[i] <= array[i + 1]))) {
                a[++plus] = array[i];
            }
        }
        if (plus == -1) {
            a = array;
        }
        int[] result = new int[++plus];
        System.arraycopy(a, 0, result, 0, plus);

        return result;
    }
}
