package com.epam.rd.autotasks;

class CycleSwap {
    static void cycleSwap(int[] array) {
        cycleSwap(array, 1);
    }

    static void cycleSwap(int[] array, int shift) {
        if (array.length == 0 || shift == array.length) {
            return;
        }
        int[] buf = new int[array.length];
        System.arraycopy(array, array.length - shift, buf, 0, shift);
        System.arraycopy(array, 0, buf, shift, array.length - shift);
        System.arraycopy(buf, 0, array, 0, array.length);
    }
}
