package com.dsantos.converter;
import java.util.HashMap;
import java.util.Map;
public class ConverterRegistry {
    private final Map<TypePair, Converter<?, ?>> converters = new HashMap<>();
    public <S, T> void register(Class<S> sourceType, Class<T> targetType, Converter<S, T> converter) {
        converters.put(new TypePair(sourceType, targetType), converter);
    }
    @SuppressWarnings("unchecked")
    public <S, T> Converter<S, T> find(Class<S> sourceType, Class<T> targetType) {
        return (Converter<S, T>) converters.get(new TypePair(sourceType, targetType));
    }
    public boolean has(Class<?> sourceType, Class<?> targetType) {
        return converters.containsKey(new TypePair(sourceType, targetType));
    }
}
