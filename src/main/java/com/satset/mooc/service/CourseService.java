package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;

public interface CourseService {

    Course getCourseById(Long course_id);

    Boolean createCourse(Course course, Long instructor_id);

    void setCourseStatus(Course course, String status);

    void deleteCourse(Course course);

}
