package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Lecture;

import java.util.List;

public interface CourseService {


    List<Course> getCourse();

    Course getCourseById(Long course_id);

    Boolean createCourse(Course course, Long instructor_id);

    void setCourseStatus(Course course, String status);

    void deleteCourse(Course course);

    void addLecture(Course course, Lecture lecture);

    void save(Course course);

    void deleteLecture(Course course, Lecture lecture);

}
