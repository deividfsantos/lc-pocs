package com.dsantos.repository;

import com.dsantos.domain.Follow;
import com.dsantos.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryFollowRepository implements FollowRepository {

    private final Map<String, Follow> store = new HashMap<>();

    @Override
    public Follow save(Follow follow) {
        store.put(follow.getId(), follow);
        return follow;
    }

    @Override
    public List<Follow> findByFollower(User follower) {
        return store.values().stream()
                .filter(f -> f.getFollower().getId().equals(follower.getId()))
                .toList();
    }

    @Override
    public List<Follow> findByFollowing(User following) {
        return store.values().stream()
                .filter(f -> f.getFollowing().getId().equals(following.getId()))
                .toList();
    }

    @Override
    public Optional<Follow> findByFollowerAndFollowing(User follower, User following) {
        return store.values().stream()
                .filter(f -> f.getFollower().getId().equals(follower.getId())
                        && f.getFollowing().getId().equals(following.getId()))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}

