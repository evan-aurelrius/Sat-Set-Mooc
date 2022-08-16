package com.satset.mooc.service;

import com.satset.mooc.controller.AdminController;
import com.satset.mooc.model.*;
import com.satset.mooc.model.response.InstructorCourseResponse;
import com.satset.mooc.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    InstructorService instructorService;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LectureService lectureService;
    @Autowired
    QuizService quizService;
    @Autowired
    StudentService studentService;

    @Override
    public Iterable<Course> getCourse(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Override
    public Course getCourseById(Long course_id) {
        return courseRepository.findById(course_id).orElse(null);
    }

    @Override
    public Boolean createCourse(Course course, Long instructor_id) {
        Instructor instructor = instructorService.getInstructorById(instructor_id);
        if(instructor == null) return false;
        course.setInstructor(instructor);

        instructorService.addAndSaveCourse(instructor, course);
        lectureService.addAndSaveAllLectures(course.getLectures(), course);
        quizService.saveQuizzesAndQuestions(course.getQuizzes(), course);
        courseRepository.save(course);
        return true;
    }

    @Override
    public void setCourseStatus(Course course, String status) {
        course.setStatus(status);
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public void addLecture(Course course, Lecture lecture) {
        lectureService.setAndSaveLecture(lecture, course);
        course.addLecture(lecture);
        save(course);
    }

    @Override
    public void addQuiz(Course course, Quiz quiz) {
        quizService.setAndSaveQuiz(quiz, course);
        course.addQuiz(quiz);
        save(course);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteLecture(Course course, Lecture lecture) {
        course.getLectures().remove(lecture);
        courseRepository.save(course);
    }

    @Override
    public void enroll(Long course_id, Long student_id) {
        Student student = studentService.getStudentById(student_id);
        Course course = courseRepository.findById(course_id).orElse(null);
        studentService.addCourse(student, course);
        course.addStudents(student);
        save(course);
    }

    @Override
    public Page<Course> getCourseWithPagination(int page, long user_id) {
        if(instructorService.getInstructorById(user_id)==null) return null;
        return courseRepository.findAll(PageRequest.of(page-1,10));

    }

    @Override
    public List<InstructorCourseResponse> convertToList(Page<Course> coursePage) {
        List<Course> rawLst = coursePage.toList();
        List<InstructorCourseResponse> responseList = new ArrayList<>();
        for(Course c:rawLst) {
            responseList.add(new InstructorCourseResponse(c.getId(), c.getTitle(), c.getImage(), c.getStudents().size(), c.getStatus()));
        }
        return responseList;
    }
}
