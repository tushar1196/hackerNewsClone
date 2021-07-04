package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;

import java.util.List;

public interface CommentService {
    public void saveComment(Comment comment);
    public void updateCommentById(Comment comment, Integer commentId);
    public Comment getCommentById(Integer commentId);
    public void deleteCommentById(Integer id);
}
