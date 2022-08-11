package com.satset.mooc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("isAuthenticated()")
public class CourseController {

    @GetMapping("/course")
    public ResponseEntity<String> authenticateAccount() {
        return ResponseEntity.ok("Coba");
   }
}
