package com.dsantos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Test
    void constructorSetsFields() {
        Note note = new Note("abc123", "Title", "Content");
        assertEquals("abc123", note.getId());
        assertEquals("Title", note.getTitle());
        assertEquals("Content", note.getContent());
        assertNotNull(note.getUpdatedAt());
    }

    @Test
    void setTitleUpdatesTimestamp() throws InterruptedException {
        Note note = new Note("1", "Old", "Body");
        LocalDateTime before = note.getUpdatedAt();
        Thread.sleep(10);
        note.setTitle("New");
        assertEquals("New", note.getTitle());
        assertTrue(note.getUpdatedAt().isAfter(before));
    }

    @Test
    void setContentUpdatesTimestamp() throws InterruptedException {
        Note note = new Note("1", "Title", "Old");
        LocalDateTime before = note.getUpdatedAt();
        Thread.sleep(10);
        note.setContent("New");
        assertEquals("New", note.getContent());
        assertTrue(note.getUpdatedAt().isAfter(before));
    }

    @Test
    void toStringContainsAllFields() {
        Note note = new Note("id1", "MyTitle", "MyContent");
        String str = note.toString();
        assertTrue(str.contains("id1"));
        assertTrue(str.contains("MyTitle"));
        assertTrue(str.contains("MyContent"));
    }
}

