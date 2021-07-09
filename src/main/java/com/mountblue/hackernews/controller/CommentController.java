package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.model.User;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.CommentService;
import com.mountblue.hackernews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserService userService;

    @PostMapping("/save/{postId}")
    public String saveComment(@PathVariable("postId") int postId, @ModelAttribute("comment") Comment comment,
                              Authentication authentication) {
        Timestamp instant = Timestamp.from(Instant.now());

        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(instant);
        }
        User user = userService.findByEmail(authentication.getName());

        comment.setName(user.getName());
        comment.setEmail(user.getEmail());
        comment.setUpdatedAt(instant);

        Post post = postService.getPostById(postId);
        post.getComments().add(comment);
        postService.savePost(post);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/updateCommentForm/{commentId}/{postId}")
    public String commentForm(Model model, @PathVariable("commentId") int commentId,
                              @PathVariable("postId") int postId) {
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);
        model.addAttribute("postId", postId);
        return "updatecommentform";
    }

    @PostMapping("/update/{commentId}/{postId}")
    public String updateComment(@PathVariable("commentId") int commentId, @PathVariable("postId") int
            postId, @ModelAttribute("comment") Comment comment) {
        commentService.updateCommentById(comment, commentId);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/delete/{commentId}/{postId}")
    public String deleteComment(@PathVariable("commentId") int commentId, @PathVariable("postId") int postId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/upvotecomment/{commentId}/{postId}")
    public String upVoteComment(@PathVariable("commentId") int commentId, @PathVariable("postId") int postId,
                                Authentication authentication) {
        Comment comment = commentService.getCommentById(commentId);
        User user = userService.findByEmail(authentication.getName());
        if (!(comment.getUsersVotedUp().contains(user))) {
            comment.getUsersVotedUp().add(user);
            comment.setPoints(comment.getPoints() + 1);
            commentService.saveComment(comment);
        }

        return "redirect:/post/" + postId;
    }

    @GetMapping("/downvotecomment/{commentId}/{postId}")
    public String downVoteComment(@PathVariable("commentId") int commentId, @PathVariable("postId") int postId,
                                  Authentication authentication) {
        Comment comment = commentService.getCommentById(commentId);
        User user = userService.findByEmail(authentication.getName());
        if ((comment.getUsersVotedUp().contains(user))) {
            comment.getUsersVotedUp().remove(user);
            comment.setPoints(comment.getPoints() - 1);
            commentService.saveComment(comment);
        }
        return "redirect:/post/" + postId;
    }
}
