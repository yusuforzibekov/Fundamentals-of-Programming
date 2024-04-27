package com.epam.rd.autotasks.godutch;

import java.util.Scanner;

public class GoDutch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int bill = sc.nextInt();
        int friends = sc.nextInt();
        int mixBill;
        if (bill < 0) {
            System.out.println("Bill total amount cannot be negative");
        } else if (friends <= 0) {
            System.out.println("Number of friends cannot be negative or zero");
        } else {
            int result = bill + (bill / 100) * 10;
            mixBill = result / friends;
            System.out.println(mixBill);
        }
        sc.close();
    }
}