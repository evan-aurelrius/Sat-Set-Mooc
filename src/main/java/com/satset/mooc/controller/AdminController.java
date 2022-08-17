package com.satset.mooc.controller;

import com.satset.mooc.model.*;
import com.satset.mooc.model.dto.AdminDto;
import com.satset.mooc.model.response.AdminSummaryResponse;
import com.satset.mooc.model.response.DailyNewUserResponse;
import com.satset.mooc.model.response.InstructorDashboardResponse;
import com.satset.mooc.model.response.TopCourseResponse;
import com.satset.mooc.security.jwt.JwtUtils;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.AdminService;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
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

    @PostMapping("/admin-login")
    public ResponseEntity<JwtResponse> authenticateAccount(@RequestBody AdminDto adminDto) {
        var userData = userUtil.getJwt(adminDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("token", userData.getToken());
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/verify-instructor")
    public ResponseEntity<String> verifyInstructor(@RequestBody Map<String, Object> request) {
        Instructor instructor = instructorService.getInstructorById(Long.valueOf((Integer)request.get("user_id")));
        if(instructor==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        String verified_status = (String) request.get("verified_status");
        Boolean isSet = instructorService.setInstructorStatus(instructor, verified_status);
        if(Boolean.TRUE.equals(isSet))
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/verify-course")
    public ResponseEntity<String> verifyCourse(@RequestBody Map<String, Object> request) {
        Long course_id = Long.valueOf((Integer)request.get("course_id"));
        String verified_status = (String) request.get("verified_status");
        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Boolean isSet = courseService.setCourseStatus(course, verified_status);
        if(Boolean.TRUE.equals(isSet))
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/admin-dashboard")
    public ResponseEntity<?> getDashboard() {
        HashMap<String, Object> outerMap = new HashMap<>();
        HashMap<String, Object> innerMap = new HashMap<>();
        outerMap.put("data",innerMap);

        List<AdminSummaryResponse> summaryResponses = adminService.getSummary();
        innerMap.put("data", summaryResponses);

        LinkedList<DailyNewUserResponse> newUserResponseList = adminService.getDailyNewUser();
        innerMap.put("new_user", newUserResponseList);

        List<TopCourseResponse> topCourseResponses = adminService.getTopCourse();
        innerMap.put("top_courses", topCourseResponses);

        return new ResponseEntity<>(outerMap, HttpStatus.ACCEPTED);
    }

}
