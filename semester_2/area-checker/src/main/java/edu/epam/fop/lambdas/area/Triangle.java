package edu.epam.fop.lambdas.area;

import static edu.epam.fop.lambdas.Line.l;
import static edu.epam.fop.lambdas.area.Area.gt;
import static edu.epam.fop.lambdas.area.Area.lt;

import edu.epam.fop.lambdas.Point;
import java.util.function.Predicate;

public record Triangle(Point v1, Point v2, Point v3) implements Area {

  public static Triangle t(Point v1, Point v2, Point v3) {
    return new Triangle(v1, v2, v3);
  }

  @Override
  public Predicate<Point> test(boolean includeBorders) {
    return point -> {
      int d1 = l(v1, v2).compareTo(point);
      int d2 = l(v2, v3).compareTo(point);
      int d3 = l(v3, v1).compareTo(point);
      return lt(d1, 0, includeBorders)
          && lt(d2, 0, includeBorders)
          && lt(d3, 0, includeBorders)
          || gt(d1, 0, includeBorders)
          && gt(d2, 0, includeBorders)
          && gt(d3, 0, includeBorders);
    };
  }

  @Override
  public String toString() {
    return String.format("Triangle[%s; %s; %s]", v1.toString(), v2.toString(), v3.toString());
  }
}
