package com.dsantos.service;

import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;

import java.time.Duration;
import java.util.List;

/**
 * Strategy interface for suggesting free meeting slots for two people.
 */
public interface TimeSuggester {

    /**
     * Suggests available {@link TimeSlot}s of the given {@code duration} within
     * {@code searchWindow} where both {@code person1} and {@code person2} are free.
     *
     * @param person1      first participant
     * @param person2      second participant
     * @param duration     required length of the meeting slot
     * @param searchWindow the time window to search within
     * @return list of available slots (may be empty), ordered by start time
     */
    List<TimeSlot> suggest(Person person1, Person person2, Duration duration, TimeSlot searchWindow);
}

