package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.dto.CourseDto;
import com.satset.mooc.model.response.StudentCourseResponse;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    CourseService courseService;

    @Autowired
    LectureService lectureService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollCourse(@RequestBody Map<String, Object> request, Authentication authentication) {
        var course_id = Long.valueOf((Integer)request.get("course_id"));
        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        courseService.enroll(course_id, principal.getId());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/enrolled-course/{page}")
    public ResponseEntity<?> getEnrolledCourse(@PathVariable("page") int page, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        if(page<1 || principal.getId()<1) return ResponseEntity.badRequest().build();
        List<StudentCourseResponse> courses = courseService.getStudentCourseWithPagination(page, principal.getId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("data", courses);
        map.put("next", "/api/enrolled-course/%d/".formatted(page+1));
        if(page>1)
            map.put("prev", "/api/enrolled-course/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

    @PostMapping("/progress/{lecture_id}")
    public ResponseEntity<String> enrollCourse(@PathVariable("lecture_id") long lecture_id, Authentication authentication) {
        Lecture lecture = lectureService.getLectureById(lecture_id);
        if(lecture==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        lectureService.addLectureProgress(lecture_id, principal.getId());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
