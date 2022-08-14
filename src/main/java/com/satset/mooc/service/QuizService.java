package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Quiz;

import java.util.List;

public interface QuizService {

    void saveQuizzesAndQuestions(List<Quiz> quizzes, Course course);

    void setAndSaveQuiz(Quiz quiz, Course course);

    void save(Quiz quiz);

}
