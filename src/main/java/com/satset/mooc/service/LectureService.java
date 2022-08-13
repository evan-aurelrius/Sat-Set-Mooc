package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;

import java.util.List;
import java.util.Map;

public interface LectureService {

    Lecture buildLecturesFromCourseRequest(Map<String, Object> request);

    Lecture save(Lecture lecture);

    void addAndSaveAllLectures(List<Lecture> lectures, Course course);
}
