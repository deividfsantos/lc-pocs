package com.dsantos;

import com.dsantos.repository.InMemoryCommentRepository;
import com.dsantos.repository.InMemoryFollowRepository;
import com.dsantos.repository.InMemoryLikeRepository;
import com.dsantos.repository.InMemoryPhotoRepository;
import com.dsantos.repository.InMemoryTagRepository;
import com.dsantos.repository.InMemoryUserRepository;
import com.dsantos.service.CommentService;
import com.dsantos.service.FollowService;
import com.dsantos.service.LikeService;
import com.dsantos.service.PhotoService;
import com.dsantos.service.TagService;
import com.dsantos.service.TimelineService;
import com.dsantos.service.UserService;

public class Main {

    public static void main(String[] args) {
        var userRepository   = new InMemoryUserRepository();
        var photoRepository  = new InMemoryPhotoRepository();
        var tagRepository    = new InMemoryTagRepository();
        var followRepository = new InMemoryFollowRepository();
        var likeRepository   = new InMemoryLikeRepository();

        var userService     = new UserService(userRepository);
        var photoService    = new PhotoService(photoRepository, tagRepository);
        var commentService  = new CommentService(new InMemoryCommentRepository(), photoRepository);
        var tagService      = new TagService(tagRepository, photoRepository);
        var followService   = new FollowService(followRepository);
        var likeService     = new LikeService(likeRepository);
        var timelineService = new TimelineService(photoRepository, followRepository);

        var alice = userService.registerUser("alice", "alice@example.com");
        var bob   = userService.registerUser("bob",   "bob@example.com");
        var carol = userService.registerUser("carol", "carol@example.com");

        followService.follow(alice, bob);
        followService.follow(alice, carol);
        followService.follow(bob, alice);

        var sunset = photoService.publishPhoto("https://imgs.example.com/sunset.jpg", "Beautiful sunset", alice);
        var coffee = photoService.publishPhoto("https://imgs.example.com/coffee.jpg", "Morning coffee", bob);
        var beach  = photoService.publishPhoto("https://imgs.example.com/beach.jpg",  "Weekend at the beach", carol);
        var city   = photoService.publishPhoto("https://imgs.example.com/city.jpg",   "City lights at night", alice);

        photoService.tagPhoto(sunset, "nature"); photoService.tagPhoto(sunset, "sunset");
        photoService.tagPhoto(coffee, "coffee"); photoService.tagPhoto(coffee, "morning");
        photoService.tagPhoto(beach,  "nature"); photoService.tagPhoto(beach,  "beach");
        photoService.tagPhoto(city,   "city");   photoService.tagPhoto(city,   "night");

        commentService.addComment("Stunning view!", bob, sunset);
        commentService.addComment("Love this shot!", carol, sunset);
        commentService.addComment("Where was this taken?", alice, beach);

        likeService.likePhoto(bob,   sunset);
        likeService.likePhoto(carol, sunset);
        likeService.likePhoto(alice, coffee);
        likeService.likePhoto(bob,   city);

        System.out.println("=== Alice's Timeline (follows bob and carol) ===");
        timelineService.getUserTimeline(alice).getPhotos().forEach(p ->
            System.out.printf("  @%-6s \"%s\"  %s  (%d likes)%n",
                p.getAuthor().getUsername(), p.getCaption(), p.getTags(), likeService.getLikeCount(p))
        );

        System.out.println("\n=== Global Timeline ===");
        timelineService.getGlobalTimeline().getPhotos().forEach(p ->
            System.out.printf("  @%-6s \"%s\"%n", p.getAuthor().getUsername(), p.getCaption())
        );

        System.out.println("\n=== Photos liked by alice ===");
        likeService.getPhotosLikedByUser(alice).forEach(p ->
            System.out.printf("  \"%s\" by @%s%n", p.getCaption(), p.getAuthor().getUsername())
        );

        System.out.println("\n=== Top Tags ===");
        tagService.getMostUsedTags(5).forEach(t -> System.out.println("  " + t));

        System.out.println("\n=== Comments on \"" + sunset.getCaption() + "\" ===");
        commentService.getCommentsByPhoto(sunset).forEach(c ->
            System.out.printf("  @%s: %s%n", c.getAuthor().getUsername(), c.getContent())
        );

        System.out.printf("%nalice follows %d users | %d users follow alice%n",
            followService.getFollowing(alice).size(),
            followService.getFollowers(alice).size());
    }
}
