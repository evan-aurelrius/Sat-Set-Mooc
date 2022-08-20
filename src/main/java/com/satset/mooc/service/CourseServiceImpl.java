package com.satset.mooc.service;

import com.satset.mooc.model.*;
import com.satset.mooc.model.response.*;
import com.satset.mooc.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    @Autowired
    MailService mailService;
    @Autowired
    StudentLectureService studentLectureService;
    @Autowired
    StudentQuizService studentQuizService;

    static final String APPROVED = "Approved";

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
        mailService.sendMailToAllAdmin("Course Verification", "Hello, \n\nNew Course with the name "+course.getTitle()+" from Instructor "+instructor.getName()+" waiting for verification \n\nThanks.");

        return true;
    }

    @Override
    public Boolean setCourseStatus(Course course, String status) {
        if(Boolean.FALSE.equals(course.getStatus().equalsIgnoreCase(APPROVED))) {
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
    public Page<Course> getAllCourseWithPagination(int page) {
        return courseRepository.findAllByStatus(APPROVED,PageRequest.of(page-1,10));
    }

    @Override
    public List<CourseResponse> convertToListOfCourseResponse(Page<Course> coursePage) {
        List<Course> rawLst = coursePage.toList();
        List<CourseResponse> responseList = new ArrayList<>();
        for(Course c:rawLst) {
            responseList.add(new CourseResponse(c.getId(), c.getTitle(), c.getImage(), c.getStudents().size(), c.getInstructor().getName()));
        }
        return responseList;
    }

    @Override
    public Page<Map<String, Object>> getStudentCourseWithPagination(int page, long user_id) {
        if(studentService.getStudentById(user_id)==null) return null;
        return courseRepository.findAllStudentCourseWithPagination(user_id, PageRequest.of(page-1,10));
    }

    @Override
    public List<StudentCourseResponse> convertToListOfStudentCourseResponse(Page<Map<String, Object>> studentCoursePage) {
        List<Map<String, Object>> rawLst = studentCoursePage.toList();
        List<StudentCourseResponse> responseList = new ArrayList<>();
        for(Map<String, Object> c:rawLst) {
            responseList.add(new StudentCourseResponse(((BigInteger)c.get("id")).longValue(), c.get("title").toString(), c.get("image").toString(), c.get("instructor_name").toString(), ((BigInteger)c.get("total_content")).longValue(), ((BigInteger)c.get("completed_content")).longValue()));
        }
        return responseList;
    }

    @Override
    public Page<Course> getCourseWithPagination(int page, long user_id) {
        if(instructorService.getInstructorById(user_id)==null) return null;
        return courseRepository.findAll(PageRequest.of(page-1,10));
    }

    @Override
    public List<InstructorCourseResponse> convertToListOfInstructorCourseResponse(Page<Course> coursePage) {
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

    @Override
    public Page<Course> getCourseProposalPage(int page) {
        Pageable pageable = PageRequest.of(page-1,10);
        return courseRepository.findAllByVerified_status(pageable);
    }

    @Override
    public List<CourseProposalResponse> convertToListOfCourseProposalResponse(Page<Course> coursePage) {
        List<Course> rawLst = coursePage.toList();
        List<CourseProposalResponse> lst = new ArrayList<>();
        for (Course c : rawLst) {
            lst.add(new CourseProposalResponse(c.getId(), c.getTitle(), c.getImage(), c.getInstructor().getName()));
        }
        return lst;
    }

    @Override
    public long countEnrolledCourse(long student_Id) {
        return courseRepository.findAllByStudent(student_Id).stream().count();
    }

    @Override
    public long countCompletedCourse(long student_Id) {
        return courseRepository.findAllCompletedCourse(student_Id).stream().count();
    }

    @Override
    public Boolean isValidated(Course course) {
        return course.getStatus().equalsIgnoreCase(APPROVED);
    }

    @Override
    public Boolean eligibilityCheck(Course course, Instructor instructor) {
        return !Boolean.FALSE.equals(instructor.getCourseOwned().contains(course));
    }

    @Override
    public Boolean enrolledCheck(Course course, Student student) {
        return !Boolean.FALSE.equals(student.getEnrolledClass().contains(course));
    }

    @Override
    public void fillCourseDetailResponseData(CourseDetailResponse courseDetailResponse, Object user) {
        if(user instanceof Student student) {
            long user_id = student.getId();
            setLectureStatus(courseDetailResponse.getLectures(), user_id);
            setQuizStatus(courseDetailResponse.getQuizzes(), user_id);
        }
    }

    public void setLectureStatus(List<CourseDetailLectureResponse> cdlr, long user_id) {
        StudentLecture studentLecture;
        for(CourseDetailLectureResponse c:cdlr) {
            studentLecture = studentLectureService.getStudentLectureByStudentIdAndLectureId(user_id, c.getId());
            if(studentLecture!=null) c.setIs_completed(true);
        }
    }

    public void setQuizStatus(List<CourseDetailQuizResponse> cdqr, long user_id) {
        StudentQuiz studentQuiz;
        for(CourseDetailQuizResponse c:cdqr) {
            studentQuiz = studentQuizService.getStudentQuizByStudentIdAndQuizId(user_id, c.getId());
            if(studentQuiz!=null) c.setScore(studentQuiz.getScore());
        }
    }

    @Override
    public void setCourseOrder(Course course, List<String> order) {
        course.setCourseOrder(order);
        save(course);
    }

    @Override
    public Boolean eligibilityCheck(Course course, long user_id) {
        Instructor instructor = instructorService.getInstructorById(user_id);
        if(instructor==null) {
            Student student = studentService.getStudentById(user_id);
            if(student==null) return false;
            return student.getEnrolledClass().contains(course);
        } else {
            return instructor.getCourseOwned().contains(course);
        }
    }

}
