package com.epam.rd.autotasks;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Words {

    public String countWords(List<String> lines) {
        Map<String, Integer> wordFrequency = new HashMap<>();

        Pattern pattern = Pattern.compile("[^\\d\\p{IsL}]+");
        for (String line : lines) {

            line = line.replaceAll(pattern.pattern(), " ").toLowerCase();
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.length() >= 4) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }

        wordFrequency.entrySet().removeIf(new Predicate<Map.Entry<String, Integer>>() {
            @Override
            public boolean test(Map.Entry<String, Integer> entry) {
                return entry.getValue() < 10;
            }
        });

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());
        sortedEntries.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                int freqComparison = entry2.getValue().compareTo(entry1.getValue());
                if (freqComparison != 0) {
                    return freqComparison;
                } else {
                    return entry1.getKey().compareTo(entry2.getKey());
                }
            }
        });
        int count = 0;
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            result.append(entry.getKey()).append(" - ").append(entry.getValue());
            count++;
            if (count < sortedEntries.size())
                result.append("\n");

        }

        return result.toString();
    }
}