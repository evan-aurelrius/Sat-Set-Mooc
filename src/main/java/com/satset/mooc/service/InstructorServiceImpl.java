package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import com.satset.mooc.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Instructor registerInstructor(String name, String gender, String image, String email, String password) {
        return instructorRepository.save(new Instructor(name, gender, image, email, password));
    }

    @Override
    public Boolean instructorExist(String email) {
        return instructorRepository.findByEmail(email).isPresent();
    }

    @Override
    public Instructor getInstructorById(Long user_id) {
        return instructorRepository.findById(user_id).orElse(null);
    }

    @Override
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void setInstuctorStatus(Instructor instructor, String status) {
        instructor.setVerified_status(status);
        save(instructor);
    }

    @Override
    public void save(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    @Override
    public void addAndSaveCourse(Instructor instructor, Course course) {
        instructor.addCourseOwned(course);
        save(instructor);
    }

    @Override
    public Boolean quizEligibilityCheck(Instructor instructor, Quiz quiz) {
        if(Boolean.FALSE.equals(instructor.getCourseOwned().contains(quiz.getCourse()))) return false;
        return true;
    }
}
