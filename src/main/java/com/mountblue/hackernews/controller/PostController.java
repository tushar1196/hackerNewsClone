package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.model.User;
import com.mountblue.hackernews.service.PostService;
import com.mountblue.hackernews.service.CommentService;
import com.mountblue.hackernews.service.ReplyService;
import com.mountblue.hackernews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        return paginatedPage(1, "createdAt", "asc", model, authentication, "none");
    }

    @GetMapping("/ask")
    public String getAllPostByAskHN(Model model) {
        model.addAttribute("postsByAskHN", postService.getAllByAskHN());
        return "ask";
    }

    @GetMapping("/postform")
    public String postForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "postform";
    }

    @PostMapping("/addpost")
    public String savePost(@ModelAttribute("post") Post post, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        post.setUserName(user.getName());
        post.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        post.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        postService.savePost(post);

        return "redirect:/";
    }

    @GetMapping("/show")
    public String getAllShowHN(Model model) {
        model.addAttribute("postsByShowHN", postService.getAllByShowHN());
        return "show";
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable("id") int id, Model model, Authentication authentication) {
        Comment comment = new Comment();

        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("comment", comment);
        model.addAttribute("replies", replyService.getAllReplies());

        if (authentication != null) {
            User user = userService.findByEmail(authentication.getName());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }
        return "viewpost";
    }

    @GetMapping("/hide/{postId}")
    public String hidePost(@PathVariable("postId") int postId, Authentication authentication) {
        Post post = postService.getPostById(postId);
        User user = userService.findByEmail(authentication.getName());
        if (!(post.getHideByUsers().contains(user))) {
            post.getHideByUsers().add(user);
            postService.savePost(post);
        }
        return "redirect:/";
    }

    @GetMapping("/showhidepost/")
    public String showHidePostsByUser(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findByEmail(authentication.getName()));
        model.addAttribute("posts", postService.findAll());
        return "hidepostsdashboard";
    }

    @GetMapping("/unhide/{postId}")
    public String unHidePost(@PathVariable("postId") int postId, Authentication authentication, Model model) {
        Post post = postService.getPostById(postId);
        User user = userService.findByEmail(authentication.getName());
        if ((post.getHideByUsers().contains(user))) {
            post.getHideByUsers().remove(user);
            postService.savePost(post);
        }
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.findAll());
        return "hidepostsdashboard";
    }

    @GetMapping("/upvote/{id}")
    public String vote(@PathVariable("id") int postId, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Post post = postService.getPostById(postId);
        if (!(post.getUsersVotedUp().contains(user))) {
            post.getUsersVotedUp().add(user);
            post.setPoints(post.getPoints() + 1);
            postService.savePost(post);
        }
        return "redirect:/";
    }

    @GetMapping("/downvote/{id}")
    public String downVote(@PathVariable("id") int postId, Authentication authentication) {
        Post post = postService.getPostById(postId);
        User user = userService.findByEmail(authentication.getName());
        if (post.getUsersVotedUp().contains(user)) {
            post.getUsersVotedUp().remove(user);
            post.setPoints(post.getPoints() - 1);
            postService.savePost(post);
        }
        return "redirect:/";
    }

    @GetMapping("/deletepost/{postId}")
    public String deletePost(@PathVariable("postId") int postId) {
        postService.deletePostById(postId);
        return "redirect:/";
    }

    @GetMapping("/updatepost/{postId}")
    public String updatePost(@PathVariable("postId") int postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId));
        return "postform";
    }


    @GetMapping("/page/{pageNo}")
    public String paginatedPage(@PathVariable(value = "pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDirection") String sortDirection, Model model,
                                Authentication authentication, String nonUser) {
        int pageSize = 10;
        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Post> posts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("posts", posts);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>"+posts);
        if (authentication != null) {
            User user = userService.findByEmail(authentication.getName());
            if (user.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("user", user);
                return "admindashboard";
            } else if (user.getRole().equals("ROLE_USER")) {
                model.addAttribute("user", user);
                return "userdashboard";
            }
        }
        return "dashboard";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String keyWord, Model model) {
        model.addAttribute("search", keyWord);
//        model.addAttribute("comments", commentService.getCommentsByKeyWord(keyWord, "", ""));
        model.addAttribute("posts", postService.getPostByKeyWord(keyWord, "", ""));
        return "search";
    }

    @RequestMapping("/filter")
    public String filter(@RequestParam("type") String selected, @RequestParam("by") String by,
                         @RequestParam(value = "startDateTime", required = false, defaultValue = "") String startDate,
                         @RequestParam(value = "endDateTime", required = false, defaultValue = "") String endDate,
                         @RequestParam("search") String search, Model model) {

        boolean popular = by.equals("popular") || startDate.isEmpty() || endDate.isEmpty();
        boolean date = by.equals("date") || startDate.isEmpty() || endDate.isEmpty();
        if (selected.equals("comments")) {
            model.addAttribute("search", search);
            if (date) {
                model.addAttribute("comments", commentService.getCommentsByKeyWord(search, startDate, endDate));
            } else if (popular) {
                model.addAttribute("comments", commentService.getCommentsByKeyWordWithPoints(search, startDate, endDate));
            }
        } else if (selected.equals("stories")) {
            model.addAttribute("search", search);
            if (date) {
                model.addAttribute("posts", postService.getPostByKeyWord(search, startDate, endDate));
            } else if (popular) {
                model.addAttribute("posts", postService.getPostByKeyWordWithPoints(search, startDate, endDate));
            }
        }
        return "search";
    }
}