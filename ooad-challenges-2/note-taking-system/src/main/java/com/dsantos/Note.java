package com.dsantos;

import java.time.LocalDateTime;

public class Note {

    private final String id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public Note(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (updated: %s)", id, title, content, updatedAt);
    }
}

