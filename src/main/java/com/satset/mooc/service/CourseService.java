package com.satset.mooc.service;

import com.satset.mooc.model.*;
import com.satset.mooc.model.response.*;
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

    Page<Course> getAllCourseWithPagination(int page);

    List<CourseResponse> convertToListOfCourseResponse(Page<Course> coursePage);

    Page<Map<String, Object>> getStudentCourseWithPagination(int page, long user_id);

    List<StudentCourseResponse> convertToListOfStudentCourseResponse(Page<Map<String, Object>> coursePage);

    Page<Course> getCourseWithPagination(int page, long user_id);

    List<InstructorCourseResponse> convertToListOfInstructorCourseResponse(Page<Course> coursePage);

    long countAll();

    List<Map<String, Object>> getTop5Course();

    Page<Course> getCourseProposalPage(int page);

    List<CourseProposalResponse> convertToListOfCourseProposalResponse(Page<Course> coursePage);

    Boolean eligibilityCheck(Course course, Instructor instructor);

    Boolean enrolledCheck(Course course, Student student);

    void fillCourseDetailResponseData(CourseDetailResponse courseDetailResponse, Object user);

    void setLectureStatus(List<CourseDetailLectureResponse> cdlr, long user_id);

    void setQuizStatus(List<CourseDetailQuizResponse> cdqr, long user_id);

    void setCourseOrder(Course course, List<String> order);

    Boolean eligibilityCheck(Course course, long user_id);

    long countEnrolledCourse(long student_id);

    long countCompletedCourse(long student_Id);

    Boolean isValidated(Course course);



}
