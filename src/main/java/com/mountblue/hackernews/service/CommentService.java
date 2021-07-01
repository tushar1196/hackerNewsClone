package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;

import java.util.List;

public interface CommentService {
    public void addComments(Comment comment);
    public List<Comment> getCommentByQuestionId(Integer questionId);
    public void updateCommentById(Comment comment, Integer commentId);
    public Comment getCommentById(Integer commentId);
    public void deleteCommentById(Integer id);
}
