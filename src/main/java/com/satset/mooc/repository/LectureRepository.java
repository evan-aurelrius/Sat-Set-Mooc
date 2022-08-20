package com.satset.mooc.repository;

import com.satset.mooc.model.Course;
import com.satset.mooc.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query(nativeQuery = true, value = "select * from lecture join student_lecture_status on lecture.id = student_lecture_status.lecture_id where student_id = ?1 ")
    List<Map<String, Object>> findAllCompletedLecture(long student_id);
}
