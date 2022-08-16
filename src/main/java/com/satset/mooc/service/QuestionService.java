package com.satset.mooc.service;

import com.satset.mooc.model.Question;
import com.satset.mooc.model.Quiz;

import java.util.List;

public interface QuestionService {

    void addAndSaveAllQuestions(List<Question> questions, Quiz quiz);

    void modify(List<Question> oldQuestions, List<Question> newQuestions);

    void deleteQuestions(List<Question> questions);

    void saveAll(List<Question> questions);

    void setAndSave(Question question, Quiz quiz);

    void save(Question question);

    Question getQuestionById(long id);

    void modifyQuestion(Question oldQuestion, Question question);

    void delete(Question question);
}
