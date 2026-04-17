package com.dsantos.converter.builtin;
import com.dsantos.converter.Converter;
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Cannot convert null or blank string to Integer");
        }
        return Integer.parseInt(source.trim());
    }
}
