package com.satset.mooc.controller;

import com.satset.mooc.model.AdminDto;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.service.AdminService;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    InstructorService instructorService;

    @Autowired
    CourseService courseService;

    private String msg = "";

    @PostMapping("/api/admin-login")
    public ResponseEntity<String> authenticateAccount(@ModelAttribute AdminDto adminDto) {
        var isExist = adminService.authenticate(adminDto.getEmail(), adminDto.getPassword());
        if(isExist) msg = "User is valid";
        else msg = "Invalid email or password!";

        return ResponseEntity.ok(msg);
    }

    @PostMapping("/api/verify-instructor")
    public ResponseEntity<String> verifyInstructor(@ModelAttribute Map<String, Object> request) {
        var user_id = (Long) request.get("user_id");
        var verified_status = StringUtils.capitalize((String) request.get("verified_status"));
        var instructor = instructorService.getInstructorById(user_id);

        if(instructor==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        instructorService.setInstuctorStatus(instructor, verified_status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/verify-course")
    public ResponseEntity<String> verifyCourse(@ModelAttribute Map<String, Object> request) {
        var course_id = (Long) request.get("course_id");
        var verified_status = StringUtils.capitalize((String) request.get("verified_status"));
        var course = courseService.getCourseById(course_id);

        if(course==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        courseService.setCourseStatus(course, verified_status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
