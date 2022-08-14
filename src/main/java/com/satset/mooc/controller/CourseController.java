package com.satset.mooc.controller;

import com.satset.mooc.model.AdminDto;
import com.satset.mooc.model.Course;
import com.satset.mooc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course")
    public List<Course> courseList(Authentication authentication){
        return courseService.getAllCourse();
    }

    @GetMapping("/course/test")
    public ResponseEntity<Object> courseTest(Authentication authentication) {
        return ResponseEntity.ok(authentication.getPrincipal());
    }
}
