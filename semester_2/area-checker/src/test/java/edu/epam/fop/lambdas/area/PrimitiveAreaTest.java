package edu.epam.fop.lambdas.area;

import static edu.epam.fop.lambdas.Line.OY;
import static edu.epam.fop.lambdas.Point.p;
import static edu.epam.fop.lambdas.Point.p0;
import static edu.epam.fop.lambdas.area.Circle.c;
import static edu.epam.fop.lambdas.area.Circle.unitCircle;
import static edu.epam.fop.lambdas.area.HalfPlane.onTheLeft;
import static edu.epam.fop.lambdas.area.HalfPlane.onTheRight;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import edu.epam.fop.lambdas.Point;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests for predefined/primitive areas")
class PrimitiveAreaTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource({
      "circleCases",
      "halfPlaneCases",
      "rectangleCases",
      "triangleCases",
  })
  @DisplayName("Primitives")
  void test(String name, Area area, Point point, boolean includeBorders, boolean expected) {
    boolean actual = area.test(includeBorders).test(point);
    Assertions.assertEquals(expected, actual);
  }

  static Stream<Arguments> circleCases() {
    return Stream.of(
        testCase(unitCircle(), p0(), false, true),
        testCase(unitCircle(), p(0, 1), false, false),
        testCase(unitCircle(), p(1, 0), false, false),
        testCase(unitCircle(), p(0, -1), false, false),
        testCase(unitCircle(), p(-1, 0), false, false),
        testCase(unitCircle(), unitCircle().pointOn(42), false, false),
        testCase(unitCircle(), p(5, 5), false, false),
        testCase(c(p0(), 5), p(3, 3), false, true),
        testCase(unitCircle(), p0(), true, true),
        testCase(unitCircle(), p(0, 1), true, true),
        testCase(unitCircle(), p(1, 0), true, true),
        testCase(unitCircle(), p(0, -1), true, true),
        testCase(unitCircle(), p(-1, 0), true, true),
        testCase(unitCircle(), unitCircle().pointOn(42), true, true),
        testCase(unitCircle(), p(5, 5), true, false),
        testCase(c(p0(), 5), p(3, 3), true, true)
    );
  }

  static Stream<Arguments> halfPlaneCases() {
    return Stream.of(
        testCase(onTheLeft(OY()), p0(), false, false),
        testCase(onTheLeft(OY()), p(1, 0), false, false),
        testCase(onTheLeft(OY()), p(-1, 0), false, true),
        testCase(onTheRight(OY()), p0(), false, false),
        testCase(onTheRight(OY()), p(1, 0), false, true),
        testCase(onTheRight(OY()), p(-1, 0), false, false),
        testCase(onTheLeft(OY()), p0(), true, true),
        testCase(onTheLeft(OY()), p(1, 0), true, false),
        testCase(onTheLeft(OY()), p(-1, 0), true, true),
        testCase(onTheRight(OY()), p0(), true, true),
        testCase(onTheRight(OY()), p(1, 0), true, true),
        testCase(onTheRight(OY()), p(-1, 0), true, false)
    );
  }

  static Stream<Arguments> rectangleCases() {
    Area rectangle = new Rectangle(p0(), p(5, 5));
    return Stream.of(
        testCase(rectangle, p0(), false, false),
        testCase(rectangle, p(5, 5), false, false),
        testCase(rectangle, p(0, 5), false, false),
        testCase(rectangle, p(5, 0), false, false),
        testCase(rectangle, p(2.5, 2.5), false, true),
        testCase(rectangle, p(5, 3), false, false),
        testCase(rectangle, p(6, 3), false, false),
        testCase(rectangle, p0(), true, true),
        testCase(rectangle, p(5, 5), true, true),
        testCase(rectangle, p(0, 5), true, true),
        testCase(rectangle, p(5, 0), true, true),
        testCase(rectangle, p(2.5, 2.5), true, true),
        testCase(rectangle, p(5, 3), true, true),
        testCase(rectangle, p(6, 3), true, false)
    );
  }

  static Stream<Arguments> triangleCases() {
    Area triangle = new Triangle(p0(), p(0, 1), p(1, 0));
    return Stream.of(
        testCase(triangle, p0(), false, false),
        testCase(triangle, p(0, 1), false, false),
        testCase(triangle, p(1, 0), false, false),
        testCase(triangle, p(1, 1), false, false),
        testCase(triangle, p(0.5, 0.5), false, false),
        testCase(triangle, p0(), true, true),
        testCase(triangle, p(0, 1), true, true),
        testCase(triangle, p(1, 0), true, true),
        testCase(triangle, p(1, 1), true, false),
        testCase(triangle, p(0.5, 0.5), true, true),
        testCase(triangle, p(0.3, 0.3), false, true)
    );
  }

  private static final Map<String, Integer> indices = new ConcurrentHashMap<>();

  private static Arguments testCase(Area area, Point point, boolean includeBorders, boolean expected) {
    var areaName = area.getClass().getSimpleName();
    var index = indices.merge(areaName, 1, Integer::sum);
    String name = String.format("[%s][%d] %s inside %s (with%s borders) must be %B", areaName, index,
        point.toString(), area, includeBorders ? "" : "out", expected);
    return arguments(name, area, point, includeBorders, expected);
  }
}
