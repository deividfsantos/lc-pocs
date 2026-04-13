package com.dsantos;

import com.dsantos.domain.Photo;
import com.dsantos.domain.User;
import com.dsantos.repository.InMemoryFollowRepository;
import com.dsantos.service.FollowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FollowServiceTest {

    private FollowService followService;
    private User alice;
    private User bob;

    @BeforeEach
    void setUp() {
        followService = new FollowService(new InMemoryFollowRepository());
        alice = new User("alice", "alice@example.com");
        bob   = new User("bob",   "bob@example.com");
    }

    @Test
    void follow_createsRelationship() {
        followService.follow(alice, bob);

        assertTrue(followService.isFollowing(alice, bob));
    }

    @Test
    void follow_doesNotAffectReverseDirection() {
        followService.follow(alice, bob);

        assertFalse(followService.isFollowing(bob, alice));
    }

    @Test
    void follow_throwsWhenFollowingSelf() {
        assertThrows(IllegalArgumentException.class, () -> followService.follow(alice, alice));
    }

    @Test
    void follow_throwsWhenAlreadyFollowing() {
        followService.follow(alice, bob);

        assertThrows(IllegalStateException.class, () -> followService.follow(alice, bob));
    }

    @Test
    void unfollow_removesRelationship() {
        followService.follow(alice, bob);
        followService.unfollow(alice, bob);

        assertFalse(followService.isFollowing(alice, bob));
    }

    @Test
    void getFollowing_returnsFollowedUsers() {
        var carol = new User("carol", "carol@example.com");
        followService.follow(alice, bob);
        followService.follow(alice, carol);

        var following = followService.getFollowing(alice);

        assertEquals(2, following.size());
        assertTrue(following.contains(bob));
        assertTrue(following.contains(carol));
    }

    @Test
    void getFollowers_returnsUsersWhoFollow() {
        followService.follow(alice, bob);
        followService.follow(new User("carol", "carol@example.com"), bob);

        assertEquals(2, followService.getFollowers(bob).size());
    }
}

