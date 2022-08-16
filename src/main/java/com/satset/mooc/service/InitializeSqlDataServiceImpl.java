package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Student;
import com.satset.mooc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitializeSqlDataServiceImpl implements InitializeSqlDataService{

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    QuizRepository quizRepository;


    @Override
    public void quickInitialize() {
        if(adminRepository.count()==0) {
            initializeAdmin();
            initializeInstructor();
            initializeStudent();
        }
    }

    private void initializeAdmin() {
        Admin admin1 = new Admin("Administrator","admin1@localhost",passwordEncoder.encode("admin123"));
        adminRepository.save(admin1);
    }

    private void initializeInstructor() {
        Instructor instructor1 = new Instructor("Jack","m","profile1.jpeg","jacktheripper@gmail.com", passwordEncoder.encode("jack123"));
        instructorRepository.save(instructor1);
    }

    private void initializeStudent() {
        Student student1 = new Student("Timmy","m","profile2.jpg","timmyadventure@gmail.com",passwordEncoder.encode("timmy123"));
        studentRepository.save(student1);
    }
}
