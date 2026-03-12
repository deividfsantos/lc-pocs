package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.exception.ConflictException;
import com.dsantos.repository.MeetingRepository;

import java.util.List;
import java.util.Objects;

/**
 * Validates that a proposed {@link TimeSlot} does not conflict with
 * any existing meeting for the given participants.
 */
public class MeetingValidator {

    private final MeetingRepository repository;

    public MeetingValidator(MeetingRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repository must not be null");
    }

    /**
     * Checks that none of the {@code participants} has an overlapping meeting.
     *
     * @throws ConflictException if any participant has a scheduling conflict.
     */
    public void validate(TimeSlot proposedSlot, List<Person> participants) {
        Objects.requireNonNull(proposedSlot, "Proposed slot must not be null");
        Objects.requireNonNull(participants, "Participants must not be null");

        for (Person person : participants) {
            List<Meeting> existing = repository.findByParticipant(person);
            for (Meeting meeting : existing) {
                if (meeting.getTimeSlot().overlaps(proposedSlot)) {
                    throw new ConflictException(
                            String.format("Participant '%s' already has meeting '%s' during %s",
                                    person.getName(), meeting.getTitle(), meeting.getTimeSlot()));
                }
            }
        }
    }
}

