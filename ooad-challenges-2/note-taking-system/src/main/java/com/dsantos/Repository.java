package com.dsantos;

import java.util.List;

public interface Repository {
    void add(Note note);
    boolean edit(String id, String newTitle, String newContent);
    boolean delete(String id);
    List<Note> getAll();
    void clear();
}

