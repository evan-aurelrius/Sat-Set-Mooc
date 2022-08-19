package com.satset.mooc.controller;

import com.satset.mooc.model.*;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.service.StudentService;
import com.satset.mooc.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    UserUtil userUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request){
        HashMap<String, Object> map = new HashMap<>();
        var userData = userUtil.getJwt(request);
        if (userData.getRole().equalsIgnoreCase("instructor")){
            Instructor instructor = instructorService.getInstructorById(userData.getId());
            if(Boolean.FALSE.equals(instructorService.isValidated(instructor))) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        map.put("data", userData);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("token", userData.getToken());

        return ResponseEntity.ok().headers(responseHeaders).body(map);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest request){
//        Register
        Boolean studentExist = studentService.studentExist(request.getEmail());
        Boolean instructorExist = instructorService.instructorExist(request.getEmail());
        if(studentExist || instructorExist) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (request.getRole().equals("student")){
            studentService.registerStudent(request.getName(), request.getGender(), request.getImage(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
            LoginRequest loginRequest = new LoginRequest(request.getEmail(), request.getPassword());

            HashMap<String, Object> map = new HashMap<>();
            var userData = userUtil.getJwt(loginRequest);
            map.put("data", userData);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("token", userData.getToken());

            return ResponseEntity.ok().headers(responseHeaders).body(map);
        }else if (request.getRole().equals("instructor")){
            Instructor instructor = instructorService.registerInstructor(request.getName(), request.getGender(), request.getImage(), request.getEmail(), passwordEncoder.encode(request.getPassword()));

            HashMap<String, Object> data = new HashMap<>();
            data.put("id", instructor.getId());
            data.put("name", instructor.getName());
            data.put("gender", instructor.getGender());
            data.put("image", instructor.getImage());
            data.put("email", instructor.getEmail());
            data.put("role", "instructor");
            data.put("created_at", instructor.getCreated_at());

            HashMap<String, Object> map = new HashMap<>();
            map.put("data", data);

            return ResponseEntity.ok(map);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
