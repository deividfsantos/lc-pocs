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

import static org.junit.jupiter.api.Assertions.*;

class MeetingValidatorTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 3, 13, 9, 0);
    private static final Person ALICE = new Person("Alice", "alice@example.com");
    private static final Person BOB = new Person("Bob", "bob@example.com");

    private InMemoryMeetingRepository repository;
    private MeetingValidator validator;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMeetingRepository();
        validator = new MeetingValidator(repository);
    }

    private TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(BASE.withHour(startHour), BASE.withHour(endHour));
    }

    private void saveMeeting(String title, TimeSlot ts, Person... people) {
        repository.save(new Meeting(title, ts, List.of(people)));
    }

    // ── No conflicts ─────────────────────────────────────────────────────────

    @Test
    void validate_doesNotThrowWhenNoConflict() {
        saveMeeting("Standup", slot(9, 10), ALICE);
        // Proposing 11-12 — no overlap
        assertDoesNotThrow(() -> validator.validate(slot(11, 12), List.of(ALICE)));
    }

    @Test
    void validate_doesNotThrowForAdjacentSlots() {
        saveMeeting("Standup", slot(9, 10), ALICE);
        // Immediately after — touching boundary is NOT an overlap
        assertDoesNotThrow(() -> validator.validate(slot(10, 11), List.of(ALICE)));
    }

    @Test
    void validate_doesNotThrowWhenRepositoryIsEmpty() {
        assertDoesNotThrow(() -> validator.validate(slot(9, 10), List.of(ALICE, BOB)));
    }

    // ── Conflicts ────────────────────────────────────────────────────────────

    @Test
    void validate_throwsWhenSlotOverlapsExistingMeeting() {
        saveMeeting("Standup", slot(9, 11), ALICE);
        assertThrows(ConflictException.class,
                () -> validator.validate(slot(10, 12), List.of(ALICE)));
    }

    @Test
    void validate_throwsWhenProposedSlotContainsExistingMeeting() {
        saveMeeting("Standup", slot(10, 11), ALICE);
        assertThrows(ConflictException.class,
                () -> validator.validate(slot(9, 12), List.of(ALICE)));
    }

    @Test
    void validate_throwsForSecondParticipantConflict() {
        saveMeeting("Bob Meeting", slot(9, 11), BOB);
        // Alice is free, Bob is not
        assertThrows(ConflictException.class,
                () -> validator.validate(slot(10, 12), List.of(ALICE, BOB)));
    }
}

