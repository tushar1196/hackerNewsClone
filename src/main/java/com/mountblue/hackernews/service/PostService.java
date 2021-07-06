package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    List<Post> getAllByAskHN();

    void savePost(Post question);

    Post getPostById(Integer id);

    List<Post> getAllByShowHN();

    List<Post> findAll();

    Page<Post> findPaginated(Integer pageNo, Integer pageSize, String sortingField, String sortingOrder);

    List<Post> getPostByKeyWord(String keyWord, String startDate, String endDate);

    void deletePostById(int postId);

    List<Post> getPostByKeyWordWithPoints(String keyWord, String startDate, String endDate);
}
