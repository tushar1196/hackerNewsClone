package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from post where title like 'Ask HN:%'", nativeQuery = true)
    List<Post> findAllByAskHN();

    @Query(value = "select * from post where title like 'Show HN:%'", nativeQuery = true)
    List<Post> findAllByShowHN();

    Post findById(int id);
}
