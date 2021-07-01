package com.mountblue.hackernews.controller;

import com.mountblue.hackernews.model.Ask;
import com.mountblue.hackernews.model.Comment;
import com.mountblue.hackernews.service.AskService;
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
public class AskController {

    @Autowired
    private AskService askService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String demo(){
        return "dashboard";
    }
    @GetMapping("/ask")
    public String getAllAskedQuestions(Model model){
        List<Ask> questions=askService.getAllAskedQuestion();
        model.addAttribute("questionList", questions);
        return "askpage";
    }

    @GetMapping("/questionform")
    public  String formForQuestion(Model model){
        Ask question=new Ask();
        model.addAttribute("questions", question);
        return "questionform";
    }
    @PostMapping("/addquestion")
    public String addQuestionOrNews(@ModelAttribute("questions") Ask question){
        askService.saveQuestion(question);
        return "redirect:/ask";
    }

    @GetMapping("/showquestion/{id}")
    public String showQuestion(@PathVariable("id") Integer id, Model model){
        Comment comment=new Comment();
        Ask question = askService.getQuestionById(id);
        List<Comment> commentList=commentService.getCommentByQuestionId(id);
       // System.out.println(question.toString());
        model.addAttribute("Question", question);
        model.addAttribute("comment", comment);
        model.addAttribute("comments", commentList);
        return "showquestion";
    }

}
