package com.epam.rd.autotasks;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class QuadraticEquation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        double disc = b * b - 4 * a * c; // formula of discriminant;

        double x1;
        double x2;

        if (disc > 0) {
            x1 = (-b + Math.sqrt(disc)) / (2 * a);
            x2 = (-b - Math.sqrt(disc)) / (2 * a);
            System.out.println(x1 + " " + x2);
        } else if (disc == 0) {
            x1 = (-b + disc) / 2 * a;
            System.out.println(x1);
        } else {
            System.out.println("no roots");
        }
    }
}