package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Reply;
import com.mountblue.hackernews.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyRepository replyRepository;


    @Override
    public void saveReply(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    public List<Reply> getReplyByCommentId(Integer commentId) {
        return replyRepository.getReplyByCommentId(commentId);
    }

    @Override
    public List<Reply> getAllReplies() {
        return replyRepository.findAll();
    }
}