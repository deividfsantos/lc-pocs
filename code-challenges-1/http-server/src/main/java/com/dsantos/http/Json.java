package com.dsantos.http;

import java.util.Map;
import java.util.stream.Collectors;

public class Json {
    public static String of(Map<String, Object> fields) {
        return "{" + fields.entrySet().stream()
            .map(e -> "\"" + e.getKey() + "\":" + toValue(e.getValue()))
            .collect(Collectors.joining(",")) + "}";
    }

    public static String array(String key, java.util.List<?> items) {
        String values = items.stream()
            .map(i -> "\"" + i + "\"")
            .collect(Collectors.joining(","));
        return "{\"" + key + "\":[" + values + "]}";
    }

    private static String toValue(Object value) {
        if (value instanceof String s) return "\"" + s + "\"";
        if (value instanceof Boolean b) return b.toString();
        return String.valueOf(value);
    }
}
