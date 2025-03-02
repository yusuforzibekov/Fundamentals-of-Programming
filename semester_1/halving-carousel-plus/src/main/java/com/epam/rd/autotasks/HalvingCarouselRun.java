package com.epam.rd.autotasks;

public class HalvingCarouselRun extends CarouselRun {
    public HalvingCarouselRun(int[] elements) {
        super(elements);
    }
    
    @Override
    protected int decrement(int element) {
        return element / 2;
    }
}
