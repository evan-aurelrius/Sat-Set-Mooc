package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
