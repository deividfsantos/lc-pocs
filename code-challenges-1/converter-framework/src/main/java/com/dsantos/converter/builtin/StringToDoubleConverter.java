package com.dsantos.converter.builtin;
import com.dsantos.converter.Converter;
public class StringToDoubleConverter implements Converter<String, Double> {
    @Override
    public Double convert(String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Cannot convert null or blank string to Double");
        }
        return Double.parseDouble(source.trim());
    }
}
