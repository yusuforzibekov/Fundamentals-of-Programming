package com.epam.rd.autotasks.figures;

import java.util.Arrays;

import static java.lang.Math.abs;

class Quadrilateral extends Figure {
    private final Point A;
    private final Point B;
    private final Point C;
    private final Point D;

    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public Quadrilateral(Point A, Point B, Point C, Point D) {
        if (A == null || B == null || C == null || D == null) throw new IllegalArgumentException("Vertex is null");

        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;

        isDegenerated(A, B, C, D);

        a = new Segment(A, B).length();
        b = new Segment(B, C).length();
        c = new Segment(C, D).length();
        d = new Segment(D, A).length();
    }

    public boolean isDegenerated(Point A, Point B, Point C, Point D) {
        boolean collinear = Point.collinear(A, B, C) || Point.collinear(B, C, D) || Point.collinear(A, D, C) || Point.collinear(A, B, D);

        if (area() <= 0.0 || isNotConvex() || collinear) {
            throw new IllegalArgumentException("Quadrilateral is degenerated");
        }
        return false;
    }

    public boolean isNotConvex() {
        return new Segment(A, C).intersection(new Segment(B, D)) == null;
    }


    public double area() {
        double d1 = new Segment(A, C).length();
        double d2 = new Segment(B, D).length();

        double sinAngle = abs(((C.getY() - A.getY()) * (D.getX() - B.getX()) -
                (D.getY() - B.getY()) * (C.getX() - A.getX())) / (d1 * d2));

        return 0.5 * sinAngle * d1 * d2;
    }

    @Override
    public Point centroid() {
        return calculateCenterOfMass(new Triangle(A, B, C).centroid(), new Triangle(A, C, D).centroid(),
                                     new Triangle(B, C, D).centroid(), new Triangle(A, B, D).centroid());
    }

    private Point calculateCenterOfMass(Point centroid1, Point centroid2, Point centroid3, Point centroid4) {
        return new Segment(centroid1, centroid2).intersection(new Segment(centroid3, centroid4));
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (this == figure) return true;
        if (figure == null || getClass() != figure.getClass()) return false;

        Quadrilateral that = (Quadrilateral) figure;
        return compareEdges(that) && comparePoints(that);
    }

    public boolean compareEdges(Quadrilateral figure) {
        double[] array1 = {this.a, this.b, this.c, this.d};
        double[] array2 = {figure.a, figure.b, figure.c, figure.d};
        Arrays.sort(array1);
        Arrays.sort(array2);

        return Arrays.equals(array1, array2);
    }

    public boolean comparePoints(Quadrilateral figure) {
        Point[] array1 = {this.A, this.B, this.C, this.D};
        Point[] array2 = {figure.A, figure.B, figure.C, figure.D};
        Arrays.sort(array1);
        Arrays.sort(array2);

        return Arrays.equals(array1, array2);
    }
}


