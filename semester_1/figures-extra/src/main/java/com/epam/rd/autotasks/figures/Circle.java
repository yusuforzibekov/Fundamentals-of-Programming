package com.epam.rd.autotasks.figures;
import java.util.Objects;

public class Circle extends Figure {
    private final Point center;
    private final double radius;
    private static final double ERROR_DELTA = 0.0001;

    Circle (Point center, double radius) {
        if (center == null || radius <= 0) throw new IllegalArgumentException();
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Point centroid() {
        return center;
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (this == figure) return true;
        if (figure == null || getClass() != figure.getClass()) return false;

        Circle circle = (Circle) figure;

        return Math.abs(circle.radius - radius) <= ERROR_DELTA
                && Objects.equals(center, circle.center);
    }
}
