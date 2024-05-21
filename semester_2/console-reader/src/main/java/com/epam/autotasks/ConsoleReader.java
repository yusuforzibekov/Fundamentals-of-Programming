package com.epam.autotasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class ConsoleReader {

    public static void readNames() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        try {
            while (true) {
                String name = reader.readLine().trim();
                if (name.equals("0")) {
                    break;
                }
                if (!Pattern.matches("^[a-zA-Z,.'\\-\\s]+$", name)) {
                    throw new RuntimeException("Invalid character in name");
                }
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Number of names: " + count);
    }

    public static void readNumbers() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = reader.readLine();
            if (!Pattern.matches("^[0-9\\s]+$", line)) {
                throw new RuntimeException("Invalid character in numbers");
            }
            String[] numbers = line.split("\\s+");
            int sum = 0;
            for (String number : numbers) {
                sum += Integer.parseInt(number);
            }
            System.out.println("Numbers: " + line);
            System.out.println("Sum: " + sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}