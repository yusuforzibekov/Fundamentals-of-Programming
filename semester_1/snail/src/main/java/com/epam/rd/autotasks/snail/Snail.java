package com.epam.rd.autotasks.snail;

import java.util.Scanner;

public class Snail {
    public static void main(String[] args) {
        // Write a program that reads a,b and h (line by lyne in this order) and prints
        // the number of days for which the snail reach the top of the tree.
        // a - feet that snail travels up each day, b - feet that slides down each
        // night, h - height of the tree
        Scanner sc = new Scanner(System.in);
        int above = sc.nextInt();
        int below = sc.nextInt();
        int tree = sc.nextInt();

        int day = 1;
        int i = 0;

        if (above <= below && above < tree) {
            System.out.println("Impossible");
        } else {
            while (i < tree) {
                i += above;
                if (i >= tree) {
                    System.out.println(day);
                    break;
                } else {
                    day++;
                    i -= below;
                }
            }
        }
        sc.close();
    }
}
