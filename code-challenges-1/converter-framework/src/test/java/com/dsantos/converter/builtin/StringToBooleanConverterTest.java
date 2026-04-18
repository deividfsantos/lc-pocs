package com.dsantos.converter.builtin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StringToBooleanConverterTest {
    private final StringToBooleanConverter converter = new StringToBooleanConverter();
    @Test
    void convertsTrueValues() {
        assertTrue(converter.convert("true"));
        assertTrue(converter.convert("yes"));
        assertTrue(converter.convert("1"));
    }
    @Test
    void convertsFalseValues() {
        assertFalse(converter.convert("false"));
        assertFalse(converter.convert("no"));
        assertFalse(converter.convert("0"));
    }
    @Test
    void isCaseInsensitive() {
        assertTrue(converter.convert("TRUE"));
        assertFalse(converter.convert("FALSE"));
    }
    @Test
    void throwsOnUnknownValue() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert("maybe"));
    }
}
