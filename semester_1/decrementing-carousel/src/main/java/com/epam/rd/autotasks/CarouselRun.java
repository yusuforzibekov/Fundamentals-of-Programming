package com.epam.rd.autotasks;

public class CarouselRun {
    private final DecrementingCarousel carousel;

    public CarouselRun(DecrementingCarousel carousel) {
        this.carousel = carousel;
    }

    public int next() {
        return carousel.next();
    }

    public boolean isFinished() {
        return carousel.isFinished();
    }
}