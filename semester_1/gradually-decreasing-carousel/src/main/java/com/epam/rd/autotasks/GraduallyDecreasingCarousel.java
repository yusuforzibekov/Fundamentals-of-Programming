package com.epam.rd.autotasks;

public class GraduallyDecreasingCarousel extends DecrementingCarousel {
    int decreaseCounter = 1;

    public GraduallyDecreasingCarousel(final int capacity) {
        super(capacity);
    }

    @Override
    public int processNext() {
        int original = array[index];

        array[index] = array[index] - decreaseCounter;
        index++;

        return original;
    }

    @Override
    public void resetIndex() {
        if (index == array.length) {
            index = 0;
            decreaseCounter++;
        }
    }
}