package com.dsantos.converter.builtin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StringToIntegerConverterTest {
    private final StringToIntegerConverter converter = new StringToIntegerConverter();
    @Test
    void convertsValidString() {
        assertEquals(42, converter.convert("42"));
    }
    @Test
    void trimsWhitespace() {
        assertEquals(10, converter.convert("  10  "));
    }
    @Test
    void throwsOnBlankString() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(""));
    }
    @Test
    void throwsOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
    }
    @Test
    void throwsOnNonNumericString() {
        assertThrows(NumberFormatException.class, () -> converter.convert("abc"));
    }
}
