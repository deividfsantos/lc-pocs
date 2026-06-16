package com.mystery.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClueTest {
    @Test
    void withFoundReturnsCopyWithFoundTrue() {
        Clue clue = new Clue("id1", "A dusty footprint.", "room", false);
        Clue found = clue.withFound();
        assertFalse(clue.found());
        assertTrue(found.found());
        assertEquals(clue.id(), found.id());
        assertEquals(clue.description(), found.description());
        assertEquals(clue.location(), found.location());
    }

    @Test
    void clueRecordEquality() {
        Clue a = new Clue("x", "desc", "loc", false);
        Clue b = new Clue("x", "desc", "loc", false);
        assertEquals(a, b);
    }

    @Test
    void clueRecordInequalityOnFound() {
        Clue a = new Clue("x", "desc", "loc", false);
        Clue b = new Clue("x", "desc", "loc", true);
        assertNotEquals(a, b);
    }
}
