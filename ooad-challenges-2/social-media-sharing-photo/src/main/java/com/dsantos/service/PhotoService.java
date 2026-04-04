package com.dsantos.service;

import com.dsantos.domain.Photo;
import com.dsantos.domain.Tag;
import com.dsantos.domain.User;
import com.dsantos.repository.PhotoRepository;
import com.dsantos.repository.TagRepository;

import java.util.List;
import java.util.Optional;

public class PhotoService {

    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    public PhotoService(PhotoRepository photoRepository, TagRepository tagRepository) {
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
    }

    public Photo publishPhoto(String imageUrl, String caption, User author) {
        Photo photo = new Photo(imageUrl, caption, author);
        return photoRepository.save(photo);
    }

    public void tagPhoto(Photo photo, String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
        photo.addTag(tag);
        photoRepository.save(photo);
    }

    public void removeTag(Photo photo, String tagName) {
        tagRepository.findByName(tagName).ifPresent(tag -> {
            photo.removeTag(tag);
            photoRepository.save(photo);
        });
    }

    public Optional<Photo> findById(String id) {
        return photoRepository.findById(id);
    }

    public List<Photo> findByUser(User user) {
        return photoRepository.findByAuthor(user);
    }

    public List<Photo> findByTag(String tagName) {
        return tagRepository.findByName(tagName)
                .map(photoRepository::findByTag)
                .orElse(List.of());
    }

    public void deletePhoto(String id) {
        photoRepository.delete(id);
    }
}

