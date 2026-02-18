package com.dsantos;

import java.util.*;
import java.util.stream.Collectors;

public class FileRepository {
    private final Map<UUID, FileMetadata> store = new LinkedHashMap<>();

    public void save(FileMetadata meta) {
        store.put(meta.id(), meta);
    }

    public Optional<FileMetadata> get(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public void delete(UUID id) {
        store.remove(id);
    }

    public List<FileMetadata> listAll() {
        return new ArrayList<>(store.values());
    }

    public List<FileMetadata> searchByName(String query) {
        String q = query == null ? "" : query.toLowerCase();
        return store.values().stream()
                .filter(m -> m.originalName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}
