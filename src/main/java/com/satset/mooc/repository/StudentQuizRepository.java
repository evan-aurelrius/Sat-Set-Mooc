package com.satset.mooc.repository;

import com.satset.mooc.model.StudentQuiz;
import com.satset.mooc.model.StudentQuizKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentQuizRepository extends JpaRepository<StudentQuiz, StudentQuizKey> {
}
