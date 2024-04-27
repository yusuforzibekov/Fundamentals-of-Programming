# Area Checker

The purpose of this task is to practice with the `Predicate` combination.

Duration: _1.5 hours_

## Description

In this task, you're already provided with some primitive `Area` types,
which allows you to describe an area and test if a `Point` is located within this area or not.

You should not change `Area`, `Point`, or `Line` classes or their subtypes.

In the interface `CompositeAreasChecker`, there are several methods you must implement.
The corresponding areas are stored in _src/test/resources_.
Hence, `task1()` must cover the grey areas in _task_1.png_ file.

Take a look at the predefined classes:

### Point

This is Java Record, which represents a point on a plot. It has several convenient static methods:

1. `p(double, double)` simply creates a point; it's a shorter version of `new Point`.
2. `p0()` creates an origin point **(0; 0)**.
3. `subX(Point a, Point b)` subtracts the **X** coordinate of `b` from `a`.
4. `subY(Point a, Point b)` subtracts the **Y** coordinate of `b` from `a`.
5. `length(Point, Point)` calculates the length between two points.

### Line

This is Java Record, which represents a **2D** line on a plot. The line has an orientation,
so you can consider it a vector from `p1` to `p2` as well.
It also has some static methods:

1. `l(Point, Point)` is a shortcut for `new Line(Point, Point)`.
2. `OX()` creates a line that represents the **0X** coordinate line.
3. `OY()` creates a line that represents the **0Y** coordinate line.
4. `XY()` creates a line that passes through the origin point and point **(1; 1)**.
5. `x(double)` creates a line parallel to **0X** on the `y` coordinate.
6. `y(double)` creates a line parallel to **0Y** on the `x` coordinate.

Also, it has one specific method: `compareTo(Point)`. It returns the sign,
which tells us where this point is positioned relative to the line.
So, it'll return **0** if the point is on the line, more than **0** if it's
located in the right half-plane, and less than **0** if in the left half-plane.
Left and right are determined by the line orientation. Therefore:

```java
AB.compareTo(p) == -BA.compareTo(p)
```

### Area

`Area` is the main sealed interface that provides methods for testing is a `Point`
within the area and allows only specific subclasses of it.

As you can see, it's a `Predicate<Point>` by itself. It was made only for convenience.
It also has the method `test(boolean)`, which is implemented by `Area` subtypes.
The `Area` object itself should be used, or `includeBorders()` for a more domain-oriented API,
but you can use `test(boolean)` if you want. By default, `Area` does not include borders, which is equivalent to:

```java
area.test(false).test(point)
```

Also, this interface provides two methods:

1. `lt(T, T, boolean)`
2. `gt(T, T, boolean)`

These are used to compare two objects with a strict flag. The following code shows the similarity between these methods:

```java
lt(a, b, false) == a < b
lt(a, b, true) == a <= b
gt(a, b, false) == a > b
gt(a, b, true) == a >= b
```

Please be aware that you can't implement an `Area` interface
with a non-final class (records are final by nature) and without specifying the implementation in the `permits` clause.

### Circle

The `Circle` class represents a circle on a plot defined by the circle's center and its radius.
It has some utility methods:

1. `static c(Point, double)` is an alias for a constructor.
2. `static unitCircle()` returns a circle whose the center is the origin **(0; 0)**
   and with a radius of **1**.
3. `static pointOn(Circle, double)` returns a `Point` located on a specific circle
   and lies on the specific angle in radians (**0X** is determined as **0** radians).
4. `pointOn(double)` is the same as the static version, only for the current circle.

### HalfPlane

The `HalfPlane` class is a **2D** figure of half of the plane. So, if you have a plane, draw a line, and
it'll divide the plane into two pieces. Each of them can be represented by this class.

It requires a `Line` instance, which is a divider and `sign` that determines
which half must be taken. As mentioned above, `Line` is oriented, so
a positive value indicates the left part, and a negative value indicates the right part.
Of course, you can pass **0**, which means you're interested only in points that belong to the line.
But this won't be used in this task.

This also goes with convenient utility methods:

1. `onTheLeft(Line)` is an alias for `new HalfPlane(Line, -1)`
2. `onTheRight(Line)` is an alias for `new HalfPlane(Line, 1)`

With these, you don't need to worry about the proper values of the `sign` parameter.

### Rectangle

The `Rectangle` class is a rectangle on a plane determined by two points:

1. the left lower point
2. the right upper point

The relative position of the points is not checked, so you have to pass
the arguments properly, or the behavior is undetermined. You can be sure it won't be correct.

It has only one utility method: `r(Point, Point)` which is the alias
to `new Rectangle(Point, Point)`.

### Triangle

The `Triangle` class is a **2D** triangle figure on a plane determined by three points, without any checking
that these points form a triangle.

Has the utility method `t(Point, Point, Point)` which is just an alias for the constructor.

## Requirements

You have to implement methods of the CompositeAreasChecker class.
You can find test cases to cover them in the directory _src/test/resources_. Let's go through them.

You have to combine the predicates using primitive areas to cover the grey areas on the plane
and exclude the white ones.

As you can see, the plane has one dense, opaque grid and a more sparse and less opaque one.
The sparse one is of unit **1**, and the dense one is of unit **0.25**.
The origin point is **(0; 0)**. All the required points are marked on the picture and described here.

### Task 1

Located in _src/test/resources/task_1.png_

Must include areas with their borders.

The center of the circle is **(-0.5; 0.5)**, and its radius is **1.5**.
* A - **(0; 1)**
* B - **(-3; 1)**

### Task 2

Located in _src/test/resources/task_2.png_

Must include areas without their borders.

* A - **(-1; 1.5)**
* B - **(1.5; 3)**
* C - **(2; 2.25)**
* D - **(2; 3)**
* P - **(0.5; 0)**

### Task 3

Located in _src/test/resources/task_3.png_

Must include areas without their borders.

The triangle near point **D** intersects the lower and upper rectangles in the middle of their edges.

* A - **(-2; 2)**
* B - **(-1; 3)**
* C - **(0; 1)**
* D - **(-1; 2)**
* E - **(-2; 1)**
* F - **(0; 3)**
* P - **(-1.5; 0)**

## Examples

```java
Predicate<Point> test = c(p(0, 0), 5).and(c(p(5, 0), 5));
        assert test.test(p(0, 0)) == false
        assert test.test(p(2.5, 0)) == true
```
