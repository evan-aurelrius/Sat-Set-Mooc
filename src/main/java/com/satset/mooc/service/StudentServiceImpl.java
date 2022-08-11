package com.satset.mooc.service;

import com.satset.mooc.model.Student;
import com.satset.mooc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student registerStudent(String name, String email, String password) {
        return studentRepository.save(new Student(name, email, password));
    }
}
