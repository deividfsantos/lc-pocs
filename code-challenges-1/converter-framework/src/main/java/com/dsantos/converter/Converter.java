package com.dsantos.converter;
public interface Converter<S, T> {
    T convert(S source);
}
