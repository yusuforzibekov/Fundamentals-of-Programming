package com.epam.rd.autotasks;

public class DecrementingCarousel {
    int[] array;
    boolean isCarouselRunning = false;
    int insertionIndex = 0;
    int index = 0;

    public DecrementingCarousel(int capacity) {
        array = new int[capacity];
    }

    public boolean addElement(int element) {
        if (element <= 0 || array[array.length - 1] != 0 || isCarouselRunning) {
            return false;
        }
        array[insertionIndex++] = element;
        return true;
    }

    public CarouselRun run() {
        if (isCarouselRunning) {
            return null;
        }

        isCarouselRunning = true;
        return new CarouselRun(this);
    }

    public int next() {
        if (isFinished()) return -1;

        while (!isAllZeroOrNegative()) {
            resetIndex();

            if (array[index] > 0) return processNext();

            if (array[index] <= 0) index++;
        }
        return -1;
    }

    public void resetIndex () {
        if (index == array.length) index = 0;
    }

    public int processNext() {
        int original = array[index]--;
        index++;

        return original;
    }

    public boolean isAllZeroOrNegative() {
        for (int j : array) {
            if (j > 0)
                return false;
        }
        return true;
    }

    public boolean isFinished() {
        return array.length == 0 || isAllZeroOrNegative();
    }
}