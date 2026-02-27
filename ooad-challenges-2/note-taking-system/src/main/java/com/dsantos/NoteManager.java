package com.dsantos;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class NoteManager {

    private final Repository repository;
    private final Storage storage;

    public NoteManager(String storageFilePath) {
        this(new NoteRepository(), new NoteFileStorage(storageFilePath));
    }

    public NoteManager(Repository repository, Storage storage) {
        this.repository = repository;
        this.storage = storage;
    }

    public Note addNote(String title, String content) {
        Note note = new Note(UUID.randomUUID().toString().substring(0, 8), title, content);
        repository.add(note);
        return note;
    }

    public boolean editNote(String id, String newTitle, String newContent) {
        return repository.edit(id, newTitle, newContent);
    }

    public boolean deleteNote(String id) {
        return repository.delete(id);
    }

    public void saveNotes() throws IOException {
        storage.save(repository.getAll());
    }

    public void sync() throws IOException {
        List<Note> notes = storage.load();
        repository.clear();
        for (Note note : notes) {
            repository.add(note);
        }
    }

    public List<Note> getAllNotes() {
        return repository.getAll();
    }
}
