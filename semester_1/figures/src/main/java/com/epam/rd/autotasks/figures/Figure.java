package com.epam.rd.autotasks.figures;

abstract class Figure{

    public abstract double area();

    public abstract String pointsToString();

    public String toString() {
        if(this.getClass().getSimpleName()!="Circle") {
            return this.getClass().getSimpleName() + "[" + pointsToString() + "]";
        }
        return null;
    }

    public abstract Point leftmostPoint();
}