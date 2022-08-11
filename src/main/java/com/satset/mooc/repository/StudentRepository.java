package com.satset.mooc.repository;

import com.satset.mooc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository  extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
}
