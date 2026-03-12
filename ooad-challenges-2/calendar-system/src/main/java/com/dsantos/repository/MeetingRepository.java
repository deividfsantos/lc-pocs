package com.dsantos.repository;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for storing and retrieving {@link Meeting} instances.
 * Follows the Repository pattern to decouple domain logic from persistence.
 */
public interface MeetingRepository {

    /** Persists a new meeting. Replaces any existing meeting with the same id. */
    void save(Meeting meeting);

    /**
     * Removes the meeting with the given id.
     *
     * @return {@code true} if the meeting existed and was removed, {@code false} otherwise.
     */
    boolean remove(UUID id);

    /** Returns the meeting with the given id, or empty if not found. */
    Optional<Meeting> findById(UUID id);

    /** Returns all stored meetings. */
    List<Meeting> findAll();

    /** Returns all meetings in which {@code person} is a participant. */
    List<Meeting> findByParticipant(Person person);
}

