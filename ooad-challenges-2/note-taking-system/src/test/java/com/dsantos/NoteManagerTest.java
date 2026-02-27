package com.dsantos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteManagerTest {

    @TempDir
    Path tempDir;

    private NoteManager manager;

    @BeforeEach
    void setUp() {
        manager = new NoteManager(tempDir.resolve("notes.txt").toString());
    }

    @Test
    void addNoteStoresIt() {
        Note note = manager.addNote("Title", "Content");
        assertNotNull(note.getId());
        assertEquals("Title", note.getTitle());
        assertEquals("Content", note.getContent());
        assertEquals(1, manager.getAllNotes().size());
    }

    @Test
    void addMultipleNotes() {
        manager.addNote("A", "1");
        manager.addNote("B", "2");
        manager.addNote("C", "3");
        assertEquals(3, manager.getAllNotes().size());
    }

    @Test
    void editExistingNote() {
        Note note = manager.addNote("Old Title", "Old Content");
        boolean result = manager.editNote(note.getId(), "New Title", "New Content");
        assertTrue(result);
        Note updated = manager.getAllNotes().get(0);
        assertEquals("New Title", updated.getTitle());
        assertEquals("New Content", updated.getContent());
    }

    @Test
    void editNonExistentNoteReturnsFalse() {
        assertFalse(manager.editNote("nonexistent", "T", "C"));
    }

    @Test
    void deleteExistingNote() {
        Note note = manager.addNote("Title", "Content");
        boolean result = manager.deleteNote(note.getId());
        assertTrue(result);
        assertTrue(manager.getAllNotes().isEmpty());
    }

    @Test
    void deleteNonExistentNoteReturnsFalse() {
        assertFalse(manager.deleteNote("nonexistent"));
    }

    @Test
    void saveAndSyncRestoresNotes() throws IOException {
        manager.addNote("Persisted", "Body");
        manager.saveNotes();
        manager.addNote("InMemoryOnly", "Ignored after sync");
        manager.sync();
        List<Note> notes = manager.getAllNotes();
        assertEquals(1, notes.size());
        assertEquals("Persisted", notes.get(0).getTitle());
    }

    @Test
    void syncFromEmptyFileResultsInEmptyList() throws IOException {
        manager.addNote("Temp", "Data");
        manager.sync();
        assertTrue(manager.getAllNotes().isEmpty());
    }
}

