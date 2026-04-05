package com.dsantos.service;

import com.dsantos.domain.Photo;
import com.dsantos.domain.Tag;
import com.dsantos.repository.PhotoRepository;
import com.dsantos.repository.TagRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagService {

    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;

    public TagService(TagRepository tagRepository, PhotoRepository photoRepository) {
        this.tagRepository = tagRepository;
        this.photoRepository = photoRepository;
    }

    public Tag findOrCreate(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }

    public List<Photo> findPhotosByTag(String tagName) {
        return tagRepository.findByName(tagName)
                .map(photoRepository::findByTag)
                .orElse(List.of());
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getMostUsedTags(int limit) {
        Map<Tag, Long> tagCount = photoRepository.findAll().stream()
                .flatMap(photo -> photo.getTags().stream())
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));

        return tagCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }
}

