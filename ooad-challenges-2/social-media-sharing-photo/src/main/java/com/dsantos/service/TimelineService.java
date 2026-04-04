package com.dsantos.service;

import com.dsantos.domain.Photo;
import com.dsantos.domain.Timeline;
import com.dsantos.domain.User;
import com.dsantos.repository.PhotoRepository;

import java.util.Comparator;
import java.util.List;

public class TimelineService {

    private final PhotoRepository photoRepository;

    public TimelineService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Timeline getUserTimeline(User user) {
        Timeline timeline = new Timeline(user);

        List<Photo> photos = photoRepository.findByAuthor(user).stream()
                .sorted(Comparator.comparing(Photo::getPublishedAt).reversed())
                .toList();

        photos.forEach(timeline::addPhoto);
        return timeline;
    }

    public Timeline getGlobalTimeline() {
        Timeline timeline = new Timeline(new User("global", "global"));

        List<Photo> photos = photoRepository.findAll().stream()
                .sorted(Comparator.comparing(Photo::getPublishedAt).reversed())
                .toList();

        photos.forEach(timeline::addPhoto);
        return timeline;
    }
}

