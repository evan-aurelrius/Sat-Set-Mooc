package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LectureServiceImpl implements LectureService{

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public Lecture buildLecturesFromCourseRequest(Map<String, Object> request) {
        var title = request.get("title").toString();
        var link = request.get("description").toString();
        return save(new Lecture(title, link));
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

}
