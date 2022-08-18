package com.satset.mooc.repository;

import com.satset.mooc.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = """
            SELECT C.title, SUB.students as students
            FROM course C
            JOIN
            (
            \tSELECT course_id, count(student_id) as students
            \tFROM student_enroll_course
            \tGROUP BY 1
            ) SUB\s
            ON C.id=SUB.course_id
            ORDER BY students DESC
            LIMIT 5""", nativeQuery = true)
    List<Map<String, Object>> findTop5Course();

    @Query(value = """
            SELECT course.id,
            course.title,\s
            course.image,\s
            instructor.name instructor_name,
            count(lecture.id+quiz.id) total_content,
            count(student_lecture.student_id+student_quiz.student_id) completed_content
            from course\s
            JOIN student_enroll_course on course.id = student_enroll_course.course_id
            JOIN lecture on course.id = lecture.course_id
            JOIN quiz on course.id = quiz.course_id
            LEFT JOIN student_lecture on course.id = student_lecture.course_id
            LEFT JOIN student_quiz on quiz.id = student_quiz.quiz_id
            JOIN instructor on course.instructor_id = course.id
            WHERE student_enroll_course.student_id = ?1 GROUP BY course.id, instructor.id""", nativeQuery = true)
    Page<Map<String, Object>> findAllStudentCourseWithPagination(long student_id, Pageable pageable);
    
    @Query(value = "select c from Course as c where c.status='Pending'")
    Page<Course> findAllByVerified_status(Pageable pageable);

}
