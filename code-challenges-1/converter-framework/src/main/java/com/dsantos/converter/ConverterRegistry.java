package com.dsantos.converter;
import java.util.HashMap;
import java.util.Map;
public class ConverterRegistry {
    private final Map<String, Converter<?, ?>> converters = new HashMap<>();
    public <S, T> void register(Class<S> sourceType, Class<T> targetType, Converter<S, T> converter) {
        String key = buildKey(sourceType, targetType);
        converters.put(key, converter);
    }
    @SuppressWarnings("unchecked")
    public <S, T> Converter<S, T> find(Class<S> sourceType, Class<T> targetType) {
        String key = buildKey(sourceType, targetType);
        return (Converter<S, T>) converters.get(key);
    }
    public boolean has(Class<?> sourceType, Class<?> targetType) {
        return converters.containsKey(buildKey(sourceType, targetType));
    }
    private String buildKey(Class<?> source, Class<?> target) {
        return source.getName() + "->" + target.getName();
    }
}
