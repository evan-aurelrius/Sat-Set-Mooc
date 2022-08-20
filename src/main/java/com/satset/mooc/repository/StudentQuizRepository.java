package com.satset.mooc.repository;

import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.StudentQuiz;
import com.satset.mooc.model.StudentQuizKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import java.util.Optional;

@Repository
public interface StudentQuizRepository extends JpaRepository<StudentQuiz, StudentQuizKey> {

    @Query(value = "SELECT * from student_quiz where student_id = ?1 AND quiz_id = ?2", nativeQuery = true)
    Optional<StudentQuiz> findByStudent_idAndQuiz_id(long student_id, long quiz_id);

    @Query(nativeQuery = true, value = "SELECT * from student_quiz join quiz on quiz.id = student_quiz.quiz_id where score != -1 AND student_id = ?1")
    List<Map<String, Object>> findAllCompletedQuiz(long student_id);
}
