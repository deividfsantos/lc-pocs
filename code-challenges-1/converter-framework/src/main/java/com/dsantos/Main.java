package com.dsantos;
import com.dsantos.converter.ConversionService;
import com.dsantos.converter.ConverterRegistry;
import com.dsantos.converter.builtin.IntegerToStringConverter;
import com.dsantos.converter.builtin.StringToBooleanConverter;
import com.dsantos.converter.builtin.StringToDoubleConverter;
import com.dsantos.converter.builtin.StringToIntegerConverter;
public class Main {
    public static void main(String[] args) {
        ConverterRegistry registry = new ConverterRegistry();
        registry.register(String.class, Integer.class, new StringToIntegerConverter());
        registry.register(String.class, Double.class, new StringToDoubleConverter());
        registry.register(String.class, Boolean.class, new StringToBooleanConverter());
        registry.register(Integer.class, String.class, new IntegerToStringConverter());
        ConversionService service = new ConversionService(registry);
        Integer number = service.convert("42", Integer.class);
        System.out.println("String -> Integer: " + number);
        Double decimal = service.convert("3.14", Double.class);
        System.out.println("String -> Double: " + decimal);
        Boolean flag = service.convert("yes", Boolean.class);
        System.out.println("String -> Boolean: " + flag);
        String text = service.convert(100, String.class);
        System.out.println("Integer -> String: " + text);
    }
}
