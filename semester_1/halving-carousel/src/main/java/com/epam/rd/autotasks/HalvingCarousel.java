package com.epam.rd.autotasks;

public class HalvingCarousel extends DecrementingCarousel {
    public HalvingCarousel(final int capacity) {
        super(capacity);
    }

    @Override
    public int processNext() {
        int original = array[index];

        array[index] = original / 2;
        index++;

        return original;
    }
}
