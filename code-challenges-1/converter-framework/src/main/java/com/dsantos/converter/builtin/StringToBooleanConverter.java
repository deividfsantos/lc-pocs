package com.dsantos.converter.builtin;
import com.dsantos.converter.Converter;
public class StringToBooleanConverter implements Converter<String, Boolean> {
    @Override
    public Boolean convert(String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Cannot convert null or blank string to Boolean");
        }
        return switch (source.trim().toLowerCase()) {
            case "true", "yes", "1" -> true;
            case "false", "no", "0" -> false;
            default -> throw new IllegalArgumentException("Cannot parse boolean from: " + source);
        };
    }
}
