package com.dsantos.repository;

import com.dsantos.domain.Like;
import com.dsantos.domain.Photo;
import com.dsantos.domain.User;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {

    Like save(Like like);

    List<Like> findByPhoto(Photo photo);

    List<Like> findByUser(User user);

    Optional<Like> findByUserAndPhoto(User user, Photo photo);

    void delete(String id);
}

