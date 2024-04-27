package com.epam.rd.autotasks.figures;

class Point implements Comparable {
    private final double x;
    private final double y;

    private static final double ERROR_DELTA = 0.0001;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static boolean collinear(Point a, Point b, Point c) {
        return 0 == a.x * (b.y - c.y) +
                b.x * (c.y - a.y) +
                c.x * (a.y - b.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;
        return Math.abs(point.getX() - getX()) <= ERROR_DELTA
                && Math.abs(point.getY() - getY()) <= ERROR_DELTA;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Point p = (Point) o;

        double result = this.x - p.x;
        if (result == 0) {
            result = this.y - p.y;
        }
        return (int) result;
    }
}
