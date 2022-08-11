package com.satset.mooc.service;

import com.satset.mooc.model.Course;

public interface CourseService {

    Course getCourseById(Long course_id);

    void setCourseStatus(Course course, String status);

}
