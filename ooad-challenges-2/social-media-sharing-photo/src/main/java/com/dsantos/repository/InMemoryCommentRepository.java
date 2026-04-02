package com.dsantos.repository;

import com.dsantos.domain.Comment;
import com.dsantos.domain.Photo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCommentRepository implements CommentRepository {

    private final Map<String, Comment> store = new HashMap<>();

    @Override
    public Comment save(Comment comment) {
        store.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Comment> findByPhoto(Photo photo) {
        return store.values().stream()
                .filter(c -> c.getPhoto().getId().equals(photo.getId()))
                .toList();
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}

