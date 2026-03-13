package com.dsantos.service;

import com.dsantos.domain.Person;
import com.dsantos.domain.TimeSlot;

import java.time.Duration;
import java.util.List;

public interface TimeSuggester {

    List<TimeSlot> suggest(Person person1, Person person2, Duration duration, TimeSlot searchWindow);
}

