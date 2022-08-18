package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.dto.LectureDto;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.service.LectureService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class LectureController {

    @Autowired
    CourseService courseService;
    @Autowired
    LectureService lectureService;
    @Autowired
    InstructorService instructorService;

    final private ModelMapper modelMapper= MapperUtil.getInstance();

    @PostMapping("/api/course/{course_id}/lecture")
    public ResponseEntity<?> addLecture(@PathVariable("course_id") long course_id, @RequestBody LectureDto lectureDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Course course = courseService.getCourseById(course_id);
        if(Boolean.FALSE.equals(instructorService.lectureEligibilityViaCourseCheck(instructor, course))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Lecture lecture = modelMapper.map(lectureDto, Lecture.class);
        courseService.addLecture(course, lecture);
        HashMap<String, Long> map = new HashMap<>();
        map.put("lecture_id",lecture.getId());
        return ResponseEntity.ok(map);
    }

    @PutMapping("/api/lecture/{lecture_id}")
    public ResponseEntity<String> modifyLecture(@PathVariable("lecture_id") long lecture_id, @RequestBody LectureDto lectureDto, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Lecture oldLecture = lectureService.getLectureById(lecture_id);
        if(Boolean.FALSE.equals(instructorService.lectureEligibilityCheck(instructor, oldLecture))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Lecture lecture = modelMapper.map(lectureDto, Lecture.class);
        lectureService.modify(oldLecture, lecture);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/lecture/{lecture_id}")
    public ResponseEntity<String> deleteLecture(@PathVariable("lecture_id") long lecture_id, Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();
        Instructor instructor = instructorService.getInstructorById(user_id);

        Lecture lecture = lectureService.getLectureById(lecture_id);
        if(Boolean.FALSE.equals(instructorService.lectureEligibilityCheck(instructor, lecture))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        lectureService.deleteLecture(lecture);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
