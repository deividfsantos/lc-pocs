package com.dsantos.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {

    private final String id;
    private String content;
    private final User author;
    private final Photo photo;
    private final LocalDateTime createdAt;

    public Comment(String content, User author, Photo photo) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.author = author;
        this.photo = photo;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public Photo getPhoto() {
        return photo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Comment{id='" + id + "', author=" + author.getUsername() + ", content='" + content + "'}";
    }
}

