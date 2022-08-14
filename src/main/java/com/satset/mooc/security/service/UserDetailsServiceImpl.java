package com.satset.mooc.security.service;

import com.satset.mooc.model.Student;
import com.satset.mooc.repository.InstructorRepository;
import com.satset.mooc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));

        return com.satset.mooc.security.service.UserDetailsImpl.build(student);
    }

    public UserDetails loadUserByUsername(String email, String role) throws UsernameNotFoundException {
        if (role == "student"){
            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));

            return com.satset.mooc.security.service.UserDetailsImpl.build(student);
        }else{
            Student student = instructorRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));

            return com.satset.mooc.security.service.UserDetailsImpl.build(student);
        }

    }
}
