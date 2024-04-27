package edu.epam.fop.lambdas;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

public record Point(double x, double y) {

  public static Point p(double x, double y) {
    return new Point(x, y);
  }

  public static Point p0() {
    return p(0, 0);
  }

  public static double subX(Point a, Point b) {
    return a.x - b.x;
  }

  public static double subY(Point a, Point b) {
    return a.y - b.y;
  }

  public static double length(Point a, Point b) {
    return sqrt(pow(subX(a, b), 2) + pow(subY(a, b), 2));
  }

  @Override
  public String toString() {
    return String.format("P[%.02f; %.02f]", x, y);
  }
}
