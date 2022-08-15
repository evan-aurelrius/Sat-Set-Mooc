package com.satset.mooc.controller;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.dto.LectureDto;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.service.LectureService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LectureController {

    @Autowired
    CourseService courseService;
    @Autowired
    LectureService lectureService;

    private ModelMapper modelMapper= MapperUtil.getInstance();

    @PostMapping("/api/course/{course_id}/lecture")
    public ResponseEntity<String> addLecture(@PathVariable("course_id") long course_id, @RequestBody LectureDto lectureDto) {
        Course course = courseService.getCourseById(course_id);
        if(course==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Lecture lecture = modelMapper.map(lectureDto, Lecture.class);
        courseService.addLecture(course, lecture);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/lecture/{lecture_id}")
    public ResponseEntity<String> modifyLecture(@PathVariable("lecture_id") long lecture_id, @RequestBody LectureDto lectureDto) {
        Lecture lecture = modelMapper.map(lectureDto, Lecture.class);
        lectureService.modify(lecture_id, lecture);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/lecture/{lecture_id}")
    public ResponseEntity<String> addLecture(@PathVariable("lecture_id") long lecture_id) {
        lectureService.deleteLecture(lecture_id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
