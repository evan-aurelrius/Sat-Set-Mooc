package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Student;
import com.satset.mooc.model.dto.CourseDto;
import com.satset.mooc.model.response.CourseDetailResponse;
import com.satset.mooc.model.response.CourseResponse;
import com.satset.mooc.model.response.InstructorCourseResponse;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.service.StudentService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    StudentService studentService;

    final private ModelMapper modelMapper= MapperUtil.getInstance();

    @GetMapping("/course/{course_id}")
    public ResponseEntity<?> getCourseDetail(@PathVariable("course_id") long course_id, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();

        Object user;

        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(principal.getRole().equals("instructor")) {
            user = instructorService.getInstructorById(user_id);
            if(Boolean.FALSE.equals(instructorService.isValidated((Instructor) user))) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if(principal.getRole().equals("student"))
            user = studentService.getStudentById(user_id);
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Boolean isEligibleStudent = null;
        if (user instanceof Student) {
            isEligibleStudent = courseService.enrolledCheck(course, (Student) user);
        }
        HashMap<String, Object> map = new HashMap<>();

        CourseDetailResponse courseDetailResponse = modelMapper.map(course, CourseDetailResponse.class);
        courseService.fillCourseDetailResponseData(courseDetailResponse, user);
        if(Boolean.TRUE.equals(isEligibleStudent))
            map.put("is_enrolled", true);
        else
            map.put("is_enrolled", false);
        map.put("status", course.getStatus());
        map.put("data",courseDetailResponse);


        return ResponseEntity.ok(map);
    }


        @GetMapping("/courses/{page}")
    public ResponseEntity<?> getCourse(@PathVariable("page") int page) {
        if(page<1) return ResponseEntity.badRequest().build();
        List<CourseResponse> courses = courseService.getAllCourseWithPagination(page);

//        TODO: Handle pagination problem, check page.hasNext() before put "next" to map
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", courses);
        map.put("next", "/api/course/%d/".formatted(page+1));
        if(page>1)
            map.put("prev", "/api/course/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

    @PostMapping("/course")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto, Authentication authentication) {
        Course course = modelMapper.map(courseDto, Course.class);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Instructor instructor = instructorService.getInstructorById(principal.getId());
        if(Boolean.FALSE.equals(instructorService.isValidated(instructor))) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Boolean courseIsCreated = courseService.createCourse(course, instructor.getId());

        if(Boolean.FALSE.equals(courseIsCreated)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HashMap<String, Long> map = new HashMap<>();
        map.put("course_id",course.getId());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/mycourse/{page}")
    public ResponseEntity<?> getMyCourse(@PathVariable("page") int page, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        if(page<1 || user_id<1) return ResponseEntity.badRequest().build();

        Instructor instructor = instructorService.getInstructorById(user_id);
        if(Boolean.FALSE.equals(instructorService.isValidated(instructor))) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Page<Course> coursePage = courseService.getCourseWithPagination(page, user_id);
        HashMap<String, Object> map = new HashMap<>();
        List<InstructorCourseResponse> courses = courseService.convertToListOfInstructorCourseResponse(coursePage);
        map.put("data", courses);
        if(coursePage.hasNext()) {
            map.put("next", "/api/mycourse/%d/".formatted(page+1));
        } else map.put("next","");

        if(coursePage.hasPrevious())
            map.put("prev", "/api/mycourse/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

    @PutMapping("/course-order/{course_id}")
    public ResponseEntity<?> setCourseOrder(@PathVariable("course_id") long course_id, @RequestBody Map<String,List<String>> request, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();

        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Instructor instructor = instructorService.getInstructorById(user_id);
        Boolean isEligible = courseService.eligibilityCheck(course, instructor);
        if(Boolean.FALSE.equals(isEligible)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        courseService.setCourseOrder(course, request.get("order"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/course-order/{course_id}")
    public ResponseEntity<?> getCourseOrder(@PathVariable("course_id") long course_id) {
        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HashMap<String, List<String>> map = new HashMap<>();
        map.put("order", course.getCourseOrder());
        return ResponseEntity.ok(map);
    }

}
