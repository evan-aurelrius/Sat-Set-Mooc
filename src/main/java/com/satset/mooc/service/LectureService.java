package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;

import java.util.List;

public interface LectureService {

    Lecture getLectureById(long id);

    Lecture save(Lecture lecture);

    void addAndSaveAllLectures(List<Lecture> lectures, Course course);

    void setAndSaveLecture(Lecture lectures, Course course);
    void modify(long id, Lecture lecture);

    void deleteLecture(long id);

}
