package com.epam.rd.autotasks.meetautocode;

import java.util.Scanner;

public class ElectronicWatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int seconds = scanner.nextInt();

        int hours = seconds / 3600;
        int min = (seconds % 3600) / 60;
        int sec = seconds % 60;
        String h1 = (hours < 24) ? "" + hours : (hours == 24) ? "0" : Integer.toString(hours);
        System.out.printf(h1 + ":" + "%02d:%02d", min, sec);
    }
}