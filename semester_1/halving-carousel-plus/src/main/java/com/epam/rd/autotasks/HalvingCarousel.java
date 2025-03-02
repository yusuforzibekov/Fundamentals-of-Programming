package com.epam.rd.autotasks;

public class HalvingCarousel extends DecrementingCarousel {
    public HalvingCarousel(int capacity) {
        super(capacity);
    }
    
    @Override
    protected CarouselRun createCarouselRun() {
        return new HalvingCarouselRun(elements.clone());
    }
}
