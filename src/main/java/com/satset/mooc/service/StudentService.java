package com.satset.mooc.service;

import com.satset.mooc.model.Student;

public interface StudentService {

    Student registerStudent(String name, String gender, String image, String email, String password);

    void save(Student student);

}
