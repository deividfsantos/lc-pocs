package com.dsantos;

public interface Specification<T> {
    boolean isSatisfiedBy(T item);
}
