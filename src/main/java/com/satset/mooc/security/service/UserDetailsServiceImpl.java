package com.satset.mooc.security.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Student;
import com.satset.mooc.service.AdminService;
import com.satset.mooc.service.InstructorService;
import com.satset.mooc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    StudentService studentService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentService.getStudentByEmail(email);
        if (student != null) {
            return com.satset.mooc.security.service.UserDetailsImpl.build(student);
        }

        Instructor instructor = instructorService.getInstructorByEmail(email);
        if (instructor != null) {
            return com.satset.mooc.security.service.UserDetailsImpl.build(instructor);
        }

        Admin admin = adminService.getAdminByEmail(email);
        if (admin != null) {
            return com.satset.mooc.security.service.UserDetailsImpl.build(admin);
        }

        throw new UsernameNotFoundException("Email " + email + " not found");
    }

}
