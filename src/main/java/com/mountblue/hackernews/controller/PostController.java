package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.User;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.CommentService;
import com.mountblue.hackernews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        return paginatedPage(1, "createdAt", "asc", model, authentication);
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
    public String addPost(@ModelAttribute("post") Post post, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        System.out.println(user + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        post.setUserName(user.getName());
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/show")
    public String getAllShowHN(Model model) {
        model.addAttribute("postByShowHN", postService.getAllByShowHN());
        return "show";
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Comment comment = new Comment();
        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("comment", comment);
        if (authentication != null) {
            User user = userService.findByEmail(authentication.getName());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user",new User());
        }
        return "viewpost";
    }

    @GetMapping("/vote/{id}")
    public String vote(@PathVariable("id") int postId, Authentication authentication) {
        Post post = postService.getPostById(postId);
//        User user = userService.findByEmail(authentication.getName());
        post.setPoints(post.getPoints() + 1);
//        user.setVoted(true);
//        userService.saveUser(user);
        postService.savePost(post);
        System.out.println("after voting ihgyuvshdbidygfyvuhbewisdga8vydbfi8geqhhgsyvaxagsfvdbohcsgvdbsho" +
                "==================" + post + "------------------------" /*+ user*/);
        return "redirect:/";
    }

    @GetMapping("/unvote/{id}")
    public String unVote(@PathVariable("id") int postId, Authentication authentication) {
        Post post = postService.getPostById(postId);
//        User user = userService.findByEmail(authentication.getName());
        post.setPoints(post.getPoints() - 1);
//        user.setVoted(true);
//        userService.saveUser(user);
        postService.savePost(post);
        System.out.println("after voting ihgyuvshdbidygfyvuhbewisdga8vydbfi8geqhhgsyvaxagsfvdbohcsgvdbsho" +
                "==================" + post + "------------------------" + "" /*user*/);
        return "redirect:/";
    }

    @GetMapping("/hide/{id}")
    public String hidePostById(@PathVariable("id") int postId) {
        System.out.println("in hide post");
        Post post = postService.getPostById(postId);
        System.out.println(post);
//        post.setHide(true);
        //System.out.println("after set t_____________________________________________" + post);
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/unhide/{id}")
    public String unhidePostById(@PathVariable("id") int postId) {
        Post post = postService.getPostById(postId);

//        post.setHide(false);
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/deletepost/{postId}")
    public String deletePostById(@PathVariable("postId") int postId) {
        postService.deletePostById(postId);
        return "redirect:/";
    }

    @GetMapping("/updatepost/{postId}")
    public String updatePostById(@PathVariable("postId") int postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId));
        return "postform";
    }


    @GetMapping("/page/{pageNo}")
    public String paginatedPage(@PathVariable(value = "pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDirection") String sortDirection, Model model, Authentication authentication) {
        int pageSize = 10;

        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Post> postsList = page.getContent();


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("listOfPost", postsList);

        if (authentication != null) {
            User user = userService.findByEmail(authentication.getName());
            if (user.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("user", user);
                return "admindashboard";
            } else if (user.getRole().equals("ROLE_USER")) {
                model.addAttribute("user", user);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user.getName());
                return "userdashboard";
            }
        }
        return "dashboard";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String keyWord, Model model) {
        List<Post> postsList = postService.getPostByKeyWord(keyWord);
        List<String> stories = new ArrayList<>();
        stories.add("comments");
        stories.add("stories");
        model.addAttribute("search", keyWord);
        model.addAttribute("searchtype", stories);
        model.addAttribute("postslist", postsList);
        return "search";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam("select") String selected, @RequestParam("search") String search, Model model) {
        List<Post> postsList = postService.getPostByKeyWord(search);
        List<String> stories = new ArrayList<>();
        stories.add("comments");
        stories.add("stories");

        if (selected.equals("comments")) {
            List<Comment> commentsList = commentService.getCommentBySearch(search);
            model.addAttribute("search", search);
            model.addAttribute("searchtype", stories);
            model.addAttribute("comments", commentsList);
        } else if (selected.equals("stories")) {


            model.addAttribute("search", search);
            model.addAttribute("searchtype", stories);
            model.addAttribute("postslist", postsList);
        }
        System.out.println(selected + "--------------" + search);
        return "search";
    }

}
