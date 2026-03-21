package com.dsantos.store;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HashStore {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> data = new ConcurrentHashMap<>();

    public void set(String key, String field, String value) {
        data.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).put(field, value);
    }

    public Optional<String> get(String key, String field) {
        Map<String, String> hash = data.get(key);
        if (hash == null) return Optional.empty();
        return Optional.ofNullable(hash.get(field));
    }

    public List<String> keys(String key) {
        Map<String, String> hash = data.get(key);
        if (hash == null) return Collections.emptyList();
        return List.copyOf(hash.keySet());
    }

    public List<String> values(String key) {
        Map<String, String> hash = data.get(key);
        if (hash == null) return Collections.emptyList();
        return List.copyOf(hash.values());
    }
}

