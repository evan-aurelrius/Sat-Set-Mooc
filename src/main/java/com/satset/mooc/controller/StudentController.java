package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Student;
import com.satset.mooc.model.response.CourseResponse;
import com.satset.mooc.model.response.StudentCourseResponse;
import com.satset.mooc.model.response.StudentDashboardResponse;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.LectureService;
import com.satset.mooc.service.StudentQuizService;
import com.satset.mooc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasAuthority('student')")
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    CourseService courseService;
    @Autowired
    LectureService lectureService;
    @Autowired
    StudentService studentService;

    @Autowired
    StudentQuizService studentQuizService;

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

        Page<Map<String, Object>> coursePage = courseService.getStudentCourseWithPagination(page, principal.getId());
        HashMap<String, Object> map = new HashMap<>();
        List<StudentCourseResponse> courses = courseService.convertToListOfStudentCourseResponse(coursePage);
        map.put("data", courses);
        if(coursePage.hasNext())
            map.put("next", "/api/courses/%d/".formatted(page+1));
        else
            map.put("next","");

        if(coursePage.hasPrevious())
            map.put("prev", "/api/courses/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

    @PostMapping("/progress/{lecture_id}")
    public ResponseEntity<String> updateLectureProgress(@PathVariable("lecture_id") long lecture_id, Authentication authentication) {
        Lecture lecture = lectureService.getLectureById(lecture_id);
        if(lecture==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Student student = studentService.getStudentById(principal.getId());
        lectureService.addLectureProgress(lecture, student);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/student-dashboard")
    public ResponseEntity<?> studentDashboard(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        if(principal.getId()<1) return ResponseEntity.badRequest().build();

        var countEnrolledCourse = courseService.countEnrolledCourse(principal.getId());
        var countCompletedCourse = courseService.countCompletedCourse(principal.getId());
        var countCompletedLecture = lectureService.countCompletedLecture(principal.getId());
        var countCompletedQuiz = studentQuizService.countCompletedQuiz(principal.getId());
        StudentDashboardResponse studentDashboardResponse = new StudentDashboardResponse(countEnrolledCourse, countCompletedCourse, countCompletedLecture, countCompletedQuiz);

        HashMap<String, Object> map = new HashMap<>();
        map.put("data", studentDashboardResponse);

        return ResponseEntity.ok(map);
    }
}
