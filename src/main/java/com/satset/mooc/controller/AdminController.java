package com.satset.mooc.controller;

import com.satset.mooc.model.AdminDto;
import com.satset.mooc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    private String msg = "";

    @PostMapping("/admin-login")
    public ResponseEntity<String> authenticateAccount(@ModelAttribute AdminDto adminDto) {
        boolean isExist = adminService.authenticate(adminDto.getEmail(), adminDto.getPassword());
        if(isExist) msg = "User is valid";
        else msg = "Invalid email or password!";

        return ResponseEntity.ok(msg);
    }

}
