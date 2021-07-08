package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.model.Reply;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private PostService postService;

    @GetMapping("/addReply/{commentId}/{postId}")
    public String addReplyForm(@PathVariable("commentId") Integer commentId, @PathVariable("postId") Integer postId,
                           Model model){
        Post post= postService.getPostById(postId);
        model.addAttribute("commentId",commentId);
        model.addAttribute("post", post);

        return  "addreply";
    }

    @PostMapping("/addReply/{id}")
    public String saveReply(@PathVariable("id") Integer postId, @RequestParam("commentId") Integer commentId,
                            @RequestParam("reply") String reply){
       // System.out.println(reply+postId+commentId);
       // Post post = postService.getPostById(postId);
        Reply reply1=new Reply();
        reply1.setCommentId(commentId);
        reply1.setReply(reply);
        System.out.println(reply1.getReply());
        replyService.saveReply(reply1);

        return "redirect:/post/"+postId;
    }
}
