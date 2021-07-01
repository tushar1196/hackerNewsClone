package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Comment;
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

    @Query(value = "select * from comments where question_id=?1", nativeQuery = true)
    public List<Comment> getAllByQuestionId(Integer id);

    @Modifying
    @Query(value = "update comments set name =?1, email=?2, description=?3," +
            " updated_at=?4 WHERE id =?5", nativeQuery = true)
    public void updateCommentByCommentId(@Param("name") String name, @Param("email") String email,
                                         @Param("description") String comment,
                                         @Param("updatedAt") Timestamp upDatedAt,
                                         Integer commentId);
    @Query(value = "select * from comments where id=?1",nativeQuery = true)
    public Comment findByCommentId(Integer commentId);
}
