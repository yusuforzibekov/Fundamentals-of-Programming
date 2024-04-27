package edu.epam.fop.lambdas;

@FunctionalInterface
public interface TriFunction<T, L, K, R> {

    R apply(T t, L l, K k);

}