package com.dsantos.repository;

import com.dsantos.domain.Like;
import com.dsantos.domain.Photo;
import com.dsantos.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryLikeRepository implements LikeRepository {

    private final Map<String, Like> store = new HashMap<>();

    @Override
    public Like save(Like like) {
        store.put(like.getId(), like);
        return like;
    }

    @Override
    public List<Like> findByPhoto(Photo photo) {
        return store.values().stream()
                .filter(l -> l.getPhoto().getId().equals(photo.getId()))
                .toList();
    }

    @Override
    public List<Like> findByUser(User user) {
        return store.values().stream()
                .filter(l -> l.getUser().getId().equals(user.getId()))
                .toList();
    }

    @Override
    public Optional<Like> findByUserAndPhoto(User user, Photo photo) {
        return store.values().stream()
                .filter(l -> l.getUser().getId().equals(user.getId())
                        && l.getPhoto().getId().equals(photo.getId()))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}

