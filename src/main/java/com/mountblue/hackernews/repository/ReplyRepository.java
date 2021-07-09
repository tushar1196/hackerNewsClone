package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Transactional
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Query(value = "select * from replies where comment_id=?1", nativeQuery = true)
    public List<Reply> getReplyByCommentId(Integer commentId);

    @Query(value = "select * from replies where id=?1", nativeQuery = true)
    public Reply getReplyById(Integer replyId);

    @Modifying
    @Query(value = "update replies set reply=?1, update_at=?2 where id=?3", nativeQuery = true)
    public void updateReply(String description, Timestamp updatedAt, Integer id);

    @Modifying
    @Query(value = "delete from replies where id=?1", nativeQuery = true)
    public void deleteReply(Integer replyId);

}
