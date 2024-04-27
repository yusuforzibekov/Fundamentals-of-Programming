package edu.epam.fop.lambdas;

import java.util.HashSet;
import java.util.Set;

public interface IntArrayReducers {

    IntArrayReducer SUMMARIZER = array -> {
        int res = 0;
        for (int i : array) {
            res += i;
        }
        return res;
    };

    IntArrayReducer MULTIPLIER = array -> {
        int res = 1;
        for (int j : array) {
            res *= j;
        }
        return res;
    };

    IntArrayReducer MIN_FINDER = array -> {
        int minValue = Integer.MAX_VALUE;
        for (int i : array) {
            minValue = Math.min(minValue, i);
        }
        return minValue;
    };

    IntArrayReducer MAX_FINDER = array -> {
        int maxValue = Integer.MIN_VALUE;
        for (int i : array) {
            maxValue = Math.max(maxValue, i);
        }
        return maxValue;
    };

    IntArrayReducer AVERAGE_CALCULATOR = array -> {
        double sum = 0;
        for (int i : array) {
            sum += i;
        }
        return Math.round((float) sum / array.length);
    };

    IntArrayReducer UNIQUE_COUNTER = array -> {
        Set<Integer> unique = new HashSet<>();
        for (int element : array) {
            unique.add(element);
        }
        return unique.size();
    };

    IntArrayReducer SORT_DIRECTION_DEFINER = array -> {
        if (array.length == 1 || array.length == 0) return 0;
        if (array.length == 2 && array[0] == array[1]) return 0;

        int ascending = 1;
        int descending = - 1;

        boolean isAscending = true;
        boolean isDescending = true;

        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i - 1]) {
                isDescending = false;
            } else if (array[i] < array[i - 1]) {
                isAscending = false;
            }
        }

        if (isAscending) {
            return ascending;
        } else if (isDescending) {
            return descending;
        } else {
            return 0;
        }
    };
}