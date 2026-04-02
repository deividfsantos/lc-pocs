package com.dsantos.repository;

import com.dsantos.domain.Photo;
import com.dsantos.domain.Tag;
import com.dsantos.domain.User;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository {

    Photo save(Photo photo);

    Optional<Photo> findById(String id);

    List<Photo> findByAuthor(User author);

    List<Photo> findByTag(Tag tag);

    List<Photo> findAll();

    void delete(String id);
}

