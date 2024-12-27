package com.epam.rd.autotasks;

public class DecrementingCarousel {
    private final int[] elements;
    private int capacity;
    private int insertPosition;
    private boolean isRunning;

    public DecrementingCarousel(int capacity) {
        this.capacity = capacity;
        this.elements = new int[capacity];
        this.insertPosition = 0;
        this.isRunning = false;
    }

    public boolean addElement(int element) {
        if (isRunning || element <= 0 || insertPosition >= capacity) {
            return false;
        }
        elements[insertPosition++] = element;
        return true;
    }

    public CarouselRun run() {
        if (isRunning) {
            return null;
        }
        isRunning = true;
        return new CarouselRun(elements, insertPosition);
    }
}
