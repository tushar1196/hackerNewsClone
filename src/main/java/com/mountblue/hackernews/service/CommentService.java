package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.Post;

import java.util.List;

public interface CommentService {
    public void saveComment(Comment comment);

    public void updateCommentById(Comment comment, Integer commentId);

    public Comment getCommentById(Integer commentId);

    public void deleteCommentById(Integer id);

    public List<Comment> getCommentBySearch(String keyWord);



    public List<Comment> getCommentsByKeyWord(String keyWord, String startDate, String endDate);


    public List<Comment> getCommentByKeyWordWithPoints(String keyWord, String startDate, String endDate);
}
