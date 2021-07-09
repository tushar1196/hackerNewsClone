package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from post where title like 'Ask HN:%'", nativeQuery = true)
    List<Post> findAllByAskHN();

    @Query(value = "select * from post where title like 'Show HN:%'", nativeQuery = true)
    List<Post> findAllByShowHN();

    @Query(value = "select * from post  where (title LIKE %?1% OR user_name LIKE %?1% OR url Like %?1% OR " +
            "text LIKE %?1%) AND created_at>=?2 AND created_at<=?3 ORDER BY created_at DESC", nativeQuery = true)
    List<Post> findAllByKeyWordWithTimeDate(String keyWord, Timestamp dateFrom, Timestamp dateTo);

    @Query(value = "select * from post  where (title LIKE %?1% OR user_name LIKE %?1% OR url Like %?1% OR " +
            "text LIKE %?1%) AND created_at>=?2 AND created_at<=?3 ORDER BY points DESC", nativeQuery = true)
    List<Post> findAllByKeyWordWithPoints(String keyword, Timestamp dateFrom, Timestamp dateTo);

    @Query(value = "select * from post  where title LIKE %?1% OR user_name LIKE %?1% OR url Like %?1% OR" +
            " text LIKE %?1% ORDER BY created_at  DESC", nativeQuery = true)
    List<Post> findAllByKeyWordDescOrder(String keyWord);

    @Query(value = "select * from post  where title LIKE %?1% OR user_name LIKE %?1% OR url Like %?1% OR" +
            " text LIKE %?1% ORDER BY points  DESC", nativeQuery = true)
    List<Post> findAllByKeyWordPopularDescOrder(String keyWord);
}
