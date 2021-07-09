package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Reply;

import java.util.List;

public interface ReplyService {
    public void saveReply(Reply reply);
    public List<Reply> getReplyByCommentId(Integer commentId);
    public List<Reply> getAllReplies();
    public Reply getReplyById(Integer replyId);
    public void updateReply(Reply reply);
    public void deleteReply(Integer replyId);
}
