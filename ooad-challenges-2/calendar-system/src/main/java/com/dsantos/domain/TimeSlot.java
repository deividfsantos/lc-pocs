package com.dsantos.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable value object representing a time interval with a start and end.
 * The start must be strictly before the end.
 */
public final class TimeSlot {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "Start time must not be null");
        Objects.requireNonNull(end, "End time must not be null");
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException(
                    "Start time must be before end time, but got: " + start + " >= " + end);
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns true if this slot and {@code other} share any common time,
     * excluding touching boundaries (adjacent slots do NOT overlap).
     */
    public boolean overlaps(TimeSlot other) {
        Objects.requireNonNull(other, "Other time slot must not be null");
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    /**
     * Returns true if {@code other} is fully contained within this slot
     * (inclusive of boundaries).
     */
    public boolean contains(TimeSlot other) {
        Objects.requireNonNull(other, "Other time slot must not be null");
        return !this.start.isAfter(other.start) && !this.end.isBefore(other.end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot other)) return false;
        return start.equals(other.start) && end.equals(other.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "TimeSlot{" + start + " -> " + end + "}";
    }
}

