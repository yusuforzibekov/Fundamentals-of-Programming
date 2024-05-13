package com.epam.autotasks;

import java.util.ArrayList;
import java.util.List;

public class CatGenerator {

    public static List<Cat> generateCats(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Input arguments cannot be negative");
        }

        List<Cat> cats = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Cat cat = Cat.builder()
                    .name("Cat " + (i + 1))
                    .age(i)
                    .breed(Cat.Breed.values()[i % Cat.Breed.values().length])
                    .build();
            cats.add(cat);
        }

        return cats;
    }

    public static long generateFood(int familySize, int skip) {
        if (familySize < 0 || skip < 0) {
            throw new IllegalArgumentException("Input arguments cannot be negative");
        }

        if (skip > familySize) {
            return 0;
        }

        long totalFood = 0;
        long portion = 4;


        for (int i = 0; i < familySize; i++) {
            if (i < skip) {
                portion *= 2;
                if (portion > Long.MAX_VALUE / 2 ) {
                    throw new ArithmeticException();
                }
                continue;
            }
            totalFood += portion;

            if (totalFood > Long.MAX_VALUE / 2 ) {
                throw new ArithmeticException();
            }
            portion *= 2;
        }
        return totalFood;
    }
}