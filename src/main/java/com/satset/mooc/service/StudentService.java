package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;

public interface StudentService {

    Student registerStudent(String name, String gender, String image, String email, String password);

    Boolean studentExist(String email);

    void save(Student student);

    Student getStudentById(long user_Id);

    Student getStudentByEmail(String email);

    void addCourse(Student student, Course course);

    void addLecture(Student student, Lecture lecture);

    Boolean quizEligibilityCheck(Student student, Quiz quiz);

    long countAll();
}
