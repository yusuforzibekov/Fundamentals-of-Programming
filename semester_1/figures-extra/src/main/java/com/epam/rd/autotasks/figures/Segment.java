package com.epam.rd.autotasks.figures;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Segment {
    private final Point beginning;
    private final Point end;

    public Segment (Point point1, Point point2) {
        if (point1.equals(point2) || point1 == null || point2 == null) {
            throw new IllegalArgumentException("The segment is degenerate");
        }
        this.beginning = point1;
        this.end = point2;
    }

    public double length () {
        double length = sqrt(pow((this.end.getX() - this.beginning.getX()), 2) + pow((this.end.getY() - this.beginning.getY()), 2));
        return length;
    }

    public Point intersection(Segment another) {
        double x1 = this.beginning.getX();
        double y1 = this.beginning.getY();
        double  x2 = this.end.getX();
        double  y2 = this.end.getY();

        double x3 = another.beginning.getX();
        double y3 = another.beginning.getY();
        double x4 = another.end.getX();
        double y4 = another.end.getY();

        double divider = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        double t = (double) ((x1 - x3)*(y3 - y4) - (y1 - y3)*(x3 - x4)) / divider;
        double u = (double) ((x1 - x3)*(y1 - y2) - (y1 - y3)*(x1 - x2)) / divider;

        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            return new Point((x1 + t*(x2 - x1)), (y1 + t*(y2 - y1)));
        }
        else {return null;}
    }
}
