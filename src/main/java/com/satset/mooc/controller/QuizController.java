package com.satset.mooc.controller;

import com.satset.mooc.model.*;
import com.satset.mooc.model.dto.AnswersDto;
import com.satset.mooc.model.dto.QuestionDto;
import com.satset.mooc.model.dto.QuizDto;
import com.satset.mooc.model.dto.StudentDto;
import com.satset.mooc.model.response.InstructorCourseResponse;
import com.satset.mooc.model.response.StudentCourseResponse;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.*;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/api")
@RestController
@PreAuthorize("isAuthenticated()")
public class QuizController {

    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    QuizService quizService;
    @Autowired
    QuestionService questionService;
    @Autowired
    StudentQuizService studentQuizService;

    private final ModelMapper modelMapper= MapperUtil.getInstance();

    @GetMapping("/quiz/{quiz_id}")
    public ResponseEntity<?> viewQuiz(@PathVariable("quiz_id") long quiz_id, Authentication authentication) {
        boolean isEligible;
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        long user_id = userDetails.getId();
        String role = userDetails.getRole();
        Quiz quiz = quizService.getQuizById(quiz_id);
        if(quiz==null) return ResponseEntity.notFound().build();

        if(role.equals("student")) {
            Student student = studentService.getStudentById(user_id);
            isEligible = studentService.quizEligibilityCheck(student, quiz);
        } else if(role.equals("instructor")) {
            Instructor instructor = instructorService.getInstructorById(user_id);
            isEligible = instructorService.quizEligibilityCheck(instructor, quiz);
        } else isEligible = false;

        if(Boolean.TRUE.equals(isEligible)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("data", quiz);
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/course/{course_id}/quiz")
    public ResponseEntity<String> addQuiz(@PathVariable("course_id") long course_id, @RequestBody QuizDto quizDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(Boolean.FALSE.equals(instructorService.quizEligibilityViaCourseCheck(instructor,course))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        courseService.addQuiz(course, quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/quiz/{quiz_id}")
    public ResponseEntity<String> modifyQuiz(@PathVariable("quiz_id") long quiz_id, @RequestBody QuizDto quizDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Quiz oldQuiz = quizService.getQuizById(quiz_id);
        if(oldQuiz==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(Boolean.FALSE.equals(instructorService.quizEligibilityCheck(instructor,oldQuiz))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        quizService.modify(oldQuiz, quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/quiz/{quiz_id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("quiz_id") long quiz_id, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Quiz quiz = quizService.getQuizById(quiz_id);
        if(quiz==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(Boolean.FALSE.equals(instructorService.quizEligibilityCheck(instructor,quiz))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        quizService.delete(quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/quiz/{quiz_id}/question")
    public ResponseEntity<String> addQuestion(@PathVariable("quiz_id") long quiz_id, @RequestBody QuestionDto questionDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Quiz quiz = quizService.getQuizById(quiz_id);
        if(quiz==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(Boolean.FALSE.equals(instructorService.quizEligibilityCheck(instructor,quiz))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Question question = modelMapper.map(questionDto, Question.class);
        quizService.addQuestion(quiz, question);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/question/{question_id}")
    public ResponseEntity<String> modifyQuestion(@PathVariable("question_id") long question_id, @RequestBody QuestionDto questionDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Question oldQuestion = questionService.getQuestionById(question_id);
        if(oldQuestion==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(Boolean.FALSE.equals(instructorService.questionEligibilityCheck(instructor,oldQuestion))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Question question = modelMapper.map(questionDto, Question.class);
        questionService.modifyQuestion(oldQuestion, question);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/question/{question_id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("question_id") long question_id, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Question question = questionService.getQuestionById(question_id);
        if(question==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(Boolean.FALSE.equals(instructorService.questionEligibilityCheck(instructor,question))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        questionService.delete(question);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/quiz/{quiz_id}")
    public ResponseEntity<?> submitQuiz(@PathVariable("quiz_id") long quiz_id, @RequestBody AnswersDto answersDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Student student = studentService.getStudentById(principal.getId());

        Quiz quiz = quizService.getQuizById(quiz_id);
        if(quiz==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Question> questions = quiz.getQuestions();

        StudentQuiz studentQuiz = studentQuizService.checkAnswer(student, quiz, questions, answersDto);

        return ResponseEntity.ok(studentQuiz);
    }
}
