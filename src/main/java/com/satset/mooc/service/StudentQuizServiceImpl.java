package com.satset.mooc.service;

import com.satset.mooc.model.*;
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
    public StudentQuiz updateQuizScore(Student student, Quiz quiz, int score, String answer_feedback) {
        StudentQuiz studentQuiz = studentQuizRepository.findByStudent_idAndQuiz_id(student.getId(), quiz.getId()).orElse(null);
        if (studentQuiz == null) {
            studentQuiz = new StudentQuiz();
            studentQuiz.setStudentQuizKey(new StudentQuizKey(student.getId(), quiz.getId()));
        }
        studentQuiz.setScore(score);
        studentQuiz.setAnswer_feedback(answer_feedback);
        studentQuizRepository.save(studentQuiz);
        return studentQuiz;
    }

    @Override
    public Boolean quizAvailable(Student student, Quiz quiz) {
        StudentQuiz studentQuiz = studentQuizRepository.findByStudent_idAndQuiz_id(student.getId(), quiz.getId()).orElse(null);
        return studentQuiz != null && studentQuiz.getScore() == -1;
    }

    @Override
    public StudentQuiz generateResult(Student student, Quiz quiz, List<Question> questions, List<String> answers) {
        List<Integer> answerFeedback = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++)
            if (questions.get(i).getOpt_true().equals(answers.get(i)))
                answerFeedback.add(1);
            else
                answerFeedback.add(0);

        int score = Collections.frequency(answerFeedback, 1) * 100 / questions.size();
        return updateQuizScore(student, quiz, score, answerFeedback.toString());
    }

    @Override
    public long countCompletedQuiz(long student_Id) {
        return studentQuizRepository.findAllCompletedQuiz(student_Id).stream().count();
    }

    @Override
    public StudentQuiz getStudentQuizByStudentIdAndQuizId(long user_id, long quiz_id) {
        return studentQuizRepository.findByStudent_idAndQuiz_id(user_id, quiz_id).orElse(null);
    }
}
