package com.satset.mooc.service;

import com.satset.mooc.model.StudentLecture;
import com.satset.mooc.model.StudentLectureKey;
import com.satset.mooc.repository.StudentLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentLectureServiceImpl implements StudentLectureService{

    @Autowired
    StudentLectureRepository studentLectureRepository;

    @Override
    public StudentLecture getStudentLectureByStudentIdAndLectureId(long student_id, long quiz_id) {
        return studentLectureRepository.findByStudent_idAndLecture_id(student_id, quiz_id).orElse(null);
    }

    @Override
    public void addProgress(long student_id, long lecture_id) {
        studentLectureRepository.save(new StudentLecture(new StudentLectureKey(student_id, lecture_id)));
    }
}
