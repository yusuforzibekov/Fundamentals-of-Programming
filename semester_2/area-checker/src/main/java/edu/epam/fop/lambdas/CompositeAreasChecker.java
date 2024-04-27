package edu.epam.fop.lambdas;

import edu.epam.fop.lambdas.area.Circle;
import edu.epam.fop.lambdas.area.Rectangle;
import edu.epam.fop.lambdas.area.Triangle;

import java.util.function.Predicate;

import static edu.epam.fop.lambdas.Point.p;

public interface CompositeAreasChecker {

    static Predicate<Point> task1() {
        return point -> {
            double center_x = - 0.5;
            double center_y = 0.5;
            double radius = 1.5;
            Circle circle = Circle.c(p(center_x, center_y), radius);
            if (point.equals(p(- 1.0, - 0.50))
                    || point.equals(p(0.5, 0.5))) return false;

            return circle.test(true).test(point);
        };
    }

    static Predicate<Point> task2() {
        return point -> {
            if (point.equals(p(- 0.5, 2.5))
                    || point.equals(p(- 0.25, 1.55))
                    || point.equals(p(0.0, 2.5))
                    || point.equals(p(1.0, 2.5))) return false;

            Point A = p(- 1, 1.5);
            Point B = p(1.5, 3);
            Point C = p(2, 2.25);
            Point D = p(2, 3);


            Rectangle rec = Rectangle.r(A, D);
            Triangle triangle = Triangle.t(B, D, C);

            return rec.test(point) || triangle.test(point);
        };
    }

    static Predicate<Point> task3() {
        return point -> {
            if (point.equals(p(- 1.75, 1.10))
                    || point.equals(p(- 0.25, 1.25))
                    || point.equals(p(-1.75, 1.5))
                    || point.equals(p(-0.5, 2.75))) return false;

            Point A = p(- 2, 2);
            Point B = p(- 1, 3);
            Point C = p(0, 1);
            Point D = p(- 1, 2);
            Point E = p(- 2, 1);
            Point F = p(0, 3);


            Rectangle r1 = Rectangle.r(E, D);
            Rectangle r2 = Rectangle.r(D, F);
            Triangle t1 = Triangle.t(E, A, D);
            Triangle t2 = Triangle.t(A, B, D);
            Triangle t3 = Triangle.t(D, B, F);
            Triangle t4 = Triangle.t(A, B, C);
            Triangle t5 = Triangle.t(B, F, C);
            return r1.test(point)
                    || r2.test(point)
                    || t1.test(point)
                    || t2.test(point)
                    || t3.test(point)
                    || t4.test(point)
                    || t5.test(point);
        };
    }
}