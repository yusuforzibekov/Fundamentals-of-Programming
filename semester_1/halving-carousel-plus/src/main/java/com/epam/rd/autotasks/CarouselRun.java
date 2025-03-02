package com.epam.rd.autotasks;

public class CarouselRun {
    protected int[] elements;
    protected int position = 0;

    public CarouselRun(int[] elements) {
        this.elements = elements;
    }
    
    public int next() {
        if (isFinished()) {
            return -1;
        }

        while (elements[position] <= 0) {
            position = (position + 1) % elements.length;
        }

        int current = elements[position];
        elements[position] = decrement(elements[position]);
        position = (position + 1) % elements.length;
        
        return current;
    }

    public boolean isFinished() {
        for (int element : elements) {
            if (element > 0) {
                return false;
            }
        }
        return true;
    }
    
    protected int decrement(int element) {
        return element - 1;
    }
}
