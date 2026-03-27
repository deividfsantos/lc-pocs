package com.dsantos.model;

import com.dsantos.model.enums.SchoolDay;

import java.time.LocalTime;
import java.util.UUID;

public class TimeSlot {

    private final String id;
    private final SchoolDay day;
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(SchoolDay day, LocalTime start, LocalTime end) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.id = UUID.randomUUID().toString();
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(TimeSlot other) {
        if (this.day != other.day) {
            return false;
        }
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    public String getId() {
        return id;
    }

    public SchoolDay getDay() {
        return day;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return day + " " + start + "-" + end;
    }
}

