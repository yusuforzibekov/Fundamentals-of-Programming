package edu.epam.fop.lambdas.area;

import static java.lang.StrictMath.signum;

import edu.epam.fop.lambdas.Line;
import edu.epam.fop.lambdas.Point;
import java.util.function.Predicate;

public record HalfPlane(Line line, int sign) implements Area {

  public static HalfPlane onTheLeft(Line line) {
    return new HalfPlane(line, -1);
  }

  public static HalfPlane onTheRight(Line line) {
    return new HalfPlane(line, 1);
  }

  @Override
  public Predicate<Point> test(boolean includeBorders) {
    return point -> {
      var cmp = line.compareTo(point);
      return signum(cmp) == signum(sign)
          || includeBorders && cmp == 0;
    };
  }

  @Override
  public String toString() {
    return String.format("%s[%s]", relation(), line);
  }

  private String relation() {
    return switch (sign) {
      case -1 -> "OnTheLeftOf";
      case 0 -> "OnThe";
      case 1 -> "OnTheRightOf";
      default -> throw new IllegalStateException();
    };
  }
}
