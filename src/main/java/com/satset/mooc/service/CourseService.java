package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.response.InstructorCourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    Page<Course> getCourseWithPagination(int page, long user_id);

    List<InstructorCourseResponse> convertToList(Page<Course> coursePage);
}
