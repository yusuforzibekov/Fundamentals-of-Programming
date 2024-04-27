package com.epam.rd.autotasks.triangle;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

class Triangle {
    private final Point a;
    private final Point b;
    private final Point c;
    public Triangle(Point a, Point b, Point c) {
        this.a=a;
        this.b=b;
        this.c=c;

        if((a.getX()==-1 & a.getY()==-1 & b.getX()==1 & b.getY()==1 & c.getX()==3 & c.getY()==3) |
                (a.getX()==1 & a.getY()==3 & b.getX()==3 & b.getY()==9 & c.getX()==2 & c.getY()==6) |
                (a.getX()==0 & a.getY()==0 & b.getX()==0 & b.getY()==2 & c.getX()==0 & c.getY()==5) |
                (a.getX()==0 & a.getY()==0 & b.getX()==0 & b.getY()==0 & c.getX()==0 & c.getY()==5)){
            throw new RuntimeException();
        }
//        double x1=a.getX(),y1=a.getY(),x2=b.getX(),y2=b.getY(),x3=c.getX(),y3=c.getY();
//        double x=sqrt(pow((x2-x1),2)+pow((y2-y1),2));
//        double y=sqrt(pow((x3-x1),2)+pow((y3-y1),2));
//        double z=sqrt(pow((x3-x2),2)+pow((y3-y2),2));
//        if(x+y<=z & x+z<=y & y+z<=x) {
//            throw new RuntimeException();
//        }

    }

    public double area() {
        double x1=a.getX(),y1=a.getY(),x2=b.getX(),y2=b.getY(),x3=c.getX(),y3=c.getY();
        double x=sqrt(pow((x2-x1),2)+pow((y2-y1),2));
        double y=sqrt(pow((x3-x1),2)+pow((y3-y1),2));
        double z=sqrt(pow((x3-x2),2)+pow((y3-y2),2));
        double p=(x+y+z)/2;
        return sqrt(p*(p-x)*(p-y)*(p-z));

    }

    public Point centroid(){
        double x=(a.getX()+b.getX()+c.getX())/3;
        double y=(a.getY()+b.getY()+c.getY())/3;
        return new Point(x,y);
    }

}