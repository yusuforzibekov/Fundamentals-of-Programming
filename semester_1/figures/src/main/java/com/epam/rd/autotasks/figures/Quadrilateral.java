package com.epam.rd.autotasks.figures;

import static java.lang.Math.*;

class Quadrilateral extends Figure {
    private final Point a;
    private final Point b;
    private final Point c;
    private final Point d;

    public Quadrilateral(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double area() {
        double x1 = a.getX(), y1 = a.getY(), x2 = b.getX(), y2 = b.getY(), x3 = c.getX(), y3 = c.getY(),x4=d.getX(), y4=d.getY();
        //1/2 {(x 1y2 + x 2y3 + x 3y4  + x 4y1 ) − (x 2y1 + x3y2 + x4y3 + x1y4 )}
        return abs((x1*y2+x2*y3+x3*y4+x4*y1) - (x2*y1+x3*y2+x4*y3+x1*y4))/2;
    }
    public String pointsToString(){
        return "("+a.getX()+','+a.getY()+")"+"("+b.getX()+','+b.getY()+")"+"("+c.getX()+','+c.getY()+")"+"("+d.getX()+','+d.getY()+")";
    }
    public Point leftmostPoint(){
        double x1 = a.getX(),  x2 = b.getX(), x3 = c.getX(), x4=d.getX();
        if(x1<x2 & x1<x3 & x1<x4){
            return a;
        }
        else if(x2<x1 & x2<x3 & x2<x4){
            return b;
        }
        else if(x3<x1 & x3<x2 & x3<x4){
            return c;
        }
        else return d;
    }
}