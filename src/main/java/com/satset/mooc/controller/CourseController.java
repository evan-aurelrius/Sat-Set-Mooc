package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.CourseDto;
import com.satset.mooc.service.CourseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
//@PreAuthorize("isAuthenticated()")
public class CourseController {

    @Autowired
    CourseService courseService;

    private static final ModelMapper modelMapper = new ModelMapper();

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/api/course")
    public ResponseEntity<String> verifyCourse(@RequestBody CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        logger.warn(course.getTitle());
        logger.warn(String.valueOf(course.getLectures().size()));
        logger.warn(course.getQuizzes().get(0).getQuestions().get(0).getOpt().toString());
        Boolean courseIsCreated = courseService.createCourse(course, courseDto.getUser_id());
        if(Boolean.FALSE.equals(courseIsCreated)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
