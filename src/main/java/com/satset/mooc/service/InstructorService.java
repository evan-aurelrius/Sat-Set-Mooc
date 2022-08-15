package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;

public interface InstructorService {

    Instructor registerInstructor(String name, String gender, String image, String email, String password);

    Boolean instructorExist(String email);

    Instructor getInstructorById(Long user_id);

    Instructor getInstructorByEmail(String email);

    void setInstuctorStatus(Instructor instructor, String status);

    void save(Instructor instructor);

    void addAndSaveCourse(Instructor instructor, Course course);

}
