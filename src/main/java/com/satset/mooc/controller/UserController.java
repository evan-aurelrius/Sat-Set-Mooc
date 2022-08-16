package com.satset.mooc.controller;

import com.satset.mooc.model.*;
import com.satset.mooc.security.jwt.JwtUtils;
import com.satset.mooc.security.service.UserDetailsImpl;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.service.StudentService;
import com.satset.mooc.util.MapperUtil;
import com.satset.mooc.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StudentService studentService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserUtil userUtil;

    private ModelMapper modelMapper= MapperUtil.getInstance();

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userUtil.getJwt(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody SignupRequest request){
//        Register
        if (request.getRole().equals("student")){
            Boolean studentExist = studentService.studentExist(request.getEmail());
            if(studentExist) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Student student = studentService.registerStudent(request.getName(), request.getGender(), request.getImage(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        }else if (request.getRole().equals("instructor")){
            Boolean instructorExist = instructorService.instructorExist(request.getEmail());
            if(instructorExist) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Instructor instructor = instructorService.registerInstructor(request.getName(), request.getGender(), request.getImage(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        }

//        Login
        return ResponseEntity.ok(userUtil.getJwt(request));
    }
}
