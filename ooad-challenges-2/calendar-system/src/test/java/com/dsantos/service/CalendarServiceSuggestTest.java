package com.dsantos.service;

import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.repository.InMemoryMeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end integration test for the suggest time feature via CalendarService.
 */
class CalendarServiceSuggestTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);
    private static final Person ALICE = new Person("Alice", "alice@example.com");
    private static final Person BOB = new Person("Bob", "bob@example.com");
    private static final Duration ONE_HOUR = Duration.ofHours(1);

    private CalendarService service;

    @BeforeEach
    void setUp() {
        service = new CalendarService(new InMemoryMeetingRepository());
    }

    private TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(BASE.withHour(startHour), BASE.withHour(endHour));
    }

    @Test
    void suggestTime_returnsFreeSlotsWhenBothAreFree() {
        // 9-11 window, both free → at least one 1-hour slot
        List<TimeSlot> suggestions = service.suggestTime(ALICE, BOB, ONE_HOUR, slot(9, 11));
        assertFalse(suggestions.isEmpty());
        assertEquals(slot(9, 10), suggestions.get(0));
    }

    @Test
    void suggestTime_respectsExistingBookings() {
        service.bookMeeting("Alice 9-10", slot(9, 10), List.of(ALICE));
        service.bookMeeting("Bob 10-11", slot(10, 11), List.of(BOB));

        // Both busy in 9-11; 11-12 is free
        List<TimeSlot> suggestions = service.suggestTime(ALICE, BOB, ONE_HOUR, slot(9, 12));
        assertEquals(1, suggestions.size());
        assertEquals(slot(11, 12), suggestions.get(0));
    }

    @Test
    void suggestTime_returnsEmptyWhenNoSlotFits() {
        service.bookMeeting("Alice all day", slot(9, 17), List.of(ALICE));
        List<TimeSlot> suggestions = service.suggestTime(ALICE, BOB, ONE_HOUR, slot(9, 17));
        assertTrue(suggestions.isEmpty());
    }

    @Test
    void suggestTime_worksAfterRemovingAMeeting() {
        // Book Alice 9-10, then remove it — 9-10 should become available again
        var m = service.bookMeeting("Alice 9-10", slot(9, 10), List.of(ALICE));
        service.removeMeeting(m.getId());

        List<TimeSlot> suggestions = service.suggestTime(ALICE, BOB, ONE_HOUR, slot(9, 10));
        assertFalse(suggestions.isEmpty());
        assertEquals(slot(9, 10), suggestions.get(0));
    }
}

