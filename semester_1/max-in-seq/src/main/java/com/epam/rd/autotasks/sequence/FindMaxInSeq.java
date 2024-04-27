package com.epam.rd.autotasks.sequence;

import java.util.Scanner;

public class FindMaxInSeq {
    
    public static int max() {
        Scanner sc = new Scanner(System.in);
        int maks = Integer.MIN_VALUE;
        int a;

        while ((a = sc.nextInt()) != 0) {
            if (a > maks) {
                maks = a;
            }
        }
        return maks;
    }

    public static void main(String[] args) {
        System.out.println(max());
    }
}
