package com.satset.mooc.service;

import com.satset.mooc.model.*;

public interface InstructorService {

    Instructor registerInstructor(String name, String gender, String image, String email, String password);

    Boolean instructorExist(String email);

    Instructor getInstructorById(Long user_id);

    Instructor getInstructorByEmail(String email);

    Boolean setInstructorStatus(Instructor instructor, String status);

    void delete(Instructor instructor);

    void createDashboard(Instructor instructor);

    InstructorDashboard getInstructorDashboardById(long id);

    void updateDashboard(Instructor instructor, String courseOldStatus, String courseNewStatus);

    Boolean addPendingCourseToDashboard(Instructor instructor);

    void save(Instructor instructor);

    void addAndSaveCourse(Instructor instructor, Course course);

    Boolean quizEligibilityCheck(Instructor instructor, Quiz quiz);

    Boolean lectureEligibilityViaCourseCheck(Instructor instructor, Course course);

    Boolean lectureEligibilityCheck(Instructor instructor, Lecture oldLecture);

    Boolean quizEligibilityViaCourseCheck(Instructor instructor, Course course);

    Boolean questionEligibilityCheck(Instructor instructor, Question question);

    Boolean isValidated(Instructor instructor);

    long countAll();
}
