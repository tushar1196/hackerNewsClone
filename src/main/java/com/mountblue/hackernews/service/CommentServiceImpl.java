package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentByQuestionId(Integer questionId) {
        return commentRepository.getAllByQuestionId(questionId);
    }

    @Override
    public void updateCommentById(Comment comment, Integer commentId) {
        Timestamp instant = Timestamp.from(Instant.now());
        commentRepository.updateCommentByCommentId(comment.getName(), comment.getEmail(), comment.getDescription(),
                instant, commentId);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findByCommentId(commentId);
    }

    @Override
    public void deleteCommentById(Integer id) {
        Comment comment=commentRepository.findByCommentId(id);
        commentRepository.delete(comment);
    }
}
