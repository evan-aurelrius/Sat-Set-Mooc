package com.satset.mooc.controller;

import com.satset.mooc.model.*;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.CourseService;
import com.satset.mooc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    private ModelMapper modelMapper= MapperUtil.getInstance();

    Logger logger = LoggerFactory.getLogger(AdminController.class);


    @PostMapping("/course")
    public ResponseEntity<String> verifyCourse(@RequestBody CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        Boolean courseIsCreated = courseService.createCourse(course, courseDto.getUser_id());

        if(Boolean.FALSE.equals(courseIsCreated)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
