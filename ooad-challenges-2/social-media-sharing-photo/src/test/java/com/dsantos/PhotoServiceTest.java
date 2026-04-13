package com.dsantos;

import com.dsantos.domain.Photo;
import com.dsantos.domain.User;
import com.dsantos.repository.InMemoryPhotoRepository;
import com.dsantos.repository.InMemoryTagRepository;
import com.dsantos.service.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhotoServiceTest {

    private PhotoService photoService;
    private User user;

    @BeforeEach
    void setUp() {
        photoService = new PhotoService(new InMemoryPhotoRepository(), new InMemoryTagRepository());
        user = new User("alice", "alice@example.com");
    }

    @Test
    void publishPhoto_savesAndReturnsPhoto() {
        Photo photo = photoService.publishPhoto("https://img.com/1.jpg", "My photo", user);

        assertNotNull(photo.getId());
        assertEquals("My photo", photo.getCaption());
        assertEquals(user, photo.getAuthor());
    }

    @Test
    void tagPhoto_addsTagToPhoto() {
        Photo photo = photoService.publishPhoto("https://img.com/1.jpg", "caption", user);
        photoService.tagPhoto(photo, "nature");

        assertEquals(1, photo.getTags().size());
        assertEquals("nature", photo.getTags().get(0).getName());
    }

    @Test
    void tagPhoto_doesNotAddDuplicateTag() {
        Photo photo = photoService.publishPhoto("https://img.com/1.jpg", "caption", user);
        photoService.tagPhoto(photo, "nature");
        photoService.tagPhoto(photo, "nature");

        assertEquals(1, photo.getTags().size());
    }

    @Test
    void findByTag_returnsMatchingPhotos() {
        Photo p1 = photoService.publishPhoto("https://img.com/1.jpg", "one", user);
        Photo p2 = photoService.publishPhoto("https://img.com/2.jpg", "two", user);
        photoService.tagPhoto(p1, "nature");
        photoService.tagPhoto(p2, "city");

        var results = photoService.findByTag("nature");

        assertEquals(1, results.size());
        assertEquals(p1.getId(), results.get(0).getId());
    }

    @Test
    void findByTag_returnsEmptyWhenTagDoesNotExist() {
        assertTrue(photoService.findByTag("unknown").isEmpty());
    }

    @Test
    void deletePhoto_removesPhoto() {
        Photo photo = photoService.publishPhoto("https://img.com/1.jpg", "caption", user);
        photoService.deletePhoto(photo.getId());

        assertTrue(photoService.findById(photo.getId()).isEmpty());
    }
}

