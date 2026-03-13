package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.repository.InMemoryMeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarServiceListTest {

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

    // ── listMeetings(Person) ──────────────────────────────────────────────────

    @Test
    void listMeetings_returnsOnlyMeetingsForGivenPerson() {
        Meeting m1 = service.bookMeeting("Standup", slot(9, 10), List.of(ALICE));
        Meeting m2 = service.bookMeeting("Review", slot(11, 12), List.of(ALICE, BOB));
        service.bookMeeting("Bob-only", slot(13, 14), List.of(BOB));

        List<Meeting> aliceMeetings = service.listMeetings(ALICE);
        assertEquals(2, aliceMeetings.size());
        assertTrue(aliceMeetings.contains(m1));
        assertTrue(aliceMeetings.contains(m2));
    }

    @Test
    void listMeetings_returnsMeetingsSortedByStartTime() {
        // Book out of order intentionally
        Meeting late = service.bookMeeting("Late", slot(14, 15), List.of(ALICE));
        Meeting early = service.bookMeeting("Early", slot(9, 10), List.of(ALICE));
        Meeting mid = service.bookMeeting("Mid", slot(11, 12), List.of(ALICE));

        List<Meeting> meetings = service.listMeetings(ALICE);
        assertEquals(List.of(early, mid, late), meetings);
    }

    @Test
    void listMeetings_returnsEmptyWhenPersonHasNoMeetings() {
        service.bookMeeting("Bob-only", slot(9, 10), List.of(BOB));
        assertTrue(service.listMeetings(ALICE).isEmpty());
    }

    // ── listAllMeetings() ─────────────────────────────────────────────────────

    @Test
    void listAllMeetings_returnsAllMeetingsSortedByStartTime() {
        Meeting m3 = service.bookMeeting("Third", slot(15, 16), List.of(BOB));
        Meeting m1 = service.bookMeeting("First", slot(9, 10), List.of(ALICE));
        Meeting m2 = service.bookMeeting("Second", slot(11, 12), List.of(ALICE, BOB));

        List<Meeting> all = service.listAllMeetings();
        assertEquals(List.of(m1, m2, m3), all);
    }

    @Test
    void listAllMeetings_returnsEmptyWhenNoMeetingsBooked() {
        assertTrue(service.listAllMeetings().isEmpty());
    }
}

