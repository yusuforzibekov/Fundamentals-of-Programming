package edu.epam.fop.lambdas.area;

import static edu.epam.fop.lambdas.area.Area.lt;

import edu.epam.fop.lambdas.Point;
import java.util.function.Predicate;

public record Rectangle(Point leftLower, Point rightUpper) implements Area {

  public static Rectangle r(Point leftLower, Point rightUpper) {
    return new Rectangle(leftLower, rightUpper);
  }

  @Override
  public Predicate<Point> test(boolean includeBorders) {
    return point -> lt(leftLower.x(), point.x(), includeBorders)
        && lt(leftLower.y(), point.y(), includeBorders)
        && lt(point.x(), rightUpper.x(), includeBorders)
        && lt(point.y(), rightUpper.y(), includeBorders);
  }

  @Override
  public String toString() {
    return String.format("Rectangle[LL=%s; RU=%s]", leftLower.toString(), rightUpper.toString());
  }
}
