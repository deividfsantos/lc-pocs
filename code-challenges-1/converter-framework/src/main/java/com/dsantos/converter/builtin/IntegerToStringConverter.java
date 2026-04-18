package com.dsantos.converter.builtin;
import com.dsantos.converter.Converter;
public class IntegerToStringConverter implements Converter<Integer, String> {
    @Override
    public String convert(Integer source) {
        if (source == null) {
            throw new IllegalArgumentException("Source must not be null");
        }
        return source.toString();
    }
}
