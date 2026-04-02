package com.dsantos.repository;

import com.dsantos.domain.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTagRepository implements TagRepository {

    private final Map<String, Tag> store = new HashMap<>();

    @Override
    public Tag save(Tag tag) {
        store.put(tag.getName(), tag);
        return tag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase().trim()));
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String name) {
        store.remove(name.toLowerCase().trim());
    }
}

