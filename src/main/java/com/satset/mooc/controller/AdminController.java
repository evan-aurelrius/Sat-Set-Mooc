package com.satset.mooc.controller;

import com.satset.mooc.model.dto.AdminDto;
import com.satset.mooc.service.AdminService;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    InstructorService instructorService;

    @Autowired
    CourseService courseService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);
    private String msg = "";

    @PostMapping("/api/admin-login")
    public ResponseEntity<String> authenticateAccount(@RequestBody AdminDto adminDto) {
        var isExist = adminService.authenticate(adminDto.getEmail(), adminDto.getPassword());
        if(isExist) return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/verify-instructor")
    public ResponseEntity<String> verifyInstructor(@RequestBody Map<String, Object> request) {
        var user_id = Long.valueOf((Integer)request.get("user_id"));
        var verified_status = (String) request.get("verified_status");
        var instructor = instructorService.getInstructorById(user_id);

        if(instructor==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        instructorService.setInstuctorStatus(instructor, verified_status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/verify-course")
    public ResponseEntity<String> verifyCourse(@RequestBody Map<String, Object> request) {
        var course_id = Long.valueOf((Integer)request.get("course_id"));
        var verified_status = (String) request.get("verified_status");
        var course = courseService.getCourseById(course_id);

        if(course==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        courseService.setCourseStatus(course, verified_status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
