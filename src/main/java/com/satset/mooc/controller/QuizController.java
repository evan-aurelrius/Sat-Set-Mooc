package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import com.satset.mooc.model.dto.QuizDto;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.service.QuizService;
import com.satset.mooc.service.StudentService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    private ModelMapper modelMapper= MapperUtil.getInstance();

    @GetMapping("/quiz/{quiz_id}")
    public ResponseEntity<?> viewQuiz(@PathVariable("quiz_id") long quiz_id, Authentication authentication) {
        boolean isEligible;
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        long user_id = userDetails.getId();
        String role = userDetails.getRole();
        Quiz quiz = quizService.getQuizById(quiz_id);
        if(role.equals("student")) {
            Student student = studentService.getStudentById(user_id);
            isEligible = studentService.quizEligibilityCheck(student, quiz);
        } else if(role.equals("instructor")) {
            Instructor instructor = instructorService.getInstructorById(user_id);
            isEligible = instructorService.quizEligibilityCheck(instructor, quiz);
        } else isEligible = false;

        if(Boolean.TRUE.equals(isEligible)) {
            HashMap<String, Object> map = new HashMap<>();
            if(quiz!=null) map.put("data", quiz);
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/course/{course_id}/quiz")
    public ResponseEntity<String> addQuiz(@PathVariable("course_id") long course_id, @RequestBody QuizDto quizDto) {
        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        courseService.addQuiz(course, quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/quiz/{quiz_id}")
    public ResponseEntity<String> modifyQuiz(@PathVariable("quiz_id") long quiz_id, @RequestBody QuizDto quizDto) {
        Course course = courseService.getCourseById(quiz_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        quizService.modify(quiz_id, quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/quiz/{quiz_id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("quiz_id") long quiz_id) {
        quizService.delete(quiz_id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
