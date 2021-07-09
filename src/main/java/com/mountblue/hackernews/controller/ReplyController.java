package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.model.Reply;
import com.mountblue.hackernews.model.User;
import com.mountblue.hackernews.service.CommentService;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.ReplyService;
import com.mountblue.hackernews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/addReply/{commentId}/{postId}")
    public String addReplyForm(@PathVariable("commentId") Integer commentId, @PathVariable("postId") Integer postId,
                           Model model){
        Reply reply=new Reply();
        model.addAttribute("comment",commentService.getCommentById(commentId));
        model.addAttribute("saveOrUpdate","save");
        model.addAttribute("postId", postId);
        model.addAttribute("helperReply", reply);

        return  "addreply";
    }

    @PostMapping("/addReply/{id}")
    public String saveReply(@PathVariable("id") Integer postId, @RequestParam("commentId") Integer commentId,
                            @ModelAttribute("reply") Reply reply, @RequestParam("saveorupdate") String saveOrUpdate, Authentication authentication){
        Timestamp instant = Timestamp.from(Instant.now());
        User user = userService.findByEmail(authentication.getName());

        reply.setUpdatedAt(instant);
        reply.setName(user.getName());
        reply.setCommentId(commentId);

        if(saveOrUpdate.equals("save")) {
            replyService.saveReply(reply);
        }
        else {
            replyService.updateReply(reply);
        }
        return "redirect:/post/"+postId;
    }

    @GetMapping("/updateReplyForm/{replyId}/{postId}")
    public String updateReply(@PathVariable("replyId") Integer replyId, @PathVariable("postId") Integer postId,
                              Model model, @RequestParam("commentId") Integer commentId){
        Reply reply=replyService.getReplyById(replyId);

        model.addAttribute("comment",commentService.getCommentById(commentId));
        model.addAttribute("saveOrUpdate","update");
        model.addAttribute("helperReply", reply);

        return "addreply";
    }

    @PostMapping("/deleteReply/{replyId}/{postId}")
    public String deleteReply(@PathVariable("replyId") Integer replyId, @PathVariable("postId") Integer postId){
        System.out.println("above ");
        replyService.deleteReply(replyId);
        System.out.println("inside");
        return "redirect:/post/"+postId;
    }
}
