package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Question;
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
    public Quiz getQuizById(long id) {
        return quizRepository.findById(id).orElse(null);
    }

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
    public void modify(Quiz oldQuiz, Quiz quiz) {
        if(oldQuiz!=null) {
            oldQuiz.setTitle(quiz.getTitle());
        }
        save(oldQuiz);
    }

    @Override
    public void delete(Quiz quiz) {
        if(quiz!=null) {
            questionService.deleteQuestions(quiz.getQuestions());
            quizRepository.delete(quiz);
        }
    }

    @Override
    public void addQuestion(Quiz quiz, Question question) {
        question.setQuiz(quiz);
        questionService.setAndSave(question, quiz);
        quiz.addQuestion(question);
        save(quiz);
    }

    @Override
    public void deleteQuestion(Quiz quiz, Question question) {
        quiz.getQuestions().remove(question);
        save(quiz);
    }
}
