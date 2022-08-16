package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Quiz;

import java.util.List;

public interface QuizService {

    Quiz getQuizById(long id);

    void saveQuizzesAndQuestions(List<Quiz> quizzes, Course course);

    void setAndSaveQuiz(Quiz quiz, Course course);

    void save(Quiz quiz);

    void modify(long quiz_id, Quiz quiz);

    void delete(long id);

}
