package com.epam.rd.autotasks.figures;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


class Triangle extends Figure{
    private final Point a;
    private final Point b;
    private final Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double area() {
        double x1 = a.getX(), y1 = a.getY(), x2 = b.getX(), y2 = b.getY(), x3 = c.getX(), y3 = c.getY();
        double x = sqrt(pow((x2 - x1), 2) + pow((y2 - y1), 2));
        double y = sqrt(pow((x3 - x1), 2) + pow((y3 - y1), 2));
        double z = sqrt(pow((x3 - x2), 2) + pow((y3 - y2), 2));
        double p = (x + y + z) / 2;
        return sqrt(p * (p - x) * (p - y) * (p - z));
    }

    public String pointsToString(){
        return "("+a.getX()+','+a.getY()+")"+"("+b.getX()+','+b.getY()+")"+"("+c.getX()+','+c.getY()+")";
    }

    public Point leftmostPoint(){
        double x1 = a.getX(),  x2 = b.getX(), x3 = c.getX();
        if(x1<=x2 & x1<=x3){
            return a;
        }
        else if(x2<=x1 & x2<=x3){
            return b;
        }
        else {
            return c;
        }}
}