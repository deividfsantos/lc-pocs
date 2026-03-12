package com.dsantos.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);
    private static final TimeSlot VALID_SLOT = new TimeSlot(BASE, BASE.plusHours(1));
    private static final Person ALICE = new Person("Alice", "alice@example.com");
    private static final Person BOB = new Person("Bob", "bob@example.com");

    // ── Constructor validation ────────────────────────────────────────────────

    @Test
    void constructor_createsValidMeeting() {
        Meeting m = new Meeting("Standup", VALID_SLOT, List.of(ALICE));
        assertNotNull(m.getId());
        assertEquals("Standup", m.getTitle());
        assertEquals(VALID_SLOT, m.getTimeSlot());
        assertEquals(1, m.getParticipants().size());
    }

    @Test
    void constructor_throwsWhenTitleIsNull() {
        assertThrows(NullPointerException.class,
                () -> new Meeting(null, VALID_SLOT, List.of(ALICE)));
    }

    @Test
    void constructor_throwsWhenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Meeting("  ", VALID_SLOT, List.of(ALICE)));
    }

    @Test
    void constructor_throwsWhenTimeSlotIsNull() {
        assertThrows(NullPointerException.class,
                () -> new Meeting("Standup", null, List.of(ALICE)));
    }

    @Test
    void constructor_throwsWhenParticipantsIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Meeting("Standup", VALID_SLOT, Collections.emptyList()));
    }

    @Test
    void constructor_throwsWhenParticipantsIsNull() {
        assertThrows(NullPointerException.class,
                () -> new Meeting("Standup", VALID_SLOT, null));
    }

    // ── Immutability ─────────────────────────────────────────────────────────

    @Test
    void participants_listIsUnmodifiable() {
        Meeting m = new Meeting("Standup", VALID_SLOT, List.of(ALICE));
        assertThrows(UnsupportedOperationException.class,
                () -> m.getParticipants().add(BOB));
    }

    // ── hasParticipant ────────────────────────────────────────────────────────

    @Test
    void hasParticipant_returnsTrueForExistingParticipant() {
        Meeting m = new Meeting("Standup", VALID_SLOT, List.of(ALICE, BOB));
        assertTrue(m.hasParticipant(ALICE));
        assertTrue(m.hasParticipant(BOB));
    }

    @Test
    void hasParticipant_returnsFalseForNonParticipant() {
        Meeting m = new Meeting("Standup", VALID_SLOT, List.of(ALICE));
        assertFalse(m.hasParticipant(BOB));
    }

    // ── Equality by id ────────────────────────────────────────────────────────

    @Test
    void twoDistinctMeetingsAreNotEqual() {
        Meeting m1 = new Meeting("Standup", VALID_SLOT, List.of(ALICE));
        Meeting m2 = new Meeting("Standup", VALID_SLOT, List.of(ALICE));
        assertNotEquals(m1, m2);
    }
}

