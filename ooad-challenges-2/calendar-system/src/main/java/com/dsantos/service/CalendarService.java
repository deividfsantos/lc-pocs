package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.exception.CalendarException;
import com.dsantos.repository.MeetingRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Main application service for the Calendar System.
 * Orchestrates booking, removal, listing, and time suggestion operations.
 */
public class CalendarService {

    private final MeetingRepository repository;
    private final MeetingValidator validator;

    public CalendarService(MeetingRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repository must not be null");
        this.validator = new MeetingValidator(repository);
    }

    /**
     * Books a new meeting after validating that no participant has a scheduling conflict.
     *
     * @throws ConflictException if any participant already has a meeting in the given slot.
     */
    public Meeting bookMeeting(String title, TimeSlot timeSlot, List<Person> participants) {
        validator.validate(timeSlot, participants);
        Meeting meeting = new Meeting(title, timeSlot, participants);
        repository.save(meeting);
        return meeting;
    }

    /**
     * Removes a meeting by id.
     *
     * @return {@code true} if removed, {@code false} if no such meeting existed.
     * @throws CalendarException if the id is null.
     */
    public boolean removeMeeting(UUID id) {
        Objects.requireNonNull(id, "Meeting id must not be null");
        return repository.remove(id);
    }

    /**
     * Returns all meetings for a given participant, sorted by start time (ascending).
     */
    public List<Meeting> listMeetings(Person person) {
        Objects.requireNonNull(person, "Person must not be null");
        return repository.findByParticipant(person).stream()
                .sorted(Comparator.comparing(m -> m.getTimeSlot().getStart()))
                .toList();
    }

    /**
     * Returns every meeting in the system, sorted by start time (ascending).
     */
    public List<Meeting> listAllMeetings() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(m -> m.getTimeSlot().getStart()))
                .toList();
    }
}

