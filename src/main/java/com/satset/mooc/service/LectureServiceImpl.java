package com.satset.mooc.service;

import com.satset.mooc.controller.AdminController;
import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.repository.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void modify(long id, Lecture lecture) {
        var oldLecture = getLectureById(id);
        if(oldLecture!=null) {
            oldLecture.setTitle(lecture.getTitle());
            oldLecture.setLink(lecture.getLink());
            lectureRepository.save(oldLecture);
        }
    }

    @Override
    public void deleteLecture(long id) {
        Logger logger = LoggerFactory.getLogger(AdminController.class);
        Lecture lecture = getLectureById(id);
        if(lecture!=null){
            Course course = lecture.getCourse();
            courseService.deleteLecture(course, lecture);
            lectureRepository.deleteById(id);
        }
    }

}
