package com.satset.mooc.controller;

import com.satset.mooc.model.*;
import com.satset.mooc.model.dto.AdminDto;
import com.satset.mooc.model.response.*;
import com.satset.mooc.service.AdminService;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@PreAuthorize("hasAuthority('admin')")
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
    public ResponseEntity<HashMap<String, Object>> getDashboard() {
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

    @GetMapping("/proposal-instructors/{page}")
    public ResponseEntity<HashMap<String, Object>> getInstructorProposal(@PathVariable("page") int page) {
        HashMap<String, Object> map = new HashMap<>();
        Page<Instructor> instructorPage = instructorService.getInstructorProposalPage(page);
        List<InstructorProposalResponse> lst = instructorService.convertToList(instructorPage);

        map.put("data",lst);
        if(instructorPage.hasNext())
            map.put("next", "/api/proposal-instructors/%d/".formatted(page+1));
        else map.put("next","");

        if(instructorPage.hasPrevious())
            map.put("prev", "/api/proposal-instructors/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

    @GetMapping("/proposal-courses/{page}")
    public ResponseEntity<HashMap<String, Object>> getCourseProposal(@PathVariable("page") int page) {
        HashMap<String, Object> map = new HashMap<>();
        Page<Course> coursePage = courseService.getCourseProposalPage(page);
        List<CourseProposalResponse> lst = courseService.convertToListOfCourseProposalResponse(coursePage);

        map.put("data",lst);
        if(coursePage.hasNext())
            map.put("next", "/api/proposal-courses/%d/".formatted(page+1));
        else map.put("next","");

        if(coursePage.hasPrevious())
            map.put("prev", "/api/proposal-courses/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

}
