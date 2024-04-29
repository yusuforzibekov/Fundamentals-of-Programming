package edu.epam.fop.lambdas;

import java.util.Set;

public final class TextStatistics {

    private TextStatistics() {
        throw new UnsupportedOperationException();
    }

    public static TokenStatisticsCalculator<Token, Long> countTokens() {
        return (statistics, tokens) -> {
            while (tokens.hasNext()) {
                statistics.merge(tokens.next(), 1L, Long::sum);
            }
        };
    }


    public static TokenStatisticsCalculator<Token, Long> countKnownTokensWithMaxLimit(int maxLimit) {
        return (statistics, tokens) -> {

            while (tokens.hasNext()) {
                statistics.computeIfPresent(tokens.next(), (t, count) -> {
                    if (count < maxLimit) {
                        count++;
                    }
                    return count;
                });
            }
        };
    }

    public static TokenStatisticsCalculator<Token, Boolean> findUnknownTokensOfTypes(Set<Token.Type> types) {
        return (statistics, tokens) -> {
            while (tokens.hasNext()) {
                statistics.computeIfAbsent(tokens.next(), token -> {
                    for (Token.Type type : types) {
                        if (token.type().equals(type)) {
                            return true;
                        }
                    }
                    return null;
                });
            }
        };
    }

    public static TokenStatisticsCalculator<Token, Integer> combinedSearch(int maxLimit, Set<
            Token.Type> types) {
        return (statistics, tokens) -> {

            while (tokens.hasNext()) {
                Token token = tokens.next();
                if (! statistics.containsKey(token)) {
                    // Token is not in the map
                    statistics.compute(token, (t, value) -> value != null ? value : - 1);
                } else {
                    // Token exists in the map
                    int count = statistics.get(token);
                    if (types.contains(token.type())) {
                        if (count < maxLimit) {
                            statistics.compute(token, (t, value) -> 0);
                        } else {
                            statistics.compute(token, (t, value) -> 1);
                        }
                    } else {
                        if (count < maxLimit) {
                            statistics.compute(token, (t, value) -> 2);
                        } else {
                            statistics.compute(token, (t, value) -> 3);
                        }
                    }
                }
            }
        };
    }
}