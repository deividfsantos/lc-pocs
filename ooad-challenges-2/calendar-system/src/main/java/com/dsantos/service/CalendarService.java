package com.dsantos.service;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;
import com.dsantos.exception.CalendarException;
import com.dsantos.repository.MeetingRepository;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CalendarService {

    private final MeetingRepository repository;
    private final MeetingValidator validator;
    private final TimeSuggester suggester;

    public CalendarService(MeetingRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repository must not be null");
        this.validator = new MeetingValidator(repository);
        this.suggester = new FreeSlotTimeSuggester(repository);
    }

    public Meeting bookMeeting(String title, TimeSlot timeSlot, List<Person> participants) {
        validator.validate(timeSlot, participants);
        Meeting meeting = new Meeting(title, timeSlot, participants);
        repository.save(meeting);
        return meeting;
    }

    public boolean removeMeeting(UUID id) {
        Objects.requireNonNull(id, "Meeting id must not be null");
        return repository.remove(id);
    }

    public List<Meeting> listMeetings(Person person) {
        Objects.requireNonNull(person, "Person must not be null");
        return repository.findByParticipant(person).stream()
                .sorted(Comparator.comparing(m -> m.getTimeSlot().getStart()))
                .toList();
    }

    public List<Meeting> listAllMeetings() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(m -> m.getTimeSlot().getStart()))
                .toList();
    }

    public List<TimeSlot> suggestTime(Person person1, Person person2,
                                      Duration duration, TimeSlot searchWindow) {
        return suggester.suggest(person1, person2, duration, searchWindow);
    }
}

