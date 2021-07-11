package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.repository.CommentRepository;
import com.mountblue.hackernews.repository.PostRepository;
import com.mountblue.hackernews.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReplyRepository repository;

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void updateCommentById(Comment comment, Integer commentId) {
        Timestamp instant = Timestamp.from(Instant.now());
        comment.setCreatedAt(instant);
        comment.setUpdatedAt(instant);
        commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public void deleteCommentById(Integer id) {
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getCommentsByKeyWord(String keyWord, String startDate, String endDate) {
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            Timestamp dateFrom = Timestamp.from(Instant.parse(startDate + ":00.000Z"));
            Timestamp dateTo = Timestamp.from(Instant.parse(endDate + ":00.000Z"));

            Calendar calDateFrom = Calendar.getInstance();
            calDateFrom.setTimeInMillis(dateFrom.getTime());

            calDateFrom.add(Calendar.MINUTE, -30);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            calDateFrom.setTimeInMillis(dateFrom.getTime());
            calDateFrom.add(Calendar.HOUR, -5);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            Calendar calDateTo = Calendar.getInstance();
            calDateTo.setTimeInMillis(dateTo.getTime());

            calDateTo.add(Calendar.MINUTE, -30);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            calDateTo.setTimeInMillis(dateTo.getTime());
            calDateTo.add(Calendar.HOUR, -5);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            return this.commentRepository.findAllByKeyWordWithTimeDate(keyWord, dateFrom, dateTo);
        }
        return commentRepository.findCommentByKeyWord(keyWord);
    }

    @Override
    public List<Comment> getCommentsByKeyWordWithPoints(String keyWord, String startDate, String endDate) {
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            Timestamp dateFrom = Timestamp.from(Instant.parse(startDate + ":00.000Z"));
            Timestamp dateTo = Timestamp.from(Instant.parse(endDate + ":00.000Z"));

            Calendar calDateFrom = Calendar.getInstance();
            calDateFrom.setTimeInMillis(dateFrom.getTime());

            calDateFrom.add(Calendar.MINUTE, -30);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            calDateFrom.setTimeInMillis(dateFrom.getTime());
            calDateFrom.add(Calendar.HOUR, -5);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            Calendar calDateTo = Calendar.getInstance();
            calDateTo.setTimeInMillis(dateTo.getTime());

            calDateTo.add(Calendar.MINUTE, -30);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            calDateTo.setTimeInMillis(dateTo.getTime());
            calDateTo.add(Calendar.HOUR, -5);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            return commentRepository.findAllByKeyWordWithPoints(keyWord, dateFrom, dateTo);
        }
        return commentRepository.findCommentByKeyWord(keyWord);
    }
}
