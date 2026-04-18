package com.dsantos.converter;
public class ConversionService {
    private final ConverterRegistry registry;
    public ConversionService(ConverterRegistry registry) {
        this.registry = registry;
    }
    public <S, T> T convert(S source, Class<T> targetType) {
        if (source == null) {
            throw new ConversionException("Source must not be null");
        }
        @SuppressWarnings("unchecked")
        Class<S> sourceType = (Class<S>) source.getClass();
        Converter<S, T> converter = registry.find(sourceType, targetType);
        if (converter == null) {
            throw new ConversionException(
                "No converter found for " + sourceType.getSimpleName() + " -> " + targetType.getSimpleName()
            );
        }
        try {
            return converter.convert(source);
        } catch (Exception e) {
            throw new ConversionException("Conversion failed: " + e.getMessage(), e);
        }
    }
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return registry.has(sourceType, targetType);
    }
}
