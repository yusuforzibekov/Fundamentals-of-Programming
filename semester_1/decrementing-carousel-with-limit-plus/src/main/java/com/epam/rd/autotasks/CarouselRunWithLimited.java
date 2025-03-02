package com.epam.rd.autotasks;

public class CarouselRunWithLimited extends CarouselRun {
    private final int actionLimit;
    private int actionCount = 0;

    public CarouselRunWithLimited(int[] elements, int capacity, int actionLimit) {
        super(elements, capacity);
        this.actionLimit = actionLimit;
    }

    @Override
    public int next() {
        if (isFinished()) {
            return -1;
        }

        int current = super.next();
        actionCount++;
        return current;
    }

    @Override
    public boolean isFinished() {
        return actionCount >= actionLimit || super.isFinished();
    }
}
