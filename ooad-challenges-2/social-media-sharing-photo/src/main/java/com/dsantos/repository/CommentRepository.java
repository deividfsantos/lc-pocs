package com.dsantos.repository;

import com.dsantos.domain.Comment;
import com.dsantos.domain.Photo;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(String id);

    List<Comment> findByPhoto(Photo photo);

    void delete(String id);
}

