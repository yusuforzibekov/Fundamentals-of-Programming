package com.epam.rd.autotasks.pizzasplit;

import java.util.Scanner;

public class PizzaSplit {
    public static void main(String[] args) {
        // Write a program, reading number of people and number of pieces per pizza and
        // then
        // printing minimum number of pizzas to order to split all the pizzas equally
        // and with no remainder
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input number of people: ");
        int a = scanner.nextInt();
        System.out.print("Please input number of pieces per pizza: ");
        int b = scanner.nextInt();
        int c = 1;
        while ((c * b) % a != 0) {
            c++;
        }
        System.out.println(c);
        scanner.close();
    }
}
