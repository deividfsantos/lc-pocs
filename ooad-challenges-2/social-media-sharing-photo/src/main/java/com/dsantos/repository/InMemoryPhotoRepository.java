package com.dsantos.repository;

import com.dsantos.domain.Photo;
import com.dsantos.domain.Tag;
import com.dsantos.domain.User;

import java.util.*;

public class InMemoryPhotoRepository implements PhotoRepository {

    private final Map<String, Photo> store = new HashMap<>();

    @Override
    public Photo save(Photo photo) {
        store.put(photo.getId(), photo);
        return photo;
    }

    @Override
    public Optional<Photo> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Photo> findByAuthor(User author) {
        return store.values().stream()
                .filter(p -> p.getAuthor().getId().equals(author.getId()))
                .toList();
    }

    @Override
    public List<Photo> findByTag(Tag tag) {
        return store.values().stream()
                .filter(p -> p.getTags().contains(tag))
                .toList();
    }

    @Override
    public List<Photo> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}

