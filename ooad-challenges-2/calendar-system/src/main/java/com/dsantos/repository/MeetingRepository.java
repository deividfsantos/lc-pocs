package com.dsantos.repository;

import com.dsantos.domain.Meeting;
import com.dsantos.domain.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeetingRepository {

    void save(Meeting meeting);

    boolean remove(UUID id);

    Optional<Meeting> findById(UUID id);

    List<Meeting> findAll();

    List<Meeting> findByParticipant(Person person);
}

