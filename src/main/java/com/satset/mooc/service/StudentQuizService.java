package com.satset.mooc.service;

import com.satset.mooc.model.Question;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import com.satset.mooc.model.StudentQuiz;
import com.satset.mooc.model.dto.AnswersDto;

import java.util.List;

public interface StudentQuizService {

    void create(Student student, Quiz quiz);

    StudentQuiz updateQuizScore(Student student, Quiz quiz, int score, String answer_feedback);

    Boolean quizAvailable(Student student, Quiz quiz);

    StudentQuiz generateResult(Student student, Quiz quiz, List<Question> questions, List<String> answers);
}
