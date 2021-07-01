package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Ask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AskService {

    public List<Ask> getAllAskedQuestion();
    public void saveQuestion(Ask question);
    public Ask getQuestionById(Integer id);
}
