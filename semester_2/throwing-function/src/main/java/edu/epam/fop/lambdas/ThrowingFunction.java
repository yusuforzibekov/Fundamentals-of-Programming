package edu.epam.fop.lambdas;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {

    static <T, R, E extends Throwable> Function<T, R> quiet(ThrowingFunction<T, R, E> throwingFunction) {
        if (throwingFunction == null) {
            return null;
        }
        return t -> {
            try {
                return throwingFunction.apply(t);
            } catch (Throwable exc) {
                throw new RuntimeException(exc);
            }
        };

    }

    R apply(T t) throws E;
}