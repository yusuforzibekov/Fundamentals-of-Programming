package com.epam.rd.autotasks;

public class DecrementingCarouselWithLimitedRun extends DecrementingCarousel {
    int actionLimit;

    public DecrementingCarouselWithLimitedRun(final int capacity, final int actionLimit) {
        super(capacity);
        this.actionLimit = actionLimit;
    }

    public DecrementingCarouselWithLimitedRun(int capacity) {
        super(capacity);
    }

    @Override
    public int processNext() {
        if (actionLimit > 0) {
            actionLimit--;
            return super.processNext();
        }
        return -1;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || actionLimit == 0;
    }
}