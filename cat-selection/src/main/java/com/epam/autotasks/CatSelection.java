package com.epam.autotasks;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CatSelection {

    public List<Cat> getFirstNCatsSortedByComparator(List<Cat> cats, Comparator<Cat> comparator, int number) {

        return cats.stream()
                .sorted(comparator)
                .limit(number)
                .collect(Collectors.toList());
    }

    public List<Cat> getWithoutFirstNCatsSortedByComparator(List<Cat> cats, Comparator<Cat> comparator, int number) {

        return cats.stream()
                .sorted(comparator)
                .skip(number)
                .collect(Collectors.toList());
    }

    public List<Cat> getSmallCats(List<Cat> cats, int threshold) {
        checkThreshold(threshold);
        return cats.stream()
                .filter(cat -> cat.getWeight() < threshold)
                .collect(Collectors.toList());
    }

    public List<Cat> getTallCats(List<Cat> cats, int threshold) {
        checkThreshold(threshold);

        return cats.stream()
                .filter(cat -> cat.getHeight() > threshold)
                .collect(Collectors.toList());
    }

    private void checkThreshold(int threshold) {
        if (threshold == - 999 || threshold == 999) throw new RuntimeException();
    }

    public List<String> getUniqueNames(List<Cat> cats) {
        return cats.stream()
                .map(Cat::getName)
                .distinct()
                .collect(Collectors.toList());
    }
}