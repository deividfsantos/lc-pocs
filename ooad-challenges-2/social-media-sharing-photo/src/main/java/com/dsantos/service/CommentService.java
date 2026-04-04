package com.dsantos.service;

import com.dsantos.domain.Comment;
import com.dsantos.domain.Photo;
import com.dsantos.domain.User;
import com.dsantos.repository.CommentRepository;
import com.dsantos.repository.PhotoRepository;

import java.util.List;
import java.util.Optional;

public class CommentService {

    private final CommentRepository commentRepository;
    private final PhotoRepository photoRepository;

    public CommentService(CommentRepository commentRepository, PhotoRepository photoRepository) {
        this.commentRepository = commentRepository;
        this.photoRepository = photoRepository;
    }

    public Comment addComment(String content, User author, Photo photo) {
        Comment comment = new Comment(content, author, photo);
        photo.addComment(comment);
        photoRepository.save(photo);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPhoto(Photo photo) {
        return commentRepository.findByPhoto(photo);
    }

    public Optional<Comment> editComment(String commentId, String newContent) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setContent(newContent);
            return commentRepository.save(comment);
        });
    }

    public void deleteComment(String commentId) {
        commentRepository.delete(commentId);
    }
}

