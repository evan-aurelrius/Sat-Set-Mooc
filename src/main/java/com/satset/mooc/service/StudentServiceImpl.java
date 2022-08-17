package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import com.satset.mooc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentQuizService studentQuizService;

    @Override
    public Student registerStudent(String name, String gender, String image, String email, String password) {
        return studentRepository.save(new Student(name, gender, image, email, password));
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(long user_Id) {
        return studentRepository.findById(user_Id).orElse(null);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Boolean studentExist(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    @Override
    public void addCourse(Student student, Course course) {
        student.addEnrolledClass(course);
        studentRepository.save(student);
    }

    @Override
    public Boolean quizEligibilityCheck(Student student, Quiz quiz) {
        if(Boolean.FALSE.equals(student.getEnrolledClass().contains(quiz.getCourse()))) return false;
        return !Boolean.FALSE.equals(studentQuizService.quizAvailable(student, quiz));
    }

    @Override
    public long countAll() {
        return studentRepository.count();
    }
}
