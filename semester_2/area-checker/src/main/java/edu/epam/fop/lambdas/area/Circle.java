package edu.epam.fop.lambdas.area;

import static edu.epam.fop.lambdas.Point.length;
import static edu.epam.fop.lambdas.Point.p;
import static edu.epam.fop.lambdas.Point.p0;
import static edu.epam.fop.lambdas.area.Area.lt;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

import edu.epam.fop.lambdas.Point;
import java.util.function.Predicate;

public record Circle(Point center, double radius) implements Area {

  public static Circle c(Point center, double radius) {
    return new Circle(center, radius);
  }

  public static Circle unitCircle() {
    return c(p0(), 1);
  }

  public static Point pointOn(Circle circle, double angle) {
    return p(circle.center.x() + circle.radius * cos(angle), circle.center.y() + circle.radius * sin(angle));
  }

  public Point pointOn(double angle) {
    return pointOn(this, angle);
  }

  @Override
  public Predicate<Point> test(boolean includeBorders) {
    return point -> lt(length(center, point), radius, includeBorders);
  }

  @Override
  public String toString() {
    return String.format("Circle[center=%s; R=%.2f]", center.toString(), radius);
  }
}
