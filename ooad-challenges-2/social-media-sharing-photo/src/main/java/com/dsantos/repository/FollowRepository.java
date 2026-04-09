package com.dsantos.repository;

import com.dsantos.domain.Follow;
import com.dsantos.domain.User;

import java.util.List;
import java.util.Optional;

public interface FollowRepository {

    Follow save(Follow follow);

    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowing(User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    void delete(String id);
}

