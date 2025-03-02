package com.epam.rd.autotasks;

public class CarouselRun {
    protected final int[] elements;
    protected final int capacity;
    protected int position = 0;

    public CarouselRun(int[] elements, int capacity) {
        this.elements = elements;
        this.capacity = capacity;
    }

    public int next() {
        if (isFinished()) {
            return -1;
        }

        while (elements[position] <= 0) {
            position = (position + 1) % capacity;
        }

        int current = elements[position];
        elements[position]--;
        position = (position + 1) % capacity;
        
        return current;
    }

    public boolean isFinished() {
        for (int i = 0; i < capacity; i++) {
            if (elements[i] > 0) {
                return false;
            }
        }
        return true;
    }
}
