package com.dsantos.service;

import com.dsantos.domain.Follow;
import com.dsantos.domain.User;
import com.dsantos.repository.FollowRepository;

import java.util.List;

public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Follow follow(User follower, User following) {
        if (follower.getId().equals(following.getId())) {
            throw new IllegalArgumentException("A user cannot follow themselves");
        }
        if (isFollowing(follower, following)) {
            throw new IllegalStateException(follower.getUsername() + " already follows " + following.getUsername());
        }
        return followRepository.save(new Follow(follower, following));
    }

    public void unfollow(User follower, User following) {
        followRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(f -> followRepository.delete(f.getId()));
    }

    public List<User> getFollowers(User user) {
        return followRepository.findByFollowing(user).stream()
                .map(Follow::getFollower)
                .toList();
    }

    public List<User> getFollowing(User user) {
        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .toList();
    }

    public boolean isFollowing(User follower, User following) {
        return followRepository.findByFollowerAndFollowing(follower, following).isPresent();
    }
}

