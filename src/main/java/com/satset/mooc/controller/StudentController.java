package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.dto.CourseDto;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class StudentController {

    @Autowired
    CourseService courseService;

    @PostMapping("/enroll")
    public ResponseEntity<String> onrollCourse(@RequestBody CourseDto courseDto, Authentication authentication) {
        Course course = courseService.getCourseById(courseDto.getId());
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        courseService.enroll(courseDto.getId(), principal.getId());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/course/enrolled-course/{size}/{page}")
    public ResponseEntity<Iterable<Course>> courseList(@PathVariable("size") int size, @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(courseService.getCourse(pageable));
    }

    @PostMapping("/quiz/{course_id}")
    public ResponseEntity<String> takeQuiz(Authentication authentication, @PathVariable("course_id") long course_id, @RequestBody CourseDto courseDto) {
        Course course = courseService.getCourseById(courseDto.getId());
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        courseService.enroll(courseDto.getId(), principal.getId());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
