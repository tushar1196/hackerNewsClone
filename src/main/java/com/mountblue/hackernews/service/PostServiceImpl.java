package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Post> findPaginated(Integer pageNo, Integer pageSize, String sortingField, String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postRepository.findAll(pageable);
    }

    @Override
    public List<Post> getPostByKeyWord(String keyWord) {
        return this.postRepository.findAllByKeyWord(keyWord);
    }

    @Override
    public void deletePostById(int postId) {
        postRepository.deleteById(postId);
    }
}
