package com.dsantos.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);

    private TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(BASE.withHour(startHour), BASE.withHour(endHour));
    }

    // ── Constructor validation ────────────────────────────────────────────────

    @Test
    void constructor_throwsWhenStartEqualsEnd() {
        LocalDateTime t = BASE;
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(t, t));
    }

    @Test
    void constructor_throwsWhenStartAfterEnd() {
        assertThrows(IllegalArgumentException.class, () -> slot(10, 9));
    }

    @Test
    void constructor_throwsWhenStartIsNull() {
        assertThrows(NullPointerException.class,
                () -> new TimeSlot(null, BASE));
    }

    @Test
    void constructor_throwsWhenEndIsNull() {
        assertThrows(NullPointerException.class,
                () -> new TimeSlot(BASE, null));
    }

    // ── overlaps ─────────────────────────────────────────────────────────────

    @Test
    void overlaps_returnsTrueForOverlappingSlots() {
        assertTrue(slot(9, 11).overlaps(slot(10, 12)));
    }

    @Test
    void overlaps_returnsTrueForContainedSlot() {
        assertTrue(slot(9, 13).overlaps(slot(10, 12)));
    }

    @Test
    void overlaps_returnsTrueForIdenticalSlots() {
        assertTrue(slot(9, 10).overlaps(slot(9, 10)));
    }

    @Test
    void overlaps_returnsFalseForAdjacentSlots() {
        // end of first == start of second → touching, NOT overlapping
        assertFalse(slot(9, 10).overlaps(slot(10, 11)));
    }

    @Test
    void overlaps_returnsFalseForNonOverlappingSlots() {
        assertFalse(slot(9, 10).overlaps(slot(11, 12)));
    }

    @Test
    void overlaps_isSymmetric() {
        TimeSlot a = slot(9, 11);
        TimeSlot b = slot(10, 12);
        assertEquals(a.overlaps(b), b.overlaps(a));
    }

    // ── contains ─────────────────────────────────────────────────────────────

    @Test
    void contains_returnsTrueWhenOtherIsFullyInside() {
        assertTrue(slot(9, 13).contains(slot(10, 12)));
    }

    @Test
    void contains_returnsTrueForEqualSlots() {
        assertTrue(slot(9, 11).contains(slot(9, 11)));
    }

    @Test
    void contains_returnsFalseWhenOtherExtendsOutside() {
        assertFalse(slot(9, 11).contains(slot(10, 12)));
    }

    @Test
    void contains_returnsFalseWhenOtherIsLarger() {
        assertFalse(slot(10, 11).contains(slot(9, 13)));
    }
}

