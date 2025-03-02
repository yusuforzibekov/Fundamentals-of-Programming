package com.epam.rd.autotasks;

public class DecrementingCarousel {
    private final int capacity;
    protected int[] elements;
    protected int filledCount = 0;
    protected boolean isRunning = false;

    public DecrementingCarousel(int capacity) {
        this.capacity = capacity;
        this.elements = new int[capacity];
    }

    public boolean addElement(int element) {
        if (element <= 0 || filledCount >= capacity || isRunning) {
            return false;
        }
        elements[filledCount++] = element;
        return true;
    }

    public CarouselRun run() {
        if (isRunning) {
            return null;
        }
        isRunning = true;
        return new CarouselRun(elements, filledCount);
    }
}
