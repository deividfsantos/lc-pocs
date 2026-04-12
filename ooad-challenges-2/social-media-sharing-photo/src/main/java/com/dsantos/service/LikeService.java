package com.dsantos.service;

import com.dsantos.domain.Like;
import com.dsantos.domain.Photo;
import com.dsantos.domain.User;
import com.dsantos.repository.LikeRepository;

import java.util.List;

public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like likePhoto(User user, Photo photo) {
        if (isLikedBy(user, photo)) {
            throw new IllegalStateException(user.getUsername() + " already liked this photo");
        }
        return likeRepository.save(new Like(user, photo));
    }

    public void unlikePhoto(User user, Photo photo) {
        likeRepository.findByUserAndPhoto(user, photo)
                .ifPresent(l -> likeRepository.delete(l.getId()));
    }

    public int getLikeCount(Photo photo) {
        return likeRepository.findByPhoto(photo).size();
    }

    public boolean isLikedBy(User user, Photo photo) {
        return likeRepository.findByUserAndPhoto(user, photo).isPresent();
    }

    public List<Photo> getPhotosLikedByUser(User user) {
        return likeRepository.findByUser(user).stream()
                .map(Like::getPhoto)
                .toList();
    }
}

