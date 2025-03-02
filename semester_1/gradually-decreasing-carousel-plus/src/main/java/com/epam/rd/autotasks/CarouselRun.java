package com.epam.rd.autotasks;

public class CarouselRun {
    protected int[] elements;
    protected int position = 0;
    protected boolean isRunning = true;

    public CarouselRun(int[] elements) {
        this.elements = elements.clone();
    }

    public int next() {
        if (isFinished()) {
            return -1;
        }

        while (elements[position] <= 0) {
            position = (position + 1) % elements.length;
        }

        int current = elements[position];
        elements[position]--;
        position = (position + 1) % elements.length;

        if (isFinished()) {
            isRunning = false;
        }

        return current;
    }

    public boolean isFinished() {
        if (!isRunning) {
            return true;
        }

        for (int elem : elements) {
            if (elem > 0) {
                return false;
            }
        }
        return true;
    }
}
