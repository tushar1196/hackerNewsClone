package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Reply;

import java.util.List;

public interface ReplyService {
    public void saveReply(Reply reply);
    public List<Reply> getReplyByCommentId(Integer commentId);
    public List<Reply> getAllReplies();
}
