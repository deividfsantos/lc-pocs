package com.dsantos.repository;

import com.dsantos.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findByName(String name);

    List<Tag> findAll();

    void delete(String name);
}

