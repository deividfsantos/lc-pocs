package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.exception.ConflictException;
import com.dsantos.repository.InMemoryMeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CalendarServiceBookRemoveTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);
    private static final Person ALICE = new Person("Alice", "alice@example.com");
    private static final Person BOB = new Person("Bob", "bob@example.com");

    private CalendarService service;

    @BeforeEach
    void setUp() {
        service = new CalendarService(new InMemoryMeetingRepository());
    }

    private TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(BASE.withHour(startHour), BASE.withHour(endHour));
    }

    // ── bookMeeting ───────────────────────────────────────────────────────────

    @Test
    void bookMeeting_returnsCreatedMeeting() {
        Meeting m = service.bookMeeting("Standup", slot(9, 10), List.of(ALICE));
        assertNotNull(m);
        assertEquals("Standup", m.getTitle());
        assertTrue(m.hasParticipant(ALICE));
    }

    @Test
    void bookMeeting_allowsMultipleNonConflictingMeetings() {
        service.bookMeeting("Standup", slot(9, 10), List.of(ALICE));
        assertDoesNotThrow(() -> service.bookMeeting("Review", slot(11, 12), List.of(ALICE)));
    }

    @Test
    void bookMeeting_throwsOnOverlapForSameParticipant() {
        service.bookMeeting("Standup", slot(9, 11), List.of(ALICE));
        assertThrows(ConflictException.class,
                () -> service.bookMeeting("Review", slot(10, 12), List.of(ALICE)));
    }

    @Test
    void bookMeeting_throwsOnOverlapForAnyParticipant() {
        service.bookMeeting("Bob's call", slot(9, 11), List.of(BOB));
        // Alice is free, Bob is not — whole booking must be rejected
        assertThrows(ConflictException.class,
                () -> service.bookMeeting("Joint meeting", slot(10, 12), List.of(ALICE, BOB)));
    }

    @Test
    void bookMeeting_allowsAdjacentSlotsForSameParticipant() {
        service.bookMeeting("Standup", slot(9, 10), List.of(ALICE));
        assertDoesNotThrow(() -> service.bookMeeting("Review", slot(10, 11), List.of(ALICE)));
    }

    // ── removeMeeting ─────────────────────────────────────────────────────────

    @Test
    void removeMeeting_returnsTrueForExistingMeeting() {
        Meeting m = service.bookMeeting("Standup", slot(9, 10), List.of(ALICE));
        assertTrue(service.removeMeeting(m.getId()));
    }

    @Test
    void removeMeeting_returnsFalseForUnknownId() {
        assertFalse(service.removeMeeting(UUID.randomUUID()));
    }

    @Test
    void removeMeeting_allowsRebookingAfterRemoval() {
        Meeting m = service.bookMeeting("Standup", slot(9, 10), List.of(ALICE));
        service.removeMeeting(m.getId());
        assertDoesNotThrow(() -> service.bookMeeting("Standup again", slot(9, 10), List.of(ALICE)));
    }
}

