package com.satset.mooc.service;

import com.satset.mooc.model.Question;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void addAndSaveAllQuestions(List<Question> questions, Quiz quiz) {
        for(Question q:questions) {
            q.setQuiz(quiz);
        }
        questionRepository.saveAll(questions);
    }
}
