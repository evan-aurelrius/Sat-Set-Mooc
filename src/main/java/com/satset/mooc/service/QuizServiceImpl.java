package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionService questionService;

    @Override
    public void saveQuizzesAndQuestions(List<Quiz> quizzes, Course course) {
        quizRepository.saveAll(quizzes);
        for(Quiz q:quizzes) {
            q.setCourse(course);
            questionService.addAndSaveAllQuestions(q.getQuestions(),q);
        }
    }

    @Override
    public void setAndSaveQuiz(Quiz quiz, Course course) {
        questionService.addAndSaveAllQuestions(quiz.getQuestions(),quiz);
        quiz.setCourse(course);
        save(quiz);
    }

    @Override
    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public void modify(long quiz_id, Quiz quiz) {
        Quiz oldQuiz = quizRepository.findById(quiz_id).orElse(null);
        if(oldQuiz!=null) {
            oldQuiz.setTitle(quiz.getTitle());
            questionService.modify(oldQuiz.getQuestions(), quiz.getQuestions());
            oldQuiz.setQuestions(quiz.getQuestions());
        }
        save(oldQuiz);
    }

    @Override
    public void delete(long id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);
        if(quiz!=null) {
            questionService.deleteQuestions(quiz.getQuestions());
            quizRepository.delete(quiz);
        }
    }
}
