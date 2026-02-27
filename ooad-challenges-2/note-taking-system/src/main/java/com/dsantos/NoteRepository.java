package com.dsantos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NoteRepository {

    private final Map<String, Note> store = new LinkedHashMap<>();

    public void add(Note note) {
        store.put(note.getId(), note);
    }

    public boolean edit(String id, String newTitle, String newContent) {
        Note note = store.get(id);
        if (note == null) return false;
        note.setTitle(newTitle);
        note.setContent(newContent);
        return true;
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }

    public List<Note> getAll() {
        return new ArrayList<>(store.values());
    }

    public void clear() {
        store.clear();
    }
}

