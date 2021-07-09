package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Reply;

import java.util.List;

public interface ReplyService {

    void saveReply(Reply reply);

    List<Reply> getAllReplies();

    Reply getReplyById(Integer replyId);

    void updateReply(Reply reply);

    void deleteReply(Integer replyId);
}
