package com.satset.mooc.repository;

import com.satset.mooc.model.StudentQuiz;
import com.satset.mooc.model.StudentQuizKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentQuizRepository extends JpaRepository<StudentQuiz, StudentQuizKey> {

    @Query(value = "SELECT * from student_quiz where student_id = ?1 AND quiz_id = ?2", nativeQuery = true)
    Optional<StudentQuiz> findByStudent_idAndQuiz_id(long student_id, long quiz_id);

}
