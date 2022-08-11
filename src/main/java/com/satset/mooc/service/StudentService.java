package com.satset.mooc.service;

import com.satset.mooc.model.Student;

public interface StudentService {

    Student registerStudent(String name, String email, String password);
}
