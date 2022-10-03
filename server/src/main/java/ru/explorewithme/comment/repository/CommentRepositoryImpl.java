package ru.explorewithme.comment.repository;

import org.springframework.context.annotation.Lazy;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final CommentRepository commentRepository;

    public CommentRepositoryImpl(@Lazy CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
