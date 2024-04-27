package com.epam.rd.autotasks;
import java.util.Arrays;

public class DecrementingCarousel {
    int[] array;
    boolean isCarouselRunning = false;
    int index = 0;

    public DecrementingCarousel(int capacity) {
        array = new int[capacity];
    }

    public boolean addElement(int element) {
        if (element <= 0 || array[array.length - 1] != 0 || isCarouselRunning) {
            return false;
        }
        array[index++] = element;
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

        while (!isAllZero()) {
            if (index == array.length) index = 0;

            if (array[index] > 0) return processNext();

            if (array[index] == 0) index++;
        }

        return -1;
    }

    public int processNext() {
        int original = array[index]--;
        index++;

        return original;
    }

    public boolean isAllZero() {
        return Arrays.stream(array).noneMatch(j -> j != 0);
    }

    public boolean isFinished() {
        return array.length == 0 || isAllZero();
    }
}