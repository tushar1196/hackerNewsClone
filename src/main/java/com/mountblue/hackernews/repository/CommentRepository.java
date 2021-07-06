package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment,Integer> {

   // Comment findById(int commentId );

    @Query(value = "select * from comments where description LIKE %?1%", nativeQuery = true)
    public List<Comment> findCommentByKeyWord(String keyWord);

    @Query(value = "select * from comments  where description LIKE %?1% AND created_at>=?2 AND" +
            " created_at<=?3 ORDER BY created_at DESC", nativeQuery = true)
    public List<Comment> findAllByKeyWordWithTimeDate(String keyWord, Timestamp dateFrom, Timestamp dateTo);
    Post findById(int id);

    @Query(value = "select * from post  where description LIKE %?1% AND created_at>=?2" +
            " AND created_at<=?3 ORDER BY points DESC",
            nativeQuery = true)
    public List<Comment> findAllByKeyWordWithPoints(String keyword, Timestamp dateFrom, Timestamp dateTo);
}
