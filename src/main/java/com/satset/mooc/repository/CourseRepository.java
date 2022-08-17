package com.satset.mooc.repository;

import com.satset.mooc.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT C.title, SUB.students as students\n" +
            "FROM course C\n" +
            "JOIN\n" +
            "(\n" +
            "\tSELECT course_id, count(student_id) as students\n" +
            "\tFROM student_enroll_course\n" +
            "\tGROUP BY 1\n" +
            ") SUB \n" +
            "ON C.id=SUB.course_id\n" +
            "ORDER BY students DESC\n" +
            "LIMIT 5", nativeQuery = true)
    List<Map<String, Object>> findTop5Course();

}
