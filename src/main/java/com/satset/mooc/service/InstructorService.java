package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;

public interface InstructorService {

    Instructor getInstructorById(Long user_id);

    void setInstuctorStatus(Instructor instructor, String status);

    void save(Instructor instructor);

    void addAndSaveCourse(Instructor instructor, Course course);

}
