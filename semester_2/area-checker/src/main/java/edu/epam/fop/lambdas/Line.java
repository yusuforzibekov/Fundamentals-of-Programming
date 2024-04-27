package edu.epam.fop.lambdas;

import static edu.epam.fop.lambdas.Point.p;
import static edu.epam.fop.lambdas.Point.p0;
import static edu.epam.fop.lambdas.Point.subX;
import static edu.epam.fop.lambdas.Point.subY;
import static java.lang.StrictMath.signum;

public record Line(Point p1, Point p2) implements Comparable<Point> {

  public static Line l(Point p1, Point p2) {
    return new Line(p1, p2);
  }

  public static Line OX() {
    return l(p0(), p(1, 0));
  }

  public static Line OY() {
    return l(p0(), p(0, 1));
  }

  public static Line XY() {
    return l(p0(), p(1, 1));
  }

  public static Line x(double y) {
    return l(p(0, y), p(1, y));
  }

  public static Line y(double x) {
    return l(p(x, 0), p(x, 1));
  }

  /**
   * Calculates the sign of the half-plane where Point {@code p} is located relatively to this line.
   * <p/>
   * Returns:
   * <ul>
   *   <li>0 - {@code p} is on the line</li>
   *   <li>< 0 - {@code p} is on the left half-plane</li>
   *   <li>> 0 - {@code p} is on the right half-plane</li>
   * </ul>
   * <p/>
   * Be aware that the order of the line's points matters in this method,
   * because they're used in vector arithmetic.
   *
   * @param p point to check
   * @return sign of the relative to the line position of the point
   */
  @Override
  public int compareTo(Point p) {
    return (int) signum(subX(p, p1) * subY(p2, p1) - subY(p, p1) * subX(p2, p1));
  }

  @Override
  public String toString() {
    return String.format("Line[%s - %s]", p1.toString(), p2.toString());
  }
}
