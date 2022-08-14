package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;

import java.util.List;
import java.util.Map;

public interface LectureService {

    Lecture getLectureById(long id);

    Lecture save(Lecture lecture);

    void addAndSaveAllLectures(List<Lecture> lectures, Course course);

    void addAndSaveLecture(Lecture lectures, Course course);

    void deleteLecture(long id);

}
