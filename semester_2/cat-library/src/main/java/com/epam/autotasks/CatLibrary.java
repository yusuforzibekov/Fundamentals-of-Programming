package com.epam.autotasks;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CatLibrary {

    public static final String EMPTY_STRING = "";

    public Map<String, Cat> mapCatsByName(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getName() != null)
                .collect(Collectors.groupingBy(
                        Cat::getName,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                catsList -> catsList.size() == 1 ? catsList.get(0) : null)))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Cat.Breed, Set<Cat>> mapCatsByBreed(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null)
                .collect(Collectors.groupingBy(
                        Cat::getBreed,
                        Collectors.mapping(
                                Function.identity(),
                                Collectors.toSet())));
    }

    public Map<Cat.Breed, String> mapCatNamesByBreed(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null && !cat.getName().isEmpty())
                .collect(Collectors.groupingBy(
                        Cat::getBreed,
                        Collectors.mapping(cat -> cat.getName(),
                                Collectors.joining(", ", "Cat names: ", "."))));
    }

    public Map<Cat.Breed, Double> mapAverageResultByBreed(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null && cat.getContestResult().getSum() != null)
                .collect(Collectors.groupingBy(
                        Cat::getBreed,
                        Collectors.averagingDouble(value -> value.getContestResult().getSum())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> BigDecimal.valueOf(entry.getValue())
                                .setScale(2, RoundingMode.HALF_UP).doubleValue()));
    }

    public SortedSet<Cat> getOrderedCatsByContestResults(List<Cat> cats) {
        Comparator<Cat> catComparator = Comparator
                .<Cat, Integer>comparing(cat -> cat.getContestResult().getSum(), Comparator.reverseOrder())
                .thenComparing(Cat::getName);

        SortedSet<Cat> result = cats.stream()
                .filter(cat -> cat.getContestResult() != null && cat.getContestResult().getSum() != null)
                .sorted(catComparator)
                .collect(Collectors.toCollection(() -> new TreeSet<>(catComparator)));

        return result;
    }
}