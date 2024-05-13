package com.epam.autotasks;

import java.util.List;

public class CatContestHelper {

    public static final Integer CARRIER_THRESHOLD = 30;

    public Integer getCarrierNumber(List<Cat> cats) {

        int sum = 0;
        for (Cat cat : cats) {
            if (cat.getWeight() < 1) {
                sum += 1;
            }
            sum += cat.getWeight();
        }

        return (int) Math.ceil((double) sum / CARRIER_THRESHOLD);
    }

    public String getCarrierId(List<Cat> cats) {
        StringBuilder sb = new StringBuilder();
        sb.append("CF");
        for (Cat cat : cats) {
            if (cat.getName() != null && cat.getBreed() != null) {
                sb.append(cat.getName(), 0, 3);
                sb.append(cat.getBreed().name(), 0, 3);
            }
        }
        return sb.toString().toUpperCase();
    }

    public Integer countTeamAwards(List<Cat> cats) {
        return cats.stream()
                .mapToInt(Cat::getAwards)
                .reduce(0, Integer::sum);
    }
}