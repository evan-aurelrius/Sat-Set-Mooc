package com.satset.mooc.repository;

import com.satset.mooc.model.StudentLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentLectureRepository extends JpaRepository<StudentLecture, Long> {

    @Query(value = "select * from student_lecture_status AS s where s.student_id=?1 AND s.lecture_id=?2", nativeQuery = true)
    Optional<StudentLecture> findByStudent_idAndLecture_id(long student_id, long lecture_id);

}
