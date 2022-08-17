package com.satset.mooc.service;

import com.satset.mooc.model.*;
import com.satset.mooc.model.dto.AnswersDto;
import com.satset.mooc.repository.StudentQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StudentQuizServiceImpl implements StudentQuizService{

    @Autowired
    StudentQuizRepository studentQuizRepository;

    @Override
    public void create(Student student, Quiz quiz) {
        studentQuizRepository.save(new StudentQuiz(new StudentQuizKey(student.getId(), quiz.getId())));
    }

    @Override
    public StudentQuiz updateQuizScore(Student student, Quiz quiz, int score, String answer_feedback) {
        StudentQuiz studentQuiz = studentQuizRepository.findByStudent_idAndQuiz_id(student.getId(), quiz.getId());
        if (studentQuiz != null) {
            studentQuiz.setScore(score);
            studentQuiz.setAnswer_feedback(answer_feedback);
        }else{
            studentQuiz = new StudentQuiz();
            studentQuiz.setStudentQuizKey(new StudentQuizKey(student.getId(), quiz.getId()));
            studentQuiz.setScore(score);
            studentQuiz.setAnswer_feedback(answer_feedback);
        }
        studentQuizRepository.save(studentQuiz);
        return studentQuiz;
    }

    @Override
    public Boolean quizAvailable(Student student, Quiz quiz) {
        StudentQuiz studentQuiz = studentQuizRepository.findByStudent_idAndQuiz_id(student.getId(), quiz.getId());
        if(studentQuiz.getScore()==-1) return true;
        return false;
    }

    @Override
    public StudentQuiz generateResult(Student student, Quiz quiz, List<Question> questions, List<String> answers) {
        List<Integer> answerFeedback = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++)
            if (questions.get(i).getOpt_true().equals(answers.get(i)))
                answerFeedback.add(1);
            else
                answerFeedback.add(0);

        System.out.println(Collections.frequency(answerFeedback, 1));
        var score = Collections.frequency(answerFeedback, 1) / questions.size();

        return updateQuizScore(student, quiz, score, answerFeedback.toString());
    }
}
