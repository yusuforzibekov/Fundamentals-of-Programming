package com.epam.rd.autotasks.figures;

import static java.lang.Math.PI;
class Circle extends Figure{
    private Point a;
    private double r;

    public Circle(Point a, double r) {
        this.a = a;
        this.r = r;
    }

    public double area() {
        return PI*r*r;
    }

    public String pointsToString(){
        return "("+a.getX()+','+a.getY()+")";
    }
    public Point leftmostPoint(){
        return new Point(a.getX()-r,a.getY());
    }
    public String toString(){
        return this.getClass().getSimpleName() + "[" + pointsToString() + r+"]";
    }
}