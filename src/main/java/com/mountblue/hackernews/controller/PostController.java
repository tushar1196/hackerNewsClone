package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String showDashboard(Model model) {
        System.out.println(postService.findAll());
        model.addAttribute("posts", postService.findAll());
        return "dashboard";
    }

    @GetMapping("/ask")
    public String getAllPostByAskHN(Model model) {
        model.addAttribute("postByAskHN", postService.getAllByAskHN());
        return "ask";
    }

    @GetMapping("/postform")
    public String questionForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "postform";
    }

    @PostMapping("/addpost")
    public String addQuestionOrNews(@ModelAttribute("post") Post post) {
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/show")
    public String getAllShowHN(Model model) {
        model.addAttribute("show", postService.getAllByShowHN());
        return "show";
    }
//
//    @GetMapping("/showquestion/{id}")
//    public String showQuestion(@PathVariable("id") Integer id, Model model) {
//        Comment comment = new Comment();
//        Post question = postService.getPostById(id);
//        List<Comment> commentList = commentService.getCommentByQuestionId(id);
//        // System.out.println(question.toString());
//        model.addAttribute("Question", question);
//        model.addAttribute("comment", comment);
//        model.addAttribute("comments", commentList);
//        return "show";
//    }
}
