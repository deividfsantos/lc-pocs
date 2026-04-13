package com.dsantos;

import com.dsantos.domain.Photo;
import com.dsantos.domain.User;
import com.dsantos.repository.InMemoryLikeRepository;
import com.dsantos.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeServiceTest {

    private LikeService likeService;
    private User alice;
    private User bob;
    private Photo photo;

    @BeforeEach
    void setUp() {
        likeService = new LikeService(new InMemoryLikeRepository());
        alice = new User("alice", "alice@example.com");
        bob   = new User("bob",   "bob@example.com");
        photo = new Photo("https://img.com/1.jpg", "caption", alice);
    }

    @Test
    void likePhoto_registersLike() {
        likeService.likePhoto(bob, photo);

        assertTrue(likeService.isLikedBy(bob, photo));
        assertEquals(1, likeService.getLikeCount(photo));
    }

    @Test
    void likePhoto_throwsWhenAlreadyLiked() {
        likeService.likePhoto(bob, photo);

        assertThrows(IllegalStateException.class, () -> likeService.likePhoto(bob, photo));
    }

    @Test
    void unlikePhoto_removesLike() {
        likeService.likePhoto(bob, photo);
        likeService.unlikePhoto(bob, photo);

        assertFalse(likeService.isLikedBy(bob, photo));
        assertEquals(0, likeService.getLikeCount(photo));
    }

    @Test
    void getLikeCount_countsAllLikes() {
        likeService.likePhoto(alice, photo);
        likeService.likePhoto(bob, photo);

        assertEquals(2, likeService.getLikeCount(photo));
    }

    @Test
    void getPhotosLikedByUser_returnsLikedPhotos() {
        var photo2 = new Photo("https://img.com/2.jpg", "other", bob);
        likeService.likePhoto(alice, photo);
        likeService.likePhoto(alice, photo2);

        var liked = likeService.getPhotosLikedByUser(alice);

        assertEquals(2, liked.size());
    }
}

