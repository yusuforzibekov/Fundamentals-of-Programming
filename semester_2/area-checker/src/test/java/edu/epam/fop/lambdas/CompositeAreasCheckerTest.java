package edu.epam.fop.lambdas;

import static edu.epam.fop.lambdas.CompositeAreasChecker.task1;
import static edu.epam.fop.lambdas.CompositeAreasChecker.task2;
import static edu.epam.fop.lambdas.CompositeAreasChecker.task3;
import static edu.epam.fop.lambdas.Point.p;
import static edu.epam.fop.lambdas.Point.p0;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests for composite areas")
class CompositeAreasCheckerTest {

  @DisplayName("Test for Task #1")
  @ParameterizedTest(name = "{0}")
  @MethodSource
  void task1Test(String name, Point point, boolean expected) {
    var actual = task1().test(point);
    assertEquals(expected, actual);
  }

  static Stream<Arguments> task1Test() {
    return Stream.of(
        testCase(p0(), true),
        testCase(p(-0.5, 0.5), true),
        testCase(p(0, 1), true),
        testCase(p(-3, -1), false),
        testCase(p(-1, -1), false),
        testCase(p(-5, 5), false),
        testCase(p(-1, -0.5), false),
        testCase(p(-1, -5), false),
        testCase(p(-10, -1), false),
        testCase(p(-1.5, 0), true),
        testCase(p(-1.75, 0), true),
        testCase(p(1, -1), false),
        testCase(p(0.5, -0.5), true),
        testCase(p(2, 1), false),
        testCase(p(1, 2), false),
        testCase(p(0.5, 0.5), false),
        testCase(p(0.25, 1.5), true)
    );
  }

  @DisplayName("Test for Task #2")
  @ParameterizedTest(name = "{0}")
  @MethodSource
  void task2Test(String name, Point point, boolean expected) {
    var actual = task2().test(point);
    assertEquals(expected, actual);
  }

  static Stream<Arguments> task2Test() {
    return Stream.of(
        testCase(p0(), false),
        testCase(p(-1, -1), false),
        testCase(p(1, -1), false),
        testCase(p(1, 1), false),
        testCase(p(-1, 1), false),
        testCase(p(0.25, 1), false),
        testCase(p(0.5, 0), false),
        testCase(p(-1, 1.5), false),
        testCase(p(-0.5, 2.5), false),
        testCase(p(-0.25, 1.55), false),
        testCase(p(0, 1.8), true),
        testCase(p(0, 2.5), false),
        testCase(p(1, 2.5), false),
        testCase(p(1, 1.75), true),
        testCase(p(0.75, 2.75), true),
        testCase(p(1.75, 2.75), true),
        testCase(p(1.5, 3), false),
        testCase(p(2, 2.25), false),
        testCase(p(2, 3), false)
    );
  }

  @DisplayName("Test for Task #3")
  @ParameterizedTest(name = "{0}")
  @MethodSource
  void task3Test(String name, Point point, boolean expected) {
    var actual = task3().test(point);
    assertEquals(expected, actual);
  }

  static Stream<Arguments> task3Test() {
    return Stream.of(
        testCase(p0(), false),
        testCase(p(-1, 1), false),
        testCase(p(-0.5, 0.5), false),
        testCase(p(-2, 0.5), false),
        testCase(p(-5, 2), false),
        testCase(p(-1.5, 1), false),
        testCase(p(-1.75, 1.1), false),
        testCase(p(-1.25, 1.1), true),
        testCase(p(-0.75, 1.1), false),
        testCase(p(-0.25, 1.25), false),
        testCase(p(-1.75, 1.5), false),
        testCase(p(-1.75, 2), true),
        testCase(p(-1.25, 2.25), true),
        testCase(p(-0.5, 2.75), false),
        testCase(p(-0.25, 2.5), true),
        testCase(p(-1.15, 2.75), true),
        testCase(p(-1.65, 2.15), true),
        testCase(p(-0.85, 2.65), true),
        testCase(p(-0.6, 2.47), true),
        testCase(p(-0.6, 2.3), true),
        testCase(p(-1.25, 1.6), true),
        testCase(p(-0.75, 1.75), true)
    );
  }

  private static Arguments testCase(Point point, boolean expected) {
    String name = String.format("%s must %s", point.toString(), expected ? "be within" : "not be within");
    return arguments(name, point, expected);
  }
}
