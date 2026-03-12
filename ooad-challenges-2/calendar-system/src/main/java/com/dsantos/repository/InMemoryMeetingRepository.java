package com.dsantos.repository;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Thread-unsafe in-memory implementation of {@link MeetingRepository}.
 * Uses a {@link HashMap} keyed by meeting UUID.
 */
public class InMemoryMeetingRepository implements MeetingRepository {

    private final Map<UUID, Meeting> store = new HashMap<>();

    @Override
    public void save(Meeting meeting) {
        Objects.requireNonNull(meeting, "Meeting must not be null");
        store.put(meeting.getId(), meeting);
    }

    @Override
    public boolean remove(UUID id) {
        Objects.requireNonNull(id, "Id must not be null");
        return store.remove(id) != null;
    }

    @Override
    public Optional<Meeting> findById(UUID id) {
        Objects.requireNonNull(id, "Id must not be null");
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Meeting> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public List<Meeting> findByParticipant(Person person) {
        Objects.requireNonNull(person, "Person must not be null");
        return store.values().stream()
                .filter(m -> m.hasParticipant(person))
                .collect(Collectors.toUnmodifiableList());
    }
}

