package com.dsantos.repository;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMeetingRepositoryTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);
    private static final Person ALICE = new Person("Alice", "alice@example.com");
    private static final Person BOB = new Person("Bob", "bob@example.com");

    private InMemoryMeetingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMeetingRepository();
    }

    private Meeting buildMeeting(String title, int startHour, int endHour, Person... participants) {
        TimeSlot slot = new TimeSlot(BASE.withHour(startHour), BASE.withHour(endHour));
        return new Meeting(title, slot, List.of(participants));
    }

    // ── save / findById ───────────────────────────────────────────────────────

    @Test
    void save_andFindById_returnsSavedMeeting() {
        Meeting m = buildMeeting("Standup", 9, 10, ALICE);
        repository.save(m);
        Optional<Meeting> found = repository.findById(m.getId());
        assertTrue(found.isPresent());
        assertEquals(m, found.get());
    }

    @Test
    void findById_returnsEmptyForUnknownId() {
        assertTrue(repository.findById(UUID.randomUUID()).isEmpty());
    }

    // ── remove ────────────────────────────────────────────────────────────────

    @Test
    void remove_returnsTrueAndDeletesMeeting() {
        Meeting m = buildMeeting("Standup", 9, 10, ALICE);
        repository.save(m);
        assertTrue(repository.remove(m.getId()));
        assertTrue(repository.findById(m.getId()).isEmpty());
    }

    @Test
    void remove_returnsFalseForUnknownId() {
        assertFalse(repository.remove(UUID.randomUUID()));
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    void findAll_returnsAllSavedMeetings() {
        Meeting m1 = buildMeeting("Standup", 9, 10, ALICE);
        Meeting m2 = buildMeeting("Review", 11, 12, BOB);
        repository.save(m1);
        repository.save(m2);
        List<Meeting> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(m1));
        assertTrue(all.contains(m2));
    }

    @Test
    void findAll_returnsEmptyWhenNoMeetings() {
        assertTrue(repository.findAll().isEmpty());
    }

    // ── findByParticipant ─────────────────────────────────────────────────────

    @Test
    void findByParticipant_returnsOnlyMeetingsWithThatPerson() {
        Meeting m1 = buildMeeting("Standup", 9, 10, ALICE);
        Meeting m2 = buildMeeting("Sync", 11, 12, ALICE, BOB);
        Meeting m3 = buildMeeting("Review", 13, 14, BOB);
        repository.save(m1);
        repository.save(m2);
        repository.save(m3);

        List<Meeting> aliceMeetings = repository.findByParticipant(ALICE);
        assertEquals(2, aliceMeetings.size());
        assertTrue(aliceMeetings.contains(m1));
        assertTrue(aliceMeetings.contains(m2));
        assertFalse(aliceMeetings.contains(m3));
    }

    @Test
    void findByParticipant_returnsEmptyWhenNoMatch() {
        Meeting m = buildMeeting("Standup", 9, 10, ALICE);
        repository.save(m);
        assertTrue(repository.findByParticipant(BOB).isEmpty());
    }
}

