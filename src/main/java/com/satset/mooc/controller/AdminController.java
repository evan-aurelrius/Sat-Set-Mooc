package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.JwtResponse;
import com.satset.mooc.model.dto.AdminDto;
import com.satset.mooc.service.AdminService;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.util.UserUtil;
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

    @Autowired
    UserUtil userUtil;

    @PostMapping("/api/admin-login")
    public ResponseEntity<JwtResponse> authenticateAccount(@RequestBody AdminDto adminDto) {
        return ResponseEntity.ok(userUtil.getJwt(adminDto));
    }

    @PostMapping("/api/verify-instructor")
    public ResponseEntity<String> verifyInstructor(@RequestBody Map<String, Object> request) {
        long user_id = Long.valueOf((Integer)request.get("user_id"));
        String verified_status = (String) request.get("verified_status");
        Instructor instructor = instructorService.getInstructorById(user_id);

        if(instructor==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        instructorService.setInstuctorStatus(instructor, verified_status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/verify-course")
    public ResponseEntity<String> verifyCourse(@RequestBody Map<String, Object> request) {
        Long course_id = Long.valueOf((Integer)request.get("course_id"));
        String verified_status = (String) request.get("verified_status");
        Course course = courseService.getCourseById(course_id);

        if(course==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        courseService.setCourseStatus(course, verified_status);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
