package com.dsantos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteRepositoryTest {

    private NoteRepository repository;

    @BeforeEach
    void setUp() {
        repository = new NoteRepository();
    }

    @Test
    void addAndRetrieveNote() {
        Note note = new Note("1", "Title", "Content");
        repository.add(note);
        List<Note> all = repository.getAll();
        assertEquals(1, all.size());
        assertEquals("1", all.get(0).getId());
    }

    @Test
    void editExistingNote() {
        repository.add(new Note("1", "Old Title", "Old Content"));
        boolean result = repository.edit("1", "New Title", "New Content");
        assertTrue(result);
        Note updated = repository.getAll().get(0);
        assertEquals("New Title", updated.getTitle());
        assertEquals("New Content", updated.getContent());
    }

    @Test
    void editNonExistentNoteReturnsFalse() {
        boolean result = repository.edit("missing", "T", "C");
        assertFalse(result);
    }

    @Test
    void deleteExistingNote() {
        repository.add(new Note("1", "Title", "Content"));
        boolean result = repository.delete("1");
        assertTrue(result);
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void deleteNonExistentNoteReturnsFalse() {
        boolean result = repository.delete("missing");
        assertFalse(result);
    }

    @Test
    void clearRemovesAllNotes() {
        repository.add(new Note("1", "A", "B"));
        repository.add(new Note("2", "C", "D"));
        repository.clear();
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void getAllReturnsNotesInInsertionOrder() {
        repository.add(new Note("1", "First", ""));
        repository.add(new Note("2", "Second", ""));
        repository.add(new Note("3", "Third", ""));
        List<Note> all = repository.getAll();
        assertEquals("1", all.get(0).getId());
        assertEquals("2", all.get(1).getId());
        assertEquals("3", all.get(2).getId());
    }
}

