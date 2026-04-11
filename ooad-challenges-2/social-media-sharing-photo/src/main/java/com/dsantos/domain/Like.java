package com.dsantos.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Like {

    private final String id;
    private final User user;
    private final Photo photo;
    private final LocalDateTime likedAt;

    public Like(User user, Photo photo) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.photo = photo;
        this.likedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Photo getPhoto() {
        return photo;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    @Override
    public String toString() {
        return "Like{user=" + user.getUsername() + ", photo=" + photo.getId() + "}";
    }
}

