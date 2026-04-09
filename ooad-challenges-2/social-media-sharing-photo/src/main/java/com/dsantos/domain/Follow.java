package com.dsantos.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Follow {

    private final String id;
    private final User follower;
    private final User following;
    private final LocalDateTime followedAt;

    public Follow(User follower, User following) {
        this.id = UUID.randomUUID().toString();
        this.follower = follower;
        this.following = following;
        this.followedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowing() {
        return following;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    @Override
    public String toString() {
        return "Follow{" + follower.getUsername() + " -> " + following.getUsername() + "}";
    }
}

