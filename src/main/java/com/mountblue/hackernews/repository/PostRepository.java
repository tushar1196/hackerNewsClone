package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select * from post where title LIKE %?1% OR url Like %?1% OR text LIKE %?1%", nativeQuery = true)
    public List<Post> findAllByKeyWord( String keyWord);
    Post findById(int id);

}
