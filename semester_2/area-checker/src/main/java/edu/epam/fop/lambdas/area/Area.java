package edu.epam.fop.lambdas.area;

import edu.epam.fop.lambdas.Point;
import java.util.function.Predicate;

public sealed interface Area extends Predicate<Point>
    permits Circle, Triangle, Rectangle, HalfPlane {

  Predicate<Point> test(boolean includeBorders);

  default Predicate<Point> includeBorders() {
    return test(true);
  }

  @Override
  default boolean test(Point point) {
    return test(false).test(point);
  }

  static <T extends Comparable<T>> boolean gt(T a, T b, boolean inclusive) {
    var cmp = a.compareTo(b);
    return inclusive ? cmp >= 0 : cmp > 0;
  }

  static <T extends Comparable<T>> boolean lt(T a, T b, boolean inclusive) {
    var cmp = a.compareTo(b);
    return inclusive ? cmp <= 0 : cmp < 0;
  }
}
