package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.repository.InMemoryMeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FreeSlotTimeSuggesterTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);
    private static final Person ALICE = new Person("Alice", "alice@example.com");
    private static final Person BOB = new Person("Bob", "bob@example.com");
    private static final Duration ONE_HOUR = Duration.ofHours(1);

    private InMemoryMeetingRepository repository;
    private FreeSlotTimeSuggester suggester;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMeetingRepository();
        // Use 1-hour step for predictable test output
        suggester = new FreeSlotTimeSuggester(repository, ONE_HOUR);
    }

    private TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(BASE.withHour(startHour), BASE.withHour(endHour));
    }

    private void saveMeeting(String title, TimeSlot ts, Person... people) {
        repository.save(new Meeting(title, ts, List.of(people)));
    }

    // ── No busy slots ─────────────────────────────────────────────────────────

    @Test
    void suggest_returnsFullWindowWhenBothPeopleAreFree() {
        // 9-17 window, 1-hour slots → 8 slots (9-10, 10-11, …, 16-17)
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, slot(9, 17));
        assertEquals(8, slots.size());
        assertEquals(slot(9, 10), slots.get(0));
        assertEquals(slot(16, 17), slots.get(slots.size() - 1));
    }

    // ── Busy slots narrow the window ──────────────────────────────────────────

    @Test
    void suggest_excludesAliceBusySlot() {
        saveMeeting("Alice's call", slot(10, 11), ALICE);
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, slot(9, 12));
        // Free gaps: 9-10 and 11-12 → 2 slots
        assertEquals(2, slots.size());
        assertEquals(slot(9, 10), slots.get(0));
        assertEquals(slot(11, 12), slots.get(1));
    }

    @Test
    void suggest_excludesBobBusySlot() {
        saveMeeting("Bob's meeting", slot(9, 10), BOB);
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, slot(9, 11));
        assertEquals(1, slots.size());
        assertEquals(slot(10, 11), slots.get(0));
    }

    @Test
    void suggest_excludesBothParticipantsBusySlots() {
        saveMeeting("Alice 9-10", slot(9, 10), ALICE);
        saveMeeting("Bob 10-11", slot(10, 11), BOB);
        // Window 9-12: 9-10 blocked by Alice, 10-11 blocked by Bob, 11-12 free
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, slot(9, 12));
        assertEquals(1, slots.size());
        assertEquals(slot(11, 12), slots.get(0));
    }

    // ── Fully booked ──────────────────────────────────────────────────────────

    @Test
    void suggest_returnsEmptyWhenWindowIsFullyBooked() {
        saveMeeting("All day Alice", slot(9, 17), ALICE);
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, slot(9, 17));
        assertTrue(slots.isEmpty());
    }

    @Test
    void suggest_returnsEmptyWhenDurationDoesNotFitInRemainingGaps() {
        saveMeeting("Alice 9-11", slot(9, 11), ALICE);
        // Only 30 min left in window (11:00–11:30) but we need 1 hour
        TimeSlot smallWindow = new TimeSlot(BASE.withHour(9), BASE.withHour(11).plusMinutes(30));
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, smallWindow);
        assertTrue(slots.isEmpty());
    }

    // ── Overlapping busy slots are merged ────────────────────────────────────

    @Test
    void suggest_mergesOverlappingBusySlotsFromBothPeople() {
        saveMeeting("Alice 9-11", slot(9, 11), ALICE);
        saveMeeting("Bob 10-12", slot(10, 12), BOB);
        // Merged busy: 9-12. Free in 9-13: 12-13
        List<TimeSlot> slots = suggester.suggest(ALICE, BOB, ONE_HOUR, slot(9, 13));
        assertEquals(1, slots.size());
        assertEquals(slot(12, 13), slots.get(0));
    }
}

