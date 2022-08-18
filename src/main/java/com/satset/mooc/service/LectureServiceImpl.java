package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Student;
import com.satset.mooc.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService{

    @Autowired
    LectureRepository lectureRepository;
    @Autowired @Lazy
    CourseService courseService;
    @Autowired
    StudentService studentService;

    @Override
    public Lecture getLectureById(long id) {
        return lectureRepository.findById(id).orElse(null);
    }

    @Override
    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Override
    public void addAndSaveAllLectures(List<Lecture> lectures, Course course) {
        for(Lecture l:lectures) {
            l.setCourse(course);
        }
        lectureRepository.saveAll(lectures);
    }

    @Override
    public void setAndSaveLecture(Lecture lectures, Course course) {
        lectures.setCourse(course);
        save(lectures);
    }

    @Override
    public void modify(Lecture oldLecture, Lecture lecture) {
        if(oldLecture!=null) {
            oldLecture.setTitle(lecture.getTitle());
            oldLecture.setLink(lecture.getLink());
            lectureRepository.save(oldLecture);
        }
    }

    @Override
    public void deleteLecture(Lecture lecture) {
        if(lecture!=null){
            Course course = lecture.getCourse();
            courseService.deleteLecture(course, lecture);
            lectureRepository.delete(lecture);
        }
    }

    @Override
    public void addLectureProgress(long lecture_id, long student_id) {
        Student student = studentService.getStudentById(student_id);
        Lecture lecture = lectureRepository.findById(lecture_id).orElse(null);
        studentService.addLecture(student, lecture);
        if(lecture!=null)
            lecture.addStudents(student);
        save(lecture);
    }

    @Override
    public long countCompletedLecture(long student_Id) {
        return lectureRepository.findAllCompletedLecture(student_Id).stream().count();
    }

}
