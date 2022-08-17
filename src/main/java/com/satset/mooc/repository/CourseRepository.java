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

    @Query(value = "SELECT course.id,\n" +
            "course.title, \n" +
            "course.image, \n" +
            "instructor.name instructor_name,\n" +
            "count(lecture.id+quiz.id) total_content,\n" +
            "count(student_lecture.student_id+student_quiz.student_id) completed_content\n" +
            "from course \n" +
            "JOIN student_enroll_course on course.id = student_enroll_course.course_id\n" +
            "JOIN lecture on course.id = lecture.course_id\n" +
            "JOIN quiz on course.id = quiz.course_id\n" +
            "LEFT JOIN student_lecture on course.id = student_lecture.course_id\n" +
            "LEFT JOIN student_quiz on quiz.id = student_quiz.quiz_id\n" +
            "JOIN instructor on course.instructor_id = course.id\n" +
            "WHERE student_enroll_course.student_id = ?1 GROUP BY course.id, instructor.id", nativeQuery = true)
    Page<Map<String, Object>> findAllStudentCourseWithPagination(long student_id, Pageable pageable);
}
