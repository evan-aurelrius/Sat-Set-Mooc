package com.satset.mooc.service;

import com.satset.mooc.model.StudentLecture;

public interface StudentLectureService {

    StudentLecture getStudentLectureByStudentIdAndLectureId(long student_id, long quiz_id);

    void addProgress(long student_id, long lecture_id);
}
