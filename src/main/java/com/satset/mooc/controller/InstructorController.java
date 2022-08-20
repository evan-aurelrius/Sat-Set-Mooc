package com.satset.mooc.controller;

import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.InstructorDashboard;
import com.satset.mooc.model.response.InstructorDashboardResponse;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.util.ModelMapperInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@PreAuthorize("hasAuthority('instructor')")
@RestController
@RequestMapping("/api")
public class InstructorController {

    @Autowired
    InstructorService instructorService;

    private final ModelMapper modelMapper = ModelMapperInstance.getInstance();

    @GetMapping("/instructor-dashboard")
    public ResponseEntity<HashMap<String, Object>> getDashboard(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        long user_id = principal.getId();

        Instructor instructor = instructorService.getInstructorById(user_id);
        if(Boolean.FALSE.equals(instructorService.isValidated(instructor))) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        InstructorDashboard instructorDashboard = instructorService.getInstructorDashboardById(user_id);

        HashMap<String, Object> map = new HashMap<>();
        InstructorDashboardResponse instructorDashboardResponse = modelMapper.map(instructorDashboard, InstructorDashboardResponse.class);
        map.put("data", instructorDashboardResponse);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
}
