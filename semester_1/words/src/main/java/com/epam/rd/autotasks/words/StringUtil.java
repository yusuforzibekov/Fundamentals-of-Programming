package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (words == null || sample == null) {
            return 0;
        }
        int counter = 0;
        sample = sample.trim();

        for (String word : words) {
            if (word.trim().equalsIgnoreCase(sample)) {
                counter++;
            }
        }
        return counter;
    }

    public static String[] splitWords(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        String regex = "[\\p{P}\\s+]+";
        boolean onlyPunctuationMarks = Pattern.matches(regex, text);

        if (onlyPunctuationMarks) {
            return null;
        }
        String buf = text.replaceAll(regex, " ").trim();
        return buf.split(" ");
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isBlank() || isWrongPath(path)) {
            return null;
        }

        if (toWin) {
            return convertToWin(path);
        }
        return convertToUnix(path);
    }

    private static boolean isMatchWin(String path) {
        Pattern p = Pattern.compile("\\w+\\.\\w+");
        Pattern p2 = Pattern.compile("(\\\\.*)+(\\w+\\.\\w+)");

        return Stream.of(p, p2)
                .anyMatch(pattern -> pattern.matcher(path).matches());
    }

    private static boolean isMatchUnix(String path) {
        Pattern p = Pattern.compile("\\w+\\.{1}\\w+");
        Matcher m = p.matcher(path);
        return m.matches();
    }

    private static String convertToWin(String path) {
        if (isMatchWin(path)) {
            return path;
        }
        if (path.startsWith("~")) {
            path = path.replaceFirst("~", "C:\\\\User");
        }

        if (path.startsWith("../")) {
            path = path.replaceFirst("../", "..\\\\");
        }

        if (path.startsWith("/")) {
            path = path.replaceFirst("/", "C:\\\\");
        }

        return path.replaceAll("/", "\\\\");
    }

    private static String convertToUnix(String path) {
        if (isMatchUnix(path)) {
            return path;
        }

        return path.replaceFirst("C:\\\\User", "~")
                .replaceFirst("C:\\\\", "/")
                .replaceAll("\\\\", "/");
    }

    private static boolean isWrongPath(String path) {
        Pattern p = Pattern.compile("((\\\\.*)(/.*))|((/.*)(\\\\.*))");
        Pattern p2 = Pattern.compile("~{2,}");
        Pattern p3 = Pattern.compile(".+~");
        Pattern p4 = Pattern.compile("(~.*)(\\\\.*)|(\\\\.*)(~.*)");
        Pattern p5 = Pattern.compile("(C:).*(C:)");
        Pattern p6 = Pattern.compile("((C:.*)(/.*))|((/.*)(C:.*))");

        return Stream.of(p, p2, p3, p4, p5, p6)
                .anyMatch(pattern -> pattern.matcher(path).find());
    }

    public static String joinWords(String[] words) {
        if (isEmpty(words)) {
            return null;
        }
        return "[" + Arrays.stream(words)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(", ")) + "]";
    }

    public static boolean isEmpty(String[] words) {
        return words == null || Arrays.stream(words).allMatch(String::isEmpty);
    }
}
