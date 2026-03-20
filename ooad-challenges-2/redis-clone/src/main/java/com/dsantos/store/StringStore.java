package com.dsantos.store;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class StringStore {

    private final ConcurrentHashMap<String, String> data = new ConcurrentHashMap<>();

    public void set(String key, String value) {
        data.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(data.get(key));
    }

    public boolean remove(String key) {
        return data.remove(key) != null;
    }

    public long append(String key, String value) {
        String result = data.merge(key, value, String::concat);
        return result.length();
    }
}

