package com.satset.mooc.service;

import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import com.satset.mooc.model.StudentQuiz;
import com.satset.mooc.model.StudentQuizKey;
import com.satset.mooc.repository.StudentQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentQuizServiceImpl implements StudentQuizService{

    @Autowired
    StudentQuizRepository studentQuizRepository;

    @Override
    public void create(Student student, Quiz quiz) {
        studentQuizRepository.save(new StudentQuiz(new StudentQuizKey(student.getId(), quiz.getId())));
    }

    @Override
    public void updateQuizScore(Student student, Quiz quiz, int score) {
        StudentQuiz studentQuiz = studentQuizRepository.findByStudent_idAndQuiz_id(student.getId(), quiz.getId());
        studentQuiz.setScore(score);
        studentQuizRepository.save(studentQuiz);
    }

    @Override
    public Boolean quizAvailable(Student student, Quiz quiz) {
        StudentQuiz studentQuiz = studentQuizRepository.findByStudent_idAndQuiz_id(student.getId(), quiz.getId());
        if(studentQuiz.getScore()==-1) return true;
        return false;
    }
}
