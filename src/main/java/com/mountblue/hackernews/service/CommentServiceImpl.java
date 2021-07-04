package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.repository.CommentRepository;
import com.mountblue.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void updateCommentById(Comment comment, Integer commentId) {
        Timestamp instant = Timestamp.from(Instant.now());
        comment.setUpdatedAt(instant);
        commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        return comment;
    }

    @Override
    public void deleteCommentById(Integer id) {
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }
}
