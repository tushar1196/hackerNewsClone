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
//        System.out.println(postService.findAll());
        model.addAttribute("posts", postService.findAll());
        return "dashboard";
    }

    @GetMapping("/ask")
    public String getAllPostByAskHN(Model model) {
        model.addAttribute("postByAskHN", postService.getAllByAskHN());
        return "ask";
    }

    @GetMapping("/postform")
    public String postForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "postform";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute("post") Post post) {
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/show")
    public String getAllShowHN(Model model) {
        model.addAttribute("postByShowHN", postService.getAllByShowHN());
        return "show";
    }
    @GetMapping("/post/{id}")
    public String showQuestion(@PathVariable("id") Integer id, Model model) {
        Comment comment = new Comment();
        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("comment", comment);
        return "viewpost";
    }

    @GetMapping("/vote/{id}")
    public String vote(@PathVariable("id") int postId) {
        Post post = postService.getPostById(postId);
        post.setPoints(post.getPoints()+1);
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/hide/{id}")
    public String hidePostById(@PathVariable("id") int postId) {
        System.out.println("in hide post");
        Post post = postService.getPostById(postId);
        System.out.println(post);
        post.setHide(true);
        System.out.println("after set trur________________________________________________________________________"+post);
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/unhide/{id}")
    public String unhidePostById(@PathVariable("id") int postId) {
        Post post = postService.getPostById(postId);
        post.setHide(false);
        postService.savePost(post);
        return "redirect:/";
    }
}
