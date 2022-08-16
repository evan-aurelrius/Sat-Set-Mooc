package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Question;
import com.satset.mooc.model.Quiz;

import java.util.List;

public interface QuizService {

    Quiz getQuizById(long id);

    void saveQuizzesAndQuestions(List<Quiz> quizzes, Course course);

    void setAndSaveQuiz(Quiz quiz, Course course);

    void save(Quiz quiz);

    void modify(Quiz oldQuiz, Quiz quiz);

    void delete(Quiz quiz);

    void addQuestion(Quiz quiz, Question question);

    void deleteQuestion(Quiz quiz, Question question);
}
