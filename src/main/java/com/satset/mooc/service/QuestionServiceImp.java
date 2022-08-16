package com.satset.mooc.service;

import com.satset.mooc.model.Question;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;
    @Autowired @Lazy
    QuizService quizService;

    @Override
    public void addAndSaveAllQuestions(List<Question> questions, Quiz quiz) {
        for(Question q:questions) {
            q.setQuiz(quiz);
        }
        questionRepository.saveAll(questions);
    }

    @Override
    public void modify(List<Question> oldQuestions, List<Question> newQuestions) {

        

        oldQuestions = newQuestions;
        saveAll(oldQuestions);
    }

    @Override
    public void deleteQuestions(List<Question> questions) {
        questionRepository.deleteAll(questions);
    }

    @Override
    public void saveAll(List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    @Override
    public void setAndSave(Question question, Quiz quiz) {
        question.setQuiz(quiz);
        save(question);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public void modifyQuestion(Question oldQuestion, Question question) {
        oldQuestion.setQuestion(question.getQuestion());
        oldQuestion.setOpt_true(question.getOpt_true());
        oldQuestion.setOpt(question.getOpt());
        save(oldQuestion);
    }

    @Override
    public void delete(Question question) {
        quizService.deleteQuestion(question.getQuiz(), question);
        questionRepository.delete(question);
    }
}
