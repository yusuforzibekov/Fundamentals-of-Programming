package com.epam.rd.autotasks;

public class CarouselRun {
    private final int[] elements;
    private final int size;
    private int position;

    public CarouselRun(int[] elements, int size) {
        this.elements = elements;
        this.size = size;
        this.position = 0;
    }

    public int next() {
        if (isFinished()) {
            return -1;
        }

        while (elements[position] <= 0) {
            position = (position + 1) % size;
        }

        int current = elements[position];
        elements[position]--;
        position = (position + 1) % size;
        return current;
    }

    public boolean isFinished() {
        for (int i = 0; i < size; i++) {
            if (elements[i] > 0) {
                return false;
            }
        }
        return true;
    }
}
