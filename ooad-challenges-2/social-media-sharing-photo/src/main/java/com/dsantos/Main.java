package com.dsantos;

import com.dsantos.domain.User;
import com.dsantos.repository.InMemoryCommentRepository;
import com.dsantos.repository.InMemoryPhotoRepository;
import com.dsantos.repository.InMemoryTagRepository;
import com.dsantos.repository.InMemoryUserRepository;
import com.dsantos.service.CommentService;
import com.dsantos.service.PhotoService;
import com.dsantos.service.TagService;
import com.dsantos.service.TimelineService;

public class Main {

    static void main() {
        var photoRepository = new InMemoryPhotoRepository();
        var tagRepository = new InMemoryTagRepository();

        var photoService = new PhotoService(photoRepository, tagRepository);
        var commentService = new CommentService(new InMemoryCommentRepository(), photoRepository);
        var tagService = new TagService(tagRepository, photoRepository);
        var timelineService = new TimelineService(photoRepository);
        var userRepository = new InMemoryUserRepository();

        var alice = userRepository.save(new User("alice", "alice@example.com"));
        var bob = userRepository.save(new User("bob", "bob@example.com"));
        var carol = userRepository.save(new User("carol", "carol@example.com"));

        var sunset = photoService.publishPhoto("https://imgs.example.com/sunset.jpg", "Beautiful sunset", alice);
        var coffee = photoService.publishPhoto("https://imgs.example.com/coffee.jpg", "Morning coffee", bob);
        var beach = photoService.publishPhoto("https://imgs.example.com/beach.jpg", "Weekend at the beach", carol);
        var city = photoService.publishPhoto("https://imgs.example.com/city.jpg", "City lights at night", alice);

        photoService.tagPhoto(sunset, "nature");
        photoService.tagPhoto(sunset, "sunset");
        photoService.tagPhoto(coffee, "coffee");
        photoService.tagPhoto(coffee, "morning");
        photoService.tagPhoto(beach, "nature");
        photoService.tagPhoto(beach, "beach");
        photoService.tagPhoto(city, "city");
        photoService.tagPhoto(city, "night");

        commentService.addComment("Stunning view!", bob, sunset);
        commentService.addComment("Love this shot!", carol, sunset);
        commentService.addComment("Where was this taken?", alice, beach);

        System.out.println("=== Alice's Timeline ===");
        timelineService.getUserTimeline(alice).getPhotos().forEach(p ->
                System.out.printf("  %s  %s  (%d comments)%n", p.getCaption(), p.getTags(), p.getComments().size())
        );

        System.out.println("\n=== Global Timeline ===");
        timelineService.getGlobalTimeline().getPhotos().forEach(p ->
                System.out.printf("  @%-6s \"%s\"  %s%n", p.getAuthor().getUsername(), p.getCaption(), p.getTags())
        );

        System.out.println("\n=== Photos tagged #nature ===");
        photoService.findByTag("nature").forEach(p ->
                System.out.printf("  \"%s\" by @%s%n", p.getCaption(), p.getAuthor().getUsername())
        );

        System.out.println("\n=== Top Tags ===");
        tagService.getMostUsedTags(5).forEach(t -> System.out.println("  " + t));

        System.out.println("\n=== Comments on \"" + sunset.getCaption() + "\" ===");
        commentService.getCommentsByPhoto(sunset).forEach(c ->
                System.out.printf("  @%s: %s%n", c.getAuthor().getUsername(), c.getContent())
        );
    }
}
