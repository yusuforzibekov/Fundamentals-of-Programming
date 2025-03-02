package com.epam.rd.autotasks;

public class GraduallyDecreasingCarouselRun extends CarouselRun {
    private int[] decrements;
    
    public GraduallyDecreasingCarouselRun(int[] elements) {
        super(elements);
        this.decrements = new int[elements.length];
        for (int i = 0; i < decrements.length; i++) {
            decrements[i] = 1;
        }
    }
    
    @Override
    public int next() {
        if (isFinished()) {
            return -1;
        }

        while (elements[position] <= 0) {
            position = (position + 1) % elements.length;
        }

        int current = elements[position];
        elements[position] -= decrements[position];
        decrements[position]++;
        position = (position + 1) % elements.length;

        if (isFinished()) {
            isRunning = false;
        }

        return current;
    }
}
