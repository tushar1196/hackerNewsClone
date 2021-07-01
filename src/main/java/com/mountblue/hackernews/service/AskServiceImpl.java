package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Ask;
import com.mountblue.hackernews.repository.AskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AskServiceImpl implements AskService {

    @Autowired
    private AskRepository askRepository;

    @Override
    public List<Ask> getAllAskedQuestion() {
        return askRepository.findAll();
    }

    @Override
    public void saveQuestion(Ask question) {

        askRepository.save(question);
    }

    @Override
    public Ask getQuestionById(Integer id) {
        return askRepository.getById(id);
    }

}
