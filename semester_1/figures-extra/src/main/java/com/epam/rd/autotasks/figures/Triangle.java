package com.epam.rd.autotasks.figures;

import java.util.Objects;

class Triangle extends Figure {
    private final Point A;
    private final Point B;
    private final Point C;
    private final double a;
    private final double b;
    private final double c;
    private static final double ERROR_DELTA = 0.0001;


    public Triangle(Point A, Point B, Point C) {
        if (A == null || B == null || C == null) throw new IllegalArgumentException("Triangle is degenerate");

        a = new Segment(B, C).length();
        b = new Segment(A, C).length();
        c = new Segment(A, B).length();

        if (a < b + c - ERROR_DELTA && b < c + a - ERROR_DELTA && c < a + b - ERROR_DELTA) {
            this.A = A;
            this.B = B;
            this.C = C;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Point centroid() {
        double x = (this.A.getX() + this.B.getX() + this.C.getX()) / 3;
        double y = (this.A.getY() + this.B.getY() + this.C.getY()) / 3;

        return new Point(x, y);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (this == figure) return true;
        if (figure == null || getClass() != figure.getClass()) return false;

        Triangle triangle = (Triangle) figure;

        return this.a != triangle.a + ERROR_DELTA
                && this.b != triangle.b + ERROR_DELTA
                && this.c != triangle.c + ERROR_DELTA
                && !Objects.equals(A, triangle.A)
                && !Objects.equals(B, triangle.B)
                && Objects.equals(C, triangle.C);
    }
}
