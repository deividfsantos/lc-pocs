package com.dsantos;

import com.dsantos.domain.Photo;
import com.dsantos.domain.User;
import com.dsantos.repository.InMemoryFollowRepository;
import com.dsantos.repository.InMemoryPhotoRepository;
import com.dsantos.service.TimelineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimelineServiceTest {

    private InMemoryPhotoRepository photoRepository;
    private InMemoryFollowRepository followRepository;
    private TimelineService timelineService;
    private User alice;
    private User bob;

    @BeforeEach
    void setUp() {
        photoRepository  = new InMemoryPhotoRepository();
        followRepository = new InMemoryFollowRepository();
        timelineService  = new TimelineService(photoRepository, followRepository);
        alice = new User("alice", "alice@example.com");
        bob   = new User("bob",   "bob@example.com");
    }

    @Test
    void getUserTimeline_includesOwnPhotos() {
        photoRepository.save(new Photo("https://img.com/1.jpg", "my photo", alice));

        var timeline = timelineService.getUserTimeline(alice);

        assertEquals(1, timeline.size());
    }

    @Test
    void getUserTimeline_includesFollowedUsersPhotos() {
        photoRepository.save(new Photo("https://img.com/1.jpg", "bob's photo", bob));
        followRepository.save(new com.dsantos.domain.Follow(alice, bob));

        var timeline = timelineService.getUserTimeline(alice);

        assertEquals(1, timeline.size());
        assertEquals("bob", timeline.getPhotos().get(0).getAuthor().getUsername());
    }

    @Test
    void getUserTimeline_doesNotIncludeUnfollowedUsersPhotos() {
        photoRepository.save(new Photo("https://img.com/1.jpg", "bob's photo", bob));

        var timeline = timelineService.getUserTimeline(alice);

        assertTrue(timeline.isEmpty());
    }

    @Test
    void getGlobalTimeline_includesAllPhotos() {
        photoRepository.save(new Photo("https://img.com/1.jpg", "alice's", alice));
        photoRepository.save(new Photo("https://img.com/2.jpg", "bob's", bob));

        var timeline = timelineService.getGlobalTimeline();

        assertEquals(2, timeline.size());
    }
}

