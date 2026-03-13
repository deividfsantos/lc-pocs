package com.dsantos;

import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.domain.Meeting;
import com.dsantos.exception.ConflictException;
import com.dsantos.repository.InMemoryMeetingRepository;
import com.dsantos.service.CalendarService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Demo driver for the Calendar System.
 *
 * Demonstrates:
 *  1. bookMeeting   — schedule meetings for participants
 *  2. removeMeeting — cancel an existing meeting
 *  3. listMeetings  — list all meetings for a person (sorted by start)
 *  4. suggestTime   — find a free slot for two people
 */
public class Main {

    public static void main(String[] args) {
        CalendarService service = new CalendarService(new InMemoryMeetingRepository());

        Person alice = new Person("Alice", "alice@example.com");
        Person bob   = new Person("Bob",   "bob@example.com");

        LocalDateTime today = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);

        // ── 1. Book meetings ─────────────────────────────────────────────────
        System.out.println("=== 1. Booking meetings ===");

        Meeting standup = service.bookMeeting(
                "Daily Standup",
                new TimeSlot(today.withHour(9), today.withHour(10)),
                List.of(alice, bob));
        System.out.println("Booked: " + standup.getTitle() + " " + standup.getTimeSlot());

        Meeting aliceReview = service.bookMeeting(
                "Alice Code Review",
                new TimeSlot(today.withHour(11), today.withHour(12)),
                List.of(alice));
        System.out.println("Booked: " + aliceReview.getTitle() + " " + aliceReview.getTimeSlot());

        Meeting bobPlanning = service.bookMeeting(
                "Bob Sprint Planning",
                new TimeSlot(today.withHour(13), today.withHour(15)),
                List.of(bob));
        System.out.println("Booked: " + bobPlanning.getTitle() + " " + bobPlanning.getTimeSlot());

        // Show that double-booking is rejected
        System.out.println("\n-- Attempting to double-book Alice at 11-12 (should fail) --");
        try {
            service.bookMeeting("Conflict Meeting",
                    new TimeSlot(today.withHour(11), today.withHour(12)),
                    List.of(alice));
        } catch (ConflictException e) {
            System.out.println("Caught expected conflict: " + e.getMessage());
        }

        // ── 2. List meetings ─────────────────────────────────────────────────
        System.out.println("\n=== 2. Listing meetings ===");

        System.out.println("Alice's meetings:");
        service.listMeetings(alice).forEach(m ->
                System.out.println("  - " + m.getTitle() + " " + m.getTimeSlot()));

        System.out.println("Bob's meetings:");
        service.listMeetings(bob).forEach(m ->
                System.out.println("  - " + m.getTitle() + " " + m.getTimeSlot()));

        // ── 3. Remove a meeting ──────────────────────────────────────────────
        System.out.println("\n=== 3. Removing Alice Code Review ===");
        boolean removed = service.removeMeeting(aliceReview.getId());
        System.out.println("Removed: " + removed);

        System.out.println("Alice's meetings after removal:");
        service.listMeetings(alice).forEach(m ->
                System.out.println("  - " + m.getTitle() + " " + m.getTimeSlot()));

        // ── 4. Suggest best time ─────────────────────────────────────────────
        System.out.println("\n=== 4. Suggesting free 1-hour slots for Alice & Bob (9:00–17:00) ===");

        TimeSlot workDay = new TimeSlot(today.withHour(9), today.withHour(17));
        List<TimeSlot> suggestions = service.suggestTime(alice, bob, Duration.ofHours(1), workDay);

        if (suggestions.isEmpty()) {
            System.out.println("No free slots found.");
        } else {
            System.out.println("First 5 suggestions:");
            suggestions.stream().limit(5).forEach(s ->
                    System.out.println("  - " + s.getStart().toLocalTime() + " → " + s.getEnd().toLocalTime()));
        }
    }
}
