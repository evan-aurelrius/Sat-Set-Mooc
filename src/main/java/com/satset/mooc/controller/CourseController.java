package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.dto.CourseDto;
import com.satset.mooc.model.response.CourseResponse;
import com.satset.mooc.model.response.InstructorCourseResponse;
import com.satset.mooc.model.response.StudentCourseResponse;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    private ModelMapper modelMapper= MapperUtil.getInstance();

    @GetMapping("/courses/{page}")
    public ResponseEntity<?> getCourse(@PathVariable("page") int page) {
        if(page<1) return ResponseEntity.badRequest().build();
        List<CourseResponse> courses = courseService.getAllCourseWithPagination(page);

        HashMap<String, Object> map = new HashMap<>();
        map.put("data", courses);
        map.put("next", "/api/mycourse/%d/".formatted(page+1));
        if(page>1)
            map.put("prev", "/api/mycourse/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

    @PostMapping("/course")
    public ResponseEntity<String> createCourse(@RequestBody CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        Boolean courseIsCreated = courseService.createCourse(course, courseDto.getUser_id());

        if(Boolean.FALSE.equals(courseIsCreated)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/mycourse/{page}/{user_id}")
    public ResponseEntity<?> getMyCourse(@PathVariable("page") int page, @PathVariable("user_id") long user_id) {
        if(page<1 || user_id<1) return ResponseEntity.badRequest().build();
        List<InstructorCourseResponse> courses = courseService.getCourseWithPagination(page, user_id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("data", courses);
        map.put("next", "/api/mycourse/%d/".formatted(page+1));
        if(page>1)
            map.put("prev", "/api/mycourse/%d/".formatted(page-1));
        else
            map.put("prev","");

        return ResponseEntity.ok(map);
    }

}
