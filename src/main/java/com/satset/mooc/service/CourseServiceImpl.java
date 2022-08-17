package com.satset.mooc.service;

import com.satset.mooc.model.*;
import com.satset.mooc.model.response.CourseResponse;
import com.satset.mooc.model.response.InstructorCourseResponse;
import com.satset.mooc.model.response.StudentCourseResponse;
import com.satset.mooc.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Boolean setCourseStatus(Course course, String status) {
        if(Boolean.FALSE.equals(course.getStatus().equalsIgnoreCase("Approved"))) {
            instructorService.updateDashboard(course.getInstructor(), course.getStatus(), status);
            course.setStatus(status);
            courseRepository.save(course);
            return true;
        }
        return false;
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
        if(course!=null)
            course.addStudents(student);
        save(course);
    }

    @Override
    public Page<Course> getCourseWithPagination(int page, long user_id) {
        if(instructorService.getInstructorById(user_id)==null) return null;
        return courseRepository.findAll(PageRequest.of(page-1,10));
    }

    @Override
    public List<StudentCourseResponse> getStudentCourseWithPagination(int page, long user_id) {
        if(studentService.getStudentById(user_id)==null) return null;
        List<Course> rawLst = courseRepository.findAll(PageRequest.of(page-1,10)).toList();
        List<StudentCourseResponse> responseList = new ArrayList<>();
        for(Course c:rawLst) {
            responseList.add(new StudentCourseResponse(c.getId(), c.getTitle(), c.getImage(), c.getInstructor().getName(), (c.getLectures().size()+c.getQuizzes().size()), 0));
        }
        return responseList;
    }

    @Override
    public List<CourseResponse> getAllCourseWithPagination(int page) {
        List<Course> rawLst = courseRepository.findAll(PageRequest.of(page-1,10)).toList();
        List<CourseResponse> responseList = new ArrayList<>();
        for(Course c:rawLst) {
            responseList.add(new CourseResponse(c.getId(), c.getTitle(), c.getImage(), c.getStudents().size(), c.getInstructor().getName()));
        }
        return responseList;
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

    @Override
    public long countAll() {
        return courseRepository.count();
    }

    @Override
    public List<Map<String, Object>> getTop5Course() {
        return courseRepository.findTop5Course();
    }
}
