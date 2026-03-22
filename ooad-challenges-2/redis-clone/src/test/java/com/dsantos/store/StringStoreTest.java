package com.dsantos.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringStoreTest {

    private StringStore store;

    @BeforeEach
    void setUp() {
        store = new StringStore();
    }

    @Test
    void setAndGet() {
        store.set("key", "value");
        assertEquals("value", store.get("key").orElseThrow());
    }

    @Test
    void getMissingKeyReturnsEmpty() {
        assertTrue(store.get("missing").isEmpty());
    }

    @Test
    void setOverwritesExistingValue() {
        store.set("key", "first");
        store.set("key", "second");
        assertEquals("second", store.get("key").orElseThrow());
    }

    @Test
    void removeExistingKeyReturnsTrue() {
        store.set("key", "value");
        assertTrue(store.remove("key"));
        assertTrue(store.get("key").isEmpty());
    }

    @Test
    void removeMissingKeyReturnsFalse() {
        assertFalse(store.remove("missing"));
    }

    @Test
    void appendToNewKeyCreatesValue() {
        long length = store.append("key", "hello");
        assertEquals(5, length);
        assertEquals("hello", store.get("key").orElseThrow());
    }

    @Test
    void appendToExistingKeyConcatenates() {
        store.set("key", "hello");
        long length = store.append("key", "-world");
        assertEquals(11, length);
        assertEquals("hello-world", store.get("key").orElseThrow());
    }
}

