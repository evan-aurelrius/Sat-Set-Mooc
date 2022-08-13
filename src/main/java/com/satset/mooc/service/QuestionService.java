package com.satset.mooc.service;

import com.satset.mooc.model.Question;
import com.satset.mooc.model.Quiz;

import java.util.List;

public interface QuestionService {

    void addAndSaveAllQuestions(List<Question> questions, Quiz quiz);

}
