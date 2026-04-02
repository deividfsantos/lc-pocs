package com.dsantos.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Photo {

    private final String id;
    private String imageUrl;
    private String caption;
    private final LocalDateTime publishedAt;
    private final User author;
    private final List<Tag> tags;
    private final List<Comment> comments;

    public Photo(String imageUrl, String caption, User author) {
        this.id = UUID.randomUUID().toString();
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.author = author;
        this.publishedAt = LocalDateTime.now();
        this.tags = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public User getAuthor() {
        return author;
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Photo{id='" + id + "', caption='" + caption + "', author=" + author.getUsername() + "}";
    }
}
