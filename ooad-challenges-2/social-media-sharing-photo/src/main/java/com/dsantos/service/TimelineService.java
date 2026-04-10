package com.dsantos.service;

import com.dsantos.domain.Follow;
import com.dsantos.domain.Photo;
import com.dsantos.domain.Timeline;
import com.dsantos.domain.User;
import com.dsantos.repository.FollowRepository;
import com.dsantos.repository.PhotoRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimelineService {

    private final PhotoRepository photoRepository;
    private final FollowRepository followRepository;

    public TimelineService(PhotoRepository photoRepository, FollowRepository followRepository) {
        this.photoRepository = photoRepository;
        this.followRepository = followRepository;
    }

    public Timeline getUserTimeline(User user) {
        Timeline timeline = new Timeline(user);

        List<User> sources = new ArrayList<>();
        sources.add(user);
        followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .forEach(sources::add);

        List<Photo> photos = sources.stream()
                .flatMap(u -> photoRepository.findByAuthor(u).stream())
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

