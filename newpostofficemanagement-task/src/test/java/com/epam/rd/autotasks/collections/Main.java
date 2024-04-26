package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        // This class is not under tests
        // You can use this class for your own purposes

        NewPostOfficeManagement office = new NewPostOfficeManagementImpl(Util.getBoxes(5));
        System.out.println(office);
        IntStream.range(-1, 7)
                .forEach(id ->
                        System.out.printf("id: %s, box: %s%n", id, office.getBoxById(id)));
        System.out.println("getDescSortedBoxesByWeight()");
        System.out.println(office.getDescSortedBoxesByWeight());
        System.out.println("getAscSortedBoxesByCost()");
        System.out.println(office.getAscSortedBoxesByCost());
        System.out.println("getBoxesByRecipient()");
        System.out.println(office.getBoxesByRecipient("recipient_2"));
    }
}
