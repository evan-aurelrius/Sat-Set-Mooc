package com.satset.mooc.service;

import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import com.satset.mooc.model.StudentQuiz;

public interface StudentQuizService {

    void create(Student student, Quiz quiz);

    void updateQuizScore(Student student, Quiz quiz, int score);

    Boolean quizAvailable(Student student, Quiz quiz);

}
