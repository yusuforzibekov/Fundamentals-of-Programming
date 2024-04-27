//packages
package com.epam.rd.autotasks;
import java.util.Scanner;


//class Avarage
public class Average {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i;
        int a = 0;
        int c = 0;
        while ((i = scanner.nextInt()) != 0) {
            a += i;
            c++;
        }

        int result = a / c; //Bu yerda o'rta arifmetik sonni topish uchun
        System.out.println(result);
    }

}