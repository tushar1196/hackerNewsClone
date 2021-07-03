package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

    List<Post> getAllByAskHN();

    void savePost(Post question);

    Post getPostById(Integer id);

    List<Post> getAllByShowHN();

    List<Post> findAll();
}
