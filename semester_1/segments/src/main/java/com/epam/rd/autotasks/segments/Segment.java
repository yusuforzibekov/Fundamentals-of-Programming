package com.epam.rd.autotasks.segments;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

class Segment {

    private final Point start;
    private final Point end;

    public Segment(Point start, Point end) {
        if (start.equals(end) || end == null) {
            throw new IllegalArgumentException("The segment is degenerate");
        }
        this.start = start;
        this.end = end;
    }

    double length() {
        return sqrt(pow((this.end.getX() - this.start.getX()), 2) +
                pow((this.end.getY() - this.start.getY()), 2));
    }

    Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2,
                (this.start.getY()+ this.end.getY()) / 2);
    }

    Point intersection(Segment another) {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        double x3 = another.start.getX();
        double y3 = another.start.getY();
        double x4 = another.end.getX();
        double y4 = another.end.getY();

        double divider = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        double t = ((x1 - x3)*(y3 - y4) - (y1 - y3)*(x3 - x4)) / divider;
        double u = ((x1 - x3)*(y1 - y2) - (y1 - y3)*(x1 - x2)) / divider;

        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            return new Point((x1 + t*(x2 - x1)), (y1 + t*(y2 - y1)));
        }
        return null;
    }
}