package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.dto.LectureDto;
import com.satset.mooc.model.dto.QuizDto;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.QuizService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController {

    @Autowired
    CourseService courseService;
    @Autowired
    QuizService quizService;

    private ModelMapper modelMapper= MapperUtil.getInstance();

    @PostMapping("/api/course/{course_id}/quiz")
    public ResponseEntity<String> addLecture(@PathVariable("course_id") long course_id, @RequestBody QuizDto quizDto) {
        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        courseService.addQuiz(course, quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
