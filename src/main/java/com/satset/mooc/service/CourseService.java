package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;

public interface CourseService {


    Iterable<Course> getCourse(Pageable pageable);

    Course getCourseById(Long course_id);

    Boolean createCourse(Course course, Long instructor_id);

    void setCourseStatus(Course course, String status);

    void deleteCourse(Course course);

    void addLecture(Course course, Lecture lecture);

    void addQuiz(Course course, Quiz quiz);

    void save(Course course);

    void deleteLecture(Course course, Lecture lecture);

    void enroll(Long course_id, Long student_id);
}
