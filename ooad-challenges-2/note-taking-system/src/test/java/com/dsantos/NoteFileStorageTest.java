package com.dsantos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteFileStorageTest {

    @TempDir
    Path tempDir;

    private String tempFile() {
        return tempDir.resolve("notes.txt").toString();
    }

    @Test
    void saveAndLoadRoundTrip() throws IOException {
        NoteFileStorage storage = new NoteFileStorage(tempFile());
        List<Note> notes = List.of(
                new Note("id1", "First", "Content one"),
                new Note("id2", "Second", "Content two")
        );
        storage.save(notes);
        List<Note> loaded = storage.load();
        assertEquals(2, loaded.size());
        assertEquals("id1", loaded.get(0).getId());
        assertEquals("First", loaded.get(0).getTitle());
        assertEquals("Content one", loaded.get(0).getContent());
        assertEquals("id2", loaded.get(1).getId());
        assertEquals("Second", loaded.get(1).getTitle());
    }

    @Test
    void loadFromNonExistentFileReturnsEmptyList() throws IOException {
        NoteFileStorage storage = new NoteFileStorage(tempDir.resolve("missing.txt").toString());
        List<Note> loaded = storage.load();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveEmptyListCreatesEmptyFile() throws IOException {
        NoteFileStorage storage = new NoteFileStorage(tempFile());
        storage.save(List.of());
        List<Note> loaded = storage.load();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveOverwritesPreviousContent() throws IOException {
        NoteFileStorage storage = new NoteFileStorage(tempFile());
        storage.save(List.of(new Note("id1", "Old", "Old content")));
        storage.save(List.of(new Note("id2", "New", "New content")));
        List<Note> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("id2", loaded.get(0).getId());
    }
}

