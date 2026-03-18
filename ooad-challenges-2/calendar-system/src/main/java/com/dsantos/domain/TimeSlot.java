package com.dsantos.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public record TimeSlot(LocalDateTime start, LocalDateTime end) {

    public TimeSlot {
        Objects.requireNonNull(start, "Start time must not be null");
        Objects.requireNonNull(end, "End time must not be null");
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException(
                    "Start time must be before end time, but got: " + start + " >= " + end);
        }
    }

    public boolean overlaps(TimeSlot other) {
        Objects.requireNonNull(other, "Other time slot must not be null");
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

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
    public String toString() {
        return "TimeSlot{" + start + " -> " + end + "}";
    }
}

