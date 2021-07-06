package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/save/{id}")
    public String addComment(@PathVariable("id") Integer id, @ModelAttribute("comment") Comment comment) {
        System.out.println("inside save comment");
        Timestamp instant = Timestamp.from(Instant.now());
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(instant);
        }
        comment.setUpdatedAt(instant);
        Post post = postService.getPostById(id);
        post.getComments().add(comment);
        postService.savePost(post);
        return "redirect:/post/" + id;
    }

    @GetMapping("/updateCommentForm/{commentId}/{postId}")
    public String updateCommentForm(Model model, @PathVariable("commentId") Integer commentId,
                                    @PathVariable("postId") int postId) {
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);
        model.addAttribute("postId", postId);
        return "updatecommentform";
    }

    @PostMapping("/update/{commentId}/{postId}")
    public String updateComment(@PathVariable("commentId") Integer commentId, @PathVariable("postId") Integer
            postId, @ModelAttribute("comment") Comment comment) {
        commentService.updateCommentById(comment, commentId);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/delete/{commentId}/{postId}")
    public String deleteComment(@PathVariable("commentId") Integer commentId, @PathVariable("postId") Integer postId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/upvotecomment/{commentId}/{postId}")
    public String upvoteComment(@PathVariable("commentId") Integer commentId, @PathVariable("postId") Integer postId) {
        Comment comment = commentService.getCommentById(commentId);
        comment.setPoints(comment.getPoints()+1);
        commentService.saveComment(comment);
        return "redirect:/post/" + postId;
    }

}
