package com.satset.mooc.service;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Student;

import java.util.List;

public interface LectureService {

    Lecture getLectureById(long id);

    Lecture save(Lecture lecture);

    void addAndSaveAllLectures(List<Lecture> lectures, Course course);

    void setAndSaveLecture(Lecture lectures, Course course);
    void modify(Lecture oldLecture, Lecture lecture);

    void deleteLecture(Lecture lecture);

    void addLectureProgress(Lecture lecture, Student student);
}
