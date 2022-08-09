package com.satset.mooc.controller;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.AdminDto;
import com.satset.mooc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAccount(@ModelAttribute AdminDto adminDto) {
        boolean isExist = adminService.authenticate(adminDto.getEmail(), adminDto.getPassword());
        if(isExist) return ResponseEntity.ok("Berhasil dan ketemu");
        else return ResponseEntity.ok("Berhasil tapi ga nemu");
  }

}
