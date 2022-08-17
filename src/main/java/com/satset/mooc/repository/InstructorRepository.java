package com.satset.mooc.repository;

import com.satset.mooc.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findByEmail(String email);

    @Query(nativeQuery = false, value = "select i from Instructor as i where i.verified_status='Pending'")
    Page<Instructor> findAllByVerified_status(Pageable pageable);

}
