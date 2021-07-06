package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment);

    void updateCommentById(Comment comment, Integer commentId);

    Comment getCommentById(Integer commentId);

    void deleteCommentById(Integer id);

    List<Comment> getCommentBySearch(String keyWord);

    List<Comment> getCommentsByKeyWord(String keyWord, String startDate, String endDate);

    List<Comment> getCommentByKeyWordWithPoints(String keyWord, String startDate, String endDate);
}
