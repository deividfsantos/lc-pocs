package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.repository.MeetingRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FreeSlotTimeSuggester implements TimeSuggester {

    private static final Duration DEFAULT_STEP = Duration.ofMinutes(15);

    private final MeetingRepository repository;
    private final Duration step;

    public FreeSlotTimeSuggester(MeetingRepository repository) {
        this(repository, DEFAULT_STEP);
    }

    public FreeSlotTimeSuggester(MeetingRepository repository, Duration step) {
        this.repository = Objects.requireNonNull(repository, "Repository must not be null");
        Objects.requireNonNull(step, "Step must not be null");
        if (step.isNegative() || step.isZero()) {
            throw new IllegalArgumentException("Step must be positive");
        }
        this.step = step;
    }

    @Override
    public List<TimeSlot> suggest(Person person1, Person person2, Duration duration, TimeSlot searchWindow) {
        Objects.requireNonNull(person1, "person1 must not be null");
        Objects.requireNonNull(person2, "person2 must not be null");
        Objects.requireNonNull(duration, "duration must not be null");
        Objects.requireNonNull(searchWindow, "searchWindow must not be null");
        if (duration.isNegative() || duration.isZero()) {
            throw new IllegalArgumentException("Duration must be positive");
        }

        // Collect and merge all busy slots for both participants
        List<TimeSlot> busy = Stream.concat(
                        repository.findByParticipant(person1).stream(),
                        repository.findByParticipant(person2).stream())
                .map(Meeting::getTimeSlot)
                .sorted(Comparator.comparing(TimeSlot::getStart))
                .toList();

        List<TimeSlot> mergedBusy = merge(busy);

        // Scan the search window stepping through free gaps
        List<TimeSlot> suggestions = new ArrayList<>();
        LocalDateTime cursor = searchWindow.getStart();

        for (TimeSlot busySlot : mergedBusy) {
            // Skip busy slots entirely before the cursor
            if (!busySlot.getEnd().isAfter(cursor)) continue;

            // Scan the free gap between cursor and the start of this busy slot
            LocalDateTime gapEnd = busySlot.getStart().isBefore(searchWindow.getEnd())
                    ? busySlot.getStart()
                    : searchWindow.getEnd();
            suggestions.addAll(slotsInGap(cursor, gapEnd, duration));

            // Advance cursor past this busy slot
            if (busySlot.getEnd().isAfter(cursor)) {
                cursor = busySlot.getEnd();
            }
        }

        // Handle the trailing free gap after all busy slots
        if (cursor.isBefore(searchWindow.getEnd())) {
            suggestions.addAll(slotsInGap(cursor, searchWindow.getEnd(), duration));
        }

        return List.copyOf(suggestions);
    }

    private List<TimeSlot> merge(List<TimeSlot> sorted) {
        List<TimeSlot> merged = new ArrayList<>();
        for (TimeSlot ts : sorted) {
            if (merged.isEmpty()) {
                merged.add(ts);
            } else {
                TimeSlot last = merged.get(merged.size() - 1);
                // Merge if overlapping or adjacent
                if (!ts.getStart().isAfter(last.getEnd())) {
                    LocalDateTime newEnd = last.getEnd().isAfter(ts.getEnd()) ? last.getEnd() : ts.getEnd();
                    merged.set(merged.size() - 1, new TimeSlot(last.getStart(), newEnd));
                } else {
                    merged.add(ts);
                }
            }
        }
        return merged;
    }

    private List<TimeSlot> slotsInGap(LocalDateTime gapStart, LocalDateTime gapEnd, Duration duration) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalDateTime cursor = gapStart;
        while (!cursor.plus(duration).isAfter(gapEnd)) {
            slots.add(new TimeSlot(cursor, cursor.plus(duration)));
            cursor = cursor.plus(step);
        }
        return slots;
    }
}

