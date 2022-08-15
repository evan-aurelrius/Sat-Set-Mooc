package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.response.InstructorCourseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    Course getCourseById(Long course_id);

    Boolean createCourse(Course course, Long instructor_id);

    void setCourseStatus(Course course, String status);

    void deleteCourse(Course course);

    void addLecture(Course course, Lecture lecture);

    void addQuiz(Course course, Quiz quiz);

    void save(Course course);

    void deleteLecture(Course course, Lecture lecture);

    List<InstructorCourseResponse> getCourseWithPagination(int page, long user_id);

}
