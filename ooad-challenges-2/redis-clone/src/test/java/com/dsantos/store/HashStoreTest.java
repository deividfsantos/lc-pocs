package com.dsantos.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashStoreTest {

    private HashStore store;

    @BeforeEach
    void setUp() {
        store = new HashStore();
    }

    @Test
    void setAndGet() {
        store.set("user", "name", "Alice");
        assertEquals("Alice", store.get("user", "name").orElseThrow());
    }

    @Test
    void getMissingKeyReturnsEmpty() {
        assertTrue(store.get("missing", "field").isEmpty());
    }

    @Test
    void getMissingFieldReturnsEmpty() {
        store.set("user", "name", "Alice");
        assertTrue(store.get("user", "missing").isEmpty());
    }

    @Test
    void setOverwritesExistingField() {
        store.set("user", "name", "Alice");
        store.set("user", "name", "Bob");
        assertEquals("Bob", store.get("user", "name").orElseThrow());
    }

    @Test
    void keysReturnsAllFields() {
        store.set("user", "name", "Alice");
        store.set("user", "age", "30");
        List<String> keys = store.keys("user");
        assertEquals(2, keys.size());
        assertTrue(keys.containsAll(List.of("name", "age")));
    }

    @Test
    void keysOnMissingHashReturnsEmptyList() {
        assertTrue(store.keys("missing").isEmpty());
    }

    @Test
    void valuesReturnsAllValues() {
        store.set("user", "name", "Alice");
        store.set("user", "age", "30");
        List<String> values = store.values("user");
        assertEquals(2, values.size());
        assertTrue(values.containsAll(List.of("Alice", "30")));
    }

    @Test
    void valuesOnMissingHashReturnsEmptyList() {
        assertTrue(store.values("missing").isEmpty());
    }
}

