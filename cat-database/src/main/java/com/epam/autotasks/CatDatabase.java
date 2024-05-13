package com.epam.autotasks;

import java.util.List;
import java.util.stream.Collectors;

public class CatDatabase {

    public List<Cat> filterCatsByNamePrefix(List<Cat> cats, String prefix) {

        return cats.stream()
                .filter(cat -> {
                    if (cat.getName().length() != 0) {
                        return String.valueOf(cat.getName().charAt(0)).equalsIgnoreCase(prefix);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<String> getCatNamesByBreed(List<Cat> cats, Cat.Breed breed) {
        return cats.stream()
                .filter(cat -> cat.getBreed() == breed)
                .map(Cat::getName)
                .collect(Collectors.toList());
    }

    public List<Cat> filterYoungerCatsByAge(List<Cat> cats, Integer age) {
        return cats.stream()
                .filter(cat -> cat.getAge() <= age)
                .collect(Collectors.toList());
    }
}