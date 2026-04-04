package com.dsantos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Timeline {

    private final User owner;
    private final List<Photo> photos;

    public Timeline(User owner) {
        this.owner = owner;
        this.photos = new ArrayList<>();
    }

    public User getOwner() {
        return owner;
    }

    public List<Photo> getPhotos() {
        return Collections.unmodifiableList(photos);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public boolean isEmpty() {
        return photos.isEmpty();
    }

    public int size() {
        return photos.size();
    }

    @Override
    public String toString() {
        return "Timeline{owner=" + owner.getUsername() + ", photos=" + photos.size() + "}";
    }
}

