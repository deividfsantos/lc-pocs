package com.dsantos.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Meeting {

    private final UUID id;
    private final String title;
    private final TimeSlot timeSlot;
    private final List<Person> participants;

    public Meeting(String title, TimeSlot timeSlot, List<Person> participants) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(timeSlot, "TimeSlot must not be null");
        Objects.requireNonNull(participants, "Participants must not be null");
        if (title.isBlank()) throw new IllegalArgumentException("Title must not be blank");
        if (participants.isEmpty()) throw new IllegalArgumentException("Meeting must have at least one participant");

        this.id = UUID.randomUUID();
        this.title = title;
        this.timeSlot = timeSlot;
        this.participants = Collections.unmodifiableList(List.copyOf(participants));
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public boolean hasParticipant(Person person) {
        return participants.contains(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meeting{id=" + id + ", title='" + title + "', slot=" + timeSlot + '}';
    }
}