package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllByAskHN() {
        return postRepository.findAllByAskHN();

    }

    @Override
    public void savePost(Post post) {
        post.setHide(false);
        postRepository.save(post);
    }

    @Override
    public Post getPostById(Integer id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAllByShowHN() {
        return postRepository.findAllByShowHN();
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

}
