package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.response.CourseResponse;
import com.satset.mooc.model.response.InstructorCourseResponse;
import com.satset.mooc.model.response.StudentCourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CourseService {


    Iterable<Course> getCourse(Pageable pageable);

    Course getCourseById(Long course_id);

    Boolean createCourse(Course course, Long instructor_id);

    Boolean setCourseStatus(Course course, String status);

    void deleteCourse(Course course);

    void addLecture(Course course, Lecture lecture);

    void addQuiz(Course course, Quiz quiz);

    void save(Course course);

    void deleteLecture(Course course, Lecture lecture);

    void enroll(Long course_id, Long student_id);

    Page<Course> getCourseWithPagination(int page, long user_id);

    List<StudentCourseResponse> getStudentCourseWithPagination(int page, long user_id);

    List<CourseResponse> getAllCourseWithPagination(int page);

    List<InstructorCourseResponse> convertToList(Page<Course> coursePage);

    long countAll();

    List<Map<String, Object>> getTop5Course();
}
