package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Ask;
import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.service.AskService;
import com.mountblue.hackernews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AskService askService;

    @PostMapping("/save/{id}")
    public String addComment(@PathVariable("id") Integer id, @ModelAttribute ("comment") Comment comment){
        System.out.println("inside save comment");

        Timestamp instant = Timestamp.from(Instant.now());
        comment.setCreatedAt(instant);
        comment.setUpdatedAt(instant);
        comment.setQuestionId(id);
        commentService.addComments(comment);

        return "redirect:/showquestion/"+id;
    }


    @GetMapping("/updateCommentForm/{commentId}")
    public String updateCommentForm(Model model,@PathVariable("commentId") Integer commentId){
        Comment  comment=commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);
        return "updatecommentform";
    }

    @PostMapping("/update/{commentId}")
    public String updateComment(@PathVariable("commentId") Integer commentId, @ModelAttribute("comment") Comment comment) {
        commentService.updateCommentById(comment, commentId);
        return "redirect:/showquestion/"+commentId;
    }
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Integer commentId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/showquestion/"+commentId;
    }
}
